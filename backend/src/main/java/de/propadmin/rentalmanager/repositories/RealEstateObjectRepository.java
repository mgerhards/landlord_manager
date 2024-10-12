// src/main/java/de/propadmin/rental_manager/repositories/RealEstateObjectRepository.java
package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rentalmanager.models.RealEstateObject;


@Repository
public interface RealEstateObjectRepository extends JpaRepository<RealEstateObject, Long> {
    List<RealEstateObject> findByLandlordId(Long landlordId);
}

