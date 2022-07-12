package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.User;
import dev.group3.model.enums.UserType;
import dev.group3.util.ConnectionUtil;

public class UserDAO {
    
    private ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
    private static Logger log = LogManager.getLogger(UserDAO.class);
    
    /*
     * === CREATE ===
     */
    
    /**
     * Adds a new user to the users tables in the database.
     * @param userData The new user to add
     * @return The added user if successful, and null otherwise
     */
    public User createNewUser(User userData) {
        log.debug("Attempting to add new user to database with userData: " + userData);
        
        // Init
        String sql = "insert into users values"
                + " (?, ?, ?, ?, ?, ?, ?)"
                + " returning *";
        User user = null;
        
        // Attempting to execute query
        try (Connection conn = conUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userData.getEmail());
            ps.setString(2, userData.getPswd());
            ps.setString(3, userData.getUserType().name());
            ps.setString(4, userData.getFirstName());
            ps.setString(5, userData.getLastName());
            ps.setString(6, userData.getPhoneNumber());
            ps.setDouble(7, userData.getFunds());
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Successfully found user
                user = createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
                
        return user;
    }
    
    /*
     * === READ ===
     */
    
    /**
     * Retrieves a specific user from the users table in the database.
     * @param username The username associated with the user to find
     * @return The user if found successfully, and null otherwise.
     */
    public User getUserByUsername(String username) {
        log.debug("Attempting to get user from database with username: " + username);
        
        // Init
        String sql = "select * from users"
                + " where email = ?";
        User user = null;
        
        // Attempting to execute query
        try (Connection conn = conUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Successfully found user
                user = createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return user;
    }
    
    /*
     * === UPDATE ===
     */
    
    /**
     * Updates a specific user from the users table in the database.
     * Can update password, firstname, lastname, and phonenumber
     * @param userData The data to use to update the users information
     * @return The updated user if successful, and null otherwise.
     */
    public User updateUserByUsername(User userData) {
        log.debug("Attempting to update user from database with userData: " + userData);
        
        // Init
        String sql = "update users set"
                + " pswd = coalesce(?, pswd),"
                + " first_name = coalesce(?, first_name),"
                + " last_name = coalesce(?, last_name),"
                + " phone_number = coalesce(?, phone_number)"
                + " where email = ?"
                + " returning *";
        User user = null;
        
        // Attempting to execute update query
        try (Connection conn = conUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, userData.getPswd());
            ps.setString(2, userData.getFirstName());
            ps.setString(3, userData.getLastName());
            ps.setString(4, userData.getPhoneNumber());
            ps.setString(5, userData.getEmail());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Successfully updated user
                user = createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
       
        return user;
    }
    
    /**
     * Updates the funds of a specific user from the users table in the database.
     * @param userData The data to use to update the users information
     * @return True if successful, and false otherwise
     */
    public boolean updateUserFunds(User userData) {
        log.debug("Attempting to update user funds from database with userData: " + userData);
        
        // Init
        String sql = "update users set funds = ?"
                + " where email = ?";
        
        // Attempting to execute update query
        try (Connection conn = conUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setDouble(1, userData.getFunds());
            ps.setString(2, userData.getEmail());
            int changes = ps.executeUpdate();
            
            if (changes > 0) {
                // Successfully updated user
                return true;
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return false;
    }
    
    /*
     * === UILITY ===
     */
    
    /**
     * Creates a user object from the given result set
     * Assumes result set is valid and contains necessary data
     * @param rs The result set to create the User from
     * @return A new  user
     * @throws SQLException
     */
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("email"),
                rs.getString("pswd"),
                UserType.valueOf(rs.getString("user_type")),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getDouble("funds"));
    }
}
