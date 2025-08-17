package de.propadmin.rentalmanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.TicketComment;
import de.propadmin.rentalmanager.service.TicketAuthorizationService;
import de.propadmin.rentalmanager.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private TicketAuthorizationService ticketAuthorizationService;

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long ticketId) {
        try {
            Optional<Ticket> ticketOpt = ticketAuthorizationService.getAuthorizedTicket(ticketId);
            if (ticketOpt.isPresent()) {
                return ResponseEntity.ok(ticketOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Ticket> openTicket(@RequestBody Ticket ticket, 
                                           @RequestParam(required = false) String comment,
                                           @RequestParam(required = false) String createdBy) {
        try {
            Ticket savedTicket = ticketService.openTicket(ticket, comment, createdBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{ticketId}/close")
    public ResponseEntity<Ticket> closeTicket(@PathVariable Long ticketId,
                                             @RequestParam Long tenantId,
                                             @RequestParam(required = false) String comment,
                                             @RequestParam(required = false) String createdBy) {
        try {
            Ticket closedTicket = ticketService.closeTicket(ticketId, tenantId, comment, createdBy);
            return ResponseEntity.ok(closedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{ticketId}/assign")
    public ResponseEntity<Ticket> assignCraftsmanFirm(@PathVariable Long ticketId,
                                                     @RequestParam Long craftsmanFirmId,
                                                     @RequestParam(required = false) String comment,
                                                     @RequestParam(required = false) String createdBy) {
        try {
            Ticket assignedTicket = ticketService.assignCraftsmanFirm(ticketId, craftsmanFirmId, comment, createdBy);
            return ResponseEntity.ok(assignedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{ticketId}/auto-assign")
    public ResponseEntity<Ticket> autoAssignCraftsmanFirm(@PathVariable Long ticketId,
                                                        @RequestParam(required = false) String comment,
                                                        @RequestParam(required = false) String createdBy) {
        try {
            Ticket assignedTicket = ticketService.autoAssignCraftsmanFirm(ticketId, comment, createdBy);
            return ResponseEntity.ok(assignedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{ticketId}/resolve")
    public ResponseEntity<Ticket> resolveTicket(@PathVariable Long ticketId,
                                               @RequestParam Long craftsmanFirmId,
                                               @RequestParam(required = false) String comment,
                                               @RequestParam(required = false) String createdBy) {
        try {
            Ticket resolvedTicket = ticketService.resolveTicket(ticketId, craftsmanFirmId, comment, createdBy);
            return ResponseEntity.ok(resolvedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{ticketId}/comments")
    public ResponseEntity<TicketComment> addComment(@PathVariable Long ticketId,
                                                   @RequestParam String comment,
                                                   @RequestParam String createdBy) {
        try {
            TicketComment savedComment = ticketService.addComment(ticketId, comment, createdBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{ticketId}/comments")
    public ResponseEntity<List<TicketComment>> getTicketComments(@PathVariable Long ticketId) {
        try {
            // Also check authorization for viewing comments
            if (!ticketAuthorizationService.canViewTicket(ticketId)) {
                return ResponseEntity.notFound().build();
            }
            List<TicketComment> comments = ticketService.getTicketComments(ticketId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}