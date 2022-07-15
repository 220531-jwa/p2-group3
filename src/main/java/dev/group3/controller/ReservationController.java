package dev.group3.controller;

import java.util.ArrayList;
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

    // Creating a new Reservation
    public void createReservation(Context ctx) {

    	//Getting user input
    	String username = ctx.pathParam("username");
    	String token = ctx.header("Token");   	
		Reservation reservationFromUserBody = ctx.bodyAsClass(Reservation.class);
		//Attempting to get user
		Pair<Reservation, Integer> r = rs.createReservation(username, reservationFromUserBody, token); 
		ctx.json(r);
		ctx.status(201);

        log.debug("Recieved HTTP POST request at endpoint /reservations/{username}");
        
    }

    /*
     * === GET ===
     */

    // TODO / REVIEW: filter (for the day OR all of them ever)
    public void getAllReservations(Context ctx) {

//        log.debug("Recieved HTTP GET request at endpoint /reservations");
//
//        String token = ctx.header("Token");
//        Pair<List<Reservation>, Integer> respPair = rs.getAllReservations(token);
//
//        ArrayList<Reservation> resArray = (ArrayList<Reservation>) respPair.getFirst();
//        int stat = (Integer) respPair.getSecond();
//        ctx.json(resArray);
//        ctx.status(stat);

        
    	String token = ctx.header("Token");
    	System.out.println(token);
    	Pair<List<Reservation>, Integer> respPair = rs.getAllReservations(token);
    	
    	
    	
    	ArrayList<Reservation> resArray = (ArrayList<Reservation>) respPair.getFirst();
    	int stat = (Integer) respPair.getSecond();
    	
    	if(stat==200) {
    		
    		ctx.json(resArray);
    		ctx.status(stat);
    	}else {
    		ctx.status(stat);
    	}
    	
    	

    }

    public void getAllRservationsByUsername(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /reservations/{username}");
        
        String token = ctx.header("Token");
        String userName = ctx.pathParam("username");

        Pair<List<Reservation>, Integer> resPair = rs.getAllReservationsByUsername(userName, token);

        ArrayList<Reservation> reservList = (ArrayList<Reservation>) resPair.getFirst();
        int resStatus = resPair.getSecond();

        if (reservList != null) {
            ctx.json(reservList);
            ctx.status(resStatus);

        } else {
            ctx.status(resStatus);
        }

    }

//    public void getReservationById(Context ctx) {
//        log.debug("Recieved HTTP GET request at endpoint /reservations/{username}/{res_id}");
//        
//        // Getting user input
//        String token = ctx.header("Token");
//        Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
//        
//        // Attempting to get reservation
//        Pair<Reservation, Integer> resPair = rs.getReservationById(res_id, token);
//        Reservation res = resPair.getFirst();
//        Integer responseCode = resPair.getSecond();
//        
//        if (res != null) {
//            ctx.json(res);
//        }
//        
//        ctx.status(responseCode);
//    }



    
    public void getReservationById(Context ctx) {
    	 String token = ctx.header("Token");
//    	String token = "hi";
         String userName = ctx.pathParam("username");
         Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
//    	Pair<Reservation,Integer> resPair = rs.getReservationById(userName, res_id, token);
    	Pair<Reservation,Integer> resPair = rs.getReservationById(res_id, token);
    	
    	Reservation res = resPair.getFirst();
    	Integer responseCode = resPair.getSecond();
    	
    	ctx.json(res);
    	ctx.status(responseCode);
    	
    	
    	
    }
    
    /**
     * Handles a get request for getting a reservation by id.
     * Takes the id from the path
     * Takes the token from the header
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
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }
    

    /*
     * === PATCH ===
     */

    // owner - Checkin/out
    // dogowner - cancel
    public void updateReservationById(Context ctx) {

//        log.debug("Recieved HTTP PATCH request at endpoint /reservations//{username}/{res_id}");
    	
    	String token = ctx.header("Token");
    	Integer reserv_id = Integer.parseInt(ctx.pathParam("res_id"));
    	Reservation res = ctx.bodyAsClass(Reservation.class);
    	
    	Pair<Reservation,Integer>respPair = rs.updateReservationById(reserv_id,res, token);
    	Integer stat = respPair.getSecond();
    	
    	if(stat==200) {
    		Reservation reserv= respPair.getFirst();
    		ctx.json(reserv);
    		ctx.status(stat);
    	}else {
    		ctx.status();
    	}
    	
    	

        
        // Getting user input
//        String token = ctx.header("Token");
//        Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
//        Reservation resData = ctx.bodyAsClass(Reservation.class);
//        
//        System.out.println("Got token: " + token);
//        System.out.println("Got res_id: " + res_id);
//        System.out.println("Got resData: " + resData);
//        
//        ctx.status(200);
    }
}
