package dev.group3.controller;

import dev.group3.model.Reservation;
import dev.group3.service.ReservationService;
import io.javalin.http.Context;

public class ReservationController {
    
    private ReservationService resService = new ReservationService();
    
    /*
     * === POST ===
     */
    
    public ReservationController(ReservationService reservationService) {
		
	}

	//Creating a new Reservation
    public void createReservation(Context ctx) {
		ctx.status(201);
		Reservation reservationFromUserBody = ctx.bodyAsClass(Reservation.class);
		Reservation r = resService.createReservation(reservationFromUserBody); 
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
