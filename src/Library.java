import Books.Book;
import java.io.Serializable;
import java.util.HashMap;

public class Library implements Serializable {
    private HashMap<String, Book> books;

    public Library() {
        this.books = new HashMap<>();
    }

    public void addBook(Book book) {
        String title = book.getTitle();
        books.put(title, book);
        System.out.println("Book added: " + title);
    }

    public void removeBook(String title) {
        if (!books.containsKey(title)){
            System.out.println("Book not found: " + title);
            return;
        }

        books.remove(title);
        System.out.println("Book removed: " + title);
    }

    public void displayBooks() {
        books.values().forEach(Book::printInfo);
    }

    public Book findBook(String title){
        return books.get(title);
    }
}
