package dev.group3.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Reservation;
import dev.group3.model.User;
import dev.group3.model.DTO.ReservationDTO;
import dev.group3.model.enums.ResStatusType;
import dev.group3.model.enums.UserType;
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
        log.debug("Attempting to create new reservation with username: " + username + " resData: " + resData + " token: " + token);
        
        // Attempting to create a new reservation
        resData.setStatus(ResStatusType.REGISTERED);
    	Reservation createdReservation = resDAO.createReservation(resData);
    	
    	// Successfully created new reservation
		return new Pair<Reservation, Integer>(createdReservation, 200);
    }
    
    /*
     * === GET / READ ===
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
        log.debug("Attempting to get all reservations with token: " + token);
    	
        // Validating input
    	if(token == null || token.isBlank()) {
    	    log.error("incoming token was null or empty string");
    		return new Pair<List<Reservation>, Integer>(null, 400);
    	}
    	
    	// Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
    		return new Pair<List<Reservation>, Integer>(null, 401);
        }
        
        // Getting user information
        String requesterusername = ActiveUserSessions.getActiveUserUsername(token);
        User requesterUser = userDAO.getUserByUsername(requesterusername);
        
        // Checking if user exists
        if (requesterUser == null) {
            log.fatal("user does not exist"); // this should never happen
            return new Pair<List<Reservation>, Integer>(null, 503);
        }
        
        // Checking to make sure the user is authorized to view all reservations
        UserType userType = requesterUser.getUserType();
        if(userType == UserType.OWNER) {
        	
            // Attempting to get all reservations
        	List<Reservation> reservations = resDAO.getAllReservations();
        	
        	// Checking if reservations were found
        	if(reservations != null) {
        		// Successfully got all reservations
        		return new Pair<List<Reservation>, Integer>(reservations, 200);
        	}
        	else {
        		// Reservation was not found
        		return new Pair<List<Reservation>, Integer>(null, 404);
        	}
        }
        else {
            log.error("User is not authorized to view all reservations");
        	return new Pair<List<Reservation>, Integer>(null, 403);
        }
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
    public Pair<List<Reservation>, Integer> getAllReservationsByUsername(String username, String token) {
    	
        log.debug("Attemtping to get all reservations associated with username: " + username + " token: " + token);
        
        
        // Validating input
        if (username == null || token == null || username.isBlank() || token.isEmpty()) {
            log.error("incoming username or token was null or empty string");
            return new Pair<List<Reservation>, Integer>(null, 400);
        }

        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return new Pair<List<Reservation>, Integer>(null, 401);
        }

        // Getting user information
        String requesterUsername = ActiveUserSessions.getActiveUserUsername(token);
        User requesterUser = userDAO.getUserByUsername(requesterUsername);

        // Checking to make sure user exists
        if (requesterUser == null) {
            log.fatal("user does not exist");   // This should never happen
            return new Pair<List<Reservation>, Integer>(null, 503);
        }
        
        // Checking to make sure the user is authorized to view all reservations
        UserType userType = requesterUser.getUserType();
        List<Reservation> respPair = resDAO.getAllRservationsByUsername(username);
        
//        System.out.println(username);
//        System.out.println(respPair.get(0).getUserEmail());
        if (userType == UserType.OWNER || username.equals(respPair.get(0).getUserEmail()) ) {

            // Attempting to get all reservations associated with username
            
            // Checking if reservations were found
            if (respPair != null) {
                // Successfully got reservations
                return new Pair<List<Reservation>, Integer>(respPair, 200);
            }
            else {
            	 log.error("Failed to find reservations. Possible: username does not exist");
                 return new Pair<List<Reservation>, Integer>(null, 404);
            }
        }
        else {
        	log.error("user is not authorized to view reservaitons");
            return new Pair<List<Reservation>, Integer>(null, 403);
            
           
        }
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
    public Pair<Reservation, Integer> getReservationById(Integer rid, String token) {
        log.debug("Attempting to get reservation by id with rid: " + rid + " token: " + token);
        
        
        
        // Validating input
        if (rid == null || token == null || rid < 0 || token.isBlank()) {
            log.error("Invalid rid and/or token input(s)");
            return new Pair<Reservation, Integer>(null, 400);
        }
        
    	
    	// Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
    		return new Pair<Reservation, Integer>(null, 401);
        }
        
        // Getting user information
        String requesterUsername = ActiveUserSessions.getActiveUserUsername(token);
        User requesterUser = userDAO.getUserByUsername(requesterUsername);
        
        // Checking if user exists
        if (requesterUser == null) {
            log.fatal("User does not exist");
            return new Pair<Reservation, Integer>(null, 503);   // This should never happen (loggedin users can only login if the user exists)
        }
        
       
    	
        
        
        Reservation res = resDAO.getReservationById(rid);
       
        if(res != null) {
        	
        	// Checking to make sure the user is authorized to update reservation
            UserType userType = requesterUser.getUserType();
            String requesterUserName =requesterUser.getEmail();
            String userName = res.getUserEmail();
            
           
        	
        	if(userType == UserType.OWNER || requesterUserName.equals(userName)) {
        		 System.out.println("Here is username "+userName);
                 System.out.println("Here is userType "+userType);
                 
                 System.out.println(res);
        		// Successfully updated reservation
        		return new Pair<Reservation, Integer>(res, 200);
        	}else {
        		log.error("User is not authorized to view reservation");
        		return new Pair<Reservation, Integer>(null, 403);
        	}
        	
        }else {
        	
        	log.error("This Reservation does not exist");
        	return new Pair<Reservation, Integer>(null, 404);
        }
        	
       
       
      
        
        
//        if(userType == UserType.OWNER) {
//        	
//        	
//        	 // Attempting to get reservations by id
//        	
//        	// Checking if reservation was updated successfully
//        	if(res != null) {
//        	    // Successfully updated reservation
//        		return new Pair<Reservation, Integer>(res, 200);
//        	}
//        	else {
//        	    log.error("Failed to update reservation. Possible: Reservation does not exist");
//        		return new Pair<Reservation, Integer>(null, 404);
//        	}
//        }
        	 // Attempting to get reservations by id
//        	Reservation res = resDAO.getReservationById(rid);
        	
//        else {
//            log.error("User is not authorized to update reservation");
//        	return new Pair<Reservation, Integer>(null, 403);
//        }
    
        
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
    public Pair<ReservationDTO, Integer> getReservationDTOById(Integer rid, String token) {
        log.debug("Attempting to get reservationDTO with rid: " + rid + " token: " + token);
        
        // Validating input
        if (rid == null || token == null || rid < 0 || token.isBlank()) {
            log.error("Invalid username and/or rid and/or token input(s)");
            return new Pair<ReservationDTO, Integer>(null, 400);
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return new Pair<ReservationDTO, Integer>(null, 401);
        }
        
        // Getting user information
        String requesterUsername = ActiveUserSessions.getActiveUserUsername(token);
        User user = userDAO.getUserByUsername(requesterUsername);
        
        // Checking if user exists - Should never happen (logged in users exist)
        if (user == null) {
            log.fatal("User does not exist");
            return new Pair<ReservationDTO, Integer>(null, 503);
        }
        
        // Attempting to get reservation
        ReservationDTO reservationDTO = resDAO.getReservationDTOById(rid);
        
        // Checking if reservation exists
        if (reservationDTO == null) {
            log.error("Reservation does not exist");
            return new Pair<ReservationDTO, Integer>(null, 404);
        }
        
        // Checking if user is authorized to retrieve reservation information
        if (!requesterUsername.contentEquals(reservationDTO.getReservation().getUserEmail()) && user.getUserType() != UserType.OWNER) {
            log.error("User is not authorized to know about reservation");
            return new Pair<ReservationDTO, Integer>(null, 403);
        }
        
        // Successfully got reservation
        return new Pair<ReservationDTO, Integer>(reservationDTO, 200);
    }
    
    /*
     * === PATCH / UPDATE ===
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
    public Pair<Reservation, Integer> updateReservationById(Integer res_id, Reservation resData, String token) {
    	
    	
        
        log.debug("Attempting to update reservation with res_id: " + res_id + " resData: " + resData + " token: " + token);
    	
        // Validating input
    	if(token == null || token.isBlank() || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return new Pair<Reservation, Integer>(null, 400);
    	}
    	
    	// Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
    		return new Pair<Reservation, Integer>(null, 401);
        }
        
        // Getting user information
        String requesterUsername = ActiveUserSessions.getActiveUserUsername(token);
        User requesterUser = userDAO.getUserByUsername(requesterUsername);
        
        
        // Checking if user exists
        if (requesterUser == null) {
            log.fatal("User does not exist");
            return new Pair<Reservation, Integer>(null, 503);   // This should never happen (loggedin users can only login if the user exists)
        }
        
        String userEmail = requesterUser.getEmail();
        
        // Populating necessary data
        resData.setId(res_id);
       
        
        // Checking to make sure the user is authorized to update reservation
        UserType userType = requesterUser.getUserType();
        Reservation resp = resDAO.updateReservationById(resData);
        
        
        if(resp != null) {
//        System.out.println(resp);
        	
        	 if(userType == UserType.OWNER || requesterUser.getEmail() == resp.getUserEmail()) {
             	
         		// Successfully updated reservation
         		return new Pair<Reservation, Integer>(resp, 200);
         		
        	 }else {
        		 log.error("User is not authorized to update reservation");
             	return new Pair<Reservation, Integer>(null, 403);
        	 }
        }else {
        	log.error("This Reservation does not exist");
    		return new Pair<Reservation, Integer>(null, 404);
        }
        
       
        
    }
}