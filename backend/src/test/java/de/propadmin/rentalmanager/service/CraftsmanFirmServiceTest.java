package de.propadmin.rentalmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.enums.TradeType;
import de.propadmin.rentalmanager.repositories.CraftsmanFirmRepository;

class CraftsmanFirmServiceTest {

    @Mock
    private CraftsmanFirmRepository craftsmanFirmRepository;

    @InjectMocks
    private CraftsmanFirmService craftsmanFirmService;

    private CraftsmanFirm cheapestCompany;
    private CraftsmanFirm expensiveCompany;
    private CraftsmanFirm mediumCompany;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Create test companies with different emergency rates
        cheapestCompany = createTestCompany(1L, "Budget Handwerk", new BigDecimal("120.00"));
        mediumCompany = createTestCompany(2L, "Meister Röhrich", new BigDecimal("150.00"));  
        expensiveCompany = createTestCompany(3L, "Premium Service", new BigDecimal("180.00"));
    }

    @Test
    void testGetCheapestEmergencyCompanyByLandlord_ReturnsCorrectCompany() {
        // Given
        Long landlordId = 1L;
        // Repository query should return companies sorted by emergency rate (cheapest first)
        List<CraftsmanFirm> companies = Arrays.asList(
            cheapestCompany,   // This should be first (cheapest)
            mediumCompany,     // Medium price
            expensiveCompany   // Most expensive
        );
        
        when(craftsmanFirmRepository.findCheapestEmergencyCompanyByLandlordId(landlordId))
            .thenReturn(companies);

        // When
        CraftsmanFirm result = craftsmanFirmService.getCheapestEmergencyCompanyByLandlord(landlordId);

        // Then
        assertNotNull(result);
        assertEquals("Budget Handwerk", result.getCompanyName());
        assertEquals(new BigDecimal("120.00"), result.getEmergencyHourlyRate());
    }

    @Test
    void testGetCheapestEmergencyCompanyByLandlord_ReturnsNullWhenEmpty() {
        // Given
        Long landlordId = 1L;
        List<CraftsmanFirm> emptyList = Arrays.asList();
        
        when(craftsmanFirmRepository.findCheapestEmergencyCompanyByLandlordId(landlordId))
            .thenReturn(emptyList);

        // When
        CraftsmanFirm result = craftsmanFirmService.getCheapestEmergencyCompanyByLandlord(landlordId);

        // Then
        assertNull(result);
    }

    @Test
    void testGetCheapestEmergencyCompanyByLandlord_ReturnsFirstWhenSingleResult() {
        // Given
        Long landlordId = 1L;
        List<CraftsmanFirm> singleCompanyList = Arrays.asList(cheapestCompany);
        
        when(craftsmanFirmRepository.findCheapestEmergencyCompanyByLandlordId(landlordId))
            .thenReturn(singleCompanyList);

        // When
        CraftsmanFirm result = craftsmanFirmService.getCheapestEmergencyCompanyByLandlord(landlordId);

        // Then
        assertNotNull(result);
        assertEquals(cheapestCompany.getId(), result.getId());
        assertEquals(cheapestCompany.getCompanyName(), result.getCompanyName());
    }

    @Test
    void testCreateCraftsmanFirm() {
        // Given
        CraftsmanFirm company = createTestCompany(1L, "Test Company", new BigDecimal("100.00"));
        
        when(craftsmanFirmRepository.save(company)).thenReturn(company);

        // When
        CraftsmanFirm result = craftsmanFirmService.createCraftsmanFirm(company);

        // Then
        assertNotNull(result);
        assertEquals(company.getCompanyName(), result.getCompanyName());
        verify(craftsmanFirmRepository).save(company);
    }

    @Test
    void testGetCraftsmanFirmsByLandlord() {
        // Given
        Long landlordId = 1L;
        List<CraftsmanFirm> companies = Arrays.asList(cheapestCompany, mediumCompany);
        
        when(craftsmanFirmRepository.findByLandlordId(landlordId)).thenReturn(companies);

        // When
        List<CraftsmanFirm> result = craftsmanFirmService.getCraftsmanFirmsByLandlord(landlordId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(cheapestCompany));
        assertTrue(result.contains(mediumCompany));
    }

    private CraftsmanFirm createTestCompany(Long id, String name, BigDecimal emergencyRate) {
        CraftsmanFirm company = new CraftsmanFirm();
        company.setId(id);
        company.setCompanyName(name);
        company.setVatNumber("DE" + id + "23456789");
        company.setRegistrationNumber("HRB " + id + "2345");
        company.setPrimaryContactName("Contact " + name);
        company.setPhone("+49 30 " + id + "2345678");
        company.setEmergencyPhone("+49 176 " + id + "2345678");
        company.setEmail("info@" + name.toLowerCase().replace(" ", "") + ".de");
        company.setStreet("Test Street");
        company.setHouseNumber("" + id);
        company.setPostalCode("1234" + id);
        company.setCity("Berlin");
        company.setCountry("Deutschland");
        
        Set<TradeType> trades = new HashSet<>();
        trades.add(TradeType.PLUMBING);
        trades.add(TradeType.HEATING);
        company.setTrades(trades);
        
        company.setIsEmergencyServiceProvider(true);
        company.setAvailabilityHours("Mo-Fr 8:00-17:00");
        company.setMaxTravelRadiusKm(50);
        company.setStandardHourlyRate(new BigDecimal("85.00"));
        company.setEmergencyHourlyRate(emergencyRate);
        company.setTravelCostPerKm(new BigDecimal("0.50"));
        company.setStandardWarrantyMonths(24);
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());
        company.setCreatedBy("test@example.com");
        company.setLastModifiedBy("test@example.com");
        
        return company;
    }
}