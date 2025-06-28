package de.propadmin.rentalmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.propadmin.rentalmanager.models.ContactPerson;

@RepositoryRestResource(path = "contactPersons")
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {
    Optional<ContactPerson> findByUserAccountEmail(String email);
    Optional<ContactPerson> findByUserAccountEmailAndCraftsmanFirmId(String email, Long craftsmanFirmId);
}