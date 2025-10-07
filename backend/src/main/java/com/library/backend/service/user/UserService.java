package com.library.backend.service.user;

import com.library.backend.model.user.User;
import com.library.backend.repository.user.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for user-related operations (create, update, search, delete).
 * @param <T> the user entity type (e.g., Member, Admin), must extend {@link User}
 * @param <R> the repository type for the user entity, must extend {@link UserRepository}<{@code T}>
 */
public abstract class UserService<T extends User, R extends UserRepository<T>> {

    protected final R repository;

    protected UserService(R repository) {
        this.repository = repository;
    }

    // ----------------- CREATE -----------------
    @Transactional
    public Optional<T> createUser(T user) {
        if (repository.existsByEmail(user.getEmail())) {
            return Optional.empty();
        }
        return Optional.of(repository.save(user));
    }

    // ----------------- UPDATE -----------------
    @Transactional
    public Optional<T> updateUser(Long id, T updatedUser) {
        Optional<T> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        T user = optionalUser.get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());

        return Optional.of(repository.save(user));
    }

    // ----------------- DELETE -----------------
    @Transactional
    public Optional<T> deactivateUser(Long id) {
        Optional<T> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        T user = optionalUser.get();
        user.setIsActive(false);
        return Optional.of(repository.save(user));
    }

    // ----------------- READ -----------------
    public List<T> getAllUsers() {
        return repository.findAll();
    }

    public Optional<T> getUserById(Long id) {
        return repository.findById(id);
    }

    public Optional<T> getUserByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }

    public List<T> getUserByEmailContaining(String email) {
        return repository.findByEmailContainingIgnoreCase(email);
    }

    public List<T> searchByFullName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
    }

    public List<T> searchByFirstName(String firstName) {
        return repository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<T> searchByLastName(String lastName) {
        return repository.findByLastNameContainingIgnoreCase(lastName);
    }

    public List<T> getActiveUsers() {
        return repository.findByIsActive(true);
    }

    public List<T> getInactiveUsers() {
        return repository.findByIsActive(false);
    }

    // ----------------- Business/Utility -----------------
    @Transactional
    public Optional<T> setUserStatus(Long id, Boolean isActive) {
        Optional<T> userOpt = repository.findById(id);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        T user = userOpt.get();
        user.setIsActive(isActive);
        return Optional.of(repository.save(user));
    }

    @Transactional
    public Optional<T> setLastLogin(Long id, LocalDateTime lastLogin) {
        Optional<T> userOpt = repository.findById(id);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        T user = userOpt.get();
        user.setLastLogin(lastLogin);
        return Optional.of(repository.save(user));
    }
}
