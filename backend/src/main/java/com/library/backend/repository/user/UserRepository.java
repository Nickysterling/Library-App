package com.library.backend.repository.user;

import com.library.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface for user entities.
 * <p>
 * Provides common query methods for searching and filtering users.
 * <p>
 * This interface uses generics:
 * <ul>
 *   <li><b>T</b>: The user entity type (e.g., Member, Admin), must extend {@link com.library.backend.model.user.User}.</li>
 * </ul>
 * Extend this interface for specific user repositories (such as MemberRepository or AdminRepository).
 * Annotated with {@link org.springframework.data.repository.NoRepositoryBean} to prevent direct instantiation.
 */
@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    // ----------------- Search by Name -----------------
    List<T> findByFirstNameContainingIgnoreCase(String firstName);
    List<T> findByLastNameContainingIgnoreCase(String lastName);
    List<T> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    // ----------------- Search by Email -----------------
    boolean existsByEmail(String email);
    Optional<T> findByEmailIgnoreCase(String email);
    List<T> findByEmailContainingIgnoreCase(String email);

    // ----------------- Filter by Status -----------------
    List<T> findByIsActive(Boolean isActive);
}
