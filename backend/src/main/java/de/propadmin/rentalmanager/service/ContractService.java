package de.propadmin.rentalmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.repositories.ContractRepository;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> getContractsByLandlord(Long landlordId) {
        return contractRepository.findByLandlordId(landlordId);
    }

    public List<Contract> getContractsByTenant(Long tenantId) {
        return contractRepository.findByTenantId(tenantId);
    }

    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
}
