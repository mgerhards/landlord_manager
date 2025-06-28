package de.propadmin.rentalmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.FrameworkContract;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.TicketComment;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.enums.TicketStatus;
import de.propadmin.rentalmanager.repositories.FrameworkContractRepository;
import de.propadmin.rentalmanager.repositories.TicketCommentRepository;
import de.propadmin.rentalmanager.repositories.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private TicketCommentRepository ticketCommentRepository;
    
    @Autowired
    private FrameworkContractRepository frameworkContractRepository;

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

    // Business Logic Methods for CRUD Operations

    /**
     * Opens a new ticket by tenant with optional comment
     */
    public Ticket openTicket(Ticket ticket, String comment, String createdBy) {
        ticket.setStatus(TicketStatus.OPEN);
        Ticket savedTicket = ticketRepository.save(ticket);
        
        if (comment != null && !comment.trim().isEmpty()) {
            addComment(savedTicket.getId(), comment, createdBy);
        }
        
        return savedTicket;
    }

    /**
     * Closes a ticket by tenant with optional comment
     */
    public Ticket closeTicket(Long ticketId, Long tenantId, String comment, String createdBy) {
        Optional<Ticket> ticketOpt = ticketRepository.findByIdAndTenantId(ticketId, tenantId);
        if (ticketOpt.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found or not owned by tenant");
        }
        
        Ticket ticket = ticketOpt.get();
        ticket.setStatus(TicketStatus.CLOSED);
        Ticket savedTicket = ticketRepository.save(ticket);
        
        if (comment != null && !comment.trim().isEmpty()) {
            addComment(ticketId, comment, createdBy);
        }
        
        return savedTicket;
    }

    /**
     * Assigns a craftsman firm to a ticket manually by landlord with optional comment
     */
    public Ticket assignCraftsmanFirm(Long ticketId, Long craftsmanFirmId, String comment, String createdBy) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found");
        }
        
        Ticket ticket = ticketOpt.get();
        CraftsmanFirm craftsmanFirm = new CraftsmanFirm();
        craftsmanFirm.setId(craftsmanFirmId);
        ticket.setCraftsmanFirm(craftsmanFirm);
        ticket.setStatus(TicketStatus.IN_PROGRESS);
        Ticket savedTicket = ticketRepository.save(ticket);
        
        if (comment != null && !comment.trim().isEmpty()) {
            addComment(ticketId, comment, createdBy);
        }
        
        return savedTicket;
    }

    /**
     * Auto-assigns a craftsman firm based on active framework contracts for the landlord
     */
    public Ticket autoAssignCraftsmanFirm(Long ticketId, String comment, String createdBy) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found");
        }
        
        Ticket ticket = ticketOpt.get();
        Long landlordId = ticket.getLandlord().getId();
        
        List<FrameworkContract> activeContracts = frameworkContractRepository.findActiveByLandlordId(landlordId);
        if (!activeContracts.isEmpty()) {
            // Simple assignment: take the first active contract
            // In a real implementation, you might want more sophisticated logic 
            // based on trade type, location, etc.
            FrameworkContract contract = activeContracts.get(0);
            ticket.setCraftsmanFirm(contract.getCraftsmanFirm());
            ticket.setStatus(TicketStatus.IN_PROGRESS);
            
            Ticket savedTicket = ticketRepository.save(ticket);
            
            if (comment != null && !comment.trim().isEmpty()) {
                addComment(ticketId, comment, createdBy);
            }
            
            return savedTicket;
        } else {
            throw new IllegalStateException("No active framework contracts found for auto-assignment");
        }
    }

    /**
     * Resolves a ticket by craftsman firm contact with optional comment
     */
    public Ticket resolveTicket(Long ticketId, Long craftsmanFirmId, String comment, String createdBy) {
        Optional<Ticket> ticketOpt = ticketRepository.findByIdAndCraftsmanFirmId(ticketId, craftsmanFirmId);
        if (ticketOpt.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found or not assigned to this craftsman firm");
        }
        
        Ticket ticket = ticketOpt.get();
        ticket.setStatus(TicketStatus.RESOLVED);
        Ticket savedTicket = ticketRepository.save(ticket);
        
        if (comment != null && !comment.trim().isEmpty()) {
            addComment(ticketId, comment, createdBy);
        }
        
        return savedTicket;
    }

    /**
     * Adds a comment to a ticket
     */
    public TicketComment addComment(Long ticketId, String comment, String createdBy) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found");
        }
        
        TicketComment ticketComment = new TicketComment();
        ticketComment.setTicket(ticketOpt.get());
        ticketComment.setComment(comment);
        ticketComment.setCreatedBy(createdBy);
        
        return ticketCommentRepository.save(ticketComment);
    }

    /**
     * Gets all comments for a ticket
     */
    public List<TicketComment> getTicketComments(Long ticketId) {
        return ticketCommentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}

