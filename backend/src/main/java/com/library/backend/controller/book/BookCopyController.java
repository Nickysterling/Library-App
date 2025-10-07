//package com.library.backend.controller.book;
//
//import com.library.backend.model.book.BookCopy;
//import com.library.backend.service.book.BookCopyService;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/book-copies")
//public class BookCopyController {
//
//    private final BookCopyService bookCopyService;
//
//    public BookCopyController(BookCopyService bookCopyService) {
//        this.bookCopyService = bookCopyService;
//    }
//
//    // -------------------
//    // Create a book copy
//    // -------------------
//    @PostMapping
//    public ResponseEntity<?> createBookCopy(@RequestBody BookCopy copy) {
//        try {
//            BookCopy saved = bookCopyService.saveCopy(copy);
//            return new ResponseEntity<>(saved, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // -------------------
//    // Get all copies
//    // -------------------
//    @GetMapping
//    public ResponseEntity<List<BookCopy>> getAllCopies() {
//        return ResponseEntity.ok(bookCopyService.getAllCopies());
//    }
//
//    // -------------------
//    // Get copy by ID
//    // -------------------
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCopyById(@PathVariable Long id) {
//        try {
//            BookCopy copy = bookCopyService.getCopyById(id);
//            return ResponseEntity.ok(copy);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // -------------------
//    // Update a book copy
//    // -------------------
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateCopy(@PathVariable Long id, @RequestBody BookCopy updatedCopy) {
//        try {
//            BookCopy copy = bookCopyService.updateCopy(id, updatedCopy);
//            return ResponseEntity.ok(copy);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // -------------------
//    // Delete a book copy
//    // -------------------
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteCopy(@PathVariable Long id) {
//        try {
//            bookCopyService.deleteCopy(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//}
