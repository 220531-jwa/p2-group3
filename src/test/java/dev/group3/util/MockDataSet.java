package dev.group3.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.group3.model.Dog;
import dev.group3.model.Reservation;
import dev.group3.model.Service;
import dev.group3.model.User;
import dev.group3.model.enums.ResStatusType;
import dev.group3.model.enums.ServiceType;
import dev.group3.model.enums.UserType;

public class MockDataSet {

    private static List<User> userTestSet;
    private static List<Reservation> reservationTestSet;
    private static List<Service> serviceTestSet;
    private static List<Dog> dogTestSet;
    
    private static HashMap<String, List<Reservation>> filterStorage;

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
        reservationTestSet.add(new Reservation(1,   "email1",   1,  null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(2,   "email1",   2,  2,      ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(3,   "email2",   5,  3,      ResStatusType.CHECKEDIN,    getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(4,   "email3",   7,  null,   ResStatusType.CHECKEDIN,    getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(5,   "email3",   8,  null,   ResStatusType.CANCELLED,    getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(6,   "dogLover", 10, 4,      ResStatusType.CHECKEDOUT,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(7,   "dogLover", 11, 5,      ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        
//        reservationTestSet.add(new Reservation(1,   "email4",   1,  null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
//        reservationTestSet.add(new Reservation(1,   "email5",   1,  null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(8,   "email4",   1,  null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        reservationTestSet.add(new Reservation(9,   "email5",   1,  null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        
        
        reservationTestSet.add(new Reservation(10,   "owner",   1,  null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00")));
        // Past days
        reservationTestSet.add(new Reservation(11,   "dogLover", 12, 2,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(12,   "dogLover", 13, 3,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(13,  "dogLover", 14, 4,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(14,  "dogLover", 15, 5,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(15,  "dogLover", 16, null,   ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(16,  "dogLover", 17, 2,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(17,  "dogLover", 18, 2,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(18,  "dogLover", 19, 3,      ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
        reservationTestSet.add(new Reservation(19,  "dogLover", 20, null,   ResStatusType.CHECKEDOUT,   getTS("2022-06-01 9:00:00"),   getTS("2022-06-01 11:00:00")));
    }
    
    private static void setupServicesTestSet() {
        serviceTestSet = new ArrayList<Service>();
        serviceTestSet.add(new Service(1,   ServiceType.RATE,         1,  10.00));
        serviceTestSet.add(new Service(2,   ServiceType.GROOMING,     2,  25.00));
        serviceTestSet.add(new Service(3,   ServiceType.BELLYRUB,     1,  0.99));
        serviceTestSet.add(new Service(4,   ServiceType.DOGWALK,      2,  15.00));
        serviceTestSet.add(new Service(5,   ServiceType.TRIMNAILS,    1,  19.99));
    }
    
    private static void setupDogTestSet() {
    	dogTestSet = new ArrayList<Dog>();
    	dogTestSet.add(new Dog(1, "email1", true, "Nikita", "Black Russian Terrier", 2, true));
    	dogTestSet.add(new Dog(2, "email1", true, "Tyrion", "German Shepard", 2, false));
    	dogTestSet.add(new Dog(3, "email1", true, "Blade", "Affenpinscher", 2, true));
    	dogTestSet.add(new Dog(4, "email1", false, "Yeti", "Beagle", 2, false));
    	dogTestSet.add(new Dog(5, "email2", true, "Ribena", "Boxer", 2, true));
    	dogTestSet.add(new Dog(21, "email2", true, "Cookie", "Mix", 2, true));
    }
    
    // === GETTERS ===

    public static List<User> getUserTestSet() {
        setupUserTestSet();
        return userTestSet;
    }
    
    
    public static List<Reservation> getReservationTestSet() {
        setupReservationTestSet();
        resetFilteredStorage();     // This depends on reservation test set
        return reservationTestSet;
    }
    
    public static List<Service> getServiceTestSet() {
        setupServicesTestSet();
        return serviceTestSet;
    }
    
    public static List<Dog> getDogTestSet(){
    	setupDogTestSet();
    	return dogTestSet;
    }
    
    public static List<Reservation> getFilteredReservationDataSet(String username) {
        // Checking if test sets were made
        if (reservationTestSet == null) {
            return null;
        }
        
        // Checking if making new filters
        if (filterStorage == null) {
            filterStorage = new HashMap<>();
        }
        
        // Checking if filtered data set already exists
        if (username == null) {
            username = "";
        }
        if (filterStorage.containsKey(username)) {
            return filterStorage.get(username);
        }
        
        // Creating new list for filtered reservations
        List<Reservation> filteredReservations = new ArrayList<Reservation>();
        
        // Going through reservations to find ones associated with username
        for (Reservation res: reservationTestSet) {
            if (username == null || res.getUserEmail().equals(username)) {
                // Found request
                filteredReservations.add(res);
            }
        }
        
        // Storing filtered list of reservations to return on subsequent calls for the same list
        filterStorage.put(username, filteredReservations.isEmpty() ? null : filteredReservations);
        return filterStorage.get(username);
    }
    
    // === GET DEFAULTS ===
    
    public static User getDefaultNewUserData() {
        return new User("newUser@email.com", "NewPass123!", null, "Zed", "Zod", "1248163264", 100.00);
    }
    
    public static Reservation getDefaultNewReservationData() {
        return new Reservation(17,   "email1",   1, null,   ResStatusType.REGISTERED,   getTS("2022-07-01 9:00:00"),   getTS("2022-07-01 11:00:00"));
    }
    
    // === UTILITY ===
    
    private static Timestamp getTS(String timestamp) {
        return Timestamp.valueOf(timestamp);
    }
    
    private static void resetFilteredStorage() {
        filterStorage = null;
    }
}
