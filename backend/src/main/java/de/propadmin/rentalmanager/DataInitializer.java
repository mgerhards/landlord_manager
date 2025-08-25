package de.propadmin.rentalmanager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.ContactPerson;
import de.propadmin.rentalmanager.models.FrameworkContract;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.UserAccount;
import de.propadmin.rentalmanager.models.enums.HeatingType;
import de.propadmin.rentalmanager.models.enums.PaymentMethod;
import de.propadmin.rentalmanager.models.enums.PropertyType;
import de.propadmin.rentalmanager.models.enums.TicketStatus;
import de.propadmin.rentalmanager.models.enums.TradeType;
import de.propadmin.rentalmanager.repositories.CraftsmanFirmRepository;
import de.propadmin.rentalmanager.repositories.FrameworkContractRepository;
import de.propadmin.rentalmanager.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        if (userRepository.count() > 0 || landlordService.count() > 0 || realEstateObjectService.count() > 0) {
            System.out.println("Database is not empty, skipping data initialization.");
            return;
        }

        // Create a landlord
        Landlord landlord1 = new Landlord();
        // Create and set UserAccount for landlord
        de.propadmin.rentalmanager.models.UserAccount landlordAccount = new de.propadmin.rentalmanager.models.UserAccount();
        landlordAccount.setEmail("info@gendis.de");
        landlordAccount.setPassword(passwordEncoder.encode("password"));
        landlordAccount = userRepository.save(landlordAccount);     
        landlord1.setUserAccount(landlordAccount);
        landlord1.setName("John Doe");
                
        landlord1.setPhoneNumber("555-1234");
        landlord1.setLicenseKey("LICENSE-1234-5678-9012");
        landlord1.setLicenseExpirationDate(LocalDate.of(2030, 12, 31)); // License valid until 2030
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
        // Create and set UserAccount for tenant
        UserAccount tenantAccount = new UserAccount();
        tenantAccount.setEmail("jane.smith@example.com");
        tenantAccount.setPassword(passwordEncoder.encode("password"));
        tenantAccount = userRepository.save(tenantAccount); 

        tenant1.setUserAccount(tenantAccount);
        tenant1.setName("Jane Smith");
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

        Tenant tenant2 = new Tenant();
        // Create and set UserAccount for tenant2
        UserAccount tenant2Account = new UserAccount();
        tenant2Account.setEmail("michael.brown@example.com");
        tenant2Account.setPassword(passwordEncoder.encode("password"));
        tenant2Account = userRepository.save(tenant2Account);

        tenant2.setUserAccount(tenant2Account);
        tenant2.setName("Michael Brown");
        tenant2.setPhoneNumber("555-2468");
        tenant2.setDateOfBirth(LocalDate.of(1990, 3, 22));
        tenant2.setIdNumber("ID987654321");
        tenant2.setOccupation("Architect");
        tenant2.setEmployerName("Design Studio");
        tenant2.setEmployerContact("contact@designstudio.com");
        tenant2.setAlternativePhoneNumber("555-1357");
        tenant2.setWorkPhoneNumber("555-8642");
        tenant2.setEmergencyContactName("Sarah Brown");
        tenant2.setEmergencyContactPhone("555-2468");
        tenant2.setBankAccountHolder("Michael Brown");
        tenant2.setIban("DE12 3456 7890 1234 5678 90");
        tenant2.setBic("DEUTDEFFXXX");
        tenant2.setNumberOfOccupants(1);
        tenant2.setMoveInDate(LocalDate.of(2024, 4, 1));
        tenantService.createTenant(tenant2);

        // Create a contract
        Contract contract1 = new Contract();
        contract1.setStartDate(LocalDate.of(2023, 1, 1));
        contract1.setEndDate(LocalDate.of(2024, 1, 1));
        contract1.setMonthlyRent(BigDecimal.valueOf(1200));
        contract1.setLandlord(landlord1);
        contract1.setTenants(Arrays.asList(tenant1));
        contract1.setAsset(realEstateObject1);
        contract1.setContractNumber("C-2023-001");
        contractService.createContract(contract1);
        
        // Create another contract
        Contract contract2 = new Contract();
        contract2.setStartDate(LocalDate.of(2024, 3, 1));
        contract2.setEndDate(null);
        contract2.setMonthlyRent(BigDecimal.valueOf(1200));
        contract2.setLandlord(landlord1);
        contract2.setTenants(Arrays.asList(tenant2));
        contract2.setAsset(realEstateObject1);
        contract2.setContractNumber("C-2024-002");
       
        contractService.createContract(contract2);

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

        // Create contact persons for the craftsman firm
        ContactPerson contactPerson1 = new ContactPerson();
        // Create and set UserAccount for contactPerson1
        UserAccount contactPersonAccount = new UserAccount();
        contactPersonAccount.setEmail("hans.roehrich@roehrich-gmbh.de");
        contactPersonAccount.setPassword(passwordEncoder.encode("password"));
        contactPersonAccount = userRepository.save(contactPersonAccount); 
        
        contactPerson1.setUserAccount(contactPersonAccount);
        contactPerson1.setName("Hans Röhrich");
        contactPerson1.setPhoneNumber("+49 30 12345678");
        contactPerson1.setRole(ContactPerson.Role.CRAFTSMAN);
        contactPerson1.setCraftsmanFirm(craftsmanFirm1);

        ContactPerson contactPerson2 = new ContactPerson();
        // Create and set UserAccount for contactPerson2
        UserAccount contactPerson2Account = new UserAccount();
        contactPerson2Account.setEmail("anna.mueller@roehrich-gmbh.de");
        contactPerson2Account.setPassword(passwordEncoder.encode("password"));
        contactPerson2.setUserAccount(contactPerson2Account);
        contactPerson2.setName("Anna Müller");
        contactPerson2.setPhoneNumber("+49 30 87654321");
        contactPerson2.setRole(ContactPerson.Role.ADMINISTRATIVE_PERSONNEL);
        contactPerson2.setCraftsmanFirm(craftsmanFirm1);

        // Save contact persons
        craftsmanFirm1.setContactPersons(Arrays.asList(contactPerson1, contactPerson2));
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

        // Create additional craftsman firms with different emergency rates for testing
        
        // Company 2 - More expensive emergency rate
        CraftsmanFirm craftsmanFirm2 = new CraftsmanFirm();
        craftsmanFirm2.setCompanyName("Schnell-Service Weber AG");
        craftsmanFirm2.setVatNumber("DE987654321");
        craftsmanFirm2.setRegistrationNumber("HRB 98765");
        craftsmanFirm2.setPrimaryContactName("Klaus Weber");
        craftsmanFirm2.setPhone("+49 30 98765432");
        craftsmanFirm2.setEmergencyPhone("+49 176 98765432");
        craftsmanFirm2.setEmail("info@weber-service.de");
        craftsmanFirm2.setWebsite("www.weber-service.de");
        craftsmanFirm2.setStreet("Hauptstraße");
        craftsmanFirm2.setHouseNumber("123");
        craftsmanFirm2.setPostalCode("12349");
        craftsmanFirm2.setCity("Berlin");
        craftsmanFirm2.setCountry("Deutschland");
        craftsmanFirm2.setTrades(Set.of(TradeType.ELECTRICAL, TradeType.CLEANING));
        craftsmanFirm2.setIsEmergencyServiceProvider(true);
        craftsmanFirm2.setAvailabilityHours("24/7");
        craftsmanFirm2.setServicePostalCodes(Set.of("12349", "12350", "12351"));
        craftsmanFirm2.setMaxTravelRadiusKm(30);
        craftsmanFirm2.setStandardHourlyRate(new BigDecimal("95.00"));
        craftsmanFirm2.setEmergencyHourlyRate(new BigDecimal("180.00")); // Most expensive
        craftsmanFirm2.setTravelCostPerKm(new BigDecimal("0.60"));
        craftsmanFirm2.setStandardWarrantyMonths(12);
        craftsmanFirm2 = craftsmanFirmRepository.save(craftsmanFirm2);

        // Framework contract for Company 2
        FrameworkContract fcontract2 = new FrameworkContract();
        fcontract2.setCraftsmanFirm(craftsmanFirm2);
        fcontract2.setLandlord(landlord1);
        fcontract2.setContractNumber("FC-2024-002");
        fcontract2.setStartDate(LocalDate.now());
        fcontract2.setEndDate(LocalDate.now().plusYears(1));
        fcontract2.setIsActive(true);
        fcontract2.setNegotiatedHourlyRate(new BigDecimal("90.00"));
        fcontract2.setNegotiatedEmergencyRate(new BigDecimal("170.00"));
        fcontract2.setNegotiatedTravelCost(new BigDecimal("0.55"));
        fcontract2.setDiscountPercentage(new BigDecimal("3.00"));
        fcontract2.setWarrantyMonths(12);
        fcontract2.setIban("DE89370400440532013001");
        fcontract2.setBic("DEUTDEBBXXX");
        fcontract2.setBankName("Deutsche Bank");
        fcontract2.setAccountHolder("Schnell-Service Weber AG");
        fcontract2.setPaymentTermDays(14);
        fcontract2.setAcceptedPaymentMethods(Set.of(PaymentMethod.BANK_TRANSFER));
        fcontract2.setMaxResponseTimeHours(12);
        fcontract2.setEmergencyResponseTimeMinutes(60);
        fcontract2.setIncludesWeekendService(true);
        fcontract2.setServiceHours("24/7");
        fcontract2.setCoveredTrades(Set.of(TradeType.ELECTRICAL, TradeType.CLEANING));
        fcontract2.setRequiredInsuranceAmount(new BigDecimal("2000000.00"));
        fcontract2.setInsuranceExpiryDate(LocalDate.now().plusYears(1));
        fcontract2.setInsurancePolicyNumber("POL-789012");
        frameworkContractRepository.save(fcontract2);

        // Company 3 - Cheapest emergency rate
        CraftsmanFirm craftsmanFirm3 = new CraftsmanFirm();
        craftsmanFirm3.setCompanyName("Budget Handwerk GmbH");
        craftsmanFirm3.setVatNumber("DE456789012");
        craftsmanFirm3.setRegistrationNumber("HRB 45678");
        craftsmanFirm3.setPrimaryContactName("Maria Schmidt");
        craftsmanFirm3.setPhone("+49 30 45678901");
        craftsmanFirm3.setEmergencyPhone("+49 176 45678901");
        craftsmanFirm3.setEmail("info@budget-handwerk.de");
        craftsmanFirm3.setWebsite("www.budget-handwerk.de");
        craftsmanFirm3.setStreet("Werkstraße");
        craftsmanFirm3.setHouseNumber("7");
        craftsmanFirm3.setPostalCode("12353");
        craftsmanFirm3.setCity("Berlin");
        craftsmanFirm3.setCountry("Deutschland");
        craftsmanFirm3.setTrades(Set.of(TradeType.PLUMBING, TradeType.PAINTING));
        craftsmanFirm3.setIsEmergencyServiceProvider(true);
        craftsmanFirm3.setAvailabilityHours("Mo-So 7:00-22:00");
        craftsmanFirm3.setServicePostalCodes(Set.of("12353", "12354", "12355"));
        craftsmanFirm3.setMaxTravelRadiusKm(40);
        craftsmanFirm3.setStandardHourlyRate(new BigDecimal("75.00"));
        craftsmanFirm3.setEmergencyHourlyRate(new BigDecimal("120.00")); // Cheapest
        craftsmanFirm3.setTravelCostPerKm(new BigDecimal("0.40"));
        craftsmanFirm3.setStandardWarrantyMonths(18);
        craftsmanFirm3 = craftsmanFirmRepository.save(craftsmanFirm3);

        // Framework contract for Company 3
        FrameworkContract fcontract3 = new FrameworkContract();
        fcontract3.setCraftsmanFirm(craftsmanFirm3);
        fcontract3.setLandlord(landlord1);
        fcontract3.setContractNumber("FC-2024-003");
        fcontract3.setStartDate(LocalDate.now());
        fcontract3.setEndDate(LocalDate.now().plusYears(3));
        fcontract3.setIsActive(true);
        fcontract3.setNegotiatedHourlyRate(new BigDecimal("70.00"));
        fcontract3.setNegotiatedEmergencyRate(new BigDecimal("110.00"));
        fcontract3.setNegotiatedTravelCost(new BigDecimal("0.35"));
        fcontract3.setDiscountPercentage(new BigDecimal("7.00"));
        fcontract3.setWarrantyMonths(18);
        fcontract3.setIban("DE89370400440532013002");
        fcontract3.setBic("DEUTDEBBXXX");
        fcontract3.setBankName("Deutsche Bank");
        fcontract3.setAccountHolder("Budget Handwerk GmbH");
        fcontract3.setPaymentTermDays(21);
        fcontract3.setAcceptedPaymentMethods(Set.of(PaymentMethod.BANK_TRANSFER, PaymentMethod.INVOICE));
        fcontract3.setMaxResponseTimeHours(18);
        fcontract3.setEmergencyResponseTimeMinutes(90);
        fcontract3.setIncludesWeekendService(false);
        fcontract3.setServiceHours("Mo-So 7:00-22:00");
        fcontract3.setCoveredTrades(Set.of(TradeType.PLUMBING, TradeType.PAINTING));
        fcontract3.setRequiredInsuranceAmount(new BigDecimal("1500000.00"));
        fcontract3.setInsuranceExpiryDate(LocalDate.now().plusYears(1));
        fcontract3.setInsurancePolicyNumber("POL-345678");
        frameworkContractRepository.save(fcontract3);

        // Company 4 - Medium emergency rate
        CraftsmanFirm craftsmanFirm4 = new CraftsmanFirm();
        craftsmanFirm4.setCompanyName("Premium Service Müller OHG");
        craftsmanFirm4.setVatNumber("DE123456780");
        craftsmanFirm4.setRegistrationNumber("HRB 12350");
        craftsmanFirm4.setPrimaryContactName("Thomas Müller");
        craftsmanFirm4.setPhone("+49 30 11223344");
        craftsmanFirm4.setEmergencyPhone("+49 176 11223344");
        craftsmanFirm4.setEmail("info@premium-mueller.de");
        craftsmanFirm4.setWebsite("www.premium-mueller.de");
        craftsmanFirm4.setStreet("Servicestraße");
        craftsmanFirm4.setHouseNumber("15");
        craftsmanFirm4.setPostalCode("12357");
        craftsmanFirm4.setCity("Berlin");
        craftsmanFirm4.setCountry("Deutschland");
        craftsmanFirm4.setTrades(Set.of(TradeType.HEATING, TradeType.ELECTRICAL));
        craftsmanFirm4.setIsEmergencyServiceProvider(true);
        craftsmanFirm4.setAvailabilityHours("Mo-Sa 8:00-20:00");
        craftsmanFirm4.setServicePostalCodes(Set.of("12357", "12358", "12359"));
        craftsmanFirm4.setMaxTravelRadiusKm(35);
        craftsmanFirm4.setStandardHourlyRate(new BigDecimal("90.00"));
        craftsmanFirm4.setEmergencyHourlyRate(new BigDecimal("160.00")); // Medium price
        craftsmanFirm4.setTravelCostPerKm(new BigDecimal("0.55"));
        craftsmanFirm4.setStandardWarrantyMonths(36);
        craftsmanFirm4 = craftsmanFirmRepository.save(craftsmanFirm4);

        // Framework contract for Company 4
        FrameworkContract fcontract4 = new FrameworkContract();
        fcontract4.setCraftsmanFirm(craftsmanFirm4);
        fcontract4.setLandlord(landlord1);
        fcontract4.setContractNumber("FC-2024-004");
        fcontract4.setStartDate(LocalDate.now());
        fcontract4.setEndDate(LocalDate.now().plusYears(2));
        fcontract4.setIsActive(true);
        fcontract4.setNegotiatedHourlyRate(new BigDecimal("85.00"));
        fcontract4.setNegotiatedEmergencyRate(new BigDecimal("150.00"));
        fcontract4.setNegotiatedTravelCost(new BigDecimal("0.50"));
        fcontract4.setDiscountPercentage(new BigDecimal("4.00"));
        fcontract4.setWarrantyMonths(36);
        fcontract4.setIban("DE89370400440532013003");
        fcontract4.setBic("DEUTDEBBXXX");
        fcontract4.setBankName("Deutsche Bank");
        fcontract4.setAccountHolder("Premium Service Müller OHG");
        fcontract4.setPaymentTermDays(30);
        fcontract4.setAcceptedPaymentMethods(Set.of(PaymentMethod.BANK_TRANSFER, PaymentMethod.INVOICE, PaymentMethod.CASH));
        fcontract4.setMaxResponseTimeHours(16);
        fcontract4.setEmergencyResponseTimeMinutes(75);
        fcontract4.setIncludesWeekendService(false);
        fcontract4.setServiceHours("Mo-Sa 8:00-20:00");
        fcontract4.setCoveredTrades(Set.of(TradeType.HEATING, TradeType.ELECTRICAL));
        fcontract4.setRequiredInsuranceAmount(new BigDecimal("3000000.00"));
        fcontract4.setInsuranceExpiryDate(LocalDate.now().plusYears(1));
        fcontract4.setInsurancePolicyNumber("POL-901234");
        frameworkContractRepository.save(fcontract4);

        // Create a ticket for a tenant issue
        Ticket ticket1 = new Ticket();
        ticket1.setDescription("Water leak in the kitchen");
        ticket1.setStatus(TicketStatus.OPEN);
        ticket1.setCreationDate(LocalDateTime.now());
        ticket1.setTenant(tenant1);
        ticket1.setLandlord(landlord1);
        ticket1.setAsset(realEstateObject1);
        ticket1.setCraftsmanFirm(craftsmanFirm1); // Optional, can be assigned later
        ticketService.createTicket(ticket1);

        System.out.println("Test data inserted into the database.");
    }
}
