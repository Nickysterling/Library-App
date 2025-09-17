package model.book;

public class PrintedBook extends Book{
    private final int pages;

    public PrintedBook(String title, String author, String ISBN, int pages) {
        super(title, author, ISBN);
        this.pages = pages;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + pages + " pages]";
    }

    // Getters
    public int getPages() { return pages; }

}
