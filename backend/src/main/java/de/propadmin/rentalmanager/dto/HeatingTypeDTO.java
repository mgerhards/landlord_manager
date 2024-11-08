package de.propadmin.rentalmanager.dto;

import lombok.Getter;

@Getter
public class HeatingTypeDTO {
    private final String name;
    private final String displayName;
    
    public HeatingTypeDTO(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
} 