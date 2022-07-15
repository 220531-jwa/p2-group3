package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Timestamp;
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
    
    public Reservation createReservation(Reservation resData) {
       
    	String sql = "insert into reservations values(default, ?, ?, ?, ?, ?)";
    	//need to update SQL statement to also add serviceId (if selected)
    	
    	try (Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, resData.getUserEmail());
			ps.setInt(2, resData.getDogId());
			ps.setString(3, resData.getStatus().name());
			ps.setTimestamp(4, resData.getStartDateTime());
			ps.setTimestamp(5, resData.getEndDateTime());
			//add 'set' serviceId (if selected)
			
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Reservation(
						rs.getInt("id"),
						rs.getString("user_email"),
						rs.getInt("dog_id"),
						rs.getInt("service_id"),
						ResStatusType.valueOf(rs.getString("status")),
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
    	
    	String sql = "select * from reservations;";
    	
    	List<Reservation> resList = new ArrayList<Reservation>();
    	
    	try(Connection conn = cu.getConnection()){
    		
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			
    			Reservation inComingReserv = new Reservation();
    			
    			
    			inComingReserv.setId(rs.getInt("id"));
    			inComingReserv.setUserEmail(rs.getString("user_email"));
    			inComingReserv.setDogId(rs.getInt("dog_id"));
    			inComingReserv.setServiceId(rs.getInt("service_id"));
    			
    			ResStatusType stat = ResStatusType.valueOf(rs.getString("status"));
    			inComingReserv.setStatus(stat);
    			inComingReserv.setStartDateTime(rs.getTimestamp("start_datetime"));
    			inComingReserv.setEndDateTime(rs.getTimestamp("end_datetime"));
    		    
    			
    			resList.add(inComingReserv);
    			
    		}
    		
//    		Pair<List<Reservation>,Integer> respPairDos = new Pair<List<Reservation>,Integer>(resList,200);
    		
    		
    		
    		return resList;
    		
    		
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
//    		Pair<List<Reservation>,Integer> respPairDos = new Pair<List<Reservation>,Integer>(null,e.getErrorCode());
    	}
    			
        return null;
    }
    
    
    
    
    
    
    public List<Reservation> getAllRservationsByUsername(String username) {
        
    	List<Reservation> resList = new ArrayList<Reservation>();
    	
    	String sql = "Select * from reservations WHERE user_email=?";
    
    	
    	try(Connection conn = cu.getConnection()){
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, username);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		
    		while(rs.next()) {
    			
    			Reservation inComingReserv = new Reservation();
    			
    			inComingReserv.setId(rs.getInt("id"));
    			inComingReserv.setUserEmail(rs.getString("user_email"));
    			inComingReserv.setDogId(rs.getInt("dog_id"));
    			inComingReserv.setServiceId(rs.getInt("service_id"));
    			
    			ResStatusType stat = ResStatusType.valueOf(rs.getString("status"));
    			inComingReserv.setStatus(stat);
    			inComingReserv.setStartDateTime(rs.getTimestamp("start_datetime"));
    			inComingReserv.setEndDateTime(rs.getTimestamp("end_datetime"));
    			
    			
    		    
    			
    			resList.add(inComingReserv);
    		}
    		
    		
    		
    		return resList;
    		
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
    		return null;
    	}
    	
    }
    
    public Reservation getReservationById(int id) {
        
    	String sql = "Select * from reservations where id = ?";
    	
    	try(Connection conn = cu.getConnection()){
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			

    			Reservation inComingReserv = new Reservation();
    			
    			inComingReserv.setId(rs.getInt("id"));
    			inComingReserv.setUserEmail(rs.getString("user_email"));
    			inComingReserv.setDogId(rs.getInt("dog_id"));
    			inComingReserv.setServiceId(rs.getInt("service_id"));
    			
    			ResStatusType stat = ResStatusType.valueOf(rs.getString("status"));
    			inComingReserv.setStatus(stat);
    			inComingReserv.setStartDateTime(rs.getTimestamp("start_datetime"));
    			inComingReserv.setEndDateTime(rs.getTimestamp("end_datetime"));
    			
    			
    			return inComingReserv;
    		}else {
    			return null;
    		}
    		
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
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
                + " from users u, dogs d, reservations r"
                + " left join services s on s.id = r.service_id"
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
                reservationDTO = new ReservationDTO(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        new Reservation(
                                rs.getInt("id"),
                                rs.getString("user_email"),
                                rs.getInt("dog_id"),
                                rs.getInt("service_id"),
                                ResStatusType.valueOf(rs.getString("status")),
                                rs.getTimestamp("start_datetime"),
                                rs.getTimestamp("end_datetime")),
                        rs.getString("dog_name"),
                        serviceType);
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
    
    public Reservation updateReservationById(Reservation res) {
    	
    	String sql = "Update reservations SET "
    			+ "user_email= ?,"
    			+ "dog_id= ?,"
    			+ "status= ?,"
    			+ "start_datetime= ?,"
    			+ "end_datetime= ?"
    			+ "WHERE id = ?;";
    	
    	try(Connection conn = cu.getConnection()){
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, res.getUserEmail());
    		ps.setInt(2, res.getDogId());
    		ps.setString(3, res.getStatus().toString());
    		ps.setTimestamp(4, res.getStartDateTime());
    		ps.setTimestamp(5, res.getEndDateTime());
    		ps.setInt(6, res.getId());
    		
    		
    		boolean didEx = ps.execute();
//    		ps.getUpdateCount();
    		
    		if(didEx) {
    			return res;
    			
    		}else {
    			return null;
    		}
    		
    		
    		
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
//        return null;
    }
}
