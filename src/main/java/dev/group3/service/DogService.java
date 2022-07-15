package dev.group3.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import dev.friesner.repos.UserDAO;
import dev.group3.model.Dog;
import dev.group3.repo.DogDAO;
import dev.group3.util.ActiveUserSessions;

public class DogService {
	
	private static Logger log = LogManager.getLogger(UserService.class);
	
	// ASSIGN THE DOA TO USE
	private static DogDAO dd;
	
	public DogService(DogDAO dd) {
		this.dd = dd;
	}
	
	
	
	

	
	
	/**
	 * POST
	 * 
	 */
	
	
	// RETURNS DOG IF CREATED RETURNS NULL IF NOT CREATED ---> STORE OWNER AND STORE CLIENT
	public static Dog postCreateNewDog(Dog dg, String token) {
		
		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return null;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return null;
		}
		
		Dog newdg = dd.createDog(dg);

		return newdg;
	}

	
	
	/**
	 * GET
	 * 
	 */
	
	
	// RETURNS ALL DOGS FROM DB  --> FOR THE OWNER
	public static  List<Dog> getAllDogs(String token) {
		
		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return null;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return null;
		}
		
		List<Dog> dg = dd.getAllDogs();
		
		return dg;
	}
	
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND CLIENT
	public static List<Dog> getAllDogsByUserId(String userName, String token) {
		
		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return null;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return null;
		}
		
		List<Dog> dg = dd.getAllDogsByUserId(userName);
		
		
		return dg;
	}
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND STORE CLIENT
	public static Dog getDogById(int dogId , String token) {
		
		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return null;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return null;
		}
		Dog dg = dd.getDogByID(dogId);
		
		
		return dg;
	}
	
	// RETURNS ALL DOG BY STATUS
	public static List<Dog> getAllDogsByStatus(String userEmail, String token, boolean status){
		
		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return null;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return null;
		}
		
		List<Dog> dg = dd.getAllDogsByStatus(userEmail, status);
		
		return dg;
	}

		
	
	
	

	
	
	
	
	/**
	 * PATCH
	 * 
	 */
	
	// RETURNS A DOG AND STRING MESSAGE ---> STORE OWNER AND STORE CLIENT
	public static Dog patchUpdateDog(Dog dg, String token) {

		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return null;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return null;
		}
		
		Dog dogUpdated = dd.patchUpdateDog(dg);
		
		return dogUpdated;
	}
	
	
	
	/**
	 * DELETE
	 * 
	 */
	
	//RETURNS A BOOLEAN INDICATING IF THE DELETION WAS SUCCESSFUL
	public static boolean deleteDog(int dogId, String token) {
		
		if(token == null || token.isEmpty()) {
    		log.error("incoming token was null or empty string");
    		return false;
    		
    	}
		if(!ActiveUserSessions.isActiveUser(token)) {
			log.error("User is not in an active session");
			return false;
		}
		
		boolean dogDeleted = dd.deleteDog(dogId);
		
		return dogDeleted;
	}





















}
