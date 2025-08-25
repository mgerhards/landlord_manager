package de.propadmin.rentalmanager.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.models.ContactPerson;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.repositories.ContactPersonRepository;
import de.propadmin.rentalmanager.repositories.LandlordRepository;
import de.propadmin.rentalmanager.service.CraftsmanFirmService;

@RestController
@RequestMapping("/api/companies")
public class CraftsmanFirmController {

    @Autowired
    private CraftsmanFirmService craftsmanFirmService;
    
    @Autowired
    private LandlordRepository landlordRepository;
    
    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @GetMapping
    public ResponseEntity<List<CraftsmanFirm>> getAllCompanies() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            
            // Get landlord by user account email
            Landlord landlord = landlordRepository.findByUserAccountEmail(userEmail);
            if (landlord == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Get craftsman firms for this landlord
            List<CraftsmanFirm> companies = craftsmanFirmService.getCraftsmanFirmsByLandlord(landlord.getId());
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/cheapest-emergency")
    public ResponseEntity<CraftsmanFirm> getCheapestEmergencyCompany() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            
            // Get landlord by user account email
            Landlord landlord = landlordRepository.findByUserAccountEmail(userEmail);
            if (landlord == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Get cheapest emergency company for this landlord
            CraftsmanFirm cheapestCompany = craftsmanFirmService.getCheapestEmergencyCompanyByLandlord(landlord.getId());
            if (cheapestCompany == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(cheapestCompany);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<CraftsmanFirm> createCompany(@RequestBody CraftsmanFirm craftsmanFirm) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            
            // Get landlord by user account email
            Landlord landlord = landlordRepository.findByUserAccountEmail(userEmail);
            if (landlord == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Set audit fields
            craftsmanFirm.setCreatedAt(LocalDateTime.now());
            craftsmanFirm.setUpdatedAt(LocalDateTime.now());
            craftsmanFirm.setCreatedBy(userEmail);
            craftsmanFirm.setLastModifiedBy(userEmail);
            
            // Create the craftsman firm
            CraftsmanFirm savedCompany = craftsmanFirmService.createCraftsmanFirm(craftsmanFirm);
            return ResponseEntity.ok(savedCompany);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{companyId}/contact-persons")
    public ResponseEntity<ContactPerson> addContactPerson(@PathVariable Long companyId, @RequestBody ContactPerson contactPerson) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            
            // Get landlord by user account email
            Landlord landlord = landlordRepository.findByUserAccountEmail(userEmail);
            if (landlord == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Verify the company belongs to this landlord (through framework contracts)
            List<CraftsmanFirm> userCompanies = craftsmanFirmService.getCraftsmanFirmsByLandlord(landlord.getId());
            if (userCompanies.stream().noneMatch(c -> c.getId().equals(companyId))) {
                return ResponseEntity.badRequest().build();
            }
            
            // Set the company reference and save
            CraftsmanFirm company = new CraftsmanFirm();
            company.setId(companyId);
            contactPerson.setCraftsmanFirm(company);
            
            // As per requirement, userAccount stays null when creating a contact person
            contactPerson.setUserAccount(null);
            
            ContactPerson savedContactPerson = contactPersonRepository.save(contactPerson);
            return ResponseEntity.ok(savedContactPerson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}