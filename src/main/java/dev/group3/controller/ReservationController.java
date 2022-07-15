package dev.group3.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Reservation;
import dev.group3.model.DTO.ReservationDTO;
import dev.group3.service.ReservationService;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;
import kotlin.Pair;

public class ReservationController {

    private ReservationService rs = new ReservationService();
    private static Logger log = LogManager.getLogger(ReservationController.class);

    /*
     * === POST ===
     */

    /**
     * Handles a post request for creating a new reservation
     * Takes the username from the path
     * Takes the token from the header
     * Takes the reservation data form the body
     * @return 200 with new reservation, and 400 series error otherwise
     */
    public void createReservation(Context ctx) {
        log.debug("Recieved HTTP POST request at endpoint /reservations/{username}");

        // Getting user input
        String username = ctx.pathParam("username");
        String token = ctx.header("Token");
        Reservation reservationData = ctx.bodyAsClass(Reservation.class);
        
        // Attempting to create reservation
        Pair<Reservation, Integer> result = rs.createReservation(username, reservationData, token);
        
        // Checking if reservation was created successfully
        if (result.getFirst() != null) {
            log.info("Successfully created new reservation");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }

    /*
     * === GET ===
     */

    /**
     * Handles a get request for getting all reservations
     * Takes the token from the header
     * @return 200 with the reservation list, and 400 series error otherwise
     */
    public void getAllReservations(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /reservations");
        
        // Getting user input
        String token = ctx.header("Token");
        
        // Attempting to get all reservations
        Pair<List<Reservation>, Integer> result = rs.getAllReservations(token);

        // Checking if data was retrieved
        if (result.getFirst() != null) {
            log.info("Successfully got all reservations");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }

    /**
     * Handles a get request for getting a reservation by username
     * Takes the username from the path
     * Takes the token from the header
     * @return 200 with the reservations list, and 400 series error otherwise
     */
    public void getAllRservationsByUsername(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /reservations/{username}");

        // Getting user input
        String userName = ctx.pathParam("username");
        String token = ctx.header("Token");

        // Attempting to get reservations associated with the given username
        Pair<List<Reservation>, Integer> result = rs.getAllReservationsByUsername(userName, token);

        // Checking if data was retrieved
        if (result.getFirst() != null) {
            log.info("Successfully got all reservations associated with username");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }

    /**
     * Handles a get request for getting a reservation by id
     * Takes the id from the path
     * Takes the token from the header
     * @return 200 with reservation if successful, and 400 series error otherwise.
     */
    public void getReservationById(Context ctx) {
        log.debug("Reieved HTTP GET request at endpoint /reservations/{username}/{res_id}");

        // Getting user input
        String token = ctx.header("Token");
        Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
        
        // Attempting to get reservation
        Pair<Reservation, Integer> result = rs.getReservationById(res_id, token);
        
        // Checking if reservation was returned
        if (result.getFirst() != null) {
            log.info("Successfully got reservation");
            ctx.json(result.getFirst());
        }

        ctx.status(result.getSecond());

    }

    /**
     * Handles a get request for getting a reservation by id.
     * Takes the id from the path
     * Takes the token from the header
     * Returns reservation as a DTO
     * @return 200 with reservationDTO if successful and 400 series error otherwise.
     */
    public void getReservationDTOById(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /reservations/{username}/{res_id}/dto");

        // Getting user input
        String token = ctx.header("Token");
        Validator<Integer> vid = ctx.pathParamAsClass("res_id", Integer.class);
        Integer id = vid.getOrDefault(null);

        // Attempting to get request DTO
        Pair<ReservationDTO, Integer> result = rs.getReservationDTOById(id, token);


        // Checking if request was successful
        if (result.getFirst() != null) {
            log.info("Successfully got reservationDTO");
            ctx.json(result.getFirst());
        }

        ctx.status(result.getSecond());
    }
    
//    public void getReservationById(Context ctx) {
//    	 String token = ctx.header("Token");
////    	String token = "hi";
//         String userName = ctx.pathParam("username");
//         Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
////    	Pair<Reservation,Integer> resPair = rs.getReservationById(userName, res_id, token);
//    	Pair<Reservation,Integer> resPair = rs.getReservationById(res_id, token);
//    	
//    	Reservation res = resPair.getFirst();
//    	Integer responseCode = resPair.getSecond();
//    	
//    	if(responseCode == 200) {
//    		
//    		ctx.json(res);
//    	}
//    	
//    	ctx.status(responseCode);
//    	
//    	
//    	
//>>>>>>> jfriesner_reservations
//    }

    /*
     * === PATCH ===
     */

    /**
     * Handles an update request for updating a reservation by id
     * Takes the id from the path
     * Takes the token from the header
     * @return 200 with updated reservation if successful, and 400 series error otherwise.
     */
    public void updateReservationById(Context ctx) {
        log.debug("Recieved HTTP PATCH request at endpoint /reservations//{username}/{res_id}");

        // Getting user input
        String token = ctx.header("Token");
        Validator<Integer> vid = ctx.pathParamAsClass("res_id", Integer.class);
        Integer id = vid.getOrDefault(null);
        Reservation resData = ctx.bodyAsClass(Reservation.class);

        // Attempting to update reservaiton
        Pair<Reservation, Integer> result = rs.updateReservationById(id, resData, token);

        // Checking if reservation was returned
        if (result.getFirst() != null) {
            log.info("Successfully updated reservation");
            ctx.json(result.getFirst());
        }

        ctx.status(result.getSecond());
    }
}
