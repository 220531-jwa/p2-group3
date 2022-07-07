package dev.group3.service;

import dev.group3.model.User;
import dev.group3.repo.UserDAO;
import kotlin.Pair;

public class UserService {
    
    private UserDAO userDAO;
    
    public UserService() {
        userDAO = new UserDAO();
    }
    
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    /*
     * === POST / CREATE ===
     */
    
    public Pair<User, Integer> loginUserWithCredentials(String username, String password) {
        return null;
    }
    
    /*
     * === GET / READ ===
     */
}
