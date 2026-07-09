import java.util.ArrayList;
import java.util.List;

// A project that groups multiple tasks together
public class Project {
    int id;
    String name;
    List<Task> tasks = new ArrayList<>();

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Task findTaskById(int taskId) {
        for (Task t : tasks) {
            if (t.id == taskId) {
                return t;
            }
        }
        return null;
    }

    public String toString() {
        return "Project: " + name + " (ID: " + id + ", Tasks: " + tasks.size() + ")";
    }
}
