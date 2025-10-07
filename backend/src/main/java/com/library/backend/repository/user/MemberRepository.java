package com.library.backend.repository.user;

import com.library.backend.model.user.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Member} entities.
 * <p>
 * Extends {@link UserRepository} to inherit common user queries.
 * <p>
 * Use this interface for data access and custom queries related to library members.
 */
@Repository
public interface MemberRepository extends UserRepository<Member> {

}
