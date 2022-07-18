package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Dog;
import dev.group3.util.ConnectionUtil;

public class DogDAO {

    private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    private static Logger log = LogManager.getLogger(DogDAO.class);

    /*
     * === CREATE ===
     */

    /**
     * Adding a new dog to the database
     * @param dogData The dog to add
     * @return The dog if successfully added, and null otherwise
     */
    public boolean createDog(Dog dogData) {
        log.debug("Adding dog with dogData: " + dogData);

        // Init
        String sql = "insert into dogs values(default, ?, ?, ?, ?, ?, ?)";

        // Attempting to execute sql query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dogData.getUser_email());
            ps.setBoolean(2, dogData.getStatus());
            ps.setString(3, dogData.getDog_name());
            ps.setString(4, dogData.getBreed());
            ps.setInt(5, dogData.getDog_age());
            ps.setBoolean(6, dogData.isVaccinated());
            int rs =  ps.executeUpdate();
            
            ps = conn.prepareStatement(sql);
            
            // Checking if dog was added successfully
            if (rs == 1) {
                // Dog added successfully
                return true;
            }
        } catch (SQLException q) {
            log.error("Failed to execute query: " + sql);
            q.printStackTrace();
        }
        
        return false;
    }

    /*
     * === GET ===
     */

    /**
     * Gets all the dogs from the database
     * @return The list of dogs if successful, and null otherwise
     */
    public List<Dog> getAllDogs() {
        log.debug("Getting all dogs");
        
        // Init
        String sql = "Select * from dogs";
        List<Dog> dogs = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Checking if dogs were found
            if (rs.next()) {
                // Successfully found dogs
                dogs = new ArrayList<Dog>();
                do {
                    dogs.add(createDogFromResultSet(rs));
                } while (rs.next());
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return dogs;
    }

    /**
     * Gets all the dogs of the given user with a specified status
     * @param userEmail The username to find the dogs of
     * @param status The status of the dogs to find
     * @return The list of dogs if successful, and null otherwise
     */
    public List<Dog> getAllDogsByStatus(String userEmail, Boolean status) {
        log.debug("Attempting to get all dogs of the given username and status with userEmail: " + userEmail + " status: " + status);
        
        // Init
        String sql = "Select * from dogs where user_email = ? and status = ?";
        List<Dog> dogs = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ps.setBoolean(2, status);
            ResultSet rs = ps.executeQuery();

            // Checking if dogs were found
            if (rs.next()) {
                // Successfully found dogs
                dogs = new ArrayList<Dog>();
                do {
                    dogs.add(createDogFromResultSet(rs));
                } while (rs.next());
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return dogs;
    }

    /**
     * Gets all the dogs of the given username
     * @param userName The username to find all the dogs of
     * @return The list of dogs if successful, and null otherwise
     */
    public List<Dog> getAllDogsByUsername(String userName) {
        log.debug("Getting all dogs with userName: " + userName);
        
        // Init
        String sql = "Select * from dogs where user_email = ?";
        List<Dog> dogs = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            // Checking if dogs were found
            if (rs.next()) {
                // Successfully found dogs
                dogs = new ArrayList<Dog>();
                do {
                    dogs.add(createDogFromResultSet(rs));
                } while (rs.next());
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return dogs;
    }

    /**
     * Getting a specific dog from the database
     * @param dogId The dog to find
     * @return The dog if successfully found, and null otherwise
     */
    public Dog getDogByID(Integer dogId) {
        log.debug("Getting dog with dogId: " + dogId);
        
        // Init
        String sql = "select * from dogs where id = ?";
        Dog dog  = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, dogId);
            ResultSet rs = ps.executeQuery();

            // Checking if dog was found
            if (rs.next()) {
                // Successfully found dog
                dog = createDogFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return dog;
    }

    /*
     * === UPDATE ===
     */

    /**
     * Updating a dog with the given data
     * @param dogData The updated data of the dog to update
     * @return The updated dog if successful, and null otherwise
     */
    public Dog patchUpdateDog(Dog dogData) {
        log.debug("Updateding the dog with dogData: " + dogData);
        
        // Init
        String sql = "update dogs set status = ?, dog_name = ?, breed = ?, dog_age = ?, vaccinated = ?, where id = ? returning *";
        Dog dog = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, dogData.getStatus());
            ps.setString(2, dogData.getDog_name());
            ps.setString(3, dogData.getBreed());
            ps.setInt(4, dogData.getDog_age());
            ps.setBoolean(5, dogData.isVaccinated());
            ResultSet rs = ps.executeQuery();
            
            // Checking if update was successful
            if (rs.next()) {
                // Dog updated successfully
                dog = createDogFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return dog;
    }

    /*
     * === DELETE ===
     */

    /**
     * Deletes the given dog
     * @param dogId The dog to delete
     * @return True if the dog was deleted successfully, and false otherwise
     */
    public boolean deleteDog(Integer dogId) {
        log.debug("Deleteing the dog with the dogId: " + dogId);
        
        // Init
        String sql = "delete * from dogs where id = ?";

        // Attempting to execute delete query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, dogId);
            int deleted = ps.executeUpdate(sql);

            // Checking if deleted
            if (deleted != 0) {
                // Dog deleted successfully
                return true;
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return false;
    }
    
    /*
     * === UTILITY ===
     */
    
    /**
     * Creates a dog object from the given result set
     * Assumes result set is valid and contains necessary data
     * @param rs The result set to create the dog from
     * @return A new Dog
     * @throws SQLException
     */
    private Dog createDogFromResultSet(ResultSet rs) throws SQLException {
        return new Dog(
                rs.getInt("id"),
                rs.getString("user_email"),
                rs.getBoolean("status"),
                rs.getString("dog_name"),
                rs.getString("breed"),
                rs.getInt("dog_age"),
                rs.getBoolean("vaccinated"));
    }
}
