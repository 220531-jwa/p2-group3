package dev.group3.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dev.group3.model.Reservation;
import dev.group3.service.ReservationService;
import io.javalin.http.Context;
import kotlin.Pair;

public class ReservationController {
    
    private ReservationService rs = new ReservationService();
    
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
		Pair<Reservation, Integer> r = rs.createReservation(username, reservationFromUserBody, token); 
		ctx.json(r);
    }
    
    /*
     * === GET ===
     */
    
    // TODO / REVIEW: filter (for the day OR all of them ever)
    public void getAllReservations(Context ctx) {
        
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
//        String token = ctx.header("Toke");
    	String token = "hi";
        String userName = ctx.pathParam("username");
//        String userName = ctx.queryParam("username");
        
        
        Pair<List<Reservation>,Integer> resPair = rs.getAllReservationsByUsername(userName, token);
        
        ArrayList<Reservation> reservList = (ArrayList<Reservation>) resPair.getFirst();
        int resStatus = resPair.getSecond();
        
        if(reservList != null) {
        	ctx.json(reservList);
        	ctx.status(resStatus);
        	
        }else {
        	ctx.status(resStatus);
        }
        
    }
    
    public void getReservationById(Context ctx) {
    	 String token = ctx.header("Token");
//    	String token = "hi";
         String userName = ctx.pathParam("username");
         Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
    	Pair<Reservation,Integer> resPair = rs.getReservationById(userName, res_id, token);
    	
    	Reservation res = resPair.getFirst();
    	Integer responseCode = resPair.getSecond();
    	
    	ctx.json(res);
    	ctx.status(responseCode);
    	
    	
    	
    }
    
    /*
     * === PATCH ===
     */
    
    // owner - Checkin/out
    // dogowner - cancel
    public void updateReservationById(Context ctx) {
    	
    	String token = ctx.header("Token");
    	Integer reserv_id = Integer.parseInt(ctx.pathParam("res_id"));
    	Reservation res = ctx.bodyAsClass(Reservation.class);
    	
    	Pair<Reservation,Integer>respPair = rs.updateReservationById(res, token);
    	Integer stat = respPair.getSecond();
    	
    	if(stat==200) {
    		Reservation reserv= respPair.getFirst();
    		ctx.json(reserv);
    		ctx.status(stat);
    	}else {
    		ctx.status();
    	}
    	
    	
        
    }
}
