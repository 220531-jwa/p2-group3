package dev.group3.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import dev.group3.model.Reservation;
import dev.group3.model.User;
import dev.group3.model.enums.ResStatusType;
import dev.group3.model.enums.UserType;
import kotlin.Pair;

public class MockDataSet {

    private static List<User> userTestSet;
    private static List<Reservation> reservationTestSet;
    
    private static Hashtable<String, List<Reservation>> filterStorage;

    // === SETUP ===

    private static void setupUserTestSet() {
        userTestSet = new ArrayList<User>();
        userTestSet.add(new User("owner",       "secret",   UserType.OWNER,     "Wolf",     "Flow",     "5555555555",   100.00));
        userTestSet.add(new User("email1",      "pass1",    UserType.CUSTOMER,  "Alice",    "Apple",    "1234567890",   1000.00));
        userTestSet.add(new User("email2",      "pass2",    UserType.CUSTOMER,  "Bob",      "Bacon",    "1112223333",   100.00));
        userTestSet.add(new User("email3",      "pass3",    UserType.CUSTOMER,  "Carl",     "Cake",     "4445556666",   10.00));
        userTestSet.add(new User("email4",      "pass4",    UserType.CUSTOMER,  "David",    "Dumpling", "7778889999",   0.00));
        userTestSet.add(new User("email5",      "pass5",    UserType.CUSTOMER,  "Evelyn",   "Eggnog",   "2224448888",   1000.00));
        userTestSet.add(new User("dogLover",    "pass6",    UserType.CUSTOMER,  "Frank",    "Fudge",    "2358132134",   3.92));
    }
    
    private static void setupReservationTestSet() {
        reservationTestSet = new ArrayList<Reservation>();
        // Current day (7 dogs)
        reservationTestSet.add(new Reservation(1,   "email1",   1, ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(2,   "email1",   2,  ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(3,   "email2",   5,  ResStatusType.CHECKEDIN,    getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(4,   "email3",   7,  ResStatusType.CHECKEDIN,    getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(5,   "email3",   8,  ResStatusType.CANCELLED,    getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(6,   "dogLover", 10, ResStatusType.CHECKEDOUT,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(7,   "dogLover", 11, ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        // Past days
        reservationTestSet.add(new Reservation(8,   "dogLover", 12, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(9,   "dogLover", 13, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(10,  "dogLover", 14, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(11,  "dogLover", 15, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(12,  "dogLover", 16, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(13,  "dogLover", 17, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(14,  "dogLover", 18, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(15,  "dogLover", 19, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(16,  "dogLover", 20, ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
    }

    // === GETTERS ===

    public static List<User> getUserTestSet() {
        setupUserTestSet();
        return userTestSet;
    }
    
    
    public static List<Reservation> getReservationTestSet() {
        setupReservationTestSet();
        return reservationTestSet;
    }
    
    public static Pair<List<Reservation>, Integer> getFilteredReservationDataSet(String username) {
        // Checking if tests sets were made
        if (userTestSet == null || reservationTestSet == null) {
            return null;
        }
        
        // Checking if making new filters
        if (filterStorage == null) {
            filterStorage = new Hashtable<>();
        }
        
        // Checking if filtered data set already exists
        if (username == null) {
            username = "";
        }
        if (filterStorage.contains(username)) {
        	filterStorage.get
            return filterStorage.get(username);
        }
        
        // Getting filters - Not applicable atm
        List<Reservation> filteredReservations = new ArrayList<Reservation>();
        
        // Going through reservations to find ones associated with username
        for (Reservation res: reservationTestSet) {
            if (username == null || res.getUserEmail().equals(username)) {
                // Found request
                filteredReservations.add(res);
            }
        }
        
        filterStorage.put(username, filteredReservations.isEmpty() ? null : filteredReservations);
        return filterStorage.get(username);
    }
    
    // === GET DEFAULTS ===
    
    public static User getDefaultNewUserData() {
        return new User("newUser@email.com", "pass", UserType.CUSTOMER, "Zed", "Zod", "1248163264", 100.00);
    }
    
    public static Reservation getDefaultNewReservationData() {
        return new Reservation(17,   "email1",   1, ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00"));
    }
    
    // === UTILITY ===
    
    private static Timestamp getTS(String timestamp) {
        return Timestamp.valueOf(timestamp);
    }
    
    public static void resetFilteredStorage() {
        filterStorage = null;
    }
}
