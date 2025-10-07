//package com.library.backend.model.user;
//
//import jakarta.persistence.*;
//
///**
// * This class extends User and represents a library administrator.
// * Used for managing books and system operations.
// */
//@Entity
//@Table(name = "admins")
//public class Admin extends User {
//    // ---------------- Constructors -----------------
//    // -- Default Constructor --
//    public Admin() { super(); }
//
//    // -- Parameterized Constructor --
//    /**
//     * Constructs an admin with personal information.
//     * @param firstName Admin's first name
//     * @param lastName Admin's last name
//     * @param email Admin's email
//     * @param password Admin's password
//     */
//    public Admin(String firstName, String lastName, String email, String password) {
//        super(firstName, lastName, email, password);
//    }
//
//    @Override
//    public String toString() {
//        return "Admin #" + id + ": " + firstName + " " + lastName;
//    }
//}
