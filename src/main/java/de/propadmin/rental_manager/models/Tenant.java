package de.propadmin.rental_manager.models;

import org.hibernate.mapping.List;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "tenant")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "tenant")
    private List<Ticket> tickets;

    // Getters and Setters
}
