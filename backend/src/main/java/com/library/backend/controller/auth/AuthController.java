package com.library.backend.controller.auth;

import com.library.backend.model.user.Member;

import com.library.backend.dto.user.MemberDto;
import com.library.backend.dto.auth.LoginRequestDto;
import com.library.backend.dto.auth.SignupRequestDto;
import com.library.backend.mapper.MemberMapper;

import com.library.backend.service.auth.AuthService;

import com.library.backend.exception.EmailAlreadyExistsException;
import com.library.backend.exception.UserNotFoundException;
import com.library.backend.exception.InvalidCredentialsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MemberMapper memberMapper;

    public AuthController(AuthService authService, MemberMapper memberMapper) {
        this.authService = authService;
        this.memberMapper = memberMapper;
    }

    // ----------------- REGISTER -----------------
    @PostMapping("/register")
    public ResponseEntity<MemberDto> register(@RequestBody SignupRequestDto dto) {
        try {
            Member created = authService.registerMember(dto);
            return new ResponseEntity<>(memberMapper.toDto(created), HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------- LOGIN -----------------
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody LoginRequestDto dto) {
        try {
            Member member = authService.loginMember(dto);
            return new ResponseEntity<>(memberMapper.toDto(member), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}