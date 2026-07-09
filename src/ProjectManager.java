import java.util.ArrayList;
import java.util.List;

// Keeps track of all projects and users in the system
public class ProjectManager {
    List<Project> projects = new ArrayList<>();
    List<User> users = new ArrayList<>();
    int nextProjectId = 1;
    int nextTaskId = 1;
    int nextUserId = 1;

    public User addUser(String name) {
        User user = new User(nextUserId++, name);
        users.add(user);
        return user;
    }

    public Project createProject(String name) {
        Project project = new Project(nextProjectId++, name);
        projects.add(project);
        return project;
    }

    public Task addTaskToProject(Project project, String title, String priority, String dueDate) {
        Task task = new Task(nextTaskId++, title, priority, dueDate);
        project.tasks.add(task);
        return task;
    }

    public Project findProjectById(int id) {
        for (Project p : projects) {
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }

    public User findUserById(int id) {
        for (User u : users) {
            if (u.id == id) {
                return u;
            }
        }
        return null;
    }
}
