//package com.library.backend.controller.user;
//
//import com.library.backend.model.user.Admin;
//import com.library.backend.dto.user.UserDto;
//import com.library.backend.model.user.Member;
//import com.library.backend.service.user.AdminService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
///**
// * REST controller for managing admins.
// * Provides endpoints for creating, updating, searching, and listing admins.
// */
//@RestController
//@RequestMapping("/admins")
//public class AdminController {
//    private final AdminService adminService;
//
//    public AdminController(AdminService adminService) {
//        this.adminService = adminService;
//    }
//
//    /**
//     * Creates a new admin.
//     */
//    @PostMapping
//    public ResponseEntity<Admin> createAdmin(@RequestBody UserDto dto) {
//        Admin admin = new Admin(
//                dto.getFirstName(), dto.getLastName(),
//                dto.getEmail(), dto.getPhoneNumber(),
//                dto.getAddress(), dto.getPassword()
//        );
//        Admin saved = adminService.createUser(admin);
//        return new ResponseEntity<>(saved, HttpStatus.CREATED);
//    }
//
//    /**
//     * Retrieves an admin by their unique ID.
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
//        Admin admin = adminService.getUserById(id);
//        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    /**
//     * Retrieves an admin by their email address.
//     */
//    @GetMapping("/email/{email}")
//    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
//        Optional<Admin> admin = adminService.getUserByEmail(email);
//        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    /**
//     * Retrieves all admins.
//     */
//    @GetMapping
//    public ResponseEntity<List<Admin>> getAllAdmins() {
//        List<Admin> admins = adminService.getAllUsers();
//        return new ResponseEntity<>(admins, HttpStatus.OK);
//    }
//
//    /**
//     * Updates an admin's profile.
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
//        try {
//            Admin admin = adminService.updateUser(id, updatedAdmin);
//            return new ResponseEntity<>(admin, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    /**
//     * Deletes an admin by their unique ID.
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
//        try {
//            adminService.deactivateUser(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Mems
//    // -- List All Members --
//    /**
//     * Retrieves all members in the system.
//     * @return ResponseEntity with the list of Members and HTTP status
//     */
//    @GetMapping
//    public ResponseEntity<List<Member>> getAllMembers() {
//        List<Member> members = memberService.getAllUsers();
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    // ----------------- Delete Member -----------------
//    /**
//     * Soft-deletes a member by their unique ID.
//     * @param id Member ID
//     * @return ResponseEntity with HTTP status
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
//        try {
//            memberService.deactivateUser(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // ----------------- Search & Filter Members -----------------
//    // -- Search by Full Name --
//    /**
//     * Searches for members by full name (case-insensitive).
//     * @param firstName Member's first name
//     * @param lastName Member's last name
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/search/fullname")
//    public ResponseEntity<List<Member>> searchByFullName(@RequestParam String firstName, @RequestParam String lastName) {
//        List<Member> results = memberService.searchByFullName(firstName, lastName);
//        return new ResponseEntity<>(results, HttpStatus.OK);
//    }
//
//    // -- Search by First Name --
//    /**
//     * Searches for members by first name (partial, case-insensitive).
//     * @param firstName Substring to search for in first names
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/search/firstname/{firstName}")
//    public ResponseEntity<List<Member>> searchByFirstName(@PathVariable String firstName) {
//        List<Member> results = memberService.searchByFirstName(firstName);
//        return new ResponseEntity<>(results, HttpStatus.OK);
//    }
//
//    // -- Search by Last Name --
//    /**
//     * Searches for members by last name (partial, case-insensitive).
//     * @param lastName Substring to search for in last names
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/search/lastname/{lastName}")
//    public ResponseEntity<List<Member>> searchByLastName(@PathVariable String lastName) {
//        List<Member> results = memberService.searchByLastName(lastName);
//        return new ResponseEntity<>(results, HttpStatus.OK);
//    }
//
//    // -- Search by Phone Number --
//    /**
//     * Retrieves members by phone number.
//     * @param phoneNumber Member's phone number
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/phone/{phoneNumber}")
//    public ResponseEntity<List<Member>> getMembersByPhoneNumber(@PathVariable String phoneNumber) {
//        List<Member> members = memberService.getUsersByPhoneNumber(phoneNumber);
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    // -- Search by Address --
//    /**
//     * Retrieves members by address (partial match).
//     * @param address Substring to search for in addresses
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/address/{address}")
//    public ResponseEntity<List<Member>> getMembersByAddress(@PathVariable String address) {
//        List<Member> members = memberService.getUsersByAddress(address);
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    // -- Filter by Active Status --
//    /**
//     * Retrieves all active members.
//     * @return ResponseEntity with the list of active Members and HTTP status
//     */
//    @GetMapping("/active")
//    public ResponseEntity<List<Member>> getActiveMembers() {
//        List<Member> members = memberService.getActiveUsers();
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    // -- Filter by Inactive Status --
//    /**
//     * Retrieves all inactive members.
//     * @return ResponseEntity with the list of inactive Members and HTTP status
//     */
//    @GetMapping("/inactive")
//    public ResponseEntity<List<Member>> getInactiveMembers() {
//        List<Member> members = memberService.getUsersByIsInactive();
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    // --------------------- Set Member Status -----------------
//    /**
//     * Sets the active status of a member.
//     * @param id Member ID
//     * @param isActive New active status
//     * @return ResponseEntity with the updated Member and HTTP status
//     */
//    @PatchMapping("/{id}/status")
//    public ResponseEntity<Member> setMemberStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
//        try {
//            Member member = memberService.setUserStatus(id, isActive);
//            return new ResponseEntity<>(member, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    /**
//     * Updates the last login timestamp for a member to the current time.
//     * @param id Member ID
//     * @return ResponseEntity with the updated Member and HTTP status
//     */
//    @PatchMapping("/{id}/lastlogin")
//    public ResponseEntity<Member> setLastLogin(@PathVariable Long id) {
//        try {
//            Member member = memberService.setLastLogin(id, java.time.LocalDateTime.now());
//            return new ResponseEntity<>(member, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    /**
//     * Retrieves members whose membership date is between the given range, or defaults to now if not provided.
//     * @param startDateTime Start date-time
//     * @param endDateTime End date-time
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/membership/range")
//    public ResponseEntity<List<Member>> getMembersByMembershipDateRange(
//            @RequestParam(required = false) LocalDateTime startDateTime,
//            @RequestParam(required = false) LocalDateTime endDateTime) {
//        LocalDateTime start = (startDateTime != null) ? startDateTime : LocalDateTime.now();
//        LocalDateTime end = (endDateTime != null) ? endDateTime : LocalDateTime.now();
//        List<Member> members = memberService.getMembersByMembershipDateBetween(start, end);
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    /**
//     * Retrieves members who joined before the given date-time, or defaults to now if not provided.
//     * @param dateTime Date-time to compare
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/membership/before")
//    public ResponseEntity<List<Member>> getMembersByMembershipDateBefore(
//            @RequestParam(required = false) LocalDateTime dateTime) {
//        LocalDateTime compareDateTime = (dateTime != null) ? dateTime : LocalDateTime.now();
//        List<Member> members = memberService.getMembersByMembershipDateBefore(compareDateTime);
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//    /**
//     * Retrieves members who joined after the given date-time, or defaults to now if not provided.
//     * @param dateTime Date-time to compare
//     * @return ResponseEntity with the list of matching Members and HTTP status
//     */
//    @GetMapping("/membership/after")
//    public ResponseEntity<List<Member>> getMembersByMembershipDateAfter(
//            @RequestParam(required = false) LocalDateTime dateTime) {
//        LocalDateTime compareDateTime = (dateTime != null) ? dateTime : LocalDateTime.now();
//        List<Member> members = memberService.getMembersByMembershipDateAfter(compareDateTime);
//        return new ResponseEntity<>(members, HttpStatus.OK);
//    }
//
//
//
//}
//
