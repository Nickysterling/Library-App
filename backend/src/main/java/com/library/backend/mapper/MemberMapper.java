package com.library.backend.mapper;

import com.library.backend.model.user.Member;

import com.library.backend.dto.user.MemberDto;
import com.library.backend.dto.user.ChangePasswordDto;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    // ----------------- Entity -> DTO -----------------

    // Member -> MemberDto (Show user non-sensitive info)
    public MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.isActive(),
                member.getFines()
        );
    }

    // ----------------- DTO -> Entity -----------------

    // MemberDto -> Member (Update member non-sensitive info)
    public Member toEntity(MemberDto dto, Member existingMember) {
        existingMember.setFirstName(dto.getFirstName());
        existingMember.setLastName(dto.getLastName());
        existingMember.setEmail(dto.getEmail());
        return existingMember;
    }

    // ChangePasswordDto -> Member (Password update)
    public Member toEntity(ChangePasswordDto dto, Member existingMember) {
        existingMember.setPassword(dto.getNewPassword());
        return existingMember;
    }

}
