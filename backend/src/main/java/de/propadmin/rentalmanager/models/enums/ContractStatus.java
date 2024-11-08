package de.propadmin.rentalmanager.models.enums;

public enum ContractStatus {
    DRAFT("Draft"),
    ACTIVE("Active"),
    TERMINATED("Terminated"),
    EXPIRED("Expired");

    private String displayName;

    ContractStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}