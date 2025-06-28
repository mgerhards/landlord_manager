package de.propadmin.rentalmanager.dto;

import lombok.Getter;

@Getter
public class TicketStatusDTO {
    private final String name;
    private final String displayName;

    public TicketStatusDTO(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}