package books;

public class FictionBook extends Book {
    private String theme;

    public FictionBook(String title, String author, String genre, boolean availability, String theme, long isbn) {
        super(title, author, genre, availability, isbn);
        this.theme = theme;
    }

    @Override
    public void printInfo() {
        System.out.println("Fiction Book: " + super.getTitle() + " by " + super.getAuthor() + " (Theme: " + theme + ")");
    }
}