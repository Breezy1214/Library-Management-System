import Books.Book;
import Books.FictionBook;
import Books.NonFictionBook;

import java.util.Scanner;

public class Main {
    private static Library library;
    private static UserManager userManager;
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    private static void authenticateUser() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userManager.getUser(name);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful.");
            currentUser = user;
        } else {
            System.out.println("Login failed.");
        }
    }

    private static void executeCommand(int choice) {
        if (currentUser.getRole() == Role.ADMIN) {
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    library.displayBooks();
                    break;
                case 4:
                    findBook();
                    break;
                case 5:
                    addUser();
                    break;
                case 6:
                    removeUser();
                    break;
                case 7:
                    saveAllData();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } else {
            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    currentUser.printBorrowedBooks();
                    break;
                case 4:
                    saveAllData();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void addUser() {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (ADMIN/STANDARD_USER): ");

        try {
            Role role = Role.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter user ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            userManager.addUser(name, id, password, role);
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Role: " + e.getMessage());
        }
    }

    private static void removeUser() {
        System.out.print("Enter username to remove: ");
        String name = scanner.nextLine();
        userManager.removeUser(name);
    }

    private static void borrowBook() {
        System.out.print("Enter the book title to borrow: ");
        String title = scanner.nextLine();
        Book book = library.findBook(title);
        if (book == null) {
            System.out.println("Book not found in the library!");
            return;
        }

        currentUser.borrowBook(book);
    }

    private static void returnBook() {
        System.out.print("Enter the book title to return: ");
        String title = scanner.nextLine();
        Book book = library.findBook(title);
        if (book == null) {
            System.out.println("Book not found in the library!");
            return;
        }

        currentUser.returnBook(book);
    }

    private static void addBook() {
        System.out.print("Enter book type (Fiction/Non-Fiction): ");
        String type = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter theme or subject: ");
        String themeOrSubject = scanner.nextLine();

        Book book;
        if (type.equalsIgnoreCase("Fiction")) {
            book = new FictionBook(title, author, genre, true, themeOrSubject);
        } else {
            book = new NonFictionBook(title, author, genre, true, themeOrSubject);
        }

        library.addBook(book);
    }

    private static void removeBook() {
        System.out.print("Enter the title of the book to remove: ");
        String title = scanner.nextLine();
        library.removeBook(title);
    }

    private static void findBook() {
        System.out.println("Enter the title of the book to find: ");
        String title = scanner.nextLine();
        Book book = library.findBook(title);
        book.printInfo();
    }

    public static void loadAllData() {
        library = (Library) FileHandler.loadState("library.dat");
        if (library == null) library = new Library();

        userManager = (UserManager) FileHandler.loadState("userManager.dat");
        if (userManager == null) userManager = new UserManager();
    }

    public static void saveAllData() {
        FileHandler.saveState(library, "library.dat");
        FileHandler.saveState(userManager, "userManager.dat");
        System.out.println("Saved all data!");
    }

    public static void main(String[] args) {
        loadAllData();
        String adminName = "Shelsie";
        User admin = userManager.getUser(adminName);
        if (admin == null) {
            userManager.addUser(adminName, 1, "12345", Role.ADMIN);
            saveAllData();
        }

        authenticateUser();

        if (currentUser == null) {
            return;
        }

        while (true) {
            System.out.println("\nWelcome to the Library System!");

            if (currentUser.getRole() == Role.STANDARD_USER) {
                System.out.println("1. Borrow Book");
                System.out.println("2. Return Book");
                System.out.println("3. Print Borrowed Books");
                System.out.println("4. Exit");
            } else {
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. Display All Books");
                System.out.println("4. Find Book");
                System.out.println("5. Add User");
                System.out.println("6. Remove User");
                System.out.println("7. Exit");
            }

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            executeCommand(choice);
        }
    }

}