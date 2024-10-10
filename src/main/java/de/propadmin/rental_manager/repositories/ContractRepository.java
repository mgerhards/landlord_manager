package de.propadmin.rental_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.propadmin.rental_manager.models.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    // Additional custom queries can be defined here if needed
}