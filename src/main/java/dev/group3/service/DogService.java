package dev.group3.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Dog;
import dev.group3.repo.DogDAO;
import dev.group3.util.ActiveUserSessions;

public class DogService {

    private DogDAO dd;
    private static Logger log = LogManager.getLogger(UserService.class);

    // Use default dogDAO
    public DogService() {
        dd = new DogDAO();
    }

    // Use specified dogDAO, mostly used by mockito
    public DogService(DogDAO dd) {
        this.dd = dd;
    }

    /*
     * === POST / CREATE ===
     */

    /**
     * Creates a new dog to add to the database
     * Requires a token associated with an active user session to access this service.
     * Required Fields:
     * - dog_name
     * - breed
     * - dog_age
     *  = Must be a positive integer
     * - vaccinated
     * Authorization:
     * - Owner/Customer can create a dog for their OWN account
     * @param dogData The data of the dog to create
     * @param token The associated active user session of the requester
     * @return The dog if created successfully, and null otherwise
     */
    public Dog postCreateNewDog(Dog dogData, String token) {
        log.debug("Attempting to create a dog with dg: " + dogData + " token: " + token);

        // Validating inputs
        if (dogData == null || token == null || token.isBlank()) {
            log.error("incoming dogData or token was null or empty string");
            return null;
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return null;
        }

        // Attempting to add the dog to the database
        Dog newdg = dd.createDog(dogData);

        // Returning the found dog (or null if not found)
        return newdg;
    }

    /*
     * === GET / READ ===
     */

    /**
     * Retrieves all the dogs
     * Requires a token associated with an active user session to access this service.
     * Authorization:
     * - Only the owner can retrieve all dogs
     * @param token The associated active user session of the requester
     * @return A list of dogs if successful, and null otherwise
     */
    public List<Dog> getAllDogs(String token) {
        log.debug("Attempting to get all dogs with token: " + token);

        // Validating input
        if (token == null || token.isBlank()) {
            log.error("incoming token was null or empty string");
            return null;
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return null;
        }

        // Attempting to get all the dogs
        List<Dog> dg = dd.getAllDogs();

        // Returning the dogs list, or null if not found
        return dg;
    }

    /**
     * Gets all the dogs of a specific username
     * Requires a token associated with an active user session to access this service
     * Authorization:
     * - Owner can get a users dogs
     * - Customer can only get their OWN dogs
     * @param userName The username to find the dogs of
     * @param token The associated active user session of the requester
     * @return A list of dogs is successful, and null otherwise
     */
    public List<Dog> getAllDogsByUserId(String userName, String token) {
        log.debug("Attempting to get all dogs associated with the given userName: " + userName + " token: " + token);

        // Validating input
        if (userName == null || token == null || userName.isBlank() || token.isBlank()) {
            log.error("incoming username or token was null or empty string");
            return null;
        }
        
        // Checking if the user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return null;
        }

        // Attempting to get all the dogs associated with the given username
        List<Dog> dg = dd.getAllDogsByUserId(userName);

        // Returning the list of dogs or null if not found
        return dg;
    }

    /**
     * Gets a specific dog
     * Requires a token associated with an active user session to access this service
     * Authorization:
     * - Owner can get any dog
     * - Customer can only get their OWN dog
     * @param dogId The id associated with the dog
     * @param token The associated active user session of the requester
     * @return The dog if successful, and null otherwise
     */
    public Dog getDogById(Integer dogId, String token) {
        log.debug("Attempting to get a dog by id with dogId: " + dogId + " token: " + token);

        // Validating input
        if (dogId == null || token == null || dogId < 0 || token.isBlank()) {
            log.error("incoming dogId or token was null or empty string");
            return null;
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return null;
        }
        
        // Attempting to get the specific dog
        Dog dg = dd.getDogByID(dogId);

        // Returning the dog or null if not found
        return dg;
    }

    /**
     * Gets all the dogs of a given status related to the given username
     * Requires a token associated with an active user session to access this service
     * Authorization:
     * - Owner can get any list of a user
     * - Customer can only get their OWN dogs
     * @param userEmail The email associated with the owner of the dogs
     * @param token The associated active user session of the requester
     * @param status The status of the dogs
     * @return The list of dogs if successful, and null otherwise
     */
    public List<Dog> getAllDogsByStatus(String userEmail, String token, Boolean status) {
        log.debug("Attempting to get all dogs of a given status of a user with userEmail: " + userEmail + " token: " + token + " status: " + status);

        // Validating input
        if (userEmail == null || token == null || status == null || userEmail.isBlank() || token.isBlank()) {
            log.error("incoming userEmail, token or status was null or empty string");
            return null;
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return null;
        }

        // Attempting to get all the dogs of a given status and user
        List<Dog> dg = dd.getAllDogsByStatus(userEmail, status);

        // Returning the list of dogs, or null if not found
        return dg;
    }

    /*
     * === PATCH / UPDATE ===
     */

    /**
     * Updates the given dog
     * Requires a token associated with an active user session to access this service
     * Allowed Changes:
     * - dog_name
     * - dog_age
     * - vaccinated (Only if setting to true)
     * Authorization:
     * - Only the customer can edit their own dogs information
     * @param dogData The data with the update changes
     * @param token The associated active user session of the requester
     * @return The dog if successfully updated, and null otherwise
     */
    public Dog patchUpdateDog(Dog dogData, String token) {
        log.debug("Attempting to update a dog with dogData: " + dogData + " token: " + token);

        // Validating user input
        if (dogData == null || token == null || token.isBlank()) {
            log.error("incoming dogData or token was null or empty string");
            return null;
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return null;
        }

        // Attempting to update dog
        Dog dogUpdated = dd.patchUpdateDog(dogData);

        // Returning updated dog or null if not found
        return dogUpdated;
    }

    /*
     * === DELETE ===
     */

    /**
     * Deletes the given dog
     * Requires a token associated with an active user session to access this service.
     * This does not actually delete the dog from the database
     * - Instead this will set the dogs status to inactive
     *  = Done so that there are records regarding transactions for booking reasons
     * - In two years the dog will be permanently deleted
     * Authorization:
     * - Only the customer can delete their dog
     * @param dogId The dog to delete
     * @param token The associated active user session of the requester
     * @return True if the deletion was successful, and false otherwise
     */
    public boolean deleteDog(Integer dogId, String token) {
        log.debug("Attempting to delete dog with dogId: " + dogId + " token: " + token);

        // Validating input
        if (dogId == null || token == null || dogId < 0 ||  token.isBlank()) {
            log.error("incoming dogId or token was null or empty string");
            return false;
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return false;
        }

        // Attempting to delete the dog
        boolean dogDeleted = dd.deleteDog(dogId);

        // Returning whether the deletion was successful
        return dogDeleted;
    }
}
