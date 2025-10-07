package com.library.backend.service.auth;

import com.library.backend.dto.auth.LoginRequestDto;
import com.library.backend.model.user.Member;
import com.library.backend.service.user.MemberService;
import com.library.backend.mapper.AuthMapper;
import com.library.backend.dto.auth.SignupRequestDto;

import com.library.backend.exception.EmailAlreadyExistsException;
import com.library.backend.exception.UserNotFoundException;
import com.library.backend.exception.InvalidCredentialsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    public AuthService(MemberService memberService, PasswordEncoder passwordEncoder, AuthMapper authMapper) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.authMapper = authMapper;
    }

    // ----------------- REGISTER -----------------
    public Member registerMember(SignupRequestDto signupDto) {
        // Check if email already exists
        if (memberService.getUserByEmail(signupDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email " + signupDto.getEmail() + " is already registered");
        }

        // Map DTO -> Entity
        Member member = authMapper.toEntity(signupDto);

        // Hash password
        String hashedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPassword);

        // Create member
        return memberService.createUser(member)
                .orElseThrow(() -> new RuntimeException("Failed to create member"));
    }

    // ----------------- LOGIN -----------------
    public Member loginMember(LoginRequestDto loginDto) {
        // Retrieve member by email
        Member member = memberService.getUserByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("No member found with email: " + loginDto.getEmail()));

        // Verify password
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new InvalidCredentialsException("Invalid password for email: " + loginDto.getEmail());
        }

        // Update last login
        memberService.setLastLogin(member.getId(), LocalDateTime.now())
                .orElseThrow(() -> new RuntimeException("Failed to update last login"));

        return member;
    }
}
