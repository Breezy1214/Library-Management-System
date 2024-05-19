import books.Book;
import books.FictionBook;
import books.NonFictionBook;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Library implements Serializable {
    private final HashMap<Long, Book> books;
    private final HashMap<String, Long> titleToIsbn;

    public Library() {
        this.books = new HashMap<>();
        this.titleToIsbn = new HashMap<>();
    }

    public void addBook(Book book) {
        Long isbn = book.getIsbn();
        books.put(isbn, book);
        String title = book.getTitle();
        titleToIsbn.put(title.toLowerCase(), isbn);
        System.out.println("Book added: " + title);
    }

    public void removeBook(String title) {
        Long isbn = findISBNbyTitle(title);
        if (isbn == null || !books.containsKey(isbn)) {
            System.out.println("Book not found!");
            return;
        }

        books.remove(isbn);
        titleToIsbn.remove(title.toLowerCase());
        System.out.println("Book removed: " + title);
    }

    public void displayBooks() {
        books.values().forEach(Book::printInfo);
    }

    public Long findISBNbyTitle(String title) {
        return titleToIsbn.get(title.toLowerCase());
    }

    public void loadPredefinedBooks(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] bookData = data.split(",");
                String title = bookData[0].trim();
                String author = bookData[1].trim();
                String genre = bookData[2].trim();
                boolean isAvailable = Boolean.parseBoolean(bookData[3].trim().toLowerCase());
                long isbn = Long.parseLong(bookData[4].replace("-", "").trim());
                String themeOrSubject = bookData[5].trim();
                String fictionOrNonFiction = bookData[6].trim();

                if (books.containsKey(isbn)) {
                    continue;
                }

                Book book;

                if (fictionOrNonFiction.equalsIgnoreCase("Fiction")) {
                    book = new FictionBook(title, author, genre, isAvailable, themeOrSubject, isbn);
                } else {
                    book = new NonFictionBook(title, author, genre, isAvailable, themeOrSubject, isbn);
                }

                this.addBook(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading predefined books: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing ISBN: " + e.getMessage());
        }
    }

    public Book findBook(String title) {
        Long isbn = titleToIsbn.get(title.toLowerCase());
        if (isbn == null) {
            return null;
        }
        return books.get(isbn);
    }
}
