// A single unit of work inside a project
public class Task {
    int id;
    String title;
    String priority;  // "LOW", "MEDIUM", or "HIGH"
    String status;    // "TODO", "IN_PROGRESS", or "DONE"
    String dueDate;
    User assignedUser;

    public Task(int id, String title, String priority, String dueDate) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = "TODO"; // every new task starts as TODO
    }

    public String toString() {
        String assignee = (assignedUser == null) ? "Unassigned" : assignedUser.name;
        return "[" + id + "] " + title + " | Priority: " + priority +
               " | Status: " + status + " | Due: " + dueDate + " | Assigned to: " + assignee;
    }
}
