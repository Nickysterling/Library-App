package service;

import model.book.BookCopy;
import model.enums.BookCondition;
import model.enums.OperationStatus;
import model.loan.LoanInfo;
import model.member.Member;

import java.util.*;

/**
 * The Library class manages the storage, borrowing, returning, and searching
 * of books and members. It keeps track of available book copies, borrowed
 * copies, loan history, and registered members.
 *
 * <p>This class provides methods to:
 * <ul>
 *     <li>Add books and members</li>
 *     <li>Borrow and return books with proper validation</li>
 *     <li>Retrieve loan history and current borrowed books</li>
 *     <li>Search for books by title, ISBN, or copy ID</li>
 *     <li>Check availability of books and registration status of members</li>
 * </ul>
 */
public class Library {
    /**
     * Maximum number of books a member can borrow at the same time.
     */
    private static final int MAX_BORROW_LIMIT = 5;

    /**
     * Set of book copies currently available for borrowing.
     * Does not include borrowed copies.
     */
    private final Set<BookCopy> availableCopies = new HashSet<>();

    /**
     * Map of borrowed book copies to their corresponding LoanInfo.
     * Tracks which member has borrowed which copy.
     */
    private final Map<BookCopy, LoanInfo> borrowedCopies = new HashMap<>();

    /**
     * List of all past loans in the library, including returned books.
     */
    private final List<LoanInfo> loanHistory = new ArrayList<>();

    /**
     * Set of members registered in the library.
     */
    private final Set<Member> members = new HashSet<>();

    // ---------- ADD OPERATIONS ----------

    /**
     * Add a new book copy to the library.
     *
     * @param copy the BookCopy to add
     * @return OperationStatus indicating success or type of error
     */
    public OperationStatus addBookCopy(BookCopy copy) {
        if (copy == null || copy.getBook() == null) return OperationStatus.INVALID_INPUT;
        if (availableCopies.contains(copy)) return OperationStatus.COPY_ALREADY_EXISTS;

        availableCopies.add(copy);
        return OperationStatus.SUCCESS;
    }

    /**
     * Register a new member in the library.
     *
     * @param member the Member to add
     * @return OperationStatus indicating success or type of error
     */
    public OperationStatus addMember(Member member) {
        if (member == null) return OperationStatus.INVALID_INPUT;
        if (members.contains(member)) return OperationStatus.MEMBER_ALREADY_EXISTS;

        members.add(member);
        return OperationStatus.SUCCESS;
    }

    // ---------- BORROW & RETURN OPERATIONS ----------

    /**
     * Borrow a book copy for a member.
     *
     * @param copy   the BookCopy to borrow
     * @param member the Member borrowing the copy
     * @return OperationStatus indicating success or type of error
     */
    public OperationStatus borrowBook(BookCopy copy, Member member) {
        if (!members.contains(member)) return OperationStatus.MEMBER_NOT_FOUND;
        if (!availableCopies.contains(copy)) return OperationStatus.COPY_NOT_AVAILABLE;
        if (borrowedCopies.containsKey(copy)) return OperationStatus.ALREADY_BORROWED;
        if (getBorrowedCopies(member).size() >= MAX_BORROW_LIMIT) return OperationStatus.BORROW_LIMIT_REACHED;

        borrowedCopies.put(copy, new LoanInfo(copy, member));
        availableCopies.remove(copy);
        return OperationStatus.SUCCESS;
    }

    /**
     * Return a borrowed book copy.
     *
     * @param copy               the BookCopy being returned
     * @param conditionAtReturn  the BookCondition at return
     * @return OperationStatus indicating success or type of error
     */
    public OperationStatus returnBook(BookCopy copy, BookCondition conditionAtReturn) {
        if (copy == null || conditionAtReturn == null)
            return OperationStatus.INVALID_INPUT;

        LoanInfo loanInfo = borrowedCopies.get(copy);
        if (loanInfo == null) return OperationStatus.COPY_NOT_BORROWED;

        if (!members.contains(loanInfo.getBorrower())) return OperationStatus.MEMBER_NOT_FOUND;

        // Update loan info and copy condition
        OperationStatus status = loanInfo.markReturned(conditionAtReturn);
        if (status != OperationStatus.SUCCESS) return status;

        // Move copy back to available copies
        borrowedCopies.remove(copy);
        availableCopies.add(copy);

        // Add loan info to loan history
        loanHistory.add(loanInfo);
        return OperationStatus.SUCCESS;
    }

    // ---------- LOAN HISTORY OPERATIONS ----------

    /**
     * Get all borrowed copies for a member.
     *
     * @param member the Member to check
     * @return list of LoanInfo for books currently borrowed by the member
     */
    public List<LoanInfo> getBorrowedCopies(Member member) {
        List<LoanInfo> memberBorrowedCopies = new ArrayList<>();

        for (LoanInfo loanInfo : borrowedCopies.values())
            if (loanInfo.getBorrower().equals(member)) memberBorrowedCopies.add(loanInfo);

        return memberBorrowedCopies;
    }

    /**
     * Get loan history for a member.
     *
     * @param member the Member to check
     * @return list of LoanInfo for all loans by the member
     */
    public List<LoanInfo> getLoanHistory(Member member) {
         List<LoanInfo> memberBorrowHistory = new ArrayList<>();

         for (LoanInfo loanInfo : loanHistory)
             if (loanInfo.getBorrower().equals(member)) memberBorrowHistory.add(loanInfo);

         return memberBorrowHistory;
    }

    // ---------- OVERDUE SEARCH OPERATIONS ----------

    /**
     * Get all overdue books in the library.
     *
     * @return list of BookCopy objects that are overdue
     */
    public List<BookCopy> searchOverdueBooks() {
         List<BookCopy> overdueBooks = new ArrayList<>();

         for (LoanInfo loanInfo : borrowedCopies.values())
             if (loanInfo.isOverdue()) overdueBooks.add(loanInfo.getCopy());

         return overdueBooks;
    }

    /**
     * Get all overdue books borrowed by a specific member.
     *
     * @param member the Member to check
     * @return list of BookCopy objects overdue for the member
     */
    public List<BookCopy> searchOverdueByMember(Member member) {
         List<BookCopy> overdueBooks = new ArrayList<>();

         for (LoanInfo loanInfo : borrowedCopies.values())
             if (loanInfo.isOverdue() && loanInfo.getBorrower().equals(member)) overdueBooks.add(loanInfo.getCopy());

         return overdueBooks;
    }

    // ---------- BORROWING SEARCH OPERATIONS ----------

    /**
     * Search available copies by exact title (case-insensitive).
     *
     * @param title the book title to search for
     * @return list of available BookCopy objects with matching title
     */
    public List<BookCopy> searchAvailableByTitle(String title) {
        List<BookCopy> matches = new ArrayList<>();
        for (BookCopy copy : availableCopies)
            if (copy.getBook().getTitle().toLowerCase().contains(title.toLowerCase())) matches.add(copy);

        return matches;
    }

    /**
     * Search available copies by ISBN.
     *
     * @param isbn the ISBN to search for
     * @return list of available BookCopy objects with matching ISBN
     */

    public List<BookCopy> searchAvailableByISBN(String isbn) {
         List<BookCopy> matches = new ArrayList<>();
         for (BookCopy copy : availableCopies)
             if (copy.getBook().getISBN().equals(isbn)) matches.add(copy);

         return matches;
    }

    /**
     * Search available copy by copy ID.
     *
     * @param id the copy ID to search for
     * @return available BookCopy object with matching ID
     */
    public BookCopy searchAvailableByID(int id) {
        for (BookCopy copy : availableCopies)
            if (copy.getCopyID() == id) return copy;

        return null;
    }

    // ---------- RETURNING SEARCH OPERATIONS ----------

    /**
     * Get all borrowed books for a specific member.
     *
     * @param member the member whose borrowed books to retrieve
     * @return list of BookCopy objects currently borrowed by the member
     */
    public List<BookCopy> searchBorrowedByMember(Member member) {
        List<BookCopy> matches = new ArrayList<>();
        for (LoanInfo loanInfo : borrowedCopies.values())
            if (loanInfo.getBorrower().equals(member)) matches.add(loanInfo.getCopy());

        return matches;
    }

    /**
     * Search for a borrowed book by its copy ID.
     *
     * @param id the copy ID to search for
     * @return the BookCopy object with the matching ID, or null if not found
     */
    public BookCopy searchBorrowedByID(int id) {
        for (BookCopy copy : borrowedCopies.keySet())
            if (copy.getCopyID() == id) return copy;

        return null;
    }

    // ---------- SEARCH ALL OPERATIONS ----------

    /**
     * Compile list of all book copies in library, both available and borrowed.
     *
     * @return a list containing all BookCopy objects in the library
     */
    private List<BookCopy> compileAllBooks() {
        List<BookCopy> allBooks = new ArrayList<>(availableCopies);
        allBooks.addAll(borrowedCopies.keySet());

        return allBooks;
    }

    /**
     * Search all books (available and borrowed) by title (case-insensitive).
     *
     * @param title the book title to search for
     * @return list of BookCopy objects with matching titles
     */
    public List<BookCopy> searchAllByTitle(String title) {
        List<BookCopy> matches = new ArrayList<>();
        List<BookCopy> allBooks = compileAllBooks();

        for (BookCopy copy : allBooks)
            if (copy.getBook().getTitle().toLowerCase().contains(title.toLowerCase())) matches.add(copy);

        return matches;
    }

    /**
     * Search all books (available and borrowed) by ISBN.
     *
     * @param isbn the ISBN to search for
     * @return list of BookCopy objects with matching ISBN
     */
    public List<BookCopy> searchAllByISBN(String isbn) {
        List<BookCopy> matches = new ArrayList<>();
        List<BookCopy> allBooks = compileAllBooks();

        for (BookCopy copy : allBooks)
            if (copy.getBook().getISBN().equals(isbn)) matches.add(copy);

        return matches;
    }

    /**
     * Search all books (available and borrowed) by copy ID.
     *
     * @param id the copy ID to search for
     * @return the BookCopy object with the matching ID, or null if not found
     */
    public BookCopy searchAllByID(int id) {
        List<BookCopy> allBooks = compileAllBooks();

        for (BookCopy copy : allBooks)
            if (copy.getCopyID() == id) return copy;

        return null;
    }

    /**
     * Get all book copies that are currently available in the library.
     *
     * @return a new Set containing all available BookCopy objects
     */
    public Set<BookCopy> getAvailableCopies() {
        return new HashSet<>(availableCopies);
    }

    /**
     * Check whether a member is registered in the library.
     *
     * @param member the Member to check
     * @return true if the member is registered, false otherwise
     */
    public boolean isMemberRegistered(Member member){
        return members.contains(member);
    }

    /**
     * Check whether a specific book copy is available to borrow.
     *
     * @param copy the BookCopy to check
     * @return true if the copy is available, false otherwise
     */
    public boolean isCopyAvailable(BookCopy copy) {
        return searchAvailableByID(copy.getCopyID()) != null;
    }
}
