package com.example.service;

import com.example.model.Policy;
import com.example.repo.PolicyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @Mock
    private PolicyRepo policyRepository;

    @InjectMocks
    private PolicyService policyService;

    private Policy policy1;
    private Policy policy2;

    @BeforeEach
    void setUp() {
        policy1 = new Policy(1L, "Policy One","hello","hello");
        policy2 = new Policy(2L, "Policy Two","hello","hello");
    }

    @Test
    void testGetAllPolicies() {
        
        when(policyRepository.findAll()).thenReturn(Arrays.asList(policy1, policy2));

     
        List<Policy> policies = policyService.getAllPolicies();

      
        assertEquals(2, policies.size());
        assertTrue(policies.contains(policy1));
        assertTrue(policies.contains(policy2));

        verify(policyRepository, times(1)).findAll();
    }

    @Test
    void testGetPolicyById_ExistingId() {
     
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy1));

        Optional<Policy> result = policyService.getPolicyById(1L);

        assertTrue(result.isPresent());
        assertEquals(policy1, result.get());

        verify(policyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPolicyById_NonExistingId() {
      
        when(policyRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Policy> result = policyService.getPolicyById(3L);

        assertFalse(result.isPresent());

      
        verify(policyRepository, times(1)).findById(3L);
    }
}
