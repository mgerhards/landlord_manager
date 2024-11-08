package de.propadmin.rentalmanager.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.dto.PropertyTypeDTO;
import de.propadmin.rentalmanager.models.enums.PropertyType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/property-types")
public class PropertyTypeController {
    
    @GetMapping
    public List<PropertyTypeDTO> getAllPropertyTypes() {
        return Arrays.stream(PropertyType.values())
            .map(type -> new PropertyTypeDTO(type.name(), type.getDisplayName()))
            .collect(Collectors.toList());
    }
} 