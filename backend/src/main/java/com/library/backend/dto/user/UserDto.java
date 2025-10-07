package com.library.backend.dto.user;

public abstract class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;

    // Default constructor
    public UserDto() {}

    public UserDto(Long id, String firstName, String lastName, String email, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
    }

    // Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public boolean getIsActive() { return isActive; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
