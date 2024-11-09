package de.propadmin.rentalmanager.dto;

import lombok.Getter;

@Getter
public class TradeTypeDTO {
    private final String name;
    private final String displayName;
    
    public TradeTypeDTO(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
} 