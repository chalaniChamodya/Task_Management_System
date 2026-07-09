/**
 * Thrown when a task or project operation receives invalid input
 * (e.g. an unknown ID, a bad date format, an empty title).
 * Having a dedicated exception type is one of the things AI assistance
 * suggested over Version A's approach of just letting the program crash.
 */
public class TaskManagerException extends Exception {
    public TaskManagerException(String message) {
        super(message);
    }
}
