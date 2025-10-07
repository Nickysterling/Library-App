package com.library.backend.model.user;

import jakarta.persistence.*;

/**
 * This class extends User and represents a library member.
 * Used for storing personal and membership-related information.
 */
@Entity
@Table(name = "members")
public class Member extends User {
    // ---------------- Member-Specific Fields -----------------
    /**
     * Maximum number of books a member can borrow at once.
     */
    @Transient
    public static final int MAX_BOOKS_ALLOWED = 5;

    /**
     * Outstanding fines for the member.
     */
    @Column(nullable = false)
    private Double fines = 0.0;

    // ---------------- Constructors -----------------
    // -- Default constructor --
    public Member() { super(); }

    // -- Parameterized constructor --
    /**
     * Constructs a member with personal information.
     * @param firstName Member's first name
     * @param lastName Member's last name
     * @param email Member's email
     * @param password Member's password
     */
    public Member(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    // ---------------- Lifecycle Callbacks -----------------
    /**
     * Initializes membership date and default business fields on creation.
     */
    @PrePersist
    protected void onCreateMember() {
        super.onCreate();
        fines = 0.0;
    }

    // ---------------- Getters -----------------
    public Double getFines() { return fines; }

    // ---------------- Setters -----------------
    // -- For internal business logic only --
    public void setFines(Double fines) { this.fines = fines; }

    @Override
    public String toString() {
        return "Member #" + id + ": " + firstName + " " + lastName;
    }
}
