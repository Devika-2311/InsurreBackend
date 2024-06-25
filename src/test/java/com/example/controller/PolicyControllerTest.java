package com.example.controller;

import com.example.model.Policy;
import com.example.service.PolicyService;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyControllerTest {

    @Mock
    private PolicyService policyService;

    @InjectMocks
    private PolicyController policyController;

    private Policy policy1;
    private Policy policy2;

    @BeforeEach
    void setUp() {
        policy1 = new Policy(1L, "Policy One", "Description One", "Details One");
        policy2 = new Policy(2L, "Policy Two", "Description Two", "Details Two");
    }

    @Test
    void testGetAllPolicies() {
       
        when(policyService.getAllPolicies()).thenReturn(Arrays.asList(policy1, policy2));

      
        List<Policy> policies = policyController.getAllPolicies();

       
        assertEquals(2, policies.size());
        assertTrue(policies.contains(policy1));
        assertTrue(policies.contains(policy2));

        verify(policyService, times(1)).getAllPolicies();
    }

    @Test
    void testGetPolicyById_ExistingId() {
       
        when(policyService.getPolicyById(1L)).thenReturn(Optional.of(policy1));

        ResponseEntity<Policy> response = policyController.getPolicyById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(policy1, response.getBody());

        verify(policyService, times(1)).getPolicyById(1L);
    }

    @Test
    void testGetPolicyById_NonExistingId() {
    
        when(policyService.getPolicyById(3L)).thenReturn(Optional.empty());

        ResponseEntity<Policy> response = policyController.getPolicyById(3L);

       
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

       
        verify(policyService, times(1)).getPolicyById(3L);
    }
}
