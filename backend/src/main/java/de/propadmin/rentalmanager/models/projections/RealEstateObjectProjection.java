package de.propadmin.rentalmanager.models.projections;

import de.propadmin.rentalmanager.models.RealEstateObject;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "realEstateObjectProjection", types = { RealEstateObject.class })
public interface RealEstateObjectProjection {
    Long getId();
    String getAddress();
    double getSize();
    int getNumberOfRooms();
    String getDescription();
    // Include other fields as needed
}