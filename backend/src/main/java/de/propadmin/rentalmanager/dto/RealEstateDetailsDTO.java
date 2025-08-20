package de.propadmin.rentalmanager.dto;

import java.util.List;
import java.util.Optional;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.models.RealEstateObject;

public class RealEstateDetailsDTO {

    private Optional<RealEstateObject> realEstate;
    private List<Contract> contracts;

    public void setRealEstateObject(Optional<RealEstateObject> realEstate) {
        this.realEstate = realEstate;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

}
