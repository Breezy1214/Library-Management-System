import java.io.*;

public class FileHandler {
    private static void createDirectory() {
        File dir = new File("Database");
        if (!dir.exists()) {
            dir.mkdirs();
            dir.getParent();
                    dir.getParentFile();
            System.out.println(dir.getParent());
        }
    }

    public static void saveState(Object data, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Database/" + fileName))) {
            createDirectory();
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public static Object loadState(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Database/" + fileName))) {
            createDirectory();
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}
