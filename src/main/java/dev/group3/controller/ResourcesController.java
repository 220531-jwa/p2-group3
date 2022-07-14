package dev.group3.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Service;
import dev.group3.service.ResourcesService;
import io.javalin.http.Context;
import kotlin.Pair;

public class ResourcesController {
    
    private ResourcesService rs = new ResourcesService();
    private static Logger log = LogManager.getLogger(ReservationController.class);
    
    /**
     * Handles the HTTP GET request for retrieving dog services allowed to the user when making a reservation
     * @return 200 with services information, 400 series error otherwise
     */
    public void getAllServices(Context ctx) {
        log.debug("HTTP GET request recieved at endpoint /services");
        
        // Attempting to get services
        Pair<List<Service>, Integer> result = rs.getAllServices();
        
        // Checking if services were retrieved successfully
        if (result != null) {
            log.info("Successfully retrieved services");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }
}
