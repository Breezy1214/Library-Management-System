package Books;

public class FictionBook extends Book {
    private String theme;

    public FictionBook(String title, String author, String genre, boolean availability, String theme) {
        super(title, author, genre, availability);
        this.theme = theme;
    }

    @Override
    public void printInfo() {
        System.out.println("Fiction Book: " + super.getTitle() + " by " + super.getAuthor() + " (Theme: " + theme + ")");
    }
}