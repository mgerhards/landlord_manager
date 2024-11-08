package de.propadmin.rentalmanager.controllers;

import de.propadmin.rentalmanager.dto.HeatingTypeDTO;
import de.propadmin.rentalmanager.models.enums.HeatingType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/heating-types")
public class HeatingTypeController {
    
    @GetMapping
    public List<HeatingTypeDTO> getAllHeatingTypes() {
        return Arrays.stream(HeatingType.values())
            .map(type -> new HeatingTypeDTO(type.name(), type.getDisplayName()))
            .collect(Collectors.toList());
    }
} 