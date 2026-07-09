import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A project that groups multiple tasks together.
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String name;
    private final List<Task> tasks;

    public Project(int id, String name) throws TaskManagerException {
        if (name == null || name.trim().isEmpty()) {
            throw new TaskManagerException("Project name cannot be empty.");
        }
        this.id = id;
        this.name = name.trim();
        this.tasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task findTaskById(int taskId) throws TaskManagerException {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                return t;
            }
        }
        throw new TaskManagerException("No task found with ID " + taskId + " in this project.");
    }

    /** Returns tasks sorted by due date (soonest first). */
    public List<Task> getTasksSortedByDueDate() {
        List<Task> sorted = new ArrayList<>(tasks);
        Collections.sort(sorted);
        return sorted;
    }

    /** Returns tasks whose due date has passed and aren't done yet. */
    public List<Task> getOverdueTasks() {
        List<Task> overdue = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isOverdue()) {
                overdue.add(t);
            }
        }
        return overdue;
    }

    @Override
    public String toString() {
        return "Project: " + name + " (ID: " + id + ", Tasks: " + tasks.size() + ")";
    }
}
