package de.propadmin.rentalmanager.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuditFieldsTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testUserAccountAuditFields() {
        // Create a new UserAccount
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("test@example.com");
        userAccount.setPassword("password123");
        userAccount.setCreatedBy("system");
        userAccount.setLastModifiedBy("system");

        // Persist the entity
        entityManager.persist(userAccount);
        entityManager.flush();

        // Verify audit fields are populated
        assertThat(userAccount.getCreatedAt()).isNotNull();
        assertThat(userAccount.getUpdatedAt()).isNotNull();
        assertThat(userAccount.getCreatedBy()).isEqualTo("system");
        assertThat(userAccount.getLastModifiedBy()).isEqualTo("system");
        assertThat(userAccount.getDeletedAt()).isNull();
    }

    @Test
    public void testTenantAuditFields() {
        // Create a new Tenant
        Tenant tenant = new Tenant();
        tenant.setName("John Doe");
        tenant.setPhoneNumber("555-1234");
        tenant.setDateOfBirth(LocalDate.of(1990, 1, 1));
        tenant.setCreatedBy("admin");
        tenant.setLastModifiedBy("admin");

        // Persist the entity
        entityManager.persist(tenant);
        entityManager.flush();

        // Verify audit fields are populated
        assertThat(tenant.getCreatedAt()).isNotNull();
        assertThat(tenant.getUpdatedAt()).isNotNull();
        assertThat(tenant.getCreatedBy()).isEqualTo("admin");
        assertThat(tenant.getLastModifiedBy()).isEqualTo("admin");
        assertThat(tenant.getDeletedAt()).isNull();
    }

    @Test
    public void testLandlordAuditFields() {
        // Create a new Landlord
        Landlord landlord = new Landlord();
        landlord.setName("Jane Smith");
        landlord.setPhoneNumber("555-5678");
        landlord.setLicenseKey("LICENSE123");
        landlord.setLicenseExpirationDate(LocalDate.now().plusYears(1));
        landlord.setCreatedBy("system");
        landlord.setLastModifiedBy("system");

        // Persist the entity
        entityManager.persist(landlord);
        entityManager.flush();

        // Verify audit fields are populated
        assertThat(landlord.getCreatedAt()).isNotNull();
        assertThat(landlord.getUpdatedAt()).isNotNull();
        assertThat(landlord.getCreatedBy()).isEqualTo("system");
        assertThat(landlord.getLastModifiedBy()).isEqualTo("system");
        assertThat(landlord.getDeletedAt()).isNull();
    }

    @Test
    public void testSoftDeleteFunctionality() {
        // Create a new UserAccount
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("delete-test@example.com");
        userAccount.setPassword("password123");
        userAccount.setCreatedBy("system");
        userAccount.setLastModifiedBy("system");

        // Persist the entity
        entityManager.persist(userAccount);
        entityManager.flush();

        // Simulate soft delete
        userAccount.setDeletedAt(LocalDateTime.now());
        entityManager.merge(userAccount);
        entityManager.flush();

        // Verify soft delete field is populated
        assertThat(userAccount.getDeletedAt()).isNotNull();
        assertThat(userAccount.getCreatedAt()).isNotNull();
        assertThat(userAccount.getUpdatedAt()).isNotNull();
    }
}