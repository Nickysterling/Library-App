package model.book;

public class EBook extends Book{
    private final double fileSizeMB;
    private final String format;

    public EBook(String title, String author, String ISBN, double fileSizeMB, String format) {
        super(title, author, ISBN);
        this.fileSizeMB = fileSizeMB;
        this.format = format;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + format + ", " + fileSizeMB + "MB]";
    }

    public double getFileSizeMB() { return fileSizeMB; }
    public String getFormat() { return format; }
}
