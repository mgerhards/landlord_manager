package de.propadmin.rentalmanager.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.propadmin.rentalmanager.models.enums.PropertyType;
import de.propadmin.rentalmanager.models.enums.HeatingType;
import jakarta.persistence.Entity;
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
public class RealEstateObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private double size;  // In square meters
    private int numberOfRooms;
    private String description;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    @JsonBackReference
    private Landlord landlord;

    @OneToMany(mappedBy = "asset")
    @JsonManagedReference
    private List<Contract> contracts;

    @OneToMany(mappedBy = "asset")
    @JsonManagedReference
    private List<Ticket> tickets;

    private double latitude;
    private double longitude;

    // Basic Property Details
    private PropertyType propertyType;

    private int yearBuilt;
    private int floor;  // for apartments
    private boolean hasElevator;
    
    // Financial Details
    private double monthlyMaintenanceCosts;  // regular maintenance fees
    private double propertyTax;
    
    // Utilities & Energy
    private HeatingType heatingType;
    private boolean hasAirConditioning;
    
    // Amenities
    private boolean hasCellar;
    private boolean hasBalcony;
    private boolean hasGarden;
    private double outdoorSpace;  // in square meters
    private boolean isFurnished;
    
    // Maintenance
    private LocalDate lastRenovationDate;
    private LocalDate nextInspectionDue;

    // Lombok generates getter and setter methods
}
