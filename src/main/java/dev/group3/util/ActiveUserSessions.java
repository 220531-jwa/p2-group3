package dev.group3.util;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActiveUserSessions {
    
    private static Hashtable<String, String> activeUsers = new Hashtable<>();   // Token => username
    private static TokenGenerator tg = new TokenGenerator();
    private static Logger log = LogManager.getLogger(ActiveUserSessions.class);
    
    /**
     * Adds an active user (signed in) allowing the user to use server services.
     * Users can have multiple active sessions.
     * Assumes that the user exists.
     * Generated tokens are unique.
     * @param username The users username that has logged in.
     * @return The token associated with the now active user, and null otherwise.
     */
    public static String addActiveUser(String username) {
        log.debug("Creating active login session for username: " + username);
        // Generating token and activating user.
        String token = tg.generateToken();
        
        // Checking if token was successfully created
        if (token == null || token.isBlank()) {
            log.error("Failed to create active session: Token wasn't generated");
            return null;
        }
        
        activeUsers.put(token, username);
        
        return token;
    }
    
    /**
     * Removes an active user, meaning the user can no longer use server services.
     * Used token is returned back to TokenGenerator.
     * Will fail if the token isn't active.
     * @param token The token of the user to remove.
     * @return True if successful, and false otherwise.
     */
    public static boolean removeActiveUser(String token) {
        log.debug("Removing active login session for token: " + token);
        // Checking if user  is already active
        if (!isActiveUser(token) ) {
            // Isn't active - can't remove
            log.error("Token isn't associated with an active session.");
            return false;
        }
        
        // Removing active session
        activeUsers.remove(token);
        tg.removeUsedToken(token);
        
        return true;
    }
    
    /**
     * Removes all active users, logging every user out.
     */
    public static void clearAllActiveSessions() {
        log.debug("Removing all active sessions");
        for (String token: activeUsers.keySet()) {
            removeActiveUser(token);
        }
    }
    
    /**
     * Retrieves the username of the given token.
     * If the token isn't associated with an active session returns null.
     * @param token The token associated with the username for an active session.
     * @return The token username if found, and null otherwise.
     */
    public static String getActiveUserUsername(String token) {
        log.debug("Getting employee username associated with token: " + token);
        // Checking if user is active
        if (!isActiveUser(token)) {
            // isn't active - get get username
            log.error("Token isn't associated with an active session.");
            return null;
        }
        
        return activeUsers.get(token);
    }
    
    /**
     * Determines if the user of the given token is still active (signed in).
     * @param token The token associated with the user.
     * @return True if the user is active, and false otherwise.
     */
    public static boolean isActiveUser(String token) {
        log.debug("Checking if session is active with token: " + token);
        return activeUsers.containsKey(token);
    }
}
