package de.propadmin.rentalmanager.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.propadmin.rentalmanager.models.enums.TradeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CraftsmanFirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Company Info
    private String companyName;
    private String vatNumber;      // USt-IdNr.
    private String registrationNumber;  // Handelsregisternummer
    
    // Contact Info
    private String primaryContactName;
    private String phone;
    private String emergencyPhone;
    private String email;
    private String website;
  
    // Address
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    
    // Business Details
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TradeType> trades;
    
    private Boolean isEmergencyServiceProvider;
    private String availabilityHours;

    // Service Area
    @ElementCollection
    private Set<String> servicePostalCodes;
    private Integer maxTravelRadiusKm;
    
    // Company Rates
    private BigDecimal standardHourlyRate;
    private BigDecimal emergencyHourlyRate;
    private BigDecimal travelCostPerKm;
    private Integer standardWarrantyMonths;

    // Company Performance
    private Double averageRating;
    private Integer completedJobsCount;
    private Integer cancelledJobsCount;
    private Integer emergencyResponseTimeMinutes;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;

    @OneToMany(mappedBy = "craftsmanFirm")
    @JsonManagedReference
    private List<FrameworkContract> frameworkContracts;

    @OneToMany(mappedBy = "craftsmanFirm")
    @JsonManagedReference
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "craftsmanFirm")
    @JsonManagedReference
    private List<ContactPerson> contactPersons;
}
