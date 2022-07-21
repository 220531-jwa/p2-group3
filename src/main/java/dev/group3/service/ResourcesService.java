package dev.group3.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Service;
import dev.group3.repo.ResourcesDAO;
import kotlin.Pair;

public class ResourcesService {
    
    private ResourcesDAO resDAO;
    private static Logger log = LogManager.getLogger(ResourcesService.class);
    
    // Default constructor
    public ResourcesService() {
        resDAO = new ResourcesDAO();
    }
    
    /**
     * Used for a custom resDAO, more generally used for Mockito
     * @param resDAO The resDAO for this service to use
     */
    public ResourcesService(ResourcesDAO resDAO) {
        this.resDAO = resDAO;
    }

    /**
     * Gets all the services available for use when making a reservation for a dog.
     * Authorization: Anyone can use this, even users that are not logged in.
     * @return 200 with a list of services if successful, and 400 series error otherwise.
     */
    public Pair<List<Service>, Integer> getAllServices() {
        log.debug("Attempting to get all services");
        
        // Attempting to get services
        List<Service> services = resDAO.getAllServices();
        
        // Checking if services were found
        if (services == null) {
            log.fatal("Failed to get services. Possible: Failed to connect to database.");
            return new Pair<List<Service>, Integer>(null, 503);
        }
        
        return new Pair<List<Service>, Integer>(services, 200);
    }
}
