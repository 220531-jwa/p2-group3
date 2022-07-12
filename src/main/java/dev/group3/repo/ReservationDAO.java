package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dev.group3.model.Reservation;
import dev.group3.util.ConnectionUtil;

public class ReservationDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    /*
     * === CREATE ===
     */
    
    public Reservation createReservation(Reservation resData) {
       
    	String sql = "insert into reservations values(default, ?, ?, ?, ?, ?)";
    	
    	try (Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, resData.getUserEmail());
			ps.setInt(2, resData.getDogId());
			ps.setString(3, resData.getStatus());
			ps.setTimestamp(4, resData.getStartDateTime());
			ps.setTimestamp(5, resData.getEndDateTime());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Reservation(
						rs.getInt("id"),
						rs.getString("user_email"),
						rs.getInt("dog_id"),
						rs.getString("status"),
						rs.getTimestamp("start_datetime"),
						rs.getTimestamp("end_datetime")
						);
			}
    	}catch (SQLException q) {
    		q.printStackTrace();
    	}
    	return null;
    }
    
    /*
     * === GET ===
     */
    
    public List<Reservation> getAllReservations() {
        return null;
    }
    
    public List<Reservation> getAllRservationsByUsername(String username) {
        return null;
    }
    
    public Reservation getReservationById(int id) {
        return null;
    }
    
    /*
     * === UPDATE ===
     */
    
    public Reservation updateReservationById(int id) {
        return null;
    }
}
