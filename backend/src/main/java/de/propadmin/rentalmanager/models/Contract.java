package de.propadmin.rentalmanager.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal monthlyRent;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    @JsonBackReference
    private Landlord landlord;

    @ManyToMany
    @JoinTable(
        name = "contract_tenants",
        joinColumns = @JoinColumn(name = "contract_id"),
        inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    @JsonBackReference
    private List<Tenant> tenants;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    @JsonBackReference
    private RealEstateObject asset;

    // Getters and Setters
}

