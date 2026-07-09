import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * A single unit of work inside a project.
 * Implements Comparable so a list of tasks can be sorted by due date,
 * and Serializable so projects can be saved to / loaded from disk.
 */
public class Task implements Comparable<Task>, Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String title;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private User assignedUser;

    public Task(int id, String title, Priority priority, String dueDate) throws TaskManagerException {
        if (title == null || title.trim().isEmpty()) {
            throw new TaskManagerException("Task title cannot be empty.");
        }
        if (priority == null) {
            throw new TaskManagerException("Task priority cannot be null.");
        }
        this.id = id;
        this.title = title.trim();
        this.priority = priority;
        this.dueDate = parseDate(dueDate);
        this.status = Status.TODO;
    }

    private LocalDate parseDate(String dateStr) throws TaskManagerException {
        try {
            return LocalDate.parse(dateStr.trim());
        } catch (DateTimeParseException | NullPointerException e) {
            throw new TaskManagerException("Invalid due date '" + dateStr + "'. Use format YYYY-MM-DD.");
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) throws TaskManagerException {
        if (status == null) {
            throw new TaskManagerException("Status cannot be null.");
        }
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void assignUser(User user) {
        this.assignedUser = user;
    }

    /** A task is overdue if its due date has passed and it isn't marked DONE. */
    public boolean isOverdue() {
        return status != Status.DONE && dueDate.isBefore(LocalDate.now());
    }

    // Sort by due date, soonest first
    @Override
    public int compareTo(Task other) {
        return this.dueDate.compareTo(other.dueDate);
    }

    @Override
    public String toString() {
        String assignee = (assignedUser == null) ? "Unassigned" : assignedUser.getName();
        String overdueFlag = isOverdue() ? " (OVERDUE)" : "";
        return "[" + id + "] " + title + " | Priority: " + priority +
               " | Status: " + status + " | Due: " + dueDate +
               overdueFlag + " | Assigned to: " + assignee;
    }
}
