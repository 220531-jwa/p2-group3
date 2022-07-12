package dev.group3.controller;

import dev.group3.model.Reservation;
import dev.group3.service.ReservationService;
import io.javalin.http.Context;
import kotlin.Pair;

public class ReservationController {
    
    private ReservationService resService = new ReservationService();
    
    /*
     * === POST ===
     */

	//Creating a new Reservation
    public void createReservation(Context ctx) {
    	//Getting user input
    	String username = ctx.pathParam("username");
    	String token = ctx.header("Token");   	
		ctx.status(201);
		Reservation reservationFromUserBody = ctx.bodyAsClass(Reservation.class);
		//Attempting to get user
		Pair<Reservation, Integer> r = resService.createReservation(username, reservationFromUserBody, token); 
		ctx.json(r);
    }
    
    /*
     * === GET ===
     */
    
    // TODO / REVIEW: filter (for the day OR all of them ever)
    public void getAllReservations(Context ctx) {
        
    }
    
    public void getAllRservationsByUsername(Context ctx) {
        
    }
    
    public void getReservationById(Context ctx) {
        
    }
    
    /*
     * === PATCH ===
     */
    
    // owner - Checkin/out
    // dogowner - cancel
    public void updateReservationById(Context ctx) {
        
    }
}
