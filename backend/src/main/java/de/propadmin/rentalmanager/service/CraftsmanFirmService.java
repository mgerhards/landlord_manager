package de.propadmin.rentalmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.repositories.CraftsmanFirmRepository;

@Service
public class CraftsmanFirmService {

    @Autowired
    private CraftsmanFirmRepository craftsmanFirmRepository;

    public CraftsmanFirm createCraftsmanFirm(CraftsmanFirm craftsmanFirm) {
        return craftsmanFirmRepository.save(craftsmanFirm);
    }

    public List<CraftsmanFirm> getCraftsmanFirmsByLandlord(Long landlordId) {
        return craftsmanFirmRepository.findByLandlordId(landlordId);
    }

    public CraftsmanFirm getCheapestEmergencyCompanyByLandlord(Long landlordId) {
        List<CraftsmanFirm> companies = craftsmanFirmRepository.findCheapestEmergencyCompanyByLandlordId(landlordId);
        return companies.isEmpty() ? null : companies.get(0);
    }

    public void deleteCraftsmanFirm(Long id) {
        Optional<CraftsmanFirm> craftsmanFirm = craftsmanFirmRepository.findById(id);
        if (craftsmanFirm.isPresent()) {
            CraftsmanFirm firm = craftsmanFirm.get();
            // Soft delete: set deactivated timestamp instead of deleting
            firm.setDeactivated(LocalDateTime.now());
            craftsmanFirmRepository.save(firm);
        } else {
            throw new RuntimeException("CraftsmanFirm not found with id: " + id);
        }
    }

    public List<CraftsmanFirm> getAllCraftsmanFirms() {
        return craftsmanFirmRepository.findAll();
    }
}
