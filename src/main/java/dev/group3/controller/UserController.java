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
    
    /**
     * Handles the http POST request for creating a new user.
     * Takes the user information from the body
     * @return 200 with user information (with token replacing password), and 400 series error otherwise
     */
    public void createNewUser(Context ctx) {
        log.debug("HTTP POST request recieved at endpoint /users");
        
        // Getting entered user data
        User userData = ctx.bodyAsClass(User.class);
        
        // Attempting to create new user
        Pair<User, Integer> result = userService.createNewUser(userData);
        
        // Checking if user was created successfully
        if (result.getFirst() != null) {
            log.info("User successfully created");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }
    
    /*
     * === GET ===
     */
    
    /**
     * Handles the http GET request for getting user information
     * Takes the username from the path
     * Takes the token from the header
     * @return 200 with user information, and 400 series error otherwise
     */
    public void getUserByUsername(Context ctx) {
        log.debug("HTTP GET request recieved at endpoint /users/{username}");
        
        // Getting user input
        String username = ctx.pathParam("username");
        String token = ctx.header("Token");
        
        // Attempting to get user
        Pair<User, Integer> result = userService.getUserByUsername(username, token);
        
        // Checking if user was successfully retrieved
        if (result.getFirst() != null) {
            log.info("Successfully retrieved user information");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }
    
    /*
     * === PATCH ===
     */
    
    /**
     * Handles the http PATCH request for updating user information
     * Takes the username from the path
     * Takes updated fields from body
     * Takes the token from the header
     * @return 200 with updated user information, and 400 series error otehrwise
     */
    public void updateUserByUsername(Context ctx) {
        log.debug("HTTP PATCH request recieved at endpoint /users/{username}");
        
        // Getting user input
        String username = ctx.pathParam("username");
        User userData = ctx.bodyAsClass(User.class);
        String token = ctx.header("Token");
        
        // Attempting to get user
        Pair<User, Integer> result = userService.updateUserByUsername(username, userData, token);
        
        // Checking if user was successfully updated
        if (result.getFirst() != null) {
            log.info("Successfully updated user information");
            ctx.json(result.getFirst());
        }
        
        ctx.status(result.getSecond());
    }
}
