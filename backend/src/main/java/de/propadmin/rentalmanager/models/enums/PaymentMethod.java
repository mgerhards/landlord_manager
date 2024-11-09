package de.propadmin.rentalmanager.models.enums;

public enum PaymentMethod {
    BANK_TRANSFER("Überweisung"),
    DIRECT_DEBIT("Lastschrift"),
    CREDIT_CARD("Kreditkarte"),
    INVOICE("Rechnung"),
    CASH("Bargeld");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}