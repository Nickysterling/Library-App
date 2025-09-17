package model.book;
import model.enums.BookCondition;
import java.util.Objects;

public class BookCopy {
    private static int nextID = 1;
    private final int copyID;
    private final Book book;
    private BookCondition condition;

    public BookCopy(Book book, BookCondition condition) {
        this.copyID = nextID++;
        this.book = book;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "[Copy ID: " + copyID + "] " + book.toString() + " [Condition: " + condition + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return copyID == bookCopy.copyID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(copyID);
    }

    public int getCopyID() { return copyID; }
    public Book getBook() { return book; }
    public BookCondition getCondition() { return condition; }

    public void setCondition(BookCondition condition) {
        this.condition = condition;
    }
}
