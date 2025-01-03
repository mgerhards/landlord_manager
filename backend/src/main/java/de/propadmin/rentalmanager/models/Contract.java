package de.propadmin.rentalmanager.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.propadmin.rentalmanager.models.enums.ContractStatus;
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
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    private String contractNumber;
    
    // Financial details
    private BigDecimal securityDeposit;
    private Integer rentDueDay; // Day of month when rent is due
    private BigDecimal additionalCosts; // Nebenkosten
    private BigDecimal heatingCosts;
    
    // Payment information
    private String iban;
    private String bic;
    private String accountHolder;
    
    // Additional spaces
    private String parkingSpaceNumber;
    private String storageUnitNumber;
    
    // Contract terms
    private Integer noticePeriodMonths;
    private Boolean automaticRenewal;
    private Integer maxOccupants;
    private Boolean petsAllowed;
    
    @ElementCollection
    private Set<String> includedUtilities; // e.g., water, heating, etc.
    
    // Dates
    private LocalDate moveInDate;
    private LocalDate terminationDate;
    private LocalDate lastRentAdjustment;
    
    // Document management
    private String contractFilePath;
    private String handoverProtocolPath;
    
    // Audit fields
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String createdBy;
    private String lastModifiedBy;

    // Getters and Setters
}

