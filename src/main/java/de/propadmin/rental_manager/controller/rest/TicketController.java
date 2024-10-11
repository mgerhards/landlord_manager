package de.propadmin.rental_manager.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rental_manager.models.Ticket;
import de.propadmin.rental_manager.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Ticket>> getTicketsByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(ticketService.getTicketsByTenant(tenantId));
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<Ticket>> getTicketsByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(ticketService.getTicketsByLandlord(landlordId));
    }

    @GetMapping("/craftsman-firm/{firmId}")
    public ResponseEntity<List<Ticket>> getTicketsByCraftsmanFirm(@PathVariable Long firmId) {
        return ResponseEntity.ok(ticketService.getTicketsByCraftsmanFirm(firmId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
