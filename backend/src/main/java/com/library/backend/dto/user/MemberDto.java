package com.library.backend.dto.user;

public class MemberDto extends UserDto {

    private double fines;

    // Default Constructor
    public MemberDto() {
        super();
    }

    // Parameterized constructor
    public MemberDto(Long id, String firstName, String lastName, String email, boolean isActive, double fines) {
        super(id, firstName, lastName, email, isActive);
        this.fines = fines;
    }

    // Getters
    public double getFines() { return fines; }

    // Setters
    public void setFines(double fines) { this.fines = fines; }
}
