package dev.group3.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.group3.model.Dog;
import dev.group3.repo.DogDAO;
import kotlin.Pair;

@ExtendWith(MockitoExtension.class)

public class DogServiceTests {
	
	
	// MOCKING THE DOG SERVICE LAYER
	@InjectMocks
	private static DogService dogService;
	
	@Mock
	private static DogDAO mockDogDao;
	
	
	@BeforeAll
	static void setUp() {
		
		dogService = new DogService(mockDogDao);
		
	}
	
	@AfterAll
	static void tearDown() {
		
	}
	
	
	
	/**
	 * POST
	 * 
	 */
	
	
	// RETURNS DOG IF CREATED RETURNS NULL IF NOT CREATED
	@Test
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
	@Test
	public static  List<Dog> getAllDogs() {
		
		List<Dog> lstDogs = new ArrayList<>();
		
//		Dog dg = dd.getAllDogs();
		
		return lstDogs;
	}
	
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND CLIENT
	@Test
	public static List<Dog> getAllDogsByUserId(int userName) {
		Dog dg = new Dog();
		List<Dog> lstDogs = new ArrayList<>();
		
//			Dog dg = dd.getAllDogsByUserId(userName);
		
		
		return lstDogs;
	}
	
	
	// RETURNS ONE DOG BY ID
	@Test
	public static Dog getDogById(int dogId) {
		Dog dg = new Dog();
		
//			Dog dg = dd.getDogByID(dogId);
		
		
		return dg;
	}
	
	

		
	
	
	

	
	
	
	
	/**
	 * PATCH
	 * 
	 */
	
	@Test
	public static Pair<Dog,String> patchUpdateDog(Dog dg) {
		
		Pair<Dog,String> dgPair = new Pair<Dog,String>(null,null);

//		Pair<Dog,String> dgPair = dd.patchUpdateDog(dg);

		
		
		return dgPair;
	}
	
	
	
	/**
	 * DELETE
	 * 
	 */
	
	@Test
	public static String deleteDog(int dogId) {
		
		String dogDeleted = "";

//		String dogDeleted = = dd.deleteDog(dogId);

		
		
		return dogDeleted;
	}

	
	
		
		
}
