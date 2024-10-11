package de.propadmin.rental_manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rental_manager.models.CraftsmanFirm;

@Repository
public interface CraftsmanFirmRepository extends JpaRepository<CraftsmanFirm, Long> {
    List<CraftsmanFirm> findByLandlordId(Long landlordId);
}