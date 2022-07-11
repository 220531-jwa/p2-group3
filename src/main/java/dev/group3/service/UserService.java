package dev.group3.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.User;
import dev.group3.repo.UserDAO;
import dev.group3.util.ActiveUserSessions;
import kotlin.Pair;

public class UserService {
    
    private UserDAO userDAO;
    private static Logger log = LogManager.getLogger(UserService.class);
    
    // Use default user DAO
    public UserService() {
        userDAO = new UserDAO();
    }
    
    /**
     * Used for a custom userDAO, more generally used for Mockito
     * @param userDAO The userDAO for this service to use
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    /*
     * === POST / CREATE ===
     */
    
    /**
     * Attempts to login in the user with the given credentials.
     * If the login was successful then the user will marked as an active user and will be given a...
     *  token to represent the active user session that the server will use to verify authentication.
     *  The token will replace the password field when giving back user information.
     * The Token is necessary for the user to make use of services provided by the server.
     * @param username The username of the user (EMAIL, UNIQUE)
     * @param password The password of the user
     * @return 200 with user information if successful, and 400 null series error otherwise
     */
    public Pair<User, Integer> loginUserWithCredentials(String username, String password) {
        log.debug("Attempting to login with credentials with username: " + username + " password: " + password);
        
        // Validating input
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            log.error("Invalid username and/or password input(s)");
            return new Pair<>(null, 400);
        }
        
        // Getting user associated with given username
        User user = userDAO.getUserByUsername(username);
        
        // Checking if user exists
        if (user == null) {
            log.error("User associated with username does not exist");
            return new Pair<>(null, 404);
        }
        
        // Checking if credentials match
        if (!user.getPswd().equals(password)) {
            log.error("User credentials do not match");
            return new Pair<>(null, 401);
        }
        
        // Credentials match -> create active user session
        String token = ActiveUserSessions.addActiveUser(username);
        
        // Checking if token was generated correctly - This should never happen
        if (token == null || token.isBlank()) {
            log.fatal("Token failed to generate for active session");
            return new Pair<>(null, 503);
        }
        
        // User successfully logged in
        // Returning user information with password replaced with token
        return new Pair<User, Integer>(user.setPswd(token), 200);
    }
    
    /**
     * Attempts to logout the user from an active session, deactivating the token that was given to them.
     * The token will no longer be authorized and the user will have to login in again to get a new one.
     * @param token The token associated with the active user session.
     * @return 200 if successful, and 400 series error otherwise.
     */
    public int logoutUser(String token) {
        log.debug("Attempting to logout user with token: " + token);
        
        // Validating input
        if (token == null || token.isBlank()) {
            log.error("Invalid token input");
            return 400;
        }
        
        // Attempting to remove session token
        boolean result = ActiveUserSessions.removeActiveUser(token);
        
        // Checking if user was in an active session
        if (!result) {
            log.error("Token was not associated with an active user session");
            return 404;
        }
        
        // User logged out
        return 200;
    }
    
    /**
     * Creates a new user with the given user information.
     * All fields of the user are required.
     * - Email must have '@email.com'
     * - Funds must be between 0 and 9999.99
     * - PhoneNumber must have 10 digits (ignores all other characters)
     * - Usertype must not be OWNER
     * If the user is successfully created will login the user.
     * @param userData The data of the new user
     * @return 200 with user information if successful, and 400 null series error otherwise.
     */
    public Pair<User, Integer> createNewUser(User userData) {
        return null;
    }
    
    /*
     * === GET / READ ===
     */
    
    /**
     * Retrieves the user information of the given username.
     * Requires a token associated with an active user session to access this service.
     * Authorization:
     * - Owner can retrieve any user information
     * - Customer can only retrieve their OWN user information
     * @param username The user to retrieve the information of
     * @param token The associated active user session of the requester
     * @return 200 with user information if successful, and 400 null series error otherwise
     */
    public Pair<User, Integer> getUserByUsername(String username, String token) {
        return null;
    }
    
    /*
     * === PATCH ===
     */
    
    /**
     * Updates the user information of the given username.
     * Requires a token associated with an active user session to access this service.
     * Valid update fields:
     * - First name
     * - Last name
     * - Phone Number
     * - Note: Everything else is ignored
     * Authorization:
     * - Customer can only update their own user information
     * @param username The user to update the information of.
     * @param token The associated active user session of the requester
     * @return 200 with updated user information if successful, and 400 null series error otherwise
     */
    public Pair<User, Integer> updateUserByUsername(String username, User userData, String token) {
        return null;
    }
}
