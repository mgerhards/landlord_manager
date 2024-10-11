package de.propadmin.rental_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rental_manager.models.CraftsmanFirm;
import de.propadmin.rental_manager.repositories.CraftsmanFirmRepository;

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

    public void deleteCraftsmanFirm(Long id) {
        craftsmanFirmRepository.deleteById(id);
    }
}
