package de.propadmin.rentalmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.propadmin.rentalmanager.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}