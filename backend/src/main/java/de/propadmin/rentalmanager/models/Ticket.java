package de.propadmin.rentalmanager.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.propadmin.rentalmanager.models.enums.TicketStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    
    // Keep backward compatibility - this field is used by existing code
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

    @OneToMany(mappedBy = "ticket")
    @JsonManagedReference
    private List<TicketComment> comments;

    // Audit fields (in addition to existing creationDate)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private LocalDateTime deletedAt;
    private String createdBy;
    private String lastModifiedBy;

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
        if (status == null) {
            status = TicketStatus.OPEN;
        }
    }
}
