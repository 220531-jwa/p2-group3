package dev.group3.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.User;
import dev.group3.service.UserService;
import io.javalin.http.Context;
import kotlin.Pair;

public class UserController {
    
    private UserService userService = new UserService();
    private static Logger log = LogManager.getLogger(UserController.class);
    
    /*
     * === POST ===
     */
    
    /**
     * Handles the HTTP POST request for logging in a user and creating an active user session.
     * Takes username and password as JSON from body
     * @return 200 with user information with password replaced with session token, 400 series error otherwise.
     */
    public void loginUserWithCredentials(Context ctx) {
        log.debug("HTTP POST request recieved at endpoint /login");
        
        // Getting user credentials
        User userData = ctx.bodyAsClass(User.class);
        
        // Attempting to login
        Pair<User, Integer> result = userService.loginUserWithCredentials(userData.getEmail(), userData.getPswd());
        
        // Checking if login was successful
        if (result.getFirst() != null) {
            log.info("User successfully logged in");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }
    
    /**
     * Handles the http POST request for logging out a user that is currently in an active user session.
     * Takes the active session token from the header
     * @return Always a 200
     */
    public void logoutUser(Context ctx) {
        log.debug("HTTP POST request recieved at endpoint /logout");
        
        // Getting user token
        String token = ctx.header("Token");
        
        // Attempting to logout - Don't care about return
        int result = userService.logoutUser(token);
        
        // Checking if logout was successful
        if (result == 200) {
            log.info("User was successfully logged out");
        }
        
        ctx.status(result);
    }
    
    public void createNewUser(Context ctx) {
        
    }
    
    /*
     * === GET ===
     */
    
    // Owner is authorized
    // Customer can get their own
    public void getUserByUsername(Context ctx) {
        
    }
    
    /*
     * === PATCH ===
     */
    
    public void updateUserByUsername(Context ctx) {
        
    }
}
