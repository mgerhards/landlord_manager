package de.propadmin.rentalmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.models.projections.RealEstateObjectProjection;

@RepositoryRestResource(excerptProjection = RealEstateObjectProjection.class)
public interface RealEstateObjectRepository extends JpaRepository<RealEstateObject, Long> {
    List<RealEstateObject> findByLandlordId(Long landlordId);
}

