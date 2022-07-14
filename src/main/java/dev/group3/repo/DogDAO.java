package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.group3.model.Dog;
import dev.group3.util.ConnectionUtil;

public class DogDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	/*
     * === CREATE ===
     */
	
	public Dog createDog(Dog dogData) {
		
		String sql = "insert into dogs values(default, ?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, dogData.getUser_email());
			ps.setBoolean(2, dogData.getStatus());
			ps.setString(3, dogData.getDog_name());
			ps.setString(4, dogData.getBreed());
			ps.setInt(5, dogData.getDog_age());
			ps.setBoolean(6, dogData.isVaccinated());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Dog(
						rs.getInt("id"),
						rs.getString("user_email"),
						rs.getBoolean("status"),
						rs.getString("dog_name"),
						rs.getString("breed"),
						rs.getInt("dog_age"),
						rs.getBoolean("vaccinated")
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
	
	public List<Dog> getAllDogs() {
		List<Dog> dogs = new ArrayList<>();
		String sql = "Select * from dogs";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				dogs.add(new Dog(
						rs.getInt("id"),
						rs.getString("user_email"),
						rs.getBoolean("status"),
						rs.getString("dog_name"),
						rs.getString("breed"),
						rs.getInt("dog_age"),
						rs.getBoolean("vaccinated")
						));
			}
			return dogs;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Dog> getAllDogsByStatus(String userEmail, boolean status) {
		List<Dog> dogs = new ArrayList<>();
		String sql = "Select * from dogs where user_email = ?, status = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userEmail);
			ps.setBoolean(2, status);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				dogs.add(new Dog(
						rs.getInt("id"),
						rs.getString("user_email"),
						rs.getBoolean("status"),
						rs.getString("dog_name"),
						rs.getString("breed"),
						rs.getInt("dog_age"),
						rs.getBoolean("vaccinated")
						));
			}
			return dogs;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Dog> getAllDogsByUserId(String userName) {
		List<Dog> dogs = new ArrayList<>();
		String sql = "Select * from dogs where user_email = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				dogs.add(new Dog(
						rs.getInt("id"),
						rs.getString("user_email"),
						rs.getBoolean("status"),
						rs.getString("dog_name"),
						rs.getString("breed"),
						rs.getInt("dog_age"),
						rs.getBoolean("vaccinated")
						));
			}
			return dogs;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Dog getDogByID(int dogId) {
		String sql = "select from dogs where id = ?";
		
		try(Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dogId);
			ResultSet rs = ps.executeQuery();
			
			return new Dog(
					rs.getInt("id"),
					rs.getString("user_email"),
					rs.getBoolean("status"),
					rs.getString("dog_name"),
					rs.getString("breed"),
					rs.getInt("dog_age"),
					rs.getBoolean("vaccinated")
					);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * PATCH
	 * 
	 */
	
	public static Dog patchUpdateDog(Dog dg){
		String sql = "update dogs set status = ?, dog_name = ?, breed = ?, dog_age = ?, vaccinated = ?, where id = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, dg.getStatus());
			ps.setString(2, dg.getDog_name());
			ps.setString(3, dg.getBreed());
			ps.setInt(4, dg.getDog_age());
			ps.setBoolean(5, dg.isVaccinated());
			
			ResultSet rs = ps.executeQuery();
			
			return new Dog(
					rs.getInt("id"),
					rs.getString("user_email"),
					rs.getBoolean("status"),
					rs.getString("dog_name"),
					rs.getString("breed"),
					rs.getInt("dog_age"),
					rs.getBoolean("vaccinated")
					);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * DELETE
	 * 
	 */
	
	public boolean deleteDog(int dogId) {
String sql = "delete * from dogs where id = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dogId);
			int deleted = ps.executeUpdate(sql);
			
			if (deleted != 0) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
