package de.propadmin.rentalmanager.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.dto.TicketStatusDTO;
import de.propadmin.rentalmanager.models.enums.TicketStatus;

@RestController
@RequestMapping("/api/ticket-statuses")
public class TicketStatusController {
    @GetMapping
    public List<TicketStatusDTO> getTicketStatuses() {
        return Arrays.stream(TicketStatus.values())
            .map(status -> new TicketStatusDTO(status.name(), status.getDisplayName()))
            .collect(Collectors.toList());
    }
}