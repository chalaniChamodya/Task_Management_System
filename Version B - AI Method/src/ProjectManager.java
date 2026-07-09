import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls all projects, tasks and users in the system.
 * Adds file persistence and keyword search on top of Version A's
 * basic CRUD operations.
 */
public class ProjectManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Project> projects;
    private List<User> users;
    private int nextProjectId;
    private int nextTaskId;
    private int nextUserId;

    public ProjectManager() {
        projects = new ArrayList<>();
        users = new ArrayList<>();
        nextProjectId = 1;
        nextTaskId = 1;
        nextUserId = 1;
    }

    public User addUser(String name) throws TaskManagerException {
        User user = new User(nextUserId++, name);
        users.add(user);
        return user;
    }

    public Project createProject(String name) throws TaskManagerException {
        Project project = new Project(nextProjectId++, name);
        projects.add(project);
        return project;
    }

    public Task addTaskToProject(Project project, String title, Priority priority, String dueDate)
            throws TaskManagerException {
        Task task = new Task(nextTaskId++, title, priority, dueDate);
        project.addTask(task);
        return task;
    }

    public Project findProjectById(int id) throws TaskManagerException {
        for (Project p : projects) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new TaskManagerException("No project found with ID " + id + ".");
    }

    public User findUserById(int id) throws TaskManagerException {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        throw new TaskManagerException("No user found with ID " + id + ".");
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Task> filterTasksByStatus(Project project, Status status) {
        List<Task> result = new ArrayList<>();
        for (Task t : project.getTasks()) {
            if (t.getStatus() == status) {
                result.add(t);
            }
        }
        return result;
    }

    /** Searches task titles across a project for a keyword (case-insensitive). */
    public List<Task> searchTasks(Project project, String keyword) {
        List<Task> matches = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task t : project.getTasks()) {
            if (t.getTitle().toLowerCase().contains(lowerKeyword)) {
                matches.add(t);
            }
        }
        return matches;
    }

    /** Saves the entire manager state (projects, tasks, users) to a file. */
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(this);
        }
    }

    /** Loads a previously saved manager state from a file. */
    public static ProjectManager loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ProjectManager) in.readObject();
        }
    }
}
