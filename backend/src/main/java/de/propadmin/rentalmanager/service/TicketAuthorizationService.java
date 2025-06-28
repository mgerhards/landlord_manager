package de.propadmin.rentalmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.ContactPerson;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.repositories.ContactPersonRepository;
import de.propadmin.rentalmanager.repositories.LandlordRepository;
import de.propadmin.rentalmanager.repositories.TenantRepository;
import de.propadmin.rentalmanager.repositories.TicketRepository;

@Service
public class TicketAuthorizationService {

    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private LandlordRepository landlordRepository;
    
    @Autowired
    private ContactPersonRepository contactPersonRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Checks if the current authenticated user can view the specified ticket
     * 
     * @param ticketId The ID of the ticket to check
     * @return true if the user has permission to view the ticket, false otherwise
     */
    public boolean canViewTicket(Long ticketId) {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            return false;
        }

        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            return false;
        }

        Ticket ticket = ticketOpt.get();

        // Check if user is the tenant who created the ticket
        if (isUserTenantForTicket(currentUserEmail, ticket)) {
            return true;
        }

        // Check if user is the landlord associated with the real estate object
        if (isUserLandlordForTicket(currentUserEmail, ticket)) {
            return true;
        }

        // Check if user is a contact person from the assigned craftsman firm
        if (isUserContactPersonForTicket(currentUserEmail, ticket)) {
            return true;
        }

        return false;
    }

    /**
     * Gets a ticket only if the current user has permission to view it
     * 
     * @param ticketId The ID of the ticket to retrieve
     * @return Optional containing the ticket if authorized, empty otherwise
     */
    public Optional<Ticket> getAuthorizedTicket(Long ticketId) {
        if (canViewTicket(ticketId)) {
            return ticketRepository.findById(ticketId);
        }
        return Optional.empty();
    }

    private String getCurrentUserEmail() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isUserTenantForTicket(String userEmail, Ticket ticket) {
        Tenant tenant = tenantRepository.findByUserAccountEmail(userEmail);
        return tenant != null && ticket.getTenant() != null && 
               tenant.getId().equals(ticket.getTenant().getId());
    }

    private boolean isUserLandlordForTicket(String userEmail, Ticket ticket) {
        Landlord landlord = landlordRepository.findByUserAccountEmail(userEmail);
        if (landlord == null || ticket.getAsset() == null) {
            return false;
        }
        
        // Check through the real estate object's landlord
        return ticket.getAsset().getLandlord() != null && 
               landlord.getId().equals(ticket.getAsset().getLandlord().getId());
    }

    private boolean isUserContactPersonForTicket(String userEmail, Ticket ticket) {
        if (ticket.getCraftsmanFirm() == null) {
            return false;
        }
        
        Optional<ContactPerson> contactPersonOpt = contactPersonRepository
            .findByUserAccountEmailAndCraftsmanFirmId(userEmail, ticket.getCraftsmanFirm().getId());
        
        return contactPersonOpt.isPresent();
    }
}