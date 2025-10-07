//package com.library.backend.service.user;
//
//import com.library.backend.model.user.Admin;
//import com.library.backend.repository.user.AdminRepository;
//import org.springframework.stereotype.Service;
//
///**
// * Service class for admin-specific operations and queries logic.
// * <p>
// * Extends {@link UserService} to reuse generic user management logic for {@link Admin} entities.
// * Uses {@link AdminRepository} for data access.
// */
//@Service
//public class AdminService extends UserService<Admin, AdminRepository> {
//    /**
//     * Constructs the service with the given admin repository.
//     * @param repository the admin repository
//     */
//    public AdminService(AdminRepository repository) {
//        super(repository);
//    }
//
//    // Add any Admin-specific queries or operations here
//}
