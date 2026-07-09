import java.util.Scanner;

public class Main {
    static ProjectManager manager = new ProjectManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                createUser();
            } else if (choice == 2) {
                createProject();
            } else if (choice == 3) {
                addTask();
            } else if (choice == 4) {
                assignTask();
            } else if (choice == 5) {
                updateTaskStatus();
            } else if (choice == 6) {
                viewProject();
            } else if (choice == 0) {
                running = false;
            } else {
                System.out.println("Invalid option, try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    static void printMenu() {
        System.out.println("\n=== Task Manager ===");
        System.out.println("1. Add User");
        System.out.println("2. Create Project");
        System.out.println("3. Add Task to Project");
        System.out.println("4. Assign Task to User");
        System.out.println("5. Update Task Status");
        System.out.println("6. View Project Details");
        System.out.println("0. Exit");
    }

    static void createUser() {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        User user = manager.addUser(name);
        System.out.println("Created: " + user);
    }

    static void createProject() {
        System.out.print("Enter project name: ");
        String name = scanner.nextLine();
        Project project = manager.createProject(name);
        System.out.println("Created: " + project);
    }

    static void addTask() {
        System.out.print("Enter project ID: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        Project project = manager.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
        String priority = scanner.nextLine();
        System.out.print("Enter due date (e.g. 2026-07-15): ");
        String dueDate = scanner.nextLine();

        Task task = manager.addTaskToProject(project, title, priority, dueDate);
        System.out.println("Added: " + task);
    }

    static void assignTask() {
        System.out.print("Enter project ID: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        Project project = manager.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        System.out.print("Enter task ID: ");
        int taskId = Integer.parseInt(scanner.nextLine());
        Task task = project.findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        System.out.print("Enter user ID: ");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = manager.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        task.assignedUser = user;
        System.out.println("Assigned " + user.name + " to task: " + task.title);
    }

    static void updateTaskStatus() {
        System.out.print("Enter project ID: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        Project project = manager.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        System.out.print("Enter task ID: ");
        int taskId = Integer.parseInt(scanner.nextLine());
        Task task = project.findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        System.out.print("Enter new status (TODO, IN_PROGRESS, DONE): ");
        String status = scanner.nextLine();
        task.status = status;
        System.out.println("Updated status: " + task);
    }

    static void viewProject() {
        System.out.print("Enter project ID: ");
        int projectId = Integer.parseInt(scanner.nextLine());
        Project project = manager.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        System.out.println(project);
        if (project.tasks.isEmpty()) {
            System.out.println("No tasks yet.");
        } else {
            for (Task t : project.tasks) {
                System.out.println("  " + t);
            }
        }
    }
}
