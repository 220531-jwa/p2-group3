package dev.group3.service;

import java.util.ArrayList;
import java.util.List;

//import dev.friesner.repos.UserDAO;
import dev.group3.model.Dog;
import dev.group3.repo.DogDAO;
import kotlin.Pair;

public class DogService {
	

	
	// ASSIGN THE DOA TO USE
	private DogDAO dd;
	
	public DogService(DogDAO dd) {
		this.dd = dd;
	}
	
	
	
	
	Dog dg = new Dog();
	List<Dog> lstDogs = new ArrayList<>();
	
	
	/**
	 * POST
	 * 
	 */
	
	
	// RETURNS DOG IF CREATED RETURNS NULL IF NOT CREATED ---> STORE OWNER AND STORE CLIENT
	public static Pair<Dog,String> postCreateNewDog(Dog dg) {
		
		Pair<Dog,String> dgPair = new Pair<Dog,String>(null,null);

//		Pair<Dog,String> dgPair = dd.postCreateNewDog(dg);

		return dgPair;
	}

	
	
	/**
	 * GET
	 * 
	 */
	
	
	// RETURNS ALL DOGS FROM DB  --> FOR THE OWNER
	public static  List<Dog> getAllDogs() {
		
		List<Dog> lstDogs = new ArrayList<>();
//		Dog dg = dd.getAllDogs();
		
		return lstDogs;
	}
	
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND CLIENT
	public static List<Dog> getAllDogsByUserId(int userName) {
		Dog dg = new Dog();
		List<Dog> lstDogs = new ArrayList<>();
		
//		Dog dg = dd.getAllDogsByUserId(userName);
		
		
		return lstDogs;
	}
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND STORE CLIENT
	public static Dog getDogById(int dogId) {
		Dog dg = new Dog();
		
//			Dog dg = dd.getDogByID(dogId);
		
		
		return dg;
	}
	
	

		
	
	
	

	
	
	
	
	/**
	 * PATCH
	 * 
	 */
	
	// RETURNS A DOG AND STRING MESSAGE ---> STORE OWNER AND STORE CLIENT
	public static Pair<Dog,String> patchUpdateDog(Dog dg) {
		
		Pair<Dog,String> dgPair = new Pair<Dog,String>(null,null);

//		Pair<Dog,String> dgPair = dd.patchUpdateDog(dg);

		
		
		return dgPair;
	}
	
	
	
	/**
	 * DELETE
	 * 
	 */
	
	
	public static String deleteDog(int dogId) {
		
		String dogDeleted = "";

//		String dogDeleted = = dd.deleteDog(dogId);

		
		
		return dogDeleted;
	}





















}
