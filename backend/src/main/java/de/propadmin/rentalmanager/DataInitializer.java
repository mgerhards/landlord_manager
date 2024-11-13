package de.propadmin.rentalmanager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.FrameworkContract;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.enums.HeatingType;
import de.propadmin.rentalmanager.models.enums.PaymentMethod;
import de.propadmin.rentalmanager.models.enums.PropertyType;
import de.propadmin.rentalmanager.models.enums.TradeType;
import de.propadmin.rentalmanager.repositories.CraftsmanFirmRepository;
import de.propadmin.rentalmanager.repositories.FrameworkContractRepository;
import de.propadmin.rentalmanager.service.ContractService;
import de.propadmin.rentalmanager.service.LandlordService;
import de.propadmin.rentalmanager.service.RealEstateObjectService;
import de.propadmin.rentalmanager.service.TenantService;
import de.propadmin.rentalmanager.service.TicketService;
import de.propadmin.rentalmanager.utils.GeocodeResponse;
import de.propadmin.rentalmanager.utils.GeocodeUtils;


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
    private CraftsmanFirmRepository craftsmanFirmRepository;

    @Autowired
    private FrameworkContractRepository frameworkContractRepository;

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

       // Create craftsman firm
        CraftsmanFirm craftsmanFirm1 = new CraftsmanFirm();
        craftsmanFirm1.setCompanyName("Meister Röhrich GmbH");
        craftsmanFirm1.setVatNumber("DE123456789");
        craftsmanFirm1.setRegistrationNumber("HRB 12345");
        craftsmanFirm1.setPrimaryContactName("Hans Röhrich");
        craftsmanFirm1.setPhone("+49 30 12345678");
        craftsmanFirm1.setEmergencyPhone("+49 176 12345678");
        craftsmanFirm1.setEmail("info@roehrich-gmbh.de");
        craftsmanFirm1.setWebsite("www.roehrich-gmbh.de");
        craftsmanFirm1.setStreet("Wasserstraße");
        craftsmanFirm1.setHouseNumber("42");
        craftsmanFirm1.setPostalCode("12345");
        craftsmanFirm1.setCity("Berlin");
        craftsmanFirm1.setCountry("Deutschland");
        craftsmanFirm1.setTrades(Set.of(TradeType.PLUMBING, TradeType.HEATING));
        craftsmanFirm1.setIsEmergencyServiceProvider(true);
        craftsmanFirm1.setAvailabilityHours("Mo-Fr 8:00-17:00");
        craftsmanFirm1.setServicePostalCodes(Set.of("12345", "12347", "12348"));
        craftsmanFirm1.setMaxTravelRadiusKm(50);
        craftsmanFirm1.setStandardHourlyRate(new BigDecimal("85.00"));
        craftsmanFirm1.setEmergencyHourlyRate(new BigDecimal("150.00"));
        craftsmanFirm1.setTravelCostPerKm(new BigDecimal("0.50"));
        craftsmanFirm1.setStandardWarrantyMonths(24);
        craftsmanFirmRepository.save(craftsmanFirm1);

        // Create framework contract
        FrameworkContract fcontract1 = new FrameworkContract();
        fcontract1.setCraftsmanFirm(craftsmanFirm1);
        fcontract1.setLandlord(landlord1);
        fcontract1.setContractNumber("FC-2024-001");
        fcontract1.setStartDate(LocalDate.now());
        fcontract1.setEndDate(LocalDate.now().plusYears(2));
        fcontract1.setIsActive(true);
        fcontract1.setNegotiatedHourlyRate(new BigDecimal("80.00")); // Discounted rate
        fcontract1.setNegotiatedEmergencyRate(new BigDecimal("140.00"));
        fcontract1.setNegotiatedTravelCost(new BigDecimal("0.45"));
        fcontract1.setDiscountPercentage(new BigDecimal("5.00"));
        fcontract1.setWarrantyMonths(24);
        fcontract1.setIban("DE89370400440532013000");
        fcontract1.setBic("DEUTDEBBXXX");
        fcontract1.setBankName("Deutsche Bank");
        fcontract1.setAccountHolder("Meister Röhrich GmbH");
        fcontract1.setPaymentTermDays(30);
        fcontract1.setAcceptedPaymentMethods(Set.of(PaymentMethod.BANK_TRANSFER, PaymentMethod.INVOICE));
        fcontract1.setMaxResponseTimeHours(24);
        fcontract1.setEmergencyResponseTimeMinutes(120);
        fcontract1.setIncludesWeekendService(false);
        fcontract1.setServiceHours("Mo-Fr 8:00-17:00");
        fcontract1.setCoveredTrades(Set.of(TradeType.PLUMBING, TradeType.HEATING));
        fcontract1.setRequiredInsuranceAmount(new BigDecimal("1000000.00"));
        fcontract1.setInsuranceExpiryDate(LocalDate.now().plusYears(1));
        fcontract1.setInsurancePolicyNumber("POL-123456");
        frameworkContractRepository.save(fcontract1);

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
