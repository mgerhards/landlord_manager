package de.propadmin.rentalmanager.models.enums;

public enum HeatingType {
    ETAGENHEIZUNG("Etagenheizung"),
    ZENTRALHEIZUNG("Zentralheizung");
    
    private final String displayName;
    
    HeatingType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 