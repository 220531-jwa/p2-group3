package dev.group3.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.User;
import dev.group3.model.enums.UserType;
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
     * All fields of the user are required. (Other than usertype)
     * - Email must follow standard
     * - Password must follow standard
     * - PhoneNumber must have 10 digits (ignores all other characters)
     * - Funds must be between 0 and 9999.99
     * If the user is successfully created will login the user.
     * @param userData The data of the new user
     * @return 200 with user information if successful, and 400 null series error otherwise.
     */
    public Pair<User, Integer> createNewUser(User userData) {
        log.debug("Attempting to create user with userData: " + userData);
        
        // Validating input
        if (userData == null) {
            log.error("Invald userData input");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Checking if required fields are missing
        if (userData.getEmail() == null || userData.getEmail().isBlank() ||
            userData.getPswd() == null || userData.getPswd().isBlank() ||
            userData.getFirstName() == null || userData.getFirstName().isBlank() ||
            userData.getLastName() == null || userData.getLastName().isBlank() ||
            userData.getPhoneNumber() == null || userData.getPhoneNumber().isBlank() ||
            userData.getFunds() == null) {
            log.error("userData is missing required field(s)");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Populating fields
        userData.setPhoneNumber(trimPhoneNumber(userData.getPhoneNumber()));
        userData.setUserType(UserType.CUSTOMER);
        
        // Checking if required fields are valid
        if (!isValidEmail(userData.getEmail()) ||
            !isValidPassword(userData.getPswd()) ||
            !isValidPhoneNumber(userData.getPhoneNumber()) ||
            !isValidFunds(userData.getFunds())) {
            log.error("Invalid email and/or password and/or phoneNumber and/or funds input(s)");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Creating user
        User user = userDAO.createNewUser(userData);
        
        // Checking if user was successfully created
        if (user == null) {
            log.error("Failed to create user. Possible: Username already in use.");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Successfully created new user -> create active user session
        String token = ActiveUserSessions.addActiveUser(user.getEmail());
        
        // Checking if token was generated correctly - This should never happen
        if (token == null || token.isBlank()) {
            log.fatal("Token failed to generate for active session");
            // User was still created - but failed to login
        }
        
        // Returning user information with password replaced with token
        return new Pair<User, Integer>(user.setPswd(token), 200);
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
        log.debug("Attempting to get user with username: " + username + " token: " + token);
        
        // Validating input
        if (username == null || token == null || username.isBlank() || token.isBlank()) {
            log.error("Invalid username and/or token input(s)");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active session");
            return new Pair<User, Integer>(null, 401);
        }
        
        // Getting users (requesters) information
        String requesterUsername = ActiveUserSessions.getActiveUserUsername(token);
        User requesterUser = userDAO.getUserByUsername(requesterUsername);
        
        // Checking if requester user exists
        if (requesterUser == null) {
            // This should never happen - All active users should have an existing user account
            log.fatal("Requester user does not exist");
            return new Pair<User, Integer>(null, 503);
        }
        
        // Checking if user is authorized to know about the given usernames information
        if (!requesterUsername.equals(username) && requesterUser.getUserType() != UserType.OWNER) {
            log.error("User is not authorized to know about user information associated with given username");
            return new Pair<User, Integer>(null, 403);
        }
        
        // Attempting to get user associated with given username
        User user = userDAO.getUserByUsername(username);
        
        // Checking if user exists
        if (user == null) {
            log.error("User information associated with given username does not exist");
            return new Pair<User, Integer>(null, 404);
        }
        
        // Successfully got users information
        return new Pair<User, Integer>(user, 200);
    }
    
    /*
     * === PATCH ===
     */
    
    /**
     * Updates the user information of the given username.
     * Requires a token associated with an active user session to access this service.
     * Valid update fields:
     * - Password
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
        log.debug("Attempting to update user with username: " + username + " userData: " + userData + " token: " + token);
        
        // Validating input
        if (username == null || userData == null || token == null || username.isBlank() || token.isBlank()) {
            log.error("Invalid username and/or userData and/or token input(s)");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Checking if user is in an active session
        if (!ActiveUserSessions.isActiveUser(token)) {
            log.error("User is not in an active user session");
            return new Pair<User, Integer>(null, 401);
        }
        
        // Getting users (requesters) information
        String requesterUsername = ActiveUserSessions.getActiveUserUsername(token);
        
        // Checking if user is authorized to update user
        if (!requesterUsername.equals(username)) {
            log.error("User is not authorized to update user information associated with given username");
            return new Pair<User, Integer>(null, 403);
        }
        
        // Checking if new data was provided
        if ((userData.getPswd() == null || userData.getPswd().isBlank()) &&
            (userData.getFirstName() == null || userData.getFirstName().isBlank()) &&
            (userData.getLastName() == null || userData.getLastName().isBlank()) &&
            (userData.getPhoneNumber() == null || userData.getPhoneNumber().isBlank())) {
            log.error("No new user data was provided to update use with");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Checking if provided data is valid
        if ((userData.getPswd() != null && !isValidPassword(userData.getPswd())) ||
            (userData.getPhoneNumber() != null && !isValidPhoneNumber(userData.getPhoneNumber()))) {
            log.error("Password and/or phoneNumber input(s) are invalid");
            return new Pair<User, Integer>(null, 400);
        }
        
        // Populating necessary userData
        userData.setEmail(username);
        
        // Attempting to update user
        User user = userDAO.updateUserByUsername(userData);
        
        // Checking if user was successfully updated
        if (user == null) {
            log.error("Failed to update user. Possible: user does not exist");
            return new Pair<User, Integer>(null, 404);
        }
        
        // Successfully updated user
        return new Pair<User, Integer>(user, 200);
    }
    
    /*
     * === UTILLITY ===
     */
    
    private boolean isValidEmail(String email) {
        // Note: Regex found at: https://www.baeldung.com/ && https://www.geeksforgeeks.org/
        String validEmailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(validEmailRegex);
    }
    
    private boolean isValidPassword(String password) {
        // Note: Regex found at: https://www.baeldung.com/ && https://www.geeksforgeeks.org/
        String validPasswordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&-+=()])(?=\\S+$).{8,}$";
        return password.matches(validPasswordRegex);
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 10;
    }
    
    private boolean isValidFunds(double funds) {
        return !(funds < 0.00 || funds > 10000.00);
    }
    
    private String trimPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[^0-9]", "");
    }
}
