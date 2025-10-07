//package com.library.backend.model.book;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "ebooks")
//@PrimaryKeyJoinColumn(name = "id")
//@DiscriminatorValue("EBOOK")
//public class EBook extends Book {
//
//    // Required fields
//    @Column(nullable = false)
//    private String fileFormat;
//
//    @Column(nullable = false)
//    private Double fileSizeMB;
//
//    // Default constructor
//    protected EBook() {}
//
//    public EBook(String title, String author, String isbn, Double fileSizeMB, String fileFormat) {
//        super(title, author, isbn);
//        this.fileSizeMB = fileSizeMB;
//        this.fileFormat = fileFormat;
//    }
//
//    // -------------------
//    // Getters
//    // -------------------
//    public String getFileFormat() { return fileFormat; }
//    public Double getFileSizeMB() { return fileSizeMB; }
//
//    // -------------------
//    // Setters
//    // -------------------
//    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }
//    public void setFileSizeMB(Double fileSizeMB) { this.fileSizeMB = fileSizeMB; }
//
//    @Override
//    public String toString() {
//        return super.toString() + " [" + fileFormat + ", " + fileSizeMB + "MB]";
//    }
//}
