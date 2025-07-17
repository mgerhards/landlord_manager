package de.propadmin.rentalmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.repositories.RealEstateObjectRepository;

@Service
public class RealEstateObjectService {

    @Autowired
    private RealEstateObjectRepository realEstateObjectRepository;

    public RealEstateObject createRealEstateObject(RealEstateObject realEstateObject) {
        return realEstateObjectRepository.save(realEstateObject);
    }

    public List<RealEstateObject> getAllRealEstateObjects() {
        return realEstateObjectRepository.findAll();
    }

    public Optional<RealEstateObject> getRealEstateObjectById(Long id) {
        return realEstateObjectRepository.findById(id);
    }

    public List<RealEstateObject> getRealEstateObjectsByLandlord(Long landlordId) {
        return realEstateObjectRepository.findByLandlordId(landlordId);
    }

    public RealEstateObject updateRealEstateObject(Long id, RealEstateObject updatedObject) {
        return realEstateObjectRepository.findById(id)
            .map(existing -> {
                // Update basic properties
                existing.setAddress(updatedObject.getAddress());
                existing.setSize(updatedObject.getSize());
                existing.setNumberOfRooms(updatedObject.getNumberOfRooms());
                existing.setDescription(updatedObject.getDescription());
                existing.setLatitude(updatedObject.getLatitude());
                existing.setLongitude(updatedObject.getLongitude());
                
                // Update property details
                existing.setPropertyType(updatedObject.getPropertyType());
                existing.setYearBuilt(updatedObject.getYearBuilt());
                existing.setFloor(updatedObject.getFloor());
                existing.setHasElevator(updatedObject.isHasElevator());
                
                // Update financial details
                existing.setMonthlyMaintenanceCosts(updatedObject.getMonthlyMaintenanceCosts());
                existing.setPropertyTax(updatedObject.getPropertyTax());
                
                // Update utilities & energy
                existing.setHeatingType(updatedObject.getHeatingType());
                existing.setHasAirConditioning(updatedObject.isHasAirConditioning());
                
                // Update amenities
                existing.setHasCellar(updatedObject.isHasCellar());
                existing.setHasBalcony(updatedObject.isHasBalcony());
                existing.setHasGarden(updatedObject.isHasGarden());
                existing.setOutdoorSpace(updatedObject.getOutdoorSpace());
                existing.setFurnished(updatedObject.isFurnished());
                
                // Update maintenance dates
                existing.setLastRenovationDate(updatedObject.getLastRenovationDate());
                existing.setNextInspectionDue(updatedObject.getNextInspectionDue());
                
                // Note: We don't update landlord relationship here for safety
                // That should be handled separately if needed
                
                return realEstateObjectRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("RealEstateObject not found with id: " + id));
    }

    public void deleteRealEstateObject(Long id) {
        realEstateObjectRepository.deleteById(id);
    }

    public Long count() {
        return realEstateObjectRepository.count();
    }
}
