package service;

import model.book.BookCopy;
import model.enums.BookCondition;
import model.enums.OperationStatus;
import model.loan.LoanInfo;
import model.member.Member;

import java.util.List;
import java.util.Set;

/**
 * The LibraryService class acts as a higher-level interface to the Library class,
 * providing user-friendly methods for adding books/members, borrowing/returning,
 * and searching books. It handles displaying messages for various outcomes.
 */
public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    // ------------------- Add Book Copy -------------------
    public void addBookCopy(BookCopy copy) {
        OperationStatus status = library.addBookCopy(copy);

        if (status == OperationStatus.SUCCESS)
            System.out.println("Book copy added successfully: \"" + copy.getBook().getTitle() + "\" (ID: " + copy.getCopyID() + ")");
        else if (status == OperationStatus.INVALID_INPUT)
            System.out.println("Invalid input. Cannot add book copy.");
        else if (status == OperationStatus.COPY_ALREADY_EXISTS)
            System.out.println("Book copy already exists: \"" + copy.getBook().getTitle() + "\" (ID: " + copy.getCopyID() + ")");
        else
            System.out.println("Unknown error while adding book copy.");
    }

    // ------------------- Add Member -------------------
    public void addMember(Member member) {
        OperationStatus status = library.addMember(member);

        if (status == OperationStatus.SUCCESS)
            System.out.println("Successfully registered: " + member.getFullName() + " (ID: " + member.getMemberID() + ")\n");
        else if (status == OperationStatus.INVALID_INPUT)
            System.out.println("Invalid input. Cannot register member.");
        else if (status == OperationStatus.MEMBER_ALREADY_EXISTS)
            System.out.println(member.getFullName() + " (ID: " + member.getMemberID() + ") already exists.");
        else
            System.out.println("Unknown error during member registration.");
    }

    // ------------------- Borrow Book -------------------
    public void borrowBook(Member member, BookCopy copy) {
        if (!library.isMemberRegistered(member)) {
            System.out.println("Member not registered: " + member.getFullName());
            return;
        }

        OperationStatus status = library.borrowBook(copy, member);

        if (status == OperationStatus.SUCCESS)
            System.out.println(member.getFullName() + " successfully borrowed \"" + copy.getBook().getTitle() + "\".\n");
        else if (status == OperationStatus.COPY_NOT_AVAILABLE)
            System.out.println("Book copy not available: \"" + copy.getBook().getTitle() + "\".");
        else if (status == OperationStatus.ALREADY_BORROWED)
            System.out.println("Book copy already borrowed: \"" + copy.getBook().getTitle() + "\".");
        else if (status == OperationStatus.BORROW_LIMIT_REACHED)
            System.out.println(member.getFullName() + " has reached the borrow limit.");
        else if (status == OperationStatus.INVALID_INPUT)
            System.out.println("Invalid input.");
        else
            System.out.println("Unknown error while borrowing book.");
    }

    // ------------------- Return Book -------------------
    public void returnBook(Member member, BookCopy copy, BookCondition condition) {
        OperationStatus status = library.returnBook(copy, condition);

        if (status == OperationStatus.SUCCESS)
            System.out.println(member.getFullName() + " successfully returned \"" + copy.getBook().getTitle() + "\" in " + condition + " condition.\n");
        else if (status == OperationStatus.COPY_NOT_BORROWED)
            System.out.println("This copy was not borrowed: \"" + copy.getBook().getTitle() + "\".");
        else if (status == OperationStatus.MEMBER_NOT_FOUND)
            System.out.println("Member not found: " + member.getFullName());
        else if (status == OperationStatus.INVALID_INPUT)
            System.out.println("Invalid input.");
        else
            System.out.println("Unknown error while returning book.");
    }

    // ------------------- View Borrowed Copies -------------------
    public void viewBorrowedCopies(Member member) {
        List<LoanInfo> borrowed = library.getBorrowedCopies(member);

        if (borrowed.isEmpty()) {
            System.out.println(member.getFullName() + " has no borrowed books.\n");
        } else {
            System.out.println("\n" + member.getFullName() + "'s Borrowed Books:");
            for (LoanInfo loan : borrowed)
                System.out.println(loan.getCopy().toString()
                        + "\nDate Borrowed: " + loan.getFormattedBorrowDate()
                        + "\nDue Date: " + loan.getFormattedDueDate() + "\n");
        }
    }

    // ------------------- View Loan History -------------------
    public void viewLoanHistory(Member member) {
        List<LoanInfo> history = library.getLoanHistory(member);

        if (history.isEmpty()) {
            System.out.println(member.getFullName() + " has no loan history.");
        } else {
            System.out.println(member.getFullName() + "'s Loan History:");
            for (LoanInfo loan : history) System.out.println(loan);
        }
    }

    // ------------------- View Overdue Books -------------------
    public void viewOverdueBooks() {
        List<BookCopy> overdue = library.searchOverdueBooks();

        if (overdue.isEmpty()) {
            System.out.println("No overdue books.\n");
        } else {
            System.out.println("Overdue Books:");
            for (BookCopy copy : overdue) System.out.println(copy);
        }
    }

    public void viewOverdueBooks(Member member) {
        List<BookCopy> overdue = library.searchOverdueByMember(member);

        if (overdue.isEmpty()) {
            System.out.println(member.getFullName() + " has no overdue books.\n");
        } else {
            System.out.println(member.getFullName() + "'s Overdue Books:");
            for (BookCopy copy : overdue) System.out.println(copy);
        }
    }

    // ------------------- Print Available Copies -------------------
    public void printAvailableCopies() {
        Set<BookCopy> copies = library.getAvailableCopies();
        if (copies.isEmpty()) {
            System.out.println("No available copies in the library.\n");
        } else {
            System.out.println("Available Book Copies:");
            for (BookCopy copy : copies) System.out.println(copy);
            System.out.println();
        }
    }

    // ------------------- Search All Copies -------------------
    public void printSearchByCopyID(int id) {
        BookCopy copy = library.searchAllByID(id);
        if (copy == null) System.out.println("No copy found with ID: " + id);
        else System.out.println("Found copy: " + copy);
    }

    public void printSearchByTitle(String title) {
        List<BookCopy> results = library.searchAllByTitle(title);
        if (results.isEmpty()) System.out.println("No books found with title containing: \"" + title + "\"");
        else {
            System.out.println("Search results for title \"" + title + "\":");
            for (BookCopy copy : results) System.out.println(copy);
            System.out.println();
        }
    }

    public void printSearchByISBN(String isbn) {
        List<BookCopy> results = library.searchAllByISBN(isbn);
        if (results.isEmpty()) System.out.println("No books found with ISBN: " + isbn);
        else {
            System.out.println("Search results for ISBN " + isbn + ":");
            for (BookCopy copy : results) System.out.println(copy);
            System.out.println();
        }
    }

    public void printSearchBorrowedByMember(Member member) {
        List<BookCopy> results = library.searchBorrowedByMember(member);
        if (results.isEmpty()) System.out.println(member.getFullName() + " has no borrowed books.");
        else {
            System.out.println(member.getFullName() + "'s Borrowed Books:");
            for (BookCopy copy : results) System.out.println(copy);
        }
    }
}
