package de.propadmin.rentalmanager.repositories;

import de.propadmin.rentalmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}