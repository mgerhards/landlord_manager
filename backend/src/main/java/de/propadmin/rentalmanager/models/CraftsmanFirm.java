package de.propadmin.rentalmanager.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.propadmin.rentalmanager.models.enums.TradeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CraftsmanFirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String contactDetails; // Could be email, phone, or address
    private String vatNumber;  // USt-IdNr.
    private String registrationNumber;  // Handelsregisternummer
  
    // Address
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    
    // Business Details
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TradeType> trades;  // e.g., PLUMBING, ELECTRICAL, etc.
    
    private Boolean isEmergencyServiceProvider;
    private String emergencyPhone;
    private String availabilityHours;  // JSON string of business hours

    @ElementCollection
    private Set<String> acceptedPaymentMethods;  // e.g., "BANK_TRANSFER", "CASH"
    
    private BigDecimal standardHourlyRate;
    private BigDecimal emergencyHourlyRate;
    private BigDecimal travelCostPerKm;
    private Integer standardWarrantyMonths;

    // Service Area
    @ElementCollection
    private Set<String> servicePostalCodes;
    private Integer maxTravelRadiusKm;
    
    // Financial Information
    private String iban;
    private String bic;
    private String bankName;
    private String accountHolder;

    // Performance Metrics
    private Double averageRating;
    private Integer completedJobsCount;
    private Integer cancelledJobsCount;
    private Integer emergencyResponseTimeMinutes;

    // Relationship Management
    private LocalDateTime lastJobDate;
    private LocalDateTime contractedSince;
    private Boolean isPreferredPartner;
    private String frameworkContractNumber;
    private BigDecimal negotiatedDiscount;  // Percentage
    
    // Audit Fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    @JsonBackReference
    private Landlord landlord;

    @OneToMany(mappedBy = "craftsmanFirm")
    @JsonManagedReference
    private List<Ticket> tickets;
}
