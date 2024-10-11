package de.propadmin.rental_manager.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rental_manager.models.CraftsmanFirm;
import de.propadmin.rental_manager.service.CraftsmanFirmService;

@RestController
@RequestMapping("/craftsman-firms")
public class CraftsmanFirmController {

    @Autowired
    private CraftsmanFirmService craftsmanFirmService;

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<CraftsmanFirm>> getCraftsmanFirmsByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(craftsmanFirmService.getCraftsmanFirmsByLandlord(landlordId));
    }

    @PostMapping
    public ResponseEntity<CraftsmanFirm> createCraftsmanFirm(@RequestBody CraftsmanFirm craftsmanFirm) {
        CraftsmanFirm createdFirm = craftsmanFirmService.createCraftsmanFirm(craftsmanFirm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFirm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCraftsmanFirm(@PathVariable Long id) {
        craftsmanFirmService.deleteCraftsmanFirm(id);
        return ResponseEntity.noContent().build();
    }
}

