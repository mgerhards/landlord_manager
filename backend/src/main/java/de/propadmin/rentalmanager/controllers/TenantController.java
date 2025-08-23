package de.propadmin.rentalmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.repositories.LandlordRepository;
import de.propadmin.rentalmanager.service.TenantService;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private LandlordRepository landlordRepository;

    @GetMapping("/by-landlord")
    public ResponseEntity<List<Tenant>> getTenantsByCurrentLandlord() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            
            // Get landlord by user account email
            Landlord landlord = landlordRepository.findByUserAccountEmail(userEmail);
            if (landlord == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Get tenants for this landlord
            List<Tenant> tenants = tenantService.getTenantsByLandlordId(landlord.getId());
            return ResponseEntity.ok(tenants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}