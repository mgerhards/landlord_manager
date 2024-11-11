package de.propadmin.rentalmanager.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "landlord")
    @JsonManagedReference
    private List<RealEstateObject> realEstateObjects;

    @OneToMany(mappedBy = "landlord")
    @JsonManagedReference
    private List<Contract> contracts;

    @OneToMany(mappedBy = "landlord")
    @JsonManagedReference
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "landlord")
    @JsonManagedReference
    private List<FrameworkContract> frameworkContracts;

    // Getters and Setters
}

