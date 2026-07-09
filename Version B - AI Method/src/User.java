import java.io.Serializable;

/**
 * A person who can be assigned tasks.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private final String name;

    public User(int id, String name) throws TaskManagerException {
        if (name == null || name.trim().isEmpty()) {
            throw new TaskManagerException("User name cannot be empty.");
        }
        this.id = id;
        this.name = name.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
