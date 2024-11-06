package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.CraftsmanFirm;

@RepositoryRestResource(path = "craftsmanFirm")
public interface CraftsmanFirmRepository extends JpaRepository<CraftsmanFirm, Long> {
    List<CraftsmanFirm> findByLandlordId(Long landlordId);
}