import books.Book;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Borrowable, Serializable {
    private String name;
    private Role role;
    private int id;
    private String password;
    private ArrayList<Book> borrowedBooks;

    public User(String name, int id, String password, Role role) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.role = role;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailability(false);
            System.out.println("Book borrowed: " + book.getTitle());
        } else {
            System.out.println("This book is not available.");
        }
    }

    @Override
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailability(true);
            System.out.println("Book returned: " + book.getTitle());
        } else {
            System.out.println("This book was not borrowed by you.");
        }
    }

    public void printBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed.");
            return;
        }
        borrowedBooks.forEach(book -> System.out.println(book.getTitle()));
    }
}
