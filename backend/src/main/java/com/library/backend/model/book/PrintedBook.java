//package com.library.backend.model.book;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "printed_books")
//@PrimaryKeyJoinColumn(name = "id")
//@DiscriminatorValue("PRINTED")
//public class PrintedBook extends Book {
//
//    // Required fields
//    @Column(nullable = false)
//    private Integer pageCount;
//
//    @Column(nullable = false)
//    private String coverType;
//
//    // Default constructor
//    public PrintedBook() {}
//
//    // Constructor for required fields
//    public PrintedBook(String title, String author, String isbn,
//                       Integer pageCount, String coverType) {
//        super(title, author, isbn);
//        this.pageCount = pageCount;
//        this.coverType = coverType;
//    }
//
//    // -------------------
//    // Getters
//    // -------------------
//    public Integer getPageCount() { return pageCount; }
//    public String getCoverType() { return coverType; }
//
//    // -------------------
//    // Setters
//    // -------------------
//    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }
//    public void setCoverType(String coverType) { this.coverType = coverType; }
//
//    @Override
//    public String toString() {
//        return super.toString() + " [" + pageCount + " pages, " + coverType + "]";
//    }
//}
