package de.propadmin.rentalmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import de.propadmin.rentalmanager.models.ContactPerson;
import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.UserAccount;
import de.propadmin.rentalmanager.repositories.ContactPersonRepository;
import de.propadmin.rentalmanager.repositories.LandlordRepository;
import de.propadmin.rentalmanager.repositories.TenantRepository;
import de.propadmin.rentalmanager.repositories.TicketRepository;

class TicketAuthorizationServiceTest {

    @Mock
    private TenantRepository tenantRepository;
    
    @Mock
    private LandlordRepository landlordRepository;
    
    @Mock
    private ContactPersonRepository contactPersonRepository;
    
    @Mock
    private TicketRepository ticketRepository;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @InjectMocks
    private TicketAuthorizationService ticketAuthorizationService;
    
    private Ticket testTicket;
    private Tenant testTenant;
    private Landlord testLandlord;
    private CraftsmanFirm testCraftsmanFirm;
    private RealEstateObject testAsset;
    private ContactPerson testContactPerson;
    private UserAccount testUserAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testUserAccount = new UserAccount();
        testUserAccount.setEmail("test@example.com");
        
        testTenant = new Tenant();
        testTenant.setId(1L);
        testTenant.setUserAccount(testUserAccount);
        
        testLandlord = new Landlord();
        testLandlord.setId(1L);
        testLandlord.setUserAccount(testUserAccount);
        
        testCraftsmanFirm = new CraftsmanFirm();
        testCraftsmanFirm.setId(1L);
        
        testContactPerson = new ContactPerson();
        testContactPerson.setId(1L);
        testContactPerson.setUserAccount(testUserAccount);
        testContactPerson.setCraftsmanFirm(testCraftsmanFirm);
        
        testAsset = new RealEstateObject();
        testAsset.setId(1L);
        testAsset.setLandlord(testLandlord);
        
        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setTenant(testTenant);
        testTicket.setAsset(testAsset);
        testTicket.setCraftsmanFirm(testCraftsmanFirm);
    }

    @Test
    void testCanViewTicket_AsTenant_ShouldReturnTrue() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
            when(tenantRepository.findByUserAccountEmail("test@example.com")).thenReturn(testTenant);
            
            // When
            boolean result = ticketAuthorizationService.canViewTicket(1L);
            
            // Then
            assertTrue(result);
        }
    }

    @Test
    void testCanViewTicket_AsLandlord_ShouldReturnTrue() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
            when(tenantRepository.findByUserAccountEmail("test@example.com")).thenReturn(null);
            when(landlordRepository.findByUserAccountEmail("test@example.com")).thenReturn(testLandlord);
            
            // When
            boolean result = ticketAuthorizationService.canViewTicket(1L);
            
            // Then
            assertTrue(result);
        }
    }

    @Test
    void testCanViewTicket_AsContactPerson_ShouldReturnTrue() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
            when(tenantRepository.findByUserAccountEmail("test@example.com")).thenReturn(null);
            when(landlordRepository.findByUserAccountEmail("test@example.com")).thenReturn(null);
            when(contactPersonRepository.findByUserAccountEmailAndCraftsmanFirmId("test@example.com", 1L))
                .thenReturn(Optional.of(testContactPerson));
            
            // When
            boolean result = ticketAuthorizationService.canViewTicket(1L);
            
            // Then
            assertTrue(result);
        }
    }

    @Test
    void testCanViewTicket_AsUnauthorizedUser_ShouldReturnFalse() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("unauthorized@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
            when(tenantRepository.findByUserAccountEmail("unauthorized@example.com")).thenReturn(null);
            when(landlordRepository.findByUserAccountEmail("unauthorized@example.com")).thenReturn(null);
            when(contactPersonRepository.findByUserAccountEmailAndCraftsmanFirmId("unauthorized@example.com", 1L))
                .thenReturn(Optional.empty());
            
            // When
            boolean result = ticketAuthorizationService.canViewTicket(1L);
            
            // Then
            assertFalse(result);
        }
    }

    @Test
    void testCanViewTicket_TicketNotFound_ShouldReturnFalse() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
            
            // When
            boolean result = ticketAuthorizationService.canViewTicket(1L);
            
            // Then
            assertFalse(result);
        }
    }

    @Test
    void testCanViewTicket_NoAuthentication_ShouldReturnFalse() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);
            
            // When
            boolean result = ticketAuthorizationService.canViewTicket(1L);
            
            // Then
            assertFalse(result);
        }
    }

    @Test
    void testGetAuthorizedTicket_Authorized_ShouldReturnTicket() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
            when(tenantRepository.findByUserAccountEmail("test@example.com")).thenReturn(testTenant);
            
            // When
            Optional<Ticket> result = ticketAuthorizationService.getAuthorizedTicket(1L);
            
            // Then
            assertTrue(result.isPresent());
            assertEquals(testTicket, result.get());
        }
    }

    @Test
    void testGetAuthorizedTicket_NotAuthorized_ShouldReturnEmpty() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("unauthorized@example.com");
            
            when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
            when(tenantRepository.findByUserAccountEmail("unauthorized@example.com")).thenReturn(null);
            when(landlordRepository.findByUserAccountEmail("unauthorized@example.com")).thenReturn(null);
            when(contactPersonRepository.findByUserAccountEmailAndCraftsmanFirmId("unauthorized@example.com", 1L))
                .thenReturn(Optional.empty());
            
            // When
            Optional<Ticket> result = ticketAuthorizationService.getAuthorizedTicket(1L);
            
            // Then
            assertTrue(result.isEmpty());
        }
    }
}