package de.propadmin.rental_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rental_manager.models.Landlord;
import de.propadmin.rental_manager.repositories.LandlordRepository;

@Service
public class LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    public Optional<Landlord> getLandlordById(Long id) {
        return landlordRepository.findById(id);
    }

    public Landlord createLandlord(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public List<Landlord> getAllLandlords() {
        return landlordRepository.findAll();
    }

    public void deleteLandlord(Long id) {
        landlordRepository.deleteById(id);
    }
}
