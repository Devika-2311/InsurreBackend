package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(); // Initialize User fields
        user1.setUserId(1L);
        user2 = new User(); // Initialize User fields
        user2.setUserId(2L);
    }

    @Test
    void testGetAllUsers() {
        
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

      
        List<User> users = userController.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));

        
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
       
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user1));

        
        ResponseEntity<User> response = userController.getUserById(1L);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1, response.getBody());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        
        ResponseEntity<User> response = userController.getUserById(1L);

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testLogin_Success() {
       
        when(userService.login(anyString(), anyString())).thenReturn(user1);

        ResponseEntity<Object> response = userController.login("email@example.com", "password");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());

        verify(userService, times(1)).login("email@example.com", "password");
    }

    @Test
    void testLogin_Failure() {
   
        when(userService.login(anyString(), anyString())).thenReturn(null);

        ResponseEntity<Object> response = userController.login("email@example.com", "wrongpassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());

        verify(userService, times(1)).login("email@example.com", "wrongpassword");
    }

    @Test
    void testCreateUser() {
        
        when(userService.saveUser(any(User.class))).thenReturn(user1);

        ResponseEntity<User> response = userController.createUser(user1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user1, response.getBody());

        verify(userService, times(1)).saveUser(user1);
    }

    @Test
    void testUpdateUser_Success() {
     
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user1));
        when(userService.saveUser(any(User.class))).thenReturn(user1);

        ResponseEntity<User> response = userController.updateUser(1L, user1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1, response.getBody());

        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).saveUser(user1);
    }

    @Test
    void testUpdateUser_NotFound() {
   
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUser(1L, user1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testDeleteUser_Success() {
       
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user1));

      
        ResponseEntity<Void> response = userController.deleteUser(1L);

        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

       
        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
       
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userService, times(1)).getUserById(1L);
    }
}
