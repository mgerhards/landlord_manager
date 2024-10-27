package de.propadmin.rentalmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.propadmin.rentalmanager.models.Contract;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.service.ContractService;
import de.propadmin.rentalmanager.service.CraftsmanFirmService;
import de.propadmin.rentalmanager.service.LandlordService;
import de.propadmin.rentalmanager.service.RealEstateObjectService;
import de.propadmin.rentalmanager.service.TenantService;
import de.propadmin.rentalmanager.service.TicketService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public void run(String... args) throws Exception {
        // Create a landlord
        Landlord landlord1 = new Landlord();
        landlord1.setName("John Doe");
        landlord1.setEmail("john.doe@example.com");
        landlord1.setPhoneNumber("555-1234");
        landlordService.createLandlord(landlord1);

        // Create a real estate object (property)
        RealEstateObject realEstateObject1 = new RealEstateObject();
        realEstateObject1.setAddress("123 Main Street");
        realEstateObject1.setSize(120.5);
        realEstateObject1.setNumberOfRooms(3);
        realEstateObject1.setDescription("Beautiful 3-room apartment");
        realEstateObject1.setLandlord(landlord1);
        realEstateObjectService.createRealEstateObject(realEstateObject1);

        // Create a tenant
        Tenant tenant1 = new Tenant();
        tenant1.setName("Jane Smith");
        tenant1.setEmail("jane.smith@example.com");
        tenant1.setPhoneNumber("555-6789");
        tenantService.createTenant(tenant1);

        // Create a contract
        Contract contract1 = new Contract();
        contract1.setStartDate(LocalDate.of(2023, 1, 1));
        contract1.setEndDate(LocalDate.of(2024, 1, 1));
        contract1.setMonthlyRent(BigDecimal.valueOf(1200));
        contract1.setLandlord(landlord1);
        contract1.setTenant(tenant1);
        contract1.setAsset(realEstateObject1);
        contractService.createContract(contract1);

        // Create a craftsman firm
        CraftsmanFirm craftsmanFirm1 = new CraftsmanFirm();
        craftsmanFirm1.setCompanyName("FixIt Ltd.");
        craftsmanFirm1.setContactDetails("fixit@example.com, 555-4321");
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
