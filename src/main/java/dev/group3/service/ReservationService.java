package dev.group3.service;

import java.util.List;

//import org.eclipse.jetty.util.log.Log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Reservation;
import dev.group3.model.User;
import dev.group3.repo.ReservationDAO;
import dev.group3.repo.UserDAO;
import dev.group3.util.ActiveUserSessions;
import kotlin.Pair;

public class ReservationService {
    
    private UserDAO userDAO;
    private ReservationDAO resDAO;
    
    private static Logger log = LogManager.getLogger(UserService.class);
    
    // Use default reservation dao;
    public ReservationService() {
        userDAO = new UserDAO();
        resDAO = new ReservationDAO();
    }
    
    /**
     * Used for a customer reservationDAO, more generally used for Mockito
     * @param resDAO The resDAO for this service to use
     */
    public ReservationService(UserDAO userDAO, ReservationDAO resDAO) {
        this.userDAO = userDAO;
        this.resDAO = resDAO;
    }
    
    /*
     * === POST / CREATE ===
     */
    
    /**
     * Attempts to create a new reservation for a user.
     * Requires a token associated with an active user session to access this service.
     * Required Fields:
     * - dogId - associated with userEmail
     * - startDateTime
     * - endDateTime
     * Authorization:
     * - Only customer can create a reservation for their dog
     * @param resData The data of the reservation
     * @return 200 with reservation information if successful, and 400 null series error otherwise
     */
    public Pair<Reservation, Integer> createReservation(String username, Reservation resData, String token) {
    	Reservation createdReservation = resDAO.createReservation(resData);
		return new Pair<Reservation, Integer>(createdReservation, 200);
       
    }
    
    /*
     * === GET / READ
     */
    
    /**
     * Retrieves all the reservations.
     * Requires a token associated with an active user session to access this service.
     * Authorization:
     * - Only the owner can retrieve all reservations.
     * @param token The associated active user session of the requester
     * @return 200 with reservations if successful, and 400 null series error otherwise
     */
    public Pair<List<Reservation>, Integer> getAllReservations(String token) {
    	
    	
    	
    	if(token == null || token.isBlank() || token.isEmpty()  ) {
    		Pair<List<Reservation>, Integer> newPair400 = new Pair<List<Reservation>, Integer>(null,400);
    		log.error("incoming token was null or empty string");
    		return newPair400;
    		
    	}
    	
    	// Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
    		return new Pair<List<Reservation>, Integer>(null,401);
        }
        
        String userName = ActiveUserSessions.getActiveUserUsername(token);
        User user = userDAO.getUserByUsername(userName);
        
        
        // CHECKING TO MAKE SURE THE USERTYPE IS AUTHORIZED TO VIEW ALL RESERVATIONS
        String userType = user.getUserType().toString();
        
        if(userType == "OWNER") {
        	
        	List<Reservation> respPair = resDAO.getAllReservations();
        	if(respPair != null) {
        		
        		Pair<List<Reservation>, Integer> newPair = new Pair<List<Reservation>, Integer>(respPair,200);
        		return newPair;
        		
        	}else {
        		
        		Pair<List<Reservation>, Integer> newPair503 = new Pair<List<Reservation>, Integer>(null,503);
        		return newPair503;
        	}
        	
        }else {
        	Pair<List<Reservation>, Integer> newPair403 = new Pair<List<Reservation>, Integer>(null,403);
        	return newPair403;
        }
        	
        
//        return newPair;
}
    	
    
    /**
     * Retrieves all the reservations associated with the given username.
     * Requires a token associated with an active user session to access this service.
     * Authorization:
     * - The customer can retrieve their own reservations
     * - The owner can retrieve any user reservations
     * @param username The user to find the reservations of
     * @param token The associated active user session of the requester
     * @return 200 with reservations if successful, and 400 null series error otherwise
     */
    public Pair<List<Reservation>, Integer> getAllReservationsByUsername(String username,  String token) {
       
    	
//    	Pair<List<Reservation>, Integer> newPair = new Pair<List<Reservation>, Integer>(resPair,200);
    	System.out.println(token);
    	
    	if(token.isBlank() || token.isEmpty() ||token == null  ) {
//    		Pair<List<Reservation>, Integer> newPair400 = 
    		log.error("incoming token was null or empty string");
    		return new Pair<List<Reservation>, Integer>(null,400);
    		
    	}
    	
    	// Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
    		return new Pair<List<Reservation>, Integer>(null,401);
        }
        
        String userName = ActiveUserSessions.getActiveUserUsername(token);
        User user = userDAO.getUserByUsername(userName);
        
        
        // CHECKING TO MAKE SURE THE USERTYPE IS AUTHORIZED TO VIEW ALL RESERVATIONS
        String userType = user.getUserType().toString();
        
        if(userType == "OWNER") {
        	
        	List<Reservation> respPair = resDAO.getAllRservationsByUsername(username);
        	if(respPair != null) {
        		
        		Pair<List<Reservation>, Integer> newPair = new Pair<List<Reservation>, Integer>(respPair,200);
        		return newPair;
        		
        	}else {
        		
        		Pair<List<Reservation>, Integer> newPair503 = new Pair<List<Reservation>, Integer>(null,503);
        		return newPair503;
        	}
        	
        }else {
        	Pair<List<Reservation>, Integer> newPair403 = new Pair<List<Reservation>, Integer>(null,403);
        	return newPair403;
        }
    	
    	
    	
//    	return newPair;
    }
    
    /**
     * Retrieves a specific reservation associated with the given username.
     * Requires a token associated with an active user session to access this service.
     * Authorization:
     * - Customer can get their own reservation
     * - Owner can get any reservation
     * @param username The user to find the reservation of
     * @param rid The specific reservation id
     * @param token The associated active user session of the requester
     * @return 200 with reservation if successful, and 400 null series otherwise
     */
//    public Pair<Reservation, Integer> getReservationById(String username,Integer rid, String token) {
    public Pair<Reservation, Integer> getReservationById(Integer rid, String token) {
    	
    	System.out.println(1);
    	System.out.println(resDAO == null);
    	Reservation res = resDAO.getReservationById(rid);
    	System.out.println(2);
    	
    	if(res != null) {
    		Pair<Reservation, Integer> resPair = new Pair<Reservation, Integer>(res,200);
    		return resPair;
    	}else {
    		Pair<Reservation, Integer> resPair = new Pair<Reservation, Integer>(null,400);
    		return resPair;
    	}
    	
    }
    
    /*
     * === PATHC / UPDATE ===
     */
    
    /**
     * Updates a specific reservation associated with the given username
     * Requires a token associated with an active user session to access this service.
     * Authorization:
     * - The customer can cancel their reservation
     * - The owner can checkin/out reservation
     * - Note: All other values are ignored
     * @param username The user to update the reservation of
     * @param rid The specific reservation id
     * @param resData The updated reservation data
     * @param token The associated active user session of the requester
     * @return 200 with updated reservation if successful, and 400 null series otherwise
     */

//    public Pair<Reservation, Integer> updateReservationById(Integer rid, Reservation resData, String token) {
//        return new Pair<Reservation, Integer>(null, 500);

    public Pair<Reservation, Integer> updateReservationById(Integer res_id,Reservation resData, String token) {
    	
    	if(token == null || token.isBlank() || token.isEmpty()  ) {
    		Pair<Reservation, Integer> newPair400 = new Pair<Reservation, Integer>(null,400);
    		log.error("incoming token was null or empty string");
    		return newPair400;
    		
    	}
    	
    	// Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
    		return new Pair<Reservation, Integer>(null,401);
        }
        
        String userName = ActiveUserSessions.getActiveUserUsername(token);
        User user = userDAO.getUserByUsername(userName);
        
        
        // CHECKING TO MAKE SURE THE USERTYPE IS AUTHORIZED TO VIEW ALL RESERVATIONS
        String userType = user.getUserType().toString();
        
        if(userType == "OWNER") {
        	
        	Reservation resp = resDAO.updateReservationById(resData);
        	
        	if(resp != null) {
        		
//        		Pair<Reservation, Integer> newPair = new Pair<Reservation, Integer>(respPair,200);
        		return new Pair<Reservation, Integer>(resp,200);
        		
        	}else {
        		
//        		Pair<List<Reservation>, Integer> newPair503 = new Pair<Reservation, Integer>(null,503);
        		return new Pair<Reservation, Integer>(null,503);
        	}
        	
        }else {
//        	Pair<List<Reservation>, Integer> newPair403 = new Pair<Reservation, Integer>(null,403);
        	return new Pair<Reservation, Integer>(null,403);
        }

    }

	
}
