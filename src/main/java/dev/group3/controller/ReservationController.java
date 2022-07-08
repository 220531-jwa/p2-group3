package dev.group3.controller;

import dev.group3.service.ReservationService;
import io.javalin.http.Context;

public class ReservationController {
    
    private ReservationService resService = new ReservationService();
    
    /*
     * === POST ===
     */
    
    public void createNewReservation(Context ctx) {
        
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
