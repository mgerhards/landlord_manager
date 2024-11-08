package de.propadmin.rentalmanager.dto;

import lombok.Getter;

@Getter
public class ContractStatusDTO {
    private final String name;
    private final String displayName;

    public ContractStatusDTO(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
