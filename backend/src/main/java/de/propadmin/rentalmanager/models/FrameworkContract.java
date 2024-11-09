package de.propadmin.rentalmanager.models;

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
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter @Setter
public class FrameworkContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "craftsman_firm_id")
    @JsonBackReference
    private CraftsmanFirm craftsmanFirm;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    @JsonBackReference
    private Landlord landlord;

    // Contract Details
    private String contractNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;

    // Negotiated Terms
    private BigDecimal negotiatedHourlyRate;
    private BigDecimal negotiatedEmergencyRate;
    private BigDecimal negotiatedTravelCost;
    private BigDecimal discountPercentage;
    private Integer warrantyMonths;

    // Payment Terms
    private String iban;
    private String bic;
    private String bankName;
    private String accountHolder;
    private Integer paymentTermDays;

    // Service Level Agreement
    private Integer maxResponseTimeHours;
    private Integer emergencyResponseTimeMinutes;
    private Boolean includesWeekendService;
    private String serviceHours;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TradeType> coveredTrades;  // Might be a subset of firm's trades

    // Insurance Requirements
    private BigDecimal requiredInsuranceAmount;
    private LocalDate insuranceExpiryDate;
    private String insurancePolicyNumber;

    // Document Management
    private String contractDocumentPath;
    private String insuranceDocumentPath;
    private String termsAndConditionsPath;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;
} 