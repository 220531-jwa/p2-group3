package dev.group3;

import static io.javalin.apibuilder.ApiBuilder.path;

import dev.group3.controller.ReservationController;
import dev.group3.repo.ReservationDAO;
import dev.group3.service.ReservationService;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Driver {
    
    public static void main(String[] args) {
    	// Creating controllers
        ReservationDAO rd = new ReservationDAO();
        ReservationController rc = new ReservationController(new ReservationService(new ReservationDAO()));
    	// Init server
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public", Location.CLASSPATH);
        });
        // Starting server
        app.start(8080);

        // Handling end-points
        app.routes(() -> {
            path("/login", () -> {
                
            });
        });
            // and so on
            //User can submit a reservation 
            path("/{userid}/newreservation", () -> { 
            	post(rc::createReservation);
            });
    } 
        
        // End of end-points
    }


