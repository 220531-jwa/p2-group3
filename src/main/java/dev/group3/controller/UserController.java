package dev.group3.controller;

import dev.group3.service.UserService;
import io.javalin.http.Context;

public class UserController {
    
    private UserService userService = new UserService();
    
    /*
     * === POST ===
     */
    
    public void loginUserWithCredentials(Context ctx) {
        
    }
    
    public void logoutUser(Context ctx) {
        
    }
    
    public void createNewUser(Context ctx) {
        
    }
    
    /*
     * === GET ===
     */
    
    // Owner is authorized
    public void getAllUsers(Context ctx) {
        
    }
    
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
