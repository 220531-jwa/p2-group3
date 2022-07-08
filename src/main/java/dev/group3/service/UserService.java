package dev.group3.service;

import dev.group3.model.User;
import dev.group3.repo.UserDAO;
import kotlin.Pair;

public class UserService {
    
    private UserDAO userDAO;
    
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
        return null;
    }
    
    /**
     * Attempts to logout the user from an active session, deactivating the token that was given to them.
     * The token will no longer be authorized and the user will have to login in again to get a new one.
     * @param token The token associated with the active user session.
     * @return 200 if successful, and 400 series error otherwise.
     */
    public int logoutUser(String token) {
        return 0;
    }
    
    /**
     * Creates a new user with the given user information.
     * All fields of the user are required.
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
     * Authorization:
     * - Customer can only update their own user information
     * @param username The user to update the information of.
     * @param token The associated active user session of the requester
     * @return 200 with updated user information if successful, and 400 null series error otherwise
     */
    public Pair<User, Integer> updateUserByUsername(String username, String token) {
        return null;
    }
}
