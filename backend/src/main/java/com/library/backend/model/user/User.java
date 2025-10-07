package com.library.backend.model.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all users in the system (members, admins).
 * Stores personal and authentication information.
 */
@MappedSuperclass
public abstract class User {
    // ---------------- Personal Information -----------------
    /** Unique identifier for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /** First name of the user. */
    @Column(nullable = false)
    protected String firstName;

    /** Last name of the user. */
    @Column(nullable = false)
    protected String lastName;

    /** Email address of the user. */
    @Column(nullable = false, unique = true)
    protected String email;

    // ---------------- Authentication & Status -----------------
    /** Password for user authentication. */
    @Column(nullable = false)
    protected String password;

    /** Indicates if the user is active. */
    @Column(nullable = false)
    protected boolean isActive;

    /** Last login timestamp. */
    @Column
    protected String lastLogin;

    /** Timestamp when the user was created. */
    @Column(updatable = false, nullable = false)
    protected String createdAt;

    /** Timestamp when the user was last updated. */
    @Column(nullable = false)
    protected String updatedAt;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ---------------- Constructors -----------------
    // -- Default constructor --
    public User() {}

    // -- Parameterized constructor --
    /**
     * Constructs a user with personal information.
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param password Password
     */
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // ---------------- Lifecycle Callbacks -----------------
    /**
     * Sets creation and update timestamps automatically.
     */
    @PrePersist
    protected void onCreate() {
        String now = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        createdAt = now;
        updatedAt = now;
        isActive = true;
    }

    /**
     * Updates the timestamp when the user is modified.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    // ---------------- Getters -----------------
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }

    public String getPassword() { return password; }
    public boolean isActive() { return isActive; }
    public String getLastLogin() { return lastLogin; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    // ---------------- Setters -----------------
    // -- For user-editable fields only --
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // -- For internal business logic only --
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        if (lastLogin != null)
            this.lastLogin = lastLogin.format(DATE_TIME_FORMATTER);
    }

    public void setUpdatedAt(LocalDateTime lastUpdated) {
        if (lastUpdated != null)
            this.updatedAt = lastUpdated.format(DATE_TIME_FORMATTER);
    }
}
