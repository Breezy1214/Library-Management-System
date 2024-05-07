import java.io.*;

public class FileHandler {
    private static void createDirectory() {
        File dir = new File("Database");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void saveState(Object data, String fileName) {
        try{
            createDirectory();
        }catch (Exception e){
            e.printStackTrace();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Database/" + fileName))) {
            createDirectory();
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
            e.printStackTrace();
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
