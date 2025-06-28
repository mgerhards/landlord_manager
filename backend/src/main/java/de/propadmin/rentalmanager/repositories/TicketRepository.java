package de.propadmin.rentalmanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.enums.TicketStatus;


@RepositoryRestResource(path = "ticket")
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTenantId(Long tenantId);
    List<Ticket> findByLandlordId(Long landlordId);
    List<Ticket> findByCraftsmanFirmId(Long craftsmanFirmId);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByAssetId(Long assetId);
    Optional<Ticket> findByIdAndTenantId(Long id, Long tenantId);
    Optional<Ticket> findByIdAndCraftsmanFirmId(Long id, Long craftsmanFirmId);
    
    // Find tickets where the landlord owns the real estate object
    @Query("SELECT t FROM Ticket t WHERE t.id = :ticketId AND t.asset.landlord.id = :landlordId")
    Optional<Ticket> findByIdAndAssetLandlordId(@Param("ticketId") Long ticketId, @Param("landlordId") Long landlordId);
}
