package dev.group3.service;

import java.util.ArrayList;
import java.util.List;

//import dev.friesner.repos.UserDAO;
import dev.group3.model.Dog;
import dev.group3.repo.DogDAO;
import kotlin.Pair;

public class DogService {
	

	
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
	public static Dog postCreateNewDog(Dog dg) {

		Dog newdg = dd.createDog(dg);

		return newdg;
	}

	
	
	/**
	 * GET
	 * 
	 */
	
	
	// RETURNS ALL DOGS FROM DB  --> FOR THE OWNER
	public static  List<Dog> getAllDogs() {
		
		List<Dog> dg = dd.getAllDogs();
		
		return dg;
	}
	
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND CLIENT
	public static List<Dog> getAllDogsByUserId(String userName) {
		
		List<Dog> dg = dd.getAllDogsByUserId(userName);
		
		
		return dg;
	}
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND STORE CLIENT
	public static Dog getDogById(int dogId) {
		
		Dog dg = dd.getDogByID(dogId);
		
		
		return dg;
	}
	
	// RETURNS ALL DOG BY STATUS
	public static List<Dog> getAllDogsByStatus(String userEmail, boolean status){
		
		List<Dog> dg = dd.getAllDogsByStatus(userEmail, status);
		
		return dg;
	}

		
	
	
	

	
	
	
	
	/**
	 * PATCH
	 * 
	 */
	
	// RETURNS A DOG AND STRING MESSAGE ---> STORE OWNER AND STORE CLIENT
	public static Dog patchUpdateDog(Dog dg) {

		Dog dogUpdated = dd.patchUpdateDog(dg);
		
		return dogUpdated;
	}
	
	
	
	/**
	 * DELETE
	 * 
	 */
	
	//RETURNS A BOOLEAN INDICATING IF THE DELETION WAS SUCCESSFUL
	public static boolean deleteDog(int dogId) {

		boolean dogDeleted = dd.deleteDog(dogId);
		
		return dogDeleted;
	}





















}
