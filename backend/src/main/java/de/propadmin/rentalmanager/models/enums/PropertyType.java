package de.propadmin.rentalmanager.models.enums;
public enum PropertyType {
    APARTMENT("Wohnung"),
    HOUSE("Haus"),
    COMMERCIAL("Gewerbeeinheit");
    
    private final String displayName;
    
    PropertyType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 