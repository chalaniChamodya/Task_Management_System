// A person who can be assigned tasks
public class User {
    int id;
    String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
