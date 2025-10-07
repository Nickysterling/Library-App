//package com.library.backend.model.book;
//
//import com.fasterxml.jackson.annotation.JsonSubTypes;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//
//import jakarta.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "books")
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "book_type"
//)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = PrintedBook.class, name = "PRINTED"),
//        @JsonSubTypes.Type(value = EBook.class, name = "EBOOK")
//})
//public abstract class Book {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Required fields
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false)
//    private String author;
//
//    @Column(nullable = false, unique = true)
//    private String isbn;
//
//    // Optional fields
//    @Column(nullable = true)
//    private String publisher;
//
//    @Column(nullable = true)
//    private Integer yearPublished;
//
//    @Column(nullable = true)
//    private String genre;
//
//    @Column(nullable = true)
//    private String language;
//
//    // Default constructor
//    public Book() {}
//
//    // Constructor with required fields
//    public Book(String title, String author, String isbn) {
//        this.title = title;
//        this.author = author;
//        this.isbn = isbn;
//    }
//
//    // Constructor with all fields
//    public Book(String title, String author, String isbn,
//                String publisher, Integer yearPublished, String genre, String language) {
//        this.title = title;
//        this.author = author;
//        this.isbn = isbn;
//        this.publisher = publisher;
//        this.yearPublished = yearPublished;
//        this.genre = genre;
//        this.language = language;
//    }
//
//    // -------------------
//    // Getters
//    // -------------------
//    public Long getId() { return id; }
//    public String getTitle() { return title; }
//    public String getAuthor() { return author; }
//    public String getIsbn() { return isbn; }
//
//    public String getPublisher() { return publisher; }
//    public Integer getYearPublished() { return yearPublished; }
//    public String getGenre() { return genre; }
//    public String getLanguage() { return language; }
//
//    // -------------------
//    // Setters
//    // -------------------
//    public void setTitle(String title) { this.title = title; }
//    public void setAuthor(String author) { this.author = author; }
//    public void setIsbn(String isbn) { this.isbn = isbn; }
//
//    public void setPublisher(String publisher) { this.publisher = publisher; }
//    public void setYearPublished(Integer yearPublished) { this.yearPublished = yearPublished; }
//    public void setGenre(String genre) { this.genre = genre; }
//    public void setLanguage(String language) { this.language = language; }
//
//    @Override
//    public String toString() {
//        return title + " by " + author;
//    }
//
//    // -------------------
//    // equals & hashCode based on ISBN
//    // -------------------
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Book)) return false;
//        Book book = (Book) o;
//        return isbn.equals(book.isbn);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(isbn);
//    }
//}
