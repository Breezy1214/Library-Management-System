import Books.Book;

public interface Borrowable {
    void borrowBook(Book book);
    void returnBook(Book book);
}
