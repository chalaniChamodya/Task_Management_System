import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Console entry point. All user input is wrapped in try/catch so bad
 * input (letters where a number is expected, invalid enum names, bad
 * dates) shows a friendly message instead of crashing the program -
 * this is the main behavioural difference from Version A.
 */
public class Main {
    private static ProjectManager manager = new ProjectManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String SAVE_FILE = "taskmanager.dat";

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1: createUser(); break;
                    case 2: createProject(); break;
                    case 3: addTask(); break;
                    case 4: assignTask(); break;
                    case 5: updateTaskStatus(); break;
                    case 6: viewProject(); break;
                    case 7: searchTasks(); break;
                    case 8: viewOverdueTasks(); break;
                    case 9: saveData(); break;
                    case 10: loadData(); break;
                    case 0: running = false; break;
                    default: System.out.println("Invalid option, try again.");
                }
            } catch (TaskManagerException e) {
                // Application-level errors (bad ID, empty name, bad date) land here
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                // e.g. Priority.valueOf() or Status.valueOf() given an unknown word
                System.out.println("Error: invalid value entered - " + e.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n=== Task Manager (AI-Assisted Version) ===");
        System.out.println("1. Add User");
        System.out.println("2. Create Project");
        System.out.println("3. Add Task to Project");
        System.out.println("4. Assign Task to User");
        System.out.println("5. Update Task Status");
        System.out.println("6. View Project Details (sorted by due date)");
        System.out.println("7. Search Tasks by Keyword");
        System.out.println("8. View Overdue Tasks");
        System.out.println("9. Save Data to File");
        System.out.println("10. Load Data from File");
        System.out.println("0. Exit");
    }

    private static void createUser() throws TaskManagerException {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        User user = manager.addUser(name);
        System.out.println("Created: " + user);
    }

    private static void createProject() throws TaskManagerException {
        System.out.print("Enter project name: ");
        String name = scanner.nextLine();
        Project project = manager.createProject(name);
        System.out.println("Created: " + project);
    }

    private static void addTask() throws TaskManagerException {
        int projectId = readInt("Enter project ID: ");
        Project project = manager.findProjectById(projectId);
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
        Priority priority = Priority.valueOf(scanner.nextLine().trim().toUpperCase());
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();

        Task task = manager.addTaskToProject(project, title, priority, dueDate);
        System.out.println("Added: " + task);
    }

    private static void assignTask() throws TaskManagerException {
        int projectId = readInt("Enter project ID: ");
        Project project = manager.findProjectById(projectId);
        int taskId = readInt("Enter task ID: ");
        Task task = project.findTaskById(taskId);
        int userId = readInt("Enter user ID: ");
        User user = manager.findUserById(userId);

        task.assignUser(user);
        System.out.println("Assigned " + user.getName() + " to task: " + task.getTitle());
    }

    private static void updateTaskStatus() throws TaskManagerException {
        int projectId = readInt("Enter project ID: ");
        Project project = manager.findProjectById(projectId);
        int taskId = readInt("Enter task ID: ");
        Task task = project.findTaskById(taskId);
        System.out.print("Enter new status (TODO, IN_PROGRESS, DONE): ");
        Status status = Status.valueOf(scanner.nextLine().trim().toUpperCase());
        task.setStatus(status);
        System.out.println("Updated status: " + task);
    }

    private static void viewProject() throws TaskManagerException {
        int projectId = readInt("Enter project ID: ");
        Project project = manager.findProjectById(projectId);
        System.out.println(project);
        List<Task> tasks = project.getTasksSortedByDueDate();
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
        } else {
            for (Task t : tasks) {
                System.out.println("  " + t);
            }
        }
    }

    private static void searchTasks() throws TaskManagerException {
        int projectId = readInt("Enter project ID: ");
        Project project = manager.findProjectById(projectId);
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        List<Task> matches = manager.searchTasks(project, keyword);
        if (matches.isEmpty()) {
            System.out.println("No matching tasks.");
        } else {
            for (Task t : matches) {
                System.out.println("  " + t);
            }
        }
    }

    private static void viewOverdueTasks() throws TaskManagerException {
        int projectId = readInt("Enter project ID: ");
        Project project = manager.findProjectById(projectId);
        List<Task> overdue = project.getOverdueTasks();
        if (overdue.isEmpty()) {
            System.out.println("No overdue tasks. Nice work.");
        } else {
            for (Task t : overdue) {
                System.out.println("  " + t);
            }
        }
    }

    private static void saveData() {
        try {
            manager.saveToFile(SAVE_FILE);
            System.out.println("Data saved to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        try {
            manager = ProjectManager.loadFromFile(SAVE_FILE);
            System.out.println("Data loaded from " + SAVE_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number, defaulting to -1.");
            return -1;
        }
    }
}
