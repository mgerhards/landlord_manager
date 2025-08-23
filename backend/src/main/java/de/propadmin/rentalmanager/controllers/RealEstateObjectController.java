package de.propadmin.rentalmanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.service.ContractService;
import de.propadmin.rentalmanager.service.RealEstateObjectService;

@RestController
@RequestMapping("/api/real-estate")
public class RealEstateObjectController {

    @Autowired
    private RealEstateObjectService realEstateObjectService;

    @Autowired
    private ContractService contractService;

    @GetMapping
    public ResponseEntity<List<RealEstateObject>> getAllRealEstateObjects() {
        try {
            List<RealEstateObject> objects = realEstateObjectService.getAllRealEstateObjects();
            return ResponseEntity.ok(objects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateObject> getRealEstateObjectById(@PathVariable Long id) {
        try {
            return realEstateObjectService.getRealEstateObjectById(id)
                    .map(obj -> ResponseEntity.ok(obj))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<RealEstateObject>> getRealEstateObjectsByLandlord(@PathVariable Long landlordId) {
        try {
            List<RealEstateObject> objects = realEstateObjectService.getRealEstateObjectsByLandlord(landlordId);
            return ResponseEntity.ok(objects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<RealEstateObject> createRealEstateObject(@RequestBody RealEstateObject realEstateObject) {
        try {
            RealEstateObject createdObject = realEstateObjectService.createRealEstateObject(realEstateObject);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateObject> updateRealEstateObject(@PathVariable Long id, 
                                                                  @RequestBody RealEstateObject realEstateObject) {
        try {
            RealEstateObject updatedObject = realEstateObjectService.updateRealEstateObject(id, realEstateObject);
            return ResponseEntity.ok(updatedObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRealEstateObject(@PathVariable Long id) {
        try {
            realEstateObjectService.deleteRealEstateObject(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}