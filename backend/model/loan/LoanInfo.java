package model.loan;

import model.enums.BookCondition;
import model.enums.OperationStatus;
import model.book.BookCopy;
import model.member.Member;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LoanInfo {
    private final BookCopy copy;
    private final Member borrower;
    private final ZonedDateTime borrowDate;
    private final ZonedDateTime dueDate;
    private ZonedDateTime returnDate;
    private final BookCondition borrowCondition;
    private BookCondition returnCondition;

    private static final ZoneId TORONTO_ZONE = ZoneId.of("America/Toronto");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

    public LoanInfo(BookCopy copy, Member borrower) {
        this.copy = copy;
        this.borrower = borrower;
        this.borrowDate = ZonedDateTime.now(TORONTO_ZONE);
        this.dueDate = borrowDate.plusDays(21);
        this.returnDate = null;
        this.borrowCondition = copy.getCondition();
        this.returnCondition = null;
    }

    @Override
    public String toString() {
        return "LoanInfo {" +
                "\n  Copy: " + copy +
                "\n  Borrower: " + borrower.getFullName() +
                "\n  Borrowed: " + getFormattedBorrowDate() +
                "\n  Due: " + getFormattedDueDate() +
                "\n  Returned: " + getFormattedReturnDate() +
                "\n  Return Condition: " + (returnCondition == null ? "[Not returned]" : returnCondition) +
                "\n}";
    }

    public OperationStatus markReturned(BookCondition conditionAtReturn) {
        if (returnDate != null)
            return OperationStatus.ALREADY_RETURNED;

        if (conditionAtReturn == null)
            return OperationStatus.INVALID_INPUT;

        returnDate = ZonedDateTime.now(TORONTO_ZONE);
        returnCondition = conditionAtReturn;
        copy.setCondition(returnCondition);
        return OperationStatus.SUCCESS;
    }

    public boolean isOverdue() {
        if (returnDate == null)
            return ZonedDateTime.now(TORONTO_ZONE).isAfter(dueDate);

        return returnDate.isAfter(dueDate);
    }

    public String getFormattedBorrowDate() {
        return "[" + borrowDate.format(FORMATTER) + "]";
    }

    public String getFormattedDueDate() {
        return "[" + dueDate.format(FORMATTER) + "]";
    }

    public String getFormattedReturnDate() {
        if (returnDate == null) return "[Not returned]";
        return "[" + returnDate.format(FORMATTER) + "]";
    }

    // Getters
    public BookCopy getCopy() { return copy; }
    public Member getBorrower() { return borrower; }
    public ZonedDateTime getBorrowDate() { return borrowDate; }
    public ZonedDateTime getDueDate() { return dueDate; }
    public ZonedDateTime getReturnDate() { return returnDate; }
    public BookCondition getBorrowCondition() { return borrowCondition; }
    public BookCondition getReturnCondition() { return returnCondition; }
}
