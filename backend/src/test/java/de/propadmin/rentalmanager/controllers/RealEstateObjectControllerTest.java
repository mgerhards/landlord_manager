package de.propadmin.rentalmanager.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.propadmin.rentalmanager.models.RealEstateObject;
import de.propadmin.rentalmanager.service.RealEstateObjectService;

class RealEstateObjectControllerTest {

    @Mock
    private RealEstateObjectService realEstateObjectService;

    @InjectMocks
    private RealEstateObjectController realEstateObjectController;

    private RealEstateObject testObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testObject = new RealEstateObject();
        testObject.setId(1L);
        testObject.setAddress("Test Address 123");
        testObject.setSize(100.0);
        testObject.setNumberOfRooms(3);
        testObject.setDescription("Test property");
    }

    @Test
    void testGetAllRealEstateObjects() {
        // Arrange
        List<RealEstateObject> objects = Arrays.asList(testObject);
        when(realEstateObjectService.getAllRealEstateObjects()).thenReturn(objects);

        // Act
        ResponseEntity<List<RealEstateObject>> response = realEstateObjectController.getAllRealEstateObjects();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(realEstateObjectService).getAllRealEstateObjects();
    }

    @Test
    void testGetRealEstateObjectById() {
        // Arrange
        when(realEstateObjectService.getRealEstateObjectById(1L)).thenReturn(Optional.of(testObject));

        // Act
        ResponseEntity<RealEstateObject> response = realEstateObjectController.getRealEstateObjectById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Address 123", response.getBody().getAddress());
        verify(realEstateObjectService).getRealEstateObjectById(1L);
    }

    @Test
    void testGetRealEstateObjectByIdNotFound() {
        // Arrange
        when(realEstateObjectService.getRealEstateObjectById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<RealEstateObject> response = realEstateObjectController.getRealEstateObjectById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(realEstateObjectService).getRealEstateObjectById(999L);
    }

    @Test
    void testCreateRealEstateObject() {
        // Arrange
        when(realEstateObjectService.createRealEstateObject(any(RealEstateObject.class))).thenReturn(testObject);

        // Act
        ResponseEntity<RealEstateObject> response = realEstateObjectController.createRealEstateObject(testObject);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Address 123", response.getBody().getAddress());
        verify(realEstateObjectService).createRealEstateObject(testObject);
    }

    @Test
    void testUpdateRealEstateObject() {
        // Arrange
        when(realEstateObjectService.updateRealEstateObject(eq(1L), any(RealEstateObject.class))).thenReturn(testObject);

        // Act
        ResponseEntity<RealEstateObject> response = realEstateObjectController.updateRealEstateObject(1L, testObject);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(realEstateObjectService).updateRealEstateObject(1L, testObject);
    }

    @Test
    void testUpdateRealEstateObjectNotFound() {
        // Arrange
        when(realEstateObjectService.updateRealEstateObject(eq(999L), any(RealEstateObject.class)))
            .thenThrow(new RuntimeException("RealEstateObject not found with id: 999"));

        // Act
        ResponseEntity<RealEstateObject> response = realEstateObjectController.updateRealEstateObject(999L, testObject);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(realEstateObjectService).updateRealEstateObject(999L, testObject);
    }

    @Test
    void testDeleteRealEstateObject() {
        // Arrange
        doNothing().when(realEstateObjectService).deleteRealEstateObject(1L);

        // Act
        ResponseEntity<Void> response = realEstateObjectController.deleteRealEstateObject(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(realEstateObjectService).deleteRealEstateObject(1L);
    }
}