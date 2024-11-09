package de.propadmin.rentalmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {
    private String name;        // Enum name (e.g., "BANK_TRANSFER")
    private String displayName; // German display name (e.g., "Überweisung")
} 