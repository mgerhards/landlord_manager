package de.propadmin.rentalmanager.controller.rest;

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

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.service.ContractService;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract createdContract = contractService.createContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContract);
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<Contract>> getContractsByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(contractService.getContractsByLandlord(landlordId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Contract>> getContractsByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(contractService.getContractsByTenant(tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
