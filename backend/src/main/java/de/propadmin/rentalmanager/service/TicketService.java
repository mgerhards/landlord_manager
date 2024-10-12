package de.propadmin.rentalmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.repositories.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByTenant(Long tenantId) {
        return ticketRepository.findByTenantId(tenantId);
    }

    public List<Ticket> getTicketsByLandlord(Long landlordId) {
        return ticketRepository.findByLandlordId(landlordId);
    }

    public List<Ticket> getTicketsByCraftsmanFirm(Long craftsmanFirmId) {
        return ticketRepository.findByCraftsmanFirmId(craftsmanFirmId);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}

