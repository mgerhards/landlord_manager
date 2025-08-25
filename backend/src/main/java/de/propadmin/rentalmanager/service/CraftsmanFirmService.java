package de.propadmin.rentalmanager.service;

import java.util.List;

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
        craftsmanFirmRepository.deleteById(id);
    }
}
