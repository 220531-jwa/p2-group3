package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Reservation;
import dev.group3.model.DTO.ReservationDTO;
import dev.group3.model.enums.ResStatusType;
import dev.group3.model.enums.ServiceType;
import dev.group3.util.ConnectionUtil;

public class ReservationDAO {

    private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    private static Logger log = LogManager.getLogger(ReservationDAO.class);

    /*
     * === CREATE ===
     */

    /**
     * Adds a new reservation into the reservations table of the database
     * @param resData The data to insert
     * @return The reservation if successful, and null otherwise
     */
    public Reservation createReservation(Reservation resData) {
        log.debug("Attempting to add reservation with resData: " + resData);
        
        // Init sql query
        String sql = "insert into reservations values(default, ?, ?, ?, ?, ?)";
        // need to update SQL statement to also add serviceId (if selected)

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, resData.getUserEmail());
            ps.setInt(2, resData.getDogId());
            ps.setString(3, resData.getStatus().name());
            ps.setTimestamp(4, resData.getStartDateTime());
            ps.setTimestamp(5, resData.getEndDateTime());
            ResultSet rs = ps.executeQuery();

            // Checking if reservation was added successfully
            if (rs.next()) {
                // Reservation was added successfully
                return createReservationFromResultSet(rs);
            }
        } catch (SQLException q) {
            log.error("Failed to execute sql query: " + sql);
            q.printStackTrace();
        }
        
        return null;
    }

    /*
     * === READ ===
     */

    /**
     * Gets all the reservations from the reservations table in the database
     * @return A list of reservations if successful, and null otherwise
     */
    public List<Reservation> getAllReservations() {
        log.debug("Attempting to get all reservaitons");

        // Init
        String sql = "select * from reservations;";
        List<Reservation> resList = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Checking if reservations where retrieved
            if (rs.next()) {
                // Reservations were successfully retrieved
                resList = new ArrayList<Reservation>();
                do {
                    resList.add(createReservationFromResultSet(rs));
                } while(rs.next());
            }
        } catch (SQLException e) {
            log.error("Failed to execute sql query: " + sql);
            e.printStackTrace();
        }

        return resList;
    }

    /**
     * Gets all the reservations associated with the given username from the database.
     * @param username The username to find the reservations of
     * @return The list of reservations if successful, and null otherwise
     */
    public List<Reservation> getAllRservationsByUsername(String username) {
        log.debug("Attempting to get all reservations of username: " + username);
        
        // Init
        String sql = "SELECT * FROM reservations WHERE user_email = ?";
        List<Reservation> resList = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            // Checking if reservations where retrieved
            if (rs.next()) {
                // Reservations were successfully retrieved
                resList = new ArrayList<Reservation>();
                do {
                    resList.add(createReservationFromResultSet(rs));
                } while(rs.next());
            }
        } catch (SQLException e) {
            log.error("Failed to execute sql query: " + sql);
            e.printStackTrace();
        }
        
        return resList;
    }

    
    /**
     * Gets the reservation with the specified id
     * @param id The id of the reservation to find
     * @return The reservation if successful and null otherwise.
     */
    public Reservation getReservationById(int id) {
        log.debug("Attempting to get reservation with id: " + id);
        
        // Init
        String sql = "Select * from reservations where id = ?";
        Reservation inComingReserv = null;

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                inComingReserv = createReservationFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to execute sql query: " + sql);
            e.printStackTrace();
        }
        
        return inComingReserv;
    }

    /**
     * Attempts to get all the information related to the resevation of the given id
     * @param id The reservation id to find
     * @return A reserationDTO if successful, and null otherwise
     */
    public ReservationDTO getReservationDTOById(int id) {
        log.debug("Attempting to get reservationDTO with id: " + id);

        // Init
        String sql = "select u.first_name, u.last_name, r.*, d.dog_name, s.service_type"
                + " from users u, dogs d, reservations r" + " left join services s on s.id = r.service_id"
                + " where u.email = r.user_email and d.id = r.dog_id and r.id = ?";
        ReservationDTO reservationDTO = null;

        // Attempting to execute query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String temp = rs.getString("service_type");
                ServiceType serviceType = temp == null ? null : ServiceType.valueOf(temp);
                reservationDTO = new ReservationDTO(rs.getString("first_name"), rs.getString("last_name"),
                        new Reservation(rs.getInt("id"), rs.getString("user_email"), rs.getInt("dog_id"),
                                rs.getInt("service_id"), ResStatusType.valueOf(rs.getString("status")),
                                rs.getTimestamp("start_datetime"), rs.getTimestamp("end_datetime")),
                        rs.getString("dog_name"), serviceType);
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }

        return reservationDTO;
    }

    /*
     * === UPDATE ===
     */

    /**
     * Updates the given reservation with the given data.
     * The id is inside the resData
     * @param resData The reservation to update
     * @return The updated reservation if successful, and null otherwise
     */
    public Reservation updateReservationById(Reservation resData) {
        log.debug("Attempting to update the reservation data with resData: " + resData);
        
        // Init
        String sql = "Update reservations SET " + "user_email= ?," + "dog_id= ?," + "status= ?," + "start_datetime= ?,"
                + "end_datetime= ?" + "WHERE id = ?;";

        // Attempting to execute update query
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, resData.getUserEmail());
            ps.setInt(2, resData.getDogId());
            ps.setString(3, resData.getStatus().toString());
            ps.setTimestamp(4, resData.getStartDateTime());
            ps.setTimestamp(5, resData.getEndDateTime());
            ps.setInt(6, resData.getId());
            boolean didEx = ps.execute();

            // Checking if update was successful
            if (didEx) {
                // Update successful
                return resData;
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        // Failed to update
        return null;
    }
    
    /*
     * === UTILITY ===
     */
    
    /**
     * Creates a reservation from the given result set.
     * Assumes result set contains the necessary data.
     * @param rs The result set to create the reservation form
     * @return A new reservation
     * @throws SQLException
     */
    public Reservation createReservationFromResultSet(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getString("user_email"),
                rs.getInt("dog_id"),
                rs.getInt("service_id"),
                ResStatusType.valueOf(rs.getString("status")),
                rs.getTimestamp("start_datetime"),
                rs.getTimestamp("end_datetime"));
    }
}
