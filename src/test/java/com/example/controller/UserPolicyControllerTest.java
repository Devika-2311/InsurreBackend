package com.example.controller;

import com.example.model.UserPolicy;
import com.example.service.UserPolicyService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPolicyControllerTest {

    @Mock
    private UserPolicyService userPolicyService;

    @InjectMocks
    private UserPolicyController userPolicyController;

    private UserPolicy userPolicy1;
    private UserPolicy userPolicy2;

    @BeforeEach
    void setUp() {
        userPolicy1 = new UserPolicy(); 
        userPolicy1.setUserPolicyID(1L);
        userPolicy2 = new UserPolicy(); 
        userPolicy2.setUserPolicyID(2L);
    }

    @Test
    void testCreateUserPolicy() {
      
        when(userPolicyService.createUserPolicy(any(UserPolicy.class))).thenReturn(userPolicy1);

        Long userPolicyId = userPolicyController.createUserPolicy(userPolicy1);

        assertEquals(userPolicy1.getUserPolicyId(), userPolicyId);

        verify(userPolicyService, times(1)).createUserPolicy(userPolicy1);
    }

    @Test
    void testGetPoliciesById() {
      
        when(userPolicyService.getUserPoliciesByUserId(anyLong())).thenReturn(Arrays.asList(userPolicy1, userPolicy2));

        List<UserPolicy> userPolicies = userPolicyController.getPoliciesById(1L);

        assertEquals(2, userPolicies.size());
        assertTrue(userPolicies.contains(userPolicy1));
        assertTrue(userPolicies.contains(userPolicy2));

        verify(userPolicyService, times(1)).getUserPoliciesByUserId(1L);
    }

    @Test
    void testGetAllUserPolicies() {
      
        when(userPolicyService.getAllUserPolicies()).thenReturn(Arrays.asList(userPolicy1, userPolicy2));

        List<UserPolicy> userPolicies = userPolicyController.getAllUserPolicies();
        assertEquals(2, userPolicies.size());
        assertTrue(userPolicies.contains(userPolicy1));
        assertTrue(userPolicies.contains(userPolicy2));

        verify(userPolicyService, times(1)).getAllUserPolicies();
    }

    @Test
    void testGetUserPoliciesByUserId() {
      
        when(userPolicyService.getUserPoliciesByUserId(anyLong())).thenReturn(Arrays.asList(userPolicy1, userPolicy2));

        List<UserPolicy> userPolicies = userPolicyController.getUserPoliciesByUserId(1L);

        assertEquals(2, userPolicies.size());
        assertTrue(userPolicies.contains(userPolicy1));
        assertTrue(userPolicies.contains(userPolicy2));

        verify(userPolicyService, times(1)).getUserPoliciesByUserId(1L);
    }
    @Test
    void testIncrementPremiumCount() {
   
        Long id = 1L;
        UserPolicy policy = new UserPolicy(id, id, 0, id, "Policy A", 100, null, null, null, id, null, null);
        when(userPolicyService.incrementPremiumCount(id)).thenReturn(policy);

        ResponseEntity<UserPolicy> response = userPolicyController.incrementPremiumCount(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100, response.getBody().getPremiumCount());
        verify(userPolicyService, times(1)).incrementPremiumCount(id);
    }

}
