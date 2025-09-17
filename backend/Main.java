import model.book.BookCopy;
import model.book.PrintedBook;
import model.enums.BookCondition;
import model.member.Member;
import service.Library;
import service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Library library = new Library();
        LibraryService service = new LibraryService(library);

        setupSampleData(library);

        System.out.println("Welcome to the Library!");
        Member member = registerMember(service);

        runMainMenu(member, library, service);

        System.out.println("Goodbye!");
        scanner.close();
    }

    // ---------------- Setup ----------------

    private static void setupSampleData(Library library) {
        PrintedBook book1 = new PrintedBook("1984", "George Orwell", "123", 328);
        PrintedBook book2 = new PrintedBook("Brave New World", "Aldous Huxley", "456", 288);
        PrintedBook book3 = new PrintedBook("The Hobbit", "J.R.R. Tolkien", "789", 310);
        PrintedBook book4 = new PrintedBook("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "111", 352);

        library.addBookCopy(new BookCopy(book1, BookCondition.GOOD));
        library.addBookCopy(new BookCopy(book2, BookCondition.GOOD));
        library.addBookCopy(new BookCopy(book3, BookCondition.GOOD));
        library.addBookCopy(new BookCopy(book4, BookCondition.NEW));
        library.addBookCopy(new BookCopy(book1, BookCondition.POOR));
    }

    private static Member registerMember(LibraryService service) {
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        Member member = new Member(firstName, lastName);
        service.addMember(member);
        return member;
    }

    // ---------------- Menus ----------------

    private static void runMainMenu(Member member, Library library, LibraryService service) {
        boolean running = true;
        while (running) {
            System.out.println("Main Menu:");
            System.out.println("1) View Borrowed Books");
            System.out.println("2) Borrow Book");
            System.out.println("3) Return Book");
            System.out.println("4) View Overdue Books");
            System.out.println("5) View All Available Books");
            System.out.println("6) Search Available Books by Title");
            System.out.println("7) Search Available Books by ISBN");
            System.out.println("8) Exit");
            System.out.print("Choose an action: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> service.viewBorrowedCopies(member);
                case 2 -> runBorrowMenu(member, library, service);
                case 3 -> runReturnMenu(member, library, service);
                case 4 -> service.viewOverdueBooks(member);
                case 5 -> service.printAvailableCopies();
                case 6 -> {
                    System.out.print("Enter title keyword: ");
                    String title = scanner.nextLine();
                    service.printSearchByTitle(title);
                }
                case 7 -> {
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    service.printSearchByISBN(isbn);
                }
                case 8 -> running = false;
                default -> System.out.println("Invalid action.");
            }
        }
    }

    private static void runBorrowMenu(Member member, Library library, LibraryService service) {
        System.out.println("\nHow do you want to select a book to borrow?");
        System.out.println("1) Search by Title");
        System.out.println("2) Search by ISBN");
        System.out.println("3) Enter Copy ID");
        System.out.print("Choose an action: ");

        int searchChoice = Integer.parseInt(scanner.nextLine());

        BookCopy copyToBorrow = null;

        if (searchChoice == 1) {
            System.out.print("Enter title keyword: ");
            String title = scanner.nextLine();
            List<BookCopy> results = library.searchAvailableByTitle(title);

            if (results.isEmpty()) {
                System.out.println("No books found with that title.");
            } else {
                System.out.println("\nAvailable books:");
                for (BookCopy copy : results)
                    System.out.println(copy);

                System.out.print("Enter Copy ID to borrow: ");
                int copyID = Integer.parseInt(scanner.nextLine());

                for (BookCopy copy : results) {
                    if (copy.getCopyID() == copyID) {
                        copyToBorrow = copy;
                        break;
                    }
                }

                if (copyToBorrow != null) {
                    service.borrowBook(member, copyToBorrow);
                } else {
                        System.out.println("Invalid Copy ID. Please select from the list shown.\n");
                }
            }

        } else if (searchChoice == 2) {
            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine();
            List<BookCopy> results = library.searchAvailableByISBN(isbn);

            if (results.isEmpty()) {
                System.out.println("No books found with that ISBN.");
            } else {
                System.out.println("\nAvailable books:");
                for (BookCopy copy : results) System.out.println(copy);

                System.out.print("Enter Copy ID to borrow: ");
                int copyID = Integer.parseInt(scanner.nextLine());
                for (BookCopy copy : results) {
                    if (copy.getCopyID() == copyID) {
                        copyToBorrow = copy;
                        break;
                    }
                }

                if (copyToBorrow != null) {
                    service.borrowBook(member, copyToBorrow);
                } else {
                    System.out.println("Invalid Copy ID. Please select from the list shown.\n");
                }
            }

        } else if (searchChoice == 3) {
            System.out.println();
            service.printAvailableCopies();
            System.out.print("Enter Copy ID: ");
            int copyID = Integer.parseInt(scanner.nextLine());
            copyToBorrow = library.searchAvailableByID(copyID);

            if (copyToBorrow != null) {
                service.borrowBook(member, copyToBorrow);
            } else {
                System.out.println("Invalid Copy ID. Please select from the list shown.\n");
            }
        }
    }

    private static void runReturnMenu(Member member, Library library, LibraryService service) {
        List<BookCopy> borrowed = library.searchBorrowedByMember(member);
        if (borrowed.isEmpty()) {
            System.out.println("You have no borrowed books.");
            return;
        }

        System.out.println("\nYour borrowed books:");
        for (BookCopy copy : borrowed) System.out.println(copy);

        System.out.print("Enter Copy ID to return: ");
        int copyID = Integer.parseInt(scanner.nextLine());
        BookCopy copyToReturn = library.searchBorrowedByID(copyID);

        if (copyToReturn != null) {
            System.out.println("\nSelect condition at return (best → worst):");
            for (BookCondition condition : BookCondition.values())
                System.out.println(condition.getRank() + ") " + condition);

            System.out.print("Choose an action: ");
            int conditionChoice = Integer.parseInt(scanner.nextLine());
            BookCondition chosenCondition = null;

            for (BookCondition condition : BookCondition.values()) {
                if (condition.getRank() == conditionChoice) {
                    chosenCondition = condition;
                    break;
                }
            }

            if (chosenCondition == null) {
                System.out.println("Invalid choice.");
                return;
            }

            // Validation: can’t return in better condition than it was borrowed
            BookCondition originalCondition = copyToReturn.getCondition();
            if (!chosenCondition.canBeAssigned(originalCondition)) {
                System.out.println("Invalid: You cannot return a book in better condition than it was borrowed.");
                return;
            }

            service.returnBook(member, copyToReturn, chosenCondition);

        } else {
            System.out.println("Copy not found or not borrowed by you.");
        }
    }
}
