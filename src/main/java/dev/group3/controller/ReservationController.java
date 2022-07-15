package dev.group3.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Reservation;
import dev.group3.service.ReservationService;
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
        log.debug("Recieved HTTP POST request at endpoint /reservations/{username}");
        
        // Getting user input
        String username = ctx.pathParam("username");
        String token = ctx.header("Token");
        ctx.status(201);
        Reservation reservationFromUserBody = ctx.bodyAsClass(Reservation.class);
        // Attempting to get user
        Pair<Reservation, Integer> r = rs.createReservation(username, reservationFromUserBody, token);
        ctx.json(r);
    }

    /*
     * === GET ===
     */

    // TODO / REVIEW: filter (for the day OR all of them ever)
    public void getAllReservations(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /reservations");

        String token = ctx.header("Token");
        Pair<List<Reservation>, Integer> respPair = rs.getAllReservations(token);

        ArrayList<Reservation> resArray = (ArrayList<Reservation>) respPair.getFirst();
        int stat = (Integer) respPair.getSecond();
        ctx.json(resArray);
        ctx.status(stat);
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

    public void getReservationById(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /reservations/{username}/{res_id}");
        
        // Getting user input
        String token = ctx.header("Token");
        Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
        
        // Attempting to get reservation
        Pair<Reservation, Integer> resPair = rs.getReservationById(res_id, token);
        Reservation res = resPair.getFirst();
        Integer responseCode = resPair.getSecond();
        
        if (res != null) {
            ctx.json(res);
        }
        
        ctx.status(responseCode);
    }

    /*
     * === PATCH ===
     */

    // owner - Checkin/out
    // dogowner - cancel
    public void updateReservationById(Context ctx) {
        log.debug("Recieved HTTP PATCH request at endpoint /reservations//{username}/{res_id}");
        
        // Getting user input
        String token = ctx.header("Token");
        Integer res_id = Integer.parseInt(ctx.pathParam("res_id"));
        Reservation resData = ctx.bodyAsClass(Reservation.class);
        
        System.out.println("Got token: " + token);
        System.out.println("Got res_id: " + res_id);
        System.out.println("Got resData: " + resData);
        
        ctx.status(200);
    }
}
