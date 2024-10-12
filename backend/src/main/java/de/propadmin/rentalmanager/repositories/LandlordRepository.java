package de.propadmin.rentalmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rentalmanager.models.Landlord;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    // Additional custom queries can be defined here if needed
}
