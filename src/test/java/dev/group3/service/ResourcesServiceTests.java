package dev.group3.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.group3.model.Service;
import dev.group3.repo.ResourcesDAO;
import dev.group3.util.MockDataSet;
import kotlin.Pair;

@ExtendWith(MockitoExtension.class)
public class ResourcesServiceTests {
    
    // Init
    private static ResourcesService resService;;
    
    // Mock DAO
    private static ResourcesDAO mockResDAO;
    
    // Mock Database Data
    private static List<Service> mockServices;
    
    /*
     * === SETUP ===
     */
    
    @BeforeAll
    public static void setup() {
        mockResDAO = mock(ResourcesDAO.class);
        resService = new ResourcesService(mockResDAO);
        refreshMockData();
    }
    
    /*
     * === SETUP UTILITY ===
     */
    
    public static void refreshMockData() {
        // Getting refreshed mock data
        mockServices = MockDataSet.getServiceTestSet();
        
        // Mocking DAO behavior
        when(mockResDAO.getAllServices()).thenReturn(mockServices);
    }
    
    /*
     * === getAllServices TESTS ===
     */
    
    @Test
    public void gas_valid_200ServicesList() {
        // Running test
        Pair<List<Service>, Integer> actualServices = resService.getAllServices();
        Object[] expectedResults = {mockServices, 200};
        Object[] actualResults = {actualServices.getFirst(), actualServices.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
}
