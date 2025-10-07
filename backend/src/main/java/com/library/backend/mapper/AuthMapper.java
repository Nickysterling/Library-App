package com.library.backend.mapper;

import com.library.backend.dto.auth.SignupRequestDto;
import com.library.backend.model.user.Member;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    // ----------------- DTO -> Entity -----------------

    // SignupRequestDto -> Member (Create new member)
    public Member toEntity(SignupRequestDto dto) {
        return new Member(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword()
        );
    }
}
