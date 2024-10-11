package de.propadmin.rental_manager.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rental_manager.models.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByLandlordId(Long landlordId);
    List<Contract> findByTenantId(Long tenantId);
}
