import Books.*;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

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

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Library System!");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Find Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

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
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}