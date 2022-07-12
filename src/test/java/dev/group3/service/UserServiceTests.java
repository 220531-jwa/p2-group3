package dev.group3.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.group3.model.User;
import dev.group3.model.enums.UserType;
import dev.group3.repo.UserDAO;
import dev.group3.util.ActiveUserSessions;
import dev.group3.util.MockDataSet;
import kotlin.Pair;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    
    // Init
    private static UserService userService;
    
    // Mock DAO
    private static UserDAO mockUserDAO;
    
    // Mock Database Data
    private static List<User> mockUsers;
    
    /*
     * === SETUP ===
     */
    
    @BeforeAll
    public static void setup() {
        mockUserDAO = mock(UserDAO.class);
        userService = new UserService(mockUserDAO);
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
        
        // Mocking DAO behavior
        for (User user: mockUsers) {
            when(mockUserDAO.getUserByUsername(user.getEmail())).thenReturn(user);
        }
    }
    
    /*
     * === loginUserWithCredentials TESTS ===
     */
    
    @ParameterizedTest
    @MethodSource("luwc_invalidInputs")
    public void luwc_invalidInputs_400null(String username, String password) {
        // Running test
        Pair<User, Integer> actualUser = userService.loginUserWithCredentials(username, password);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    private static Stream<Arguments> luwc_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, "a")); // invalid username
        arguments.add(Arguments.of("a", null)); // invalid password
        arguments.add(Arguments.of("", "a"));   // invalid username
        arguments.add(Arguments.of("a", ""));   // invalid password
        return arguments.stream();
    }
    
    @Test
    public void luwc_userDoesNotExist_404null() {
        // Running test
        Pair<User, Integer> actualUser = userService.loginUserWithCredentials("ghostUser", "pass");
        Object[] expectedResults = {null, 404};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void luwc_userCredentialsDoNotMatch_401null() {
        // Running test
        Pair<User, Integer> actualUser = userService.loginUserWithCredentials("owner", "badPass");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @ParameterizedTest
    @MethodSource("luwc_validInputs")
    public void luwc_validCredentials_200UserWithTokenPassword(int userIndex, String username, String password) {
        // Running test
        Pair<User, Integer> actualUser = userService.loginUserWithCredentials(username, password);
        Object[] expectedResults = {mockUsers.get(userIndex), 200};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
        assertNotEquals(password, actualUser.getFirst().getPswd());
    }
    private static Stream<Arguments> luwc_validInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        int userIndex = 0;
        for (User user: mockUsers) {
            arguments.add(Arguments.of(userIndex++, user.getEmail(), user.getPswd()));
        }
        return arguments.stream();
    }
    
    // === logoutUser TESTS ===
    
    @ParameterizedTest
    @NullAndEmptySource
    public void lu_invalidInputs_400(String token) {
        // Running test
        int actualStatus = userService.logoutUser(token);
        
        // Assertions
        assertEquals(400, actualStatus);
    }
    
    @Test
    public void lu_tokenNoAssociatedWithActiveSession_404() {
        // Running test
        int actualStatus = userService.logoutUser("notActiveToken");
        
        // Assertions
        assertEquals(404, actualStatus);
    }
    
    @Test
    public void lu_validToken_200() {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser("owner");
        
        // Running test
        int actualStatus = userService.logoutUser(token);
        
        // Assertions
        assertEquals(200, actualStatus);
    }
    
    // === createNewUser TESTS ===
    
    @Test
    public void cnu_invalidInputs_null_400null() {
        // Running test
        Pair<User, Integer> actualUser = userService.createNewUser(null);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
    
    @Test
    public void cnu_invalidInputs_missingRequiredFields_400null() {
        // Running test
        Pair<User, Integer> actualUser = userService.createNewUser(new User());
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);        
    }
    
    @ParameterizedTest
    @MethodSource("cnu_invalidInputs")
    public void cnu_invalidInputs_requiredFieldsAreInvalid_400null(User newUser) {
        // Running test
        Pair<User, Integer> actualUser = userService.createNewUser(newUser);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);   
    }
    private static Stream<Arguments> cnu_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(MockDataSet.getDefaultNewUserData().setEmail("email")));
        arguments.add(Arguments.of(MockDataSet.getDefaultNewUserData().setFunds(-1.00)));
        arguments.add(Arguments.of(MockDataSet.getDefaultNewUserData().setFunds(10000.00)));
        arguments.add(Arguments.of(MockDataSet.getDefaultNewUserData().setPhoneNumber("123")));
        arguments.add(Arguments.of(MockDataSet.getDefaultNewUserData().setUserType(UserType.OWNER)));
        return arguments.stream();
    }
    
    @Test
    public void cnu_validInputs_200UserWithActiveSessionAndTokenPassword() {
        // Init mock test data
        User newUser = MockDataSet.getDefaultNewUserData();
        String oldPassword = newUser.getPswd();
        when(mockUserDAO.createNewUser(newUser)).thenReturn(newUser);
        
        // Running test
        Pair<User, Integer> actualUser = userService.createNewUser(newUser);
        Object[] expectedResults = {
                // Service Return
                newUser, 200,
                // Various Results
                true
                };
        Object[] actualResults = {
                // Service Return
                actualUser.getFirst(), actualUser.getSecond(),
                // Various Results
                ActiveUserSessions.isActiveUser(actualUser.getFirst().getPswd())
                };
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
        assertNotEquals(oldPassword, actualUser.getFirst().getPswd());
    }
    
    // === getUserByUsername TESTS ===
    
    @ParameterizedTest
    @MethodSource("gubu_invalidInputs")
    public void gubu_invalidInputs_400null(String username, String token) {
        // Running test
        Pair<User, Integer> actualUser = userService.getUserByUsername(username, token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> gubu_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, "a")); // invalid username
        arguments.add(Arguments.of("a", null)); // invalid password
        arguments.add(Arguments.of("", "a"));   // invalid username
        arguments.add(Arguments.of("a", ""));   // invalid password
        return arguments.stream();
    }
    
    @Test
    public void gubu_userNotInActiveSession_401null() {
        // Running test
        Pair<User, Integer> actualUser = userService.getUserByUsername("owner", "notActiveToken");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void gubu_userNotAuthorized_403null() {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test
        Pair<User, Integer> actualUser = userService.getUserByUsername("owner", token);
        Object[] expectedResults = {null, 403};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @ParameterizedTest
    @MethodSource("gubu_validInputs")
    public void gubu_userisAuthorized_200User(int userIndex, String username, String userUsername) {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser(userUsername);
        
        // Running test
        Pair<User, Integer> actualUser = userService.getUserByUsername(username, token);
        Object[] expectedResults = {mockUsers.get(userIndex), 200};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> gubu_validInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        int userIndex = 0;
        for (User user: mockUsers) {
            // Users can get their own
            arguments.add(Arguments.of(userIndex, user.getEmail(), user.getEmail()));
            // Owner can get anyone
            arguments.add(Arguments.of(userIndex, user.getEmail(), mockUsers.get(0).getEmail()));
            userIndex++;
        }
        return arguments.stream();
    }
    
    // updateUserByUsername TESTS ===
    
    @ParameterizedTest
    @MethodSource("uubu_invalidInputs")
    public void uubu_invalidInputs_nullBlank_400null(String username, User userData, String token) {
        // Running test
        Pair<User, Integer> actualUser = userService.updateUserByUsername(username, userData, token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    private static Stream<Arguments> uubu_invalidInputs() {
        List<Arguments> arguments = new ArrayList<Arguments>();
        arguments.add(Arguments.of(null, new User(), "a")); // invalid username
        arguments.add(Arguments.of("a", null, "a"));        // invalid userData
        arguments.add(Arguments.of("a", new User(), null)); // invalid password
        arguments.add(Arguments.of("", new User(), "a"));   // invalid username
        arguments.add(Arguments.of("a", new User(), ""));   // invalid password
        return arguments.stream();
    }
    
    @Test
    public void uubu_invalidInputs_noUserDataInputs_400null() {
     // Init mock data set
        String token = ActiveUserSessions.addActiveUser("a");
        
        // Running test
        Pair<User, Integer> actualUser = userService.updateUserByUsername("a", new User(), token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void uubu_invalidInputs_userDataIsInvalid_400null() {
        // Init mock data set
        String token = ActiveUserSessions.addActiveUser("a");
        
        // Running test
        Pair<User, Integer> actualUser = userService.updateUserByUsername("a", new User().setPhoneNumber("123"), token);
        Object[] expectedResults = {null, 400};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void uubu_userNotInActiveSession_401null() {
        // Running test
        Pair<User, Integer> actualUser = userService.updateUserByUsername("email1", new User().setFirstName("newName"), "notActiveToken");
        Object[] expectedResults = {null, 401};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void uubu_userNotAuthorized_403null() {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser("email1");
        
        // Running test
        Pair<User, Integer> actualUser = userService.updateUserByUsername("email2", new User().setFirstName("newName"), token);
        Object[] expectedResults = {null, 403};
        Object[] actualResults = {actualUser.getFirst(), actualUser.getSecond()};
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults); 
    }
    
    @Test
    public void uubu_userIsAuthorized_200UpdatedUser() {
        // Init mock test data
        String token = ActiveUserSessions.addActiveUser("email1");
        User userData = new User().setFirstName("newName");
        when(mockUserDAO.updateUserByUsername(userData)).thenReturn(mockUsers.get(1).setFirstName("newName"));
        
        // Running test
        Pair<User, Integer> actualUser = userService.updateUserByUsername("email1", userData, token);
        Object[] expectedResults = {
                // Service result
                mockUsers.get(1), 200,
                // Updated user information
                userData.getFirstName()
                };
        Object[] actualResults = {
                // Service result
                actualUser.getFirst(), actualUser.getSecond(),
                // Updated User information
                actualUser.getFirst().getFirstName()
                };
        
        // Assertions
        assertArrayEquals(expectedResults, actualResults);
    }
}
