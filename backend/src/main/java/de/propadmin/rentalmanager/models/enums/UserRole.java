package de.propadmin.rentalmanager.models.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    LANDLORD("Vermieter"),
    TENANT("Mieter"),
    CRAFTSMAN("Handwerker");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}
