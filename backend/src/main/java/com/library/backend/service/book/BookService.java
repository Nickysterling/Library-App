//package com.library.backend.service.book;
//
//import com.library.backend.model.book.Book;
//import com.library.backend.repository.book.BookRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookService {
//
//    private final BookRepository bookRepository;
//
//    public BookService(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }
//
//    public Book saveBook(Book book) {
//        if (bookRepository.existsByIsbn(book.getIsbn())) {
//            throw new IllegalArgumentException("Book with this ISBN already exists");
//        }
//        return bookRepository.save(book);
//    }
//
//
//    public Book getBookById(Long id) {
//        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
//    }
//}
