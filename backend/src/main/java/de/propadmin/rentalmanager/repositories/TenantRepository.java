package de.propadmin.rentalmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.Tenant;

@RepositoryRestResource(path = "tenants")
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByUserAccountEmail(String email);
}