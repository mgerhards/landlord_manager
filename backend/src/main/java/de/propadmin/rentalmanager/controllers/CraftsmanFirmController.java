package de.propadmin.rentalmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.repositories.LandlordRepository;
import de.propadmin.rentalmanager.service.CraftsmanFirmService;

@RestController
@RequestMapping("/api/companies")
public class CraftsmanFirmController {

    @Autowired
    private CraftsmanFirmService craftsmanFirmService;
    
    @Autowired
    private LandlordRepository landlordRepository;

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
}