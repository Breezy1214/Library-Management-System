package books;

import java.io.Serializable;

public abstract class Book implements Serializable {
    private String title;
    private String author;
    private boolean availability;
    private String genre;
    private long isbn;

    protected Book(String title, String author, String genre, boolean availability, long isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
        this.isbn = isbn;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public long getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return availability;
    }

    public abstract void printInfo();
}
