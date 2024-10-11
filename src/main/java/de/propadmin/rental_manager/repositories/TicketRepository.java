package de.propadmin.rental_manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rental_manager.models.Ticket;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTenantId(Long tenantId);
    List<Ticket> findByLandlordId(Long landlordId);
    List<Ticket> findByCraftsmanFirmId(Long craftsmanFirmId);
}
