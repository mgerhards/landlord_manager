package de.propadmin.rentalmanager.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import de.propadmin.rentalmanager.models.Contract;

@RepositoryRestResource(path = "contract")
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByLandlordId(Long landlordId);
    List<Contract> findByTenantId(Long tenantId);
}
