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
    
    public User createNewUser(User userData) {
        return null;
    }
    
    /*
     * === READ ===
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
    
    public User updateUserByUsername(User userData) {
        return null;
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
