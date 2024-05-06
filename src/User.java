import Books.Book;
import java.util.ArrayList;
import java.io.Serializable;
public class User implements Borrowable, Serializable {
    private String name;
    private int id;
    private ArrayList<Book> borrowedBooks;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailability(false);
            System.out.println("Books borrowed: " + book.getTitle());
        } else {
            System.out.println("This book is not available.");
        }
    }

    @Override
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailability(true);
            System.out.println("Books.Book returned: " + book.getTitle());
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
