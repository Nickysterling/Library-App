package com.library.backend.dto.auth;

public class LoginRequestDto {

    private String email;
    private String password;

    // Default constructor
    public LoginRequestDto() {}

    // ---------------- Getters ----------------
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // ---------------- Setters ----------------
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
