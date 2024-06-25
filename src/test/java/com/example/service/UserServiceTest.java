package com.example.service;

import com.example.model.User;
import com.example.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setEmailId("test@example.com");
        user.setPassword("password");
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getEmailId(), result.get(0).getEmailId());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(user.getEmailId(), result.get().getEmailId());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.saveUser(user);
        assertNotNull(result);
        assertEquals(user.getEmailId(), result.getEmailId());
    }

    @Test
    void testLogin() {
        when(userRepository.findByEmailIdAndPassword(anyString(), anyString())).thenReturn(user);

        User result = userService.login("test@example.com", "password");
        assertNotNull(result);
        assertEquals(user.getEmailId(), result.getEmailId());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
