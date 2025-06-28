package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.propadmin.rentalmanager.models.FrameworkContract;

@Repository
public interface FrameworkContractRepository extends JpaRepository<FrameworkContract, Long> {
    List<FrameworkContract> findByLandlordId(Long landlordId);
    List<FrameworkContract> findByCraftsmanFirmId(Long craftsmanFirmId);
    
    @Query("SELECT fc FROM FrameworkContract fc WHERE fc.landlord.id = :landlordId AND fc.isActive = true")
    List<FrameworkContract> findActiveByLandlordId(@Param("landlordId") Long landlordId);
}
