package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;


import dev.group3.model.Reservation;
import dev.group3.model.enums.ResStatusType;
import dev.group3.util.ConnectionUtil;
import kotlin.Pair;


public class ReservationDAO {
	
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	

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
						ResStatusType.valueOf(rs.getString("status")),
						rs.getTimestamp("start_datetime"),
						rs.getTimestamp("end_datetime")
						//add 'get' serviceId (if selected)
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
    		Pair<List<Reservation>,Integer> respPairDos = new Pair<List<Reservation>,Integer>(null,e.getErrorCode());
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
    
    /*
     * === UPDATE ===
     */
    
    public Reservation updateReservationById(int id) {
        return null;
    }
}
