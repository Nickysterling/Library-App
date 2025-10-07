//package com.library.backend.controller.book;
//
//import com.library.backend.model.book.Book;
//import com.library.backend.service.book.BookService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/books")
//public class BookController {
//
//    private final BookService bookService;
//
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }
//
//    // -------------------
//    // Create a book (PrintedBook or EBook)
//    // -------------------
//    @PostMapping
//    public ResponseEntity<Book> createBook(@RequestBody Book book) {
//        try {
//            Book savedBook = bookService.saveBook(book);
//            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
//        }
//    }
//
//    // -------------------
//    // Get all books
//    // -------------------
//    @GetMapping
//    public List<Book> getAllBooks() {
//        return bookService.getAllBooks();
//    }
//
//    // -------------------
//    // Get book by ID
//    // -------------------
//    @GetMapping("/{id}")
//    public Book getBookById(@PathVariable Long id) {
//        return bookService.getBookById(id);
//    }
//}
