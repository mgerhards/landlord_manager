package de.propadmin.rentalmanager.dto;

import lombok.Getter;

@Getter
public class PropertyTypeDTO {
    private final String name;
    private final String displayName;
    
    public PropertyTypeDTO(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
} 