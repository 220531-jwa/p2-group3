package dev.group3.service;

import java.util.List;

import dev.group3.model.Reservation;
import dev.group3.repo.ReservationDAO;
import dev.group3.repo.UserDAO;
import kotlin.Pair;

public class ReservationService {
    
    private UserDAO userDAO;
    private ReservationDAO resDAO;
    
    // Use default reservation dao;
    public ReservationService() {
        userDAO = new UserDAO();
        resDAO = new ReservationDAO();
    }
    
    /**
     * Used for a cusome reservationDAO, more generally used for Mockito
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
        return null;
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
        return null;
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
    public Pair<Reservation, Integer> getReservationById(String username, Integer rid, String token) {
        return null;
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
    public Pair<Reservation, Integer> updateReservationById(String username, Integer rid, Reservation resData, String token) {
        return null;
    }

	
}
