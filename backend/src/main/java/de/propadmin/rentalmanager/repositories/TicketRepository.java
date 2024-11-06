package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.Ticket;


@RepositoryRestResource(path = "ticket")
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTenantId(Long tenantId);
    List<Ticket> findByLandlordId(Long landlordId);
    List<Ticket> findByCraftsmanFirmId(Long craftsmanFirmId);
}
