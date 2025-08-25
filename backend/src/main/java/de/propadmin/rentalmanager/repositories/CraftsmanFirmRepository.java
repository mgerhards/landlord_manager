package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.CraftsmanFirm;

@RepositoryRestResource(path = "craftsmanFirm")
public interface CraftsmanFirmRepository extends JpaRepository<CraftsmanFirm, Long> {
    @Query("SELECT DISTINCT c FROM CraftsmanFirm c " +
           "JOIN c.frameworkContracts fc " +
           "WHERE fc.landlord.id = :landlordId")
    List<CraftsmanFirm> findByLandlordId(@Param("landlordId") Long landlordId);

    @Query("SELECT c FROM CraftsmanFirm c " +
           "JOIN c.frameworkContracts fc " +
           "WHERE fc.landlord.id = :landlordId " +
           "AND fc.isActive = true")
    List<CraftsmanFirm> findActiveByLandlordId(@Param("landlordId") Long landlordId);

    @Query("SELECT c FROM CraftsmanFirm c " +
           "JOIN c.frameworkContracts fc " +
           "WHERE fc.landlord.id = :landlordId " +
           "AND fc.isActive = true " +
           "AND c.isEmergencyServiceProvider = true " +
           "AND c.emergencyHourlyRate IS NOT NULL " +
           "ORDER BY c.emergencyHourlyRate ASC")
    List<CraftsmanFirm> findCheapestEmergencyCompanyByLandlordId(@Param("landlordId") Long landlordId);
}