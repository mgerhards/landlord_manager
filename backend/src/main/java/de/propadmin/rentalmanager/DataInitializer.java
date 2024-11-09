package de.propadmin.rentalmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.enums.HeatingType;
import de.propadmin.rentalmanager.models.enums.PropertyType;
import de.propadmin.rentalmanager.models.enums.TradeType;
import de.propadmin.rentalmanager.service.ContractService;
import de.propadmin.rentalmanager.service.CraftsmanFirmService;
import de.propadmin.rentalmanager.service.LandlordService;
import de.propadmin.rentalmanager.service.RealEstateObjectService;
import de.propadmin.rentalmanager.service.TenantService;
import de.propadmin.rentalmanager.service.TicketService;
import de.propadmin.rentalmanager.utils.GeocodeResponse;
import de.propadmin.rentalmanager.utils.GeocodeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.math.BigDecimal;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LandlordService landlordService;

    @Autowired
    private RealEstateObjectService realEstateObjectService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CraftsmanFirmService craftsmanFirmService;

    @Autowired
    private TicketService ticketService;

    @Value("${propadmin.data-initializer.enabled:false}")
    private boolean dataInitializerEnabled;

    @Value("${propadmin.openroutemap.api-key}")
    private String apiKey;

    @Override
    public void run(String... args) throws Exception {
        // Check if data initializer is enabled
        if (!dataInitializerEnabled) {
            System.out.println("Data initializer is disabled");
            return;
        } else {
            System.out.println("Data initializer is enabled");
        }

        // Create a landlord
        Landlord landlord1 = new Landlord();
        landlord1.setName("John Doe");
        landlord1.setEmail("john.doe@example.com");
        landlord1.setPhoneNumber("555-1234");
        landlordService.createLandlord(landlord1);

        // Create a real estate object (property)
        RealEstateObject realEstateObject1 = new RealEstateObject();
        realEstateObject1.setAddress("Blumenthalstraße 68, 50668 Köln");
        realEstateObject1.setSize(70);
        realEstateObject1.setNumberOfRooms(3);
        realEstateObject1.setDescription("Beautiful 3-room apartment, 3.OG li");
        realEstateObject1.setLandlord(landlord1);
        realEstateObject1.setHeatingType(HeatingType.ZENTRALHEIZUNG);
        realEstateObject1.setPropertyType(PropertyType.APARTMENT);
        realEstateObject1.setYearBuilt(1985);
        realEstateObject1.setFloor(3);
        realEstateObject1.setHasBalcony(true);
        realEstateObject1.setHasElevator(false);
        realEstateObject1.setHasGarden(false);
        
        // Set latitude and longitude
        realEstateObject1.setLatitude(50.9385);
        realEstateObject1.setLongitude(6.9592);

        // Geocode the address to get latitude and longitude
        GeocodeResponse geocodeResponse = GeocodeUtils.geocodeAddress(apiKey, realEstateObject1.getAddress());
        realEstateObject1.setLatitude(geocodeResponse.getLatitude());
        realEstateObject1.setLongitude(geocodeResponse.getLongitude());

        realEstateObjectService.createRealEstateObject(realEstateObject1);

        RealEstateObject realEstateObject2 = new RealEstateObject();
        realEstateObject2.setAddress("Blumenthalstraße 68, 50668 Köln");
        realEstateObject2.setSize(82.5);
        realEstateObject2.setNumberOfRooms(3);
        realEstateObject2.setDescription("Beautiful 4-room apartment, 1.OG re");
        realEstateObject2.setLandlord(landlord1);
        realEstateObject2.setHeatingType(HeatingType.ETAGENHEIZUNG);
        realEstateObject2.setPropertyType(PropertyType.APARTMENT);
        realEstateObject2.setYearBuilt(1985);
        realEstateObject2.setFloor(1);
        realEstateObject2.setHasBalcony(true);
        realEstateObject2.setHasElevator(false);
        realEstateObject2.setHasGarden(false);
        
        // Set latitude and longitude
        realEstateObject2.setLatitude(50.9385);
        realEstateObject2.setLongitude(6.9592);

        // Geocode the address to get latitude and longitude
        geocodeResponse = GeocodeUtils.geocodeAddress(apiKey, realEstateObject2.getAddress());
        realEstateObject2.setLatitude(geocodeResponse.getLatitude());
        realEstateObject2.setLongitude(geocodeResponse.getLongitude());

        realEstateObjectService.createRealEstateObject(realEstateObject2);

        // Create a tenant
        Tenant tenant1 = new Tenant();
        tenant1.setName("Jane Smith");
        tenant1.setEmail("jane.smith@example.com");
        tenant1.setPhoneNumber("555-6789");
        tenant1.setDateOfBirth(LocalDate.of(1985, 6, 15));
        tenant1.setIdNumber("ID123456789");
        tenant1.setOccupation("Software Engineer");
        tenant1.setEmployerName("Tech Corp");
        tenant1.setEmployerContact("employer@techcorp.com");
        tenant1.setAlternativePhoneNumber("555-9876");
        tenant1.setWorkPhoneNumber("555-4321");
        tenant1.setEmergencyContactName("John Smith");
        tenant1.setEmergencyContactPhone("555-1234");
        tenant1.setBankAccountHolder("Jane Smith");
        tenant1.setIban("DE89 3704 0044 0532 0130 00");
        tenant1.setBic("COBADEFFXXX");
        tenant1.setNumberOfOccupants(2);
        tenant1.setMoveInDate(LocalDate.of(2023, 1, 1));
        tenantService.createTenant(tenant1);

        // Create a contract
        Contract contract1 = new Contract();
        contract1.setStartDate(LocalDate.of(2023, 1, 1));
        contract1.setEndDate(LocalDate.of(2024, 1, 1));
        contract1.setMonthlyRent(BigDecimal.valueOf(1200));
        contract1.setLandlord(landlord1);
        contract1.setTenants(Arrays.asList(tenant1));
        contract1.setAsset(realEstateObject1);
        contractService.createContract(contract1);

        // Create a craftsman firm
        CraftsmanFirm craftsmanFirm1 = new CraftsmanFirm();
        craftsmanFirm1.setCompanyName("FixIt Ltd.");
        craftsmanFirm1.setContactDetails("fixit@example.com, 555-4321");
        craftsmanFirm1.setVatNumber("DE123456789");
        craftsmanFirm1.setRegistrationNumber("HRB 12345");
        
        // Address
        craftsmanFirm1.setStreet("Handwerkerstraße");
        craftsmanFirm1.setHouseNumber("42");
        craftsmanFirm1.setPostalCode("50667");
        craftsmanFirm1.setCity("Köln");
        craftsmanFirm1.setCountry("Germany");
        
        // Business Details
        craftsmanFirm1.setTrades(Set.of(TradeType.PLUMBING, TradeType.HEATING));
        craftsmanFirm1.setIsEmergencyServiceProvider(true);
        craftsmanFirm1.setEmergencyPhone("0800-24/7-FIXIT");
        craftsmanFirm1.setAvailabilityHours("{'Mon-Fri': '8:00-18:00', 'Sat': '9:00-14:00'}");
        craftsmanFirm1.setAcceptedPaymentMethods(Set.of("BANK_TRANSFER", "CREDIT_CARD"));
        craftsmanFirm1.setStandardHourlyRate(new BigDecimal("85.00"));
        craftsmanFirm1.setEmergencyHourlyRate(new BigDecimal("150.00"));
        craftsmanFirm1.setTravelCostPerKm(new BigDecimal("0.50"));
        craftsmanFirm1.setStandardWarrantyMonths(24);
        
        // Service Area
        craftsmanFirm1.setServicePostalCodes(Set.of("50667", "50668", "50669", "50670"));
        craftsmanFirm1.setMaxTravelRadiusKm(50);
        
        // Financial Information
        craftsmanFirm1.setIban("DE89 3704 0044 0532 0130 00");
        craftsmanFirm1.setBic("COBADEFFXXX");
        craftsmanFirm1.setBankName("Commerzbank");
        craftsmanFirm1.setAccountHolder("FixIt Ltd.");
        
        // Performance Metrics
        craftsmanFirm1.setAverageRating(4.8);
        craftsmanFirm1.setCompletedJobsCount(256);
        craftsmanFirm1.setCancelledJobsCount(3);
        craftsmanFirm1.setEmergencyResponseTimeMinutes(45);
        
        // Relationship Management
        craftsmanFirm1.setLastJobDate(LocalDateTime.now().minusDays(5));
        craftsmanFirm1.setContractedSince(LocalDateTime.now().minusYears(2));
        craftsmanFirm1.setIsPreferredPartner(true);
        craftsmanFirm1.setFrameworkContractNumber("FC-2021-123");
        craftsmanFirm1.setNegotiatedDiscount(new BigDecimal("10.00"));
        
        // Audit Fields
        craftsmanFirm1.setCreatedAt(LocalDateTime.now());
        craftsmanFirm1.setUpdatedAt(LocalDateTime.now());
        craftsmanFirm1.setCreatedBy("system");
        craftsmanFirm1.setLastModifiedBy("system");
        
        craftsmanFirm1.setLandlord(landlord1);
        craftsmanFirmService.createCraftsmanFirm(craftsmanFirm1);

        // Create a ticket for a tenant issue
        Ticket ticket1 = new Ticket();
        ticket1.setDescription("Water leak in the kitchen");
        ticket1.setStatus("OPEN");
        ticket1.setCreationDate(LocalDateTime.now());
        ticket1.setTenant(tenant1);
        ticket1.setLandlord(landlord1);
        ticket1.setAsset(realEstateObject1);
        ticket1.setCraftsmanFirm(craftsmanFirm1); // Optional, can be assigned later
        ticketService.createTicket(ticket1);

        System.out.println("Test data inserted into the database.");
    }
}
