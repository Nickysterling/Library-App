//package com.library.backend.model.book;
//
//import jakarta.persistence.*;
//import com.library.backend.model.enums.BookCondition;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import java.util.Objects;
//
//@Entity
//@Table(name = "book_copies")
//public class BookCopy {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Required fields
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Book book;
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private BookCondition condition;
//
//    @Column(nullable = false)
//    private String location;
//
//    @Column(nullable = false)
//    private Boolean borrowed;
//
//    // Default constructor
//    protected BookCopy() {}
//
//    // Constructor for required fields
//    public BookCopy(Book book, BookCondition condition, String location, Boolean borrowed) {
//        this.book = book;
//        this.condition = condition;
//        this.location = location;
//        this.borrowed = borrowed;
//    }
//
//    // -------------------
//    // Getters
//    // -------------------
//    public Long getId() { return id; }
//    public Book getBook() { return book; }
//    public BookCondition getCondition() { return condition; }
//    public String getLocation() { return location; }
//    public Boolean getBorrowed() { return borrowed; }
//
//    // -------------------
//    // Setters
//    // -------------------
//    public void setBook(Book book) { this.book = book; }
//    public void setCondition(BookCondition condition) { this.condition = condition; }
//    public void setLocation(String location) { this.location = location; }
//    public void setBorrowed(Boolean borrowed) { this.borrowed = borrowed; }
//
//    // -------------------
//    // equals & hashCode based on ID
//    // -------------------
//    @Override
//    public String toString() {
//        return "[Copy ID: " + id + "] " + book.toString() + " [Condition: " + condition + "]";
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof BookCopy)) return false;
//        BookCopy bookCopy = (BookCopy) o;
//        return Objects.equals(id, bookCopy.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//}
