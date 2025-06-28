package de.propadmin.rentalmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.Landlord;

@RepositoryRestResource(path = "landlord")
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    Landlord findByUserAccountEmail(String email);
}
