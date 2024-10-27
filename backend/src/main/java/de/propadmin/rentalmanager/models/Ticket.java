package de.propadmin.rentalmanager.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String status; // e.g., OPEN, IN_PROGRESS, CLOSED
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonBackReference
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    @JsonBackReference
    private Landlord landlord;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    @JsonBackReference
    private RealEstateObject asset;

    @ManyToOne
    @JoinColumn(name = "craftsman_firm_id", nullable = true)
    @JsonBackReference
    private CraftsmanFirm craftsmanFirm;  // Can be null if unassigned
}
