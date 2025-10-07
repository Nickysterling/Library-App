package com.library.backend.service.user;

import com.library.backend.model.user.Member;
import com.library.backend.repository.user.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;


/**
 * Service class for member-specific operations and queries.
 * <p>
 * Extends {@link UserService} to reuse generic user logic for {@link Member} entities.
 * Uses {@link MemberRepository} for data access.
 */
@Service
public class MemberService extends UserService<Member, MemberRepository> {

    public MemberService(MemberRepository repository) {
        super(repository);
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
