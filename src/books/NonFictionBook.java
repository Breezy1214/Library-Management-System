package books;

public class NonFictionBook extends Book {
    private String subject;

    public NonFictionBook(String title, String author, String genre, boolean availability, String subject, long isbn) {
        super(title, author, genre, availability, isbn);
        this.subject = subject;
    }

    @Override
    public void printInfo() {
        System.out.println("Non-Fiction Book: " + super.getTitle() + " by " + super.getAuthor() + " (Subject: " + subject + ")");
    }
}