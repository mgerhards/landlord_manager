package de.propadmin.rentalmanager.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    // Getters and Setters
}
