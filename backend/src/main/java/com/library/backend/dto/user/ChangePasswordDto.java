package com.library.backend.dto.user;

public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;

    // Default constructor
    public ChangePasswordDto() {}

    // Getters
    public String getOldPassword() { return oldPassword; }
    public String getNewPassword() { return newPassword; }

    // Setters
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
