package de.propadmin.rentalmanager.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    
    // Personal Information
    private LocalDate dateOfBirth;
    private String idNumber; // ID card or passport number
    private String occupation;
    private String employerName;
    private String employerContact;
    
    // Contact Information
    private String alternativePhoneNumber;
    private String workPhoneNumber;
    private String emergencyContactName;
    private String emergencyContactPhone;
    
    // Financial Information
    private String bankAccountHolder;
    private String iban;
    private String bic;
    
    // Additional Information
    private int numberOfOccupants;
    private LocalDate moveInDate;


    @ManyToMany(mappedBy = "tenants")
    @JsonManagedReference
    private List<Contract> contracts;

    @OneToMany(mappedBy = "tenant")
    @JsonManagedReference
    private List<Ticket> tickets;
}
