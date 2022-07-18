package dev.group3.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.group3.model.Dog;
import dev.group3.model.User;
import dev.group3.repo.DogDAO;
import dev.group3.util.ActiveUserSessions;
import dev.group3.util.MockDataSet;
import kotlin.Pair;

@ExtendWith(MockitoExtension.class)

public class DogServiceTests {
	
	
	// Init
	private static DogService dogService;
	
	//Mock DAO
	private static DogDAO mockDogDao;
	
	//Mocking Database Data
	private static List<Dog> mockDogs;
	private static List<User> mockUsers;
	@BeforeAll
	public static void setUp() {
		mockDogDao = mock(DogDAO.class);
		dogService = new DogService(mockDogDao);
		refreshMockData();
	}
	
	@AfterEach
	public void tearDown() {
		ActiveUserSessions.clearAllActiveSessions();
		refreshMockData();
	}
	
    /*
     * === SETUP UTILITY ===
     */
    
    public static void refreshMockData() {
        // Getting refreshed mock data
        mockDogs = MockDataSet.getDogTestSet();
        mockUsers = MockDataSet.getUserTestSet();
    }
	
	public static Stream<Dog> dogStream(){
		return mockDogs.stream();
	}
	public static Stream<User> userStream(){
		return mockUsers.stream();
	}
	
	public static List<Dog> userDogs(User user){
		List<Dog> userDogs = new ArrayList<>();
		for (int i = 0; i < mockDogs.size(); i++) {
			if(mockDogs.get(i).getUser_email() == user.getEmail()) {
				userDogs.add(mockDogs.get(i));
			}
		}
		
		return userDogs;
	}
	
	/**
	 * POST
	 * 
	 */
	
	
	// RETURNS DOG IF CREATED RETURNS NULL IF NOT CREATED
    @ParameterizedTest
    @MethodSource("dogStream")
	public void postCreateNewDogTest(Dog dg) {
    	when(mockDogDao.createDog(dg)).thenReturn(true);
		String token = ActiveUserSessions.addActiveUser("email1");
		boolean result = dogService.postCreateNewDog(dg, token);

		assertEquals(true, result);
	}
	
	
	
	/**
	 * GET
	 * 
	 */
	
	
	// RETURNS ALL DOGS FROM DB  --> FOR THE OWNER
	@Test
	public void getAllDogsTest() {
		when(mockDogDao.getAllDogs()).thenReturn(mockDogs);
		String token = ActiveUserSessions.addActiveUser("email1");
		List<Dog> lstDogs = dogService.getAllDogs(token);
		
		assertEquals(mockDogs, lstDogs);
	}
	
	
	
	// RETURNS ONE DOG BY ID --> STORE OWNER AND CLIENT
    @ParameterizedTest
    @MethodSource("dogStream")
	public void getAllDogsByUsernameTest(int userName) {
		Dog dg = new Dog();
		List<Dog> lstDogs = new ArrayList<>();
		

		

	}
	
	
	// RETURNS ONE DOG BY ID
    @ParameterizedTest
    @MethodSource("userStream")
	public void getDogsByUsernameTest(User user) {
    	
		when(mockDogDao.getAllDogsByUsername(user.getEmail())).thenReturn(userDogs(user));
		String token = ActiveUserSessions.addActiveUser("email1");
		List <Dog> dg = dogService.getAllDogsByUsername(user.getEmail(), token);
		
		assertEquals(userDogs(user), dg);
	}
	
	

		
	
	
	

	
	
	
	
	/**
	 * PATCH
	 * 
	 */
	
	@Test
	public void patchUpdateDog(Dog dg) {
		


	}
	
	
	
	/**
	 * DELETE
	 * 
	 */
	
	@Test
	public void deleteDog() {
		

	}

	
	
		
		
}
