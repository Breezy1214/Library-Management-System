import books.Book;
import books.FictionBook;
import books.NonFictionBook;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Library library;
    private static UserManager userManager;
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
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> library.displayBooks();
                case 4 -> findBook();
                case 5 -> addUser();
                case 6 -> removeUser();
                case 7 -> {
                    saveAllData();
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } else {
            switch (choice) {
                case 1 -> borrowBook();
                case 2 -> returnBook();
                case 3 -> currentUser.printBorrowedBooks();
                case 4 -> {
                    saveAllData();
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
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
            userManager.addUser(name, id, password, role);
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Role: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid id: " + e.getMessage());
        } finally {
            scanner.nextLine(); // Consume newline
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
        System.out.println("Enter ISBN: ");
        long isbn = scanner.nextLong();

        Book book;
        if (type.equalsIgnoreCase("Fiction")) {
            book = new FictionBook(title, author, genre, true, themeOrSubject, isbn);
        } else {
            book = new NonFictionBook(title, author, genre, true, themeOrSubject, isbn);
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
        if (book == null) {
            System.out.println("Book does not exist!");
            return;
        }
        book.printInfo();
    }

    public static void loadAllData() {
        library = (Library) FileHandler.loadState("library");
        if (library == null) library = new Library();
        library.loadPredefinedBooks("src/PredefinedBooks.txt");

        userManager = (UserManager) FileHandler.loadState("userManager");
        if (userManager == null) userManager = new UserManager();
    }

    public static void saveAllData() {
        FileHandler.saveState(library, "library");
        FileHandler.saveState(userManager, "userManager");
    }

    public static void main(String[] args) {
        loadAllData();
        String adminName = "Charles";
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

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                executeCommand(choice);

                if (choice == 4 || choice == 7) {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a number. " + e.getMessage());
                scanner.nextLine();
            }


        }
    }

}