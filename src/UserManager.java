import java.util.HashMap;
import java.io.Serializable;

public class UserManager implements Serializable {
    private HashMap<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public void addUser(String name, int id, String password, Role role) {
        if (!users.containsKey(name)) {
            users.put(name, new User(name, id, password, role));
            System.out.println("User added successfully: " + name);
        } else {
            System.out.println("User already exists with Name: " + name);
        }
    }

    public void removeUser(String name) {
        if (users.containsKey(name)) {
            users.remove(name);
            System.out.println("User removed successfully.");
        } else {
            System.out.println("User not found with Name: " + name);
        }
    }

    public User getUser(String name) {
        return users.get(name);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}
