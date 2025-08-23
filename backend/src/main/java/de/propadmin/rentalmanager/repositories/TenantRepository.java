package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.Tenant;

@RepositoryRestResource(path = "tenants")
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByUserAccountEmail(String email);
    
    @Query("SELECT DISTINCT t FROM Tenant t JOIN t.contracts c WHERE c.landlord.id = :landlordId")
    List<Tenant> findByContractsLandlordId(@Param("landlordId") Long landlordId);
}