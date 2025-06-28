package de.propadmin.rentalmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.propadmin.rentalmanager.models.CraftsmanFirm;
import de.propadmin.rentalmanager.models.FrameworkContract;
import de.propadmin.rentalmanager.models.Landlord;
import de.propadmin.rentalmanager.models.Tenant;
import de.propadmin.rentalmanager.models.Ticket;
import de.propadmin.rentalmanager.models.TicketComment;
import de.propadmin.rentalmanager.models.enums.TicketStatus;
import de.propadmin.rentalmanager.repositories.FrameworkContractRepository;
import de.propadmin.rentalmanager.repositories.TicketCommentRepository;
import de.propadmin.rentalmanager.repositories.TicketRepository;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    
    @Mock
    private TicketCommentRepository ticketCommentRepository;
    
    @Mock
    private FrameworkContractRepository frameworkContractRepository;
    
    @InjectMocks
    private TicketService ticketService;
    
    private Ticket testTicket;
    private Tenant testTenant;
    private Landlord testLandlord;
    private CraftsmanFirm testCraftsmanFirm;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testTenant = new Tenant();
        testTenant.setId(1L);
        
        testLandlord = new Landlord();
        testLandlord.setId(1L);
        
        testCraftsmanFirm = new CraftsmanFirm();
        testCraftsmanFirm.setId(1L);
        
        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setDescription("Test ticket");
        testTicket.setStatus(TicketStatus.OPEN);
        testTicket.setCreationDate(LocalDateTime.now());
        testTicket.setTenant(testTenant);
        testTicket.setLandlord(testLandlord);
    }
    
    @Test
    void testOpenTicket() {
        // Given
        Ticket newTicket = new Ticket();
        newTicket.setDescription("New ticket");
        newTicket.setTenant(testTenant);
        newTicket.setLandlord(testLandlord);
        
        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setDescription("New ticket");
        savedTicket.setStatus(TicketStatus.OPEN);
        savedTicket.setTenant(testTenant);
        savedTicket.setLandlord(testLandlord);
        
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(savedTicket));
        
        TicketComment mockComment = new TicketComment();
        mockComment.setId(1L);
        when(ticketCommentRepository.save(any(TicketComment.class))).thenReturn(mockComment);
        
        // When
        Ticket result = ticketService.openTicket(newTicket, "Initial comment", "tenant1");
        
        // Then
        assertNotNull(result);
        assertEquals(TicketStatus.OPEN, result.getStatus());
        verify(ticketRepository).save(newTicket);
    }
    
    @Test
    void testCloseTicket() {
        // Given
        when(ticketRepository.findByIdAndTenantId(1L, 1L)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        
        TicketComment mockComment = new TicketComment();
        mockComment.setId(1L);
        when(ticketCommentRepository.save(any(TicketComment.class))).thenReturn(mockComment);
        
        // When
        Ticket result = ticketService.closeTicket(1L, 1L, "Closing comment", "tenant1");
        
        // Then
        assertNotNull(result);
        assertEquals(TicketStatus.CLOSED, testTicket.getStatus());
        verify(ticketRepository).save(testTicket);
    }
    
    @Test
    void testCloseTicketNotFound() {
        // Given
        when(ticketRepository.findByIdAndTenantId(1L, 1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> ticketService.closeTicket(1L, 1L, "Closing comment", "tenant1"));
    }
    
    @Test
    void testAssignCraftsmanFirm() {
        // Given
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);
        
        // When
        Ticket result = ticketService.assignCraftsmanFirm(1L, 1L, "Assignment comment", "landlord1");
        
        // Then
        assertNotNull(result);
        assertEquals(TicketStatus.IN_PROGRESS, testTicket.getStatus());
        verify(ticketRepository).save(testTicket);
    }
    
    @Test
    void testAutoAssignCraftsmanFirm() {
        // Given
        FrameworkContract activeContract = new FrameworkContract();
        activeContract.setCraftsmanFirm(testCraftsmanFirm);
        
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(frameworkContractRepository.findActiveByLandlordId(1L))
            .thenReturn(Arrays.asList(activeContract));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);
        
        // When
        Ticket result = ticketService.autoAssignCraftsmanFirm(1L, "Auto assignment", "system");
        
        // Then
        assertNotNull(result);
        assertEquals(TicketStatus.IN_PROGRESS, testTicket.getStatus());
        assertNotNull(testTicket.getCraftsmanFirm());
        verify(ticketRepository).save(testTicket);
    }
    
    @Test
    void testAutoAssignCraftsmanFirmNoActiveContracts() {
        // Given
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(frameworkContractRepository.findActiveByLandlordId(1L))
            .thenReturn(Arrays.asList());
        
        // When & Then
        assertThrows(IllegalStateException.class, 
            () -> ticketService.autoAssignCraftsmanFirm(1L, "Auto assignment", "system"));
    }
    
    @Test
    void testResolveTicket() {
        // Given
        testTicket.setCraftsmanFirm(testCraftsmanFirm);
        when(ticketRepository.findByIdAndCraftsmanFirmId(1L, 1L)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        
        TicketComment mockComment = new TicketComment();
        mockComment.setId(1L);
        when(ticketCommentRepository.save(any(TicketComment.class))).thenReturn(mockComment);
        
        // When
        Ticket result = ticketService.resolveTicket(1L, 1L, "Resolution comment", "craftsman1");
        
        // Then
        assertNotNull(result);
        assertEquals(TicketStatus.RESOLVED, testTicket.getStatus());
        verify(ticketRepository).save(testTicket);
    }
    
    @Test
    void testAddComment() {
        // Given
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        
        TicketComment savedComment = new TicketComment();
        savedComment.setId(1L);
        savedComment.setComment("Test comment");
        savedComment.setCreatedBy("user1");
        savedComment.setTicket(testTicket);
        
        when(ticketCommentRepository.save(any(TicketComment.class))).thenReturn(savedComment);
        
        // When
        TicketComment result = ticketService.addComment(1L, "Test comment", "user1");
        
        // Then
        assertNotNull(result);
        assertEquals("Test comment", result.getComment());
        assertEquals("user1", result.getCreatedBy());
        verify(ticketCommentRepository).save(any(TicketComment.class));
    }
}