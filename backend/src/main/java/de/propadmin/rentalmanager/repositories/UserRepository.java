package de.propadmin.rentalmanager.repositories;

import de.propadmin.rentalmanager.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email);
}