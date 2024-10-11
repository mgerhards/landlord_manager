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

import de.propadmin.rental_manager.models.RealEstateObject;
import de.propadmin.rental_manager.service.RealEstateObjectService;

@RestController
@RequestMapping("/real-estate")
public class RealEstateObjectController {

    @Autowired
    private RealEstateObjectService realEstateObjectService;

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<RealEstateObject>> getRealEstateObjectsByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(realEstateObjectService.getRealEstateObjectsByLandlord(landlordId));
    }

    @PostMapping
    public ResponseEntity<RealEstateObject> createRealEstateObject(@RequestBody RealEstateObject realEstateObject) {
        RealEstateObject createdObject = realEstateObjectService.createRealEstateObject(realEstateObject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRealEstateObject(@PathVariable Long id) {
        realEstateObjectService.deleteRealEstateObject(id);
        return ResponseEntity.noContent().build();
    }
}

