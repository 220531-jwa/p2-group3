package dev.group3.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
import org.mockito.junit.jupiter.MockitoExtension;

import dev.group3.model.User;
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
        userService = new UserService();
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
        when(mockUserDAO.getAllUsers()).thenReturn(mockUsers);
        for (User user: mockUsers) {
            when(mockUserDAO.getUserByUsername(user.getEmail())).thenReturn(user);
        }
    }
    
    //
    
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
}
