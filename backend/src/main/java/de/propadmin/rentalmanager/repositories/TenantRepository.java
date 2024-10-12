package de.propadmin.rentalmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rentalmanager.models.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Additional custom queries can be defined here if needed
}