package dev.group3.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import dev.group3.model.Reservation;
import dev.group3.model.User;
import dev.group3.model.enums.ResStatusType;
import dev.group3.repo.ReservationDAO;
import dev.group3.repo.UserDAO;
import dev.group3.util.ActiveUserSessions;
import dev.group3.util.MockDataSet;
import kotlin.Pair;

public class ReservationServiceTests {
    
    // Init
    private static ReservationService resService;
    
    // Mock DAO
    private static UserDAO mockUserDAO;
    private static ReservationDAO mockResDAO;
   
    // Mock Database Data
    private static List<User> mockUsers;
    private static List<Reservation> mockReses;
    
    /*
     * === SETUP ===
     */
    
    @BeforeAll
    public static void setup() {
        mockUserDAO = mock(UserDAO.class);
        mockResDAO = mock(ReservationDAO.class);
        resService = new ReservationService(mockUserDAO, mockResDAO);
        refreshMockData();
    }
    
    @AfterEach
    public void cleanup() {
        ActiveUserSessions.clearAllActiveSessions();
        refreshMockData();
    }
    
    /*
     * === SETUP UTILITY ===
     */
    
    public static void refreshMockData() {
        // Getting refreshed mock data
        mockUsers = MockDataSet.getUserTestSet();
        mockReses = MockDataSet.getReservationTestSet();
        
        // Mocking DAO behavior
        // Users
        for (User user: mockUsers) {
            when(mockUserDAO.getUserByUsername(user.getEmail())).thenReturn(user);
        }
        
        // Reservations
        when(mockResDAO.getAllReservations()).thenReturn(mockReses);
        for (User user: mockUsers) {
            when(mockResDAO.getAllRservationsByUsername(user.getEmail())).thenReturn(MockDataSet.getFilteredReservationDataSet(user.getEmail()));
        }
        for (Reservation res: mockReses) {
            when(mockResDAO.getReservationById(res.getId())).thenReturn(res);
        }
    }
    
    /*
     * === createNewReservation TESTS ===
     */
    
    @ParameterizedTest
    @MethodSource("cnr_invalidInputs")
    public void cnr_invalidInputs_nullBlank_400null(String username, Reservation resData, String token) {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation(username, resData, token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    private static Stream<Arguments> cnr_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, new Reservation(), "a"));  // invalid username
        arguments.add(Arguments.of("a", null, "a"));                // invalid resData
        arguments.add(Arguments.of("a", new Reservation(), null));  // invalid password
        arguments.add(Arguments.of("", new Reservation(), "a"));    // invalid username
        arguments.add(Arguments.of("a", new Reservation(), ""));    // invalid password
        return arguments.stream();
    }
    
    @Test
    public void cnr_invalidInputs_noResData_400null() {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("a", new Reservation(), "a");
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnr_invalidInputs_resDataIsInvalid_400null() {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("a", MockDataSet.getDefaultNewReservationData().setDogId(-1), "a");
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnr_userDoesNotExist_404null() {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("ghostUser", MockDataSet.getDefaultNewReservationData(), "a");
        Object[] expectedResults = {null, 404};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};

        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnr_dogDoesNotExist_404null() {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("owner", MockDataSet.getDefaultNewReservationData().setDogId(100), "a");
        Object[] expectedResults = {null, 404};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnr_userNotInActiveSession_401null() {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("owner", MockDataSet.getDefaultNewReservationData(), "notActiveToken");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnr_userNotAuthorized_403null() {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("email2", MockDataSet.getDefaultNewReservationData(), token);
        Object[] expectedResults = {null, 403};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnr_validInputs_200Reservation() {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser("email1");
        Reservation resData = MockDataSet.getDefaultNewReservationData();
        
        // Running test
        Pair<Reservation, Integer> actualRes = resService.createReservation("email1", resData, token);
        Object[] expectedResults = {resData, 200};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    // === getAllReservations TESTS ===
    
    @ParameterizedTest
    @NullAndEmptySource
    public void gar_invalidInputs_nullBlank_400null(String token) {
            // Running test
           Pair<List<Reservation>, Integer> actualRes = resService.getAllReservations("");
           Object[] expectedResults = {null, 400};
           Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
           
           // Assertions.
           assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void gar_userNotInActiveSession_401null() {
        // Running test       
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservations("NotActive");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions.
        assertArrayEquals(expectedResults, actualResults);
        
    }
    
	@Test
    public void gar_userNotAuthorized_403null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservations(token);
        Object[] expectedResults = {null, 403};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void gar_userAuthorized_200Requests() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("owner");

        // Running test
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservations(token);
        Object[] expectedResults = {mockReses, 200};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    // === getAllReservationsByUsername TESTS ===
    
    @ParameterizedTest
    @MethodSource("garbu_invalidInputs")
    public void garbu_invaldInputs_nullBlank_400null(String username, String token) {
        // Running test
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservationsByUsername(username, token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> garbu_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, "a")); // invalid username
        arguments.add(Arguments.of("a", null)); // invalid password
        arguments.add(Arguments.of("", "a"));   // invalid username
        arguments.add(Arguments.of("a", ""));   // invalid password
        return arguments.stream();
    }
    
    @Test
    public void garbu_userNotInActiveSession_401null() {
        // Running test
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservationsByUsername("a", "notActive");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void garbu_userNotAuthorized_403null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservationsByUsername("email2", token);
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @ParameterizedTest
    @MethodSource("garbu_validInputs")
    public void garbu_userIsAuthorized_200Reservations(String username, String userUsername) {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser(userUsername);
        
        // Running test
        Pair<List<Reservation>, Integer> actualRes = resService.getAllReservationsByUsername(username, token);
        Object[] expectedResults = {MockDataSet.getFilteredReservationDataSet(username), 200};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> garbu_validInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        for (User user: mockUsers) {
            // Users can get their own
            arguments.add(Arguments.of(user.getEmail(), user.getEmail()));
            // Owner can get anyone
            arguments.add(Arguments.of(user.getEmail(), mockUsers.get(0).getEmail()));
        }
        return arguments.stream();
    }
    
    // === getReservationById TESTS ===
    
    @ParameterizedTest
    @MethodSource("grbi_invalidInputs")
    public void grbi_invalidInputs_400null(Integer rid, String token) {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.getReservationById(rid, token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> grbi_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, "a"));    // invalid reservation id
        arguments.add(Arguments.of(1, null));      // invalid password
        arguments.add(Arguments.of(-1, "a"));      // invalid reservation id
        arguments.add(Arguments.of(1, ""));        // invalid password
        return arguments.stream();
    }
    
    @Test
    public void grbi_userNotInActiveSession_401null() {
        // Running test
        Pair<Reservation, Integer> actualRes = resService.getReservationById(1, "notActive");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void grbi_userNotAuthorized_403null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test
        Pair<Reservation, Integer> actualRes = resService.getReservationById(3, token);
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @ParameterizedTest
    @MethodSource("gubu_validInputs")
    public void grbi_userIsAuthorized_200Reseration(int resIndex, Integer rid, String userUsername) {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser(userUsername);
        
        // Running test
        Pair<Reservation, Integer> actualRes = resService.getReservationById(rid, token);
        Object[] expectedResults = {mockReses, 200};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> gubu_validInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        int resIndex = 0;
        for (Reservation res: mockReses) {
            // Users can get their own
            arguments.add(Arguments.of(resIndex, res.getId(), res.getUserEmail()));
            // Owner can get anyone
            arguments.add(Arguments.of(resIndex, res.getId(), mockUsers.get(0).getEmail()));
            resIndex++;
        }
        return arguments.stream();
    }
    
    // === updateReservationById TESTS ===
    
    @ParameterizedTest
    @MethodSource("urbi_invalidInputs")
    public void urbi_invalidInputs_nullBlank_400null(Integer rid, Reservation resData, String token) {
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(rid, resData, token);

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(username, rid, resData, token);
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(resData, token);

        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> urbi_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, new Reservation(), "a"));     // invalid reservation id
        arguments.add(Arguments.of(1, null, "a"));                     // invalid resData
        arguments.add(Arguments.of(1, new Reservation(), null));       // invalid password
        arguments.add(Arguments.of(-1, new Reservation(), "a"));       // invalid reservation id
        arguments.add(Arguments.of(1, new Reservation(), ""));         // invalid password
        return arguments.stream();
    }
    
    @Test
    public void urbi_invalidInputs_resDataIsNull_400null() {
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(1, new Reservation(), "a");

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("a", 1, new Reservation(), "a");
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById( new Reservation(), "a");

        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void urbi_userNotInActiveSession_401null() {
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(1, new Reservation().setStatus(ResStatusType.CHECKEDIN), "notActiveToken");

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("email1", 1, new Reservation().setStatus(ResStatusType.CHECKEDIN), "notActiveToken");
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(new Reservation().setStatus(ResStatusType.CHECKEDIN), "notActiveToken");

        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void urbi_userNotAuthorized_403null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(3, new Reservation().setStatus(ResStatusType.CHECKEDIN), token);

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("email2", 3, new Reservation().setStatus(ResStatusType.CHECKEDIN), token);
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(new Reservation().setStatus(ResStatusType.CHECKEDIN), token);

        Object[] expectedResults = {null, 403};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void urbi_userIsActive_userDoesNotExist_503null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("ghostUser");
        
        // Running test
        Pair<Reservation, Integer> actualRes = resService.updateReservationById(1, new Reservation().setStatus(ResStatusType.CHECKEDIN), token);

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("ghostUser", 1, new Reservation().setStatus(ResStatusType.CHECKEDIN), token);
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(new Reservation().setStatus(ResStatusType.CHECKEDIN), token);

        Object[] expectedResults = {null, 503};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void urbi_userIsActive_reservationDoesNotExist_404null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(100, new Reservation().setStatus(ResStatusType.CHECKEDIN), token);

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("email1", 100, new Reservation().setStatus(ResStatusType.CHECKEDIN), token);
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(new Reservation().setStatus(ResStatusType.CHECKEDIN), token);

        Object[] expectedResults = {null, 404};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    // TODO: This is probably no longer an accurate test -> needs updated to reflect proper changes
    @ParameterizedTest
    @MethodSource("urbi_invalidStatusInputs")
    public void urbi_usernotAuthorized_invalidStatus_403null(String userusername, ResStatusType newStatus) {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser(userusername);
        
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(1, new Reservation().setStatus(newStatus), token);

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("email1", 1, new Reservation().setStatus(newStatus), token);
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(new Reservation().setStatus(newStatus), token);

        Object[] expectedResults = {null, 404};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> urbi_invalidStatusInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of("owner", ResStatusType.REGISTERED));
        arguments.add(Arguments.of("owner", ResStatusType.CANCELLED));
        arguments.add(Arguments.of("email1", ResStatusType.REGISTERED));
        arguments.add(Arguments.of("email1", ResStatusType.CHECKEDIN));
        arguments.add(Arguments.of("email1", ResStatusType.CHECKEDOUT));
        return arguments.stream();
    }
    
    // TODO: This is probably no longer an accurate test -> needs updated to reflect proper changes
    @ParameterizedTest
    @MethodSource("urbi_validInputs")
    public void urbi_isAuthorized_200Reservation(String userusername, ResStatusType newStatus) {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser(userusername);
        
        // Running test

        Pair<Reservation, Integer> actualRes = resService.updateReservationById(1, new Reservation().setStatus(newStatus), token);

//        Pair<Reservation, Integer> actualRes = resService.updateReservationById("email1", 1, new Reservation().setStatus(newStatus), token);
//        Pair<Reservation, Integer> actualRes = resService.updateReservationById(new Reservation().setStatus(newStatus), token);
//>>>>>>> jfriesner_reservations
        Object[] expectedResults = {null, 404};
        Object[] actualResults = {actualRes.getFirst(), actualRes.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> urbi_validInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of("owner", ResStatusType.CHECKEDIN));
        arguments.add(Arguments.of("owner", ResStatusType.CHECKEDOUT));
        arguments.add(Arguments.of("email1", ResStatusType.CANCELLED));
        return arguments.stream();
    }
}
