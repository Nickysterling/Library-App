//package com.library.backend.controller.user;
//
//import com.library.backend.model.user.Member;
//import com.library.backend.service.user.MemberService;
//
//import com.library.backend.dto.user.CreateUserDto;
//import com.library.backend.dto.user.MemberDto;
//import com.library.backend.mapper.MemberMapper;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * REST controller for managing library members.
// */
//@RestController
//@RequestMapping("/members")
//public class MemberController {
//
//    private final MemberService memberService;
//    private final MemberMapper memberMapper;
//
//    public MemberController(MemberService memberService, MemberMapper memberMapper) {
//        this.memberService = memberService;
//        this.memberMapper = memberMapper;
//    }
//
//    // ----------------- Create Member -----------------
//    /**
//     * Creates a new member.
//     * @param dto CreateUserDto containing member details
//     * @return ResponseEntity with the created member and HTTP status
//     */
//    @PostMapping
//    public ResponseEntity<MemberDto> createMember(@RequestBody CreateUserDto dto) {
//        Member member = memberMapper.toEntity(dto);
//        Member saved = memberService.createUser(member);
//        return ResponseEntity.status(HttpStatus.CREATED).body(memberMapper.toDto(saved));
//    }
//
//    // ----------------- Get Member -----------------
//    /**
//     * Retrieves a member by their unique ID.
//     * @param id Member ID
//     * @return ResponseEntity with the Member and HTTP status
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id) {
//        Member member = memberService.getUserById(id);
//        return ResponseEntity.ok(memberMapper.toDto(member));
//    }
//
//    /**
//     * Retrieves a member by their email address.
//     * @param email Member email
//     * @return ResponseEntity with the Member and HTTP status
//     */
//    @GetMapping("/email/{email}")
//    public ResponseEntity<MemberDto> getMemberByEmail(@PathVariable String email) {
//        Member member = memberService.getUserByEmail(email);
//        return ResponseEntity.ok(memberMapper.toDto(member));
//    }
//
//    // ----------------- Update Member -----------------
//    /**
//     * Updates a member's profile.
//     * @param id Member ID
//     * @param updatedDto Member object with updated fields
//     * @return ResponseEntity with the updated Member and HTTP status
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<MemberDto> updateMember(@PathVariable Long id, @RequestBody MemberDto updatedDto) {
//        // Fetch existing member from service
//        Member existingMember = memberService.getUserById(id);
//
//        // Use DTO to update existingMember entity
//        Member updatedMember = memberMapper.toEntity(updatedDto, existingMember);
//
//        // Update member in the service layer
//        Member savedMember = memberService.updateUser(id, updatedMember);
//
//        return ResponseEntity.ok(memberMapper.toDto(savedMember));
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
//        memberService.deactivateUser(id);
//        return ResponseEntity.noContent().build();
//    }
//}
