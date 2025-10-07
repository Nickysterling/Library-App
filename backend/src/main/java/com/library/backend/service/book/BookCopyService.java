//package com.library.backend.service.book;
//
//import com.library.backend.model.book.Book;
//import com.library.backend.model.book.BookCopy;
//import com.library.backend.repository.book.BookCopyRepository;
//import com.library.backend.repository.book.BookRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class BookCopyService {
//
//    private final BookCopyRepository bookCopyRepository;
//    private final BookRepository bookRepository;
//
//    public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
//        this.bookCopyRepository = bookCopyRepository;
//        this.bookRepository = bookRepository;
//    }
//
//    public List<BookCopy> getAllCopies() {
//        return bookCopyRepository.findAll();
//    }
//
//    @Transactional
//    public BookCopy saveCopy(BookCopy copy) {
//        if (copy.getBook() == null || copy.getBook().getId() == null) {
//            throw new IllegalArgumentException("Book reference is required");
//        }
//        Book book = bookRepository.findById(copy.getBook().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Referenced Book does not exist"));
//        copy.setBook(book);
//        return bookCopyRepository.save(copy);
//    }
//
//    public BookCopy getCopyById(Long id) {
//        return bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Copy not found"));
//    }
//
//    @Transactional
//    public BookCopy updateCopy(Long id, BookCopy updatedCopy) {
//        BookCopy existing = getCopyById(id);
//        if (updatedCopy.getBook() != null && updatedCopy.getBook().getId() != null) {
//            Book book = bookRepository.findById(updatedCopy.getBook().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Referenced Book does not exist"));
//            existing.setBook(book);
//        }
//        if (updatedCopy.getCondition() != null) existing.setCondition(updatedCopy.getCondition());
//        if (updatedCopy.getLocation() != null) existing.setLocation(updatedCopy.getLocation());
//        if (updatedCopy.getBorrowed() != null) existing.setBorrowed(updatedCopy.getBorrowed());
//        return bookCopyRepository.save(existing);
//    }
//
//    @Transactional
//    public void deleteCopy(Long id) {
//        if (!bookCopyRepository.existsById(id)) {
//            throw new RuntimeException("BookCopy not found");
//        }
//        bookCopyRepository.deleteById(id);
//    }
//}
