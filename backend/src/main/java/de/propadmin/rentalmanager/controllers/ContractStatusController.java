    package de.propadmin.rentalmanager.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.dto.ContractStatusDTO;
import de.propadmin.rentalmanager.models.enums.ContractStatus;

@RestController
@RequestMapping("/api/contract-statuses")
public class ContractStatusController {
    @GetMapping
    public List<ContractStatusDTO> getContractStatuses() {
        return Arrays.stream(ContractStatus.values())
            .map(status -> new ContractStatusDTO(status.name(), status.getDisplayName()))
            .collect(Collectors.toList());
    }
}
