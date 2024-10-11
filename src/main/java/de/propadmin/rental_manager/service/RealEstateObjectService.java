package de.propadmin.rental_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rental_manager.models.RealEstateObject;
import de.propadmin.rental_manager.repositories.RealEstateObjectRepository;
@Service
public class RealEstateObjectService {

    @Autowired
    private RealEstateObjectRepository realEstateObjectRepository;

    public RealEstateObject createRealEstateObject(RealEstateObject realEstateObject) {
        return realEstateObjectRepository.save(realEstateObject);
    }

    public List<RealEstateObject> getRealEstateObjectsByLandlord(Long landlordId) {
        return realEstateObjectRepository.findByLandlordId(landlordId);
    }

    public void deleteRealEstateObject(Long id) {
        realEstateObjectRepository.deleteById(id);
    }
}
