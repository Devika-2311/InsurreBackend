package com.example.service;

import com.example.model.UserPolicy;
import com.example.repo.UserPolicyRepo;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPolicyServiceTest {

	@Mock
	private UserPolicyRepo userPolicyRepository;

	@InjectMocks
	private UserPolicyService userPolicyService;

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
		when(userPolicyRepository.save(any(UserPolicy.class))).thenReturn(userPolicy1);

		UserPolicy createdUserPolicy = userPolicyService.createUserPolicy(userPolicy1);

		assertEquals(userPolicy1.getUserPolicyId(), createdUserPolicy.getUserPolicyId());

		verify(userPolicyRepository, times(1)).save(userPolicy1);
	}

	@Test
	void testGetAllUserPolicies() {
		when(userPolicyRepository.findAll()).thenReturn(Arrays.asList(userPolicy1, userPolicy2));

		List<UserPolicy> userPolicies = userPolicyService.getAllUserPolicies();

		assertEquals(2, userPolicies.size());
		assertTrue(userPolicies.contains(userPolicy1));
		assertTrue(userPolicies.contains(userPolicy2));

		verify(userPolicyRepository, times(1)).findAll();
	}

	@Test
	void testGetUserPoliciesByUserId() {
		when(userPolicyRepository.findAllByUser_UserId(anyLong())).thenReturn(Arrays.asList(userPolicy1, userPolicy2));

		List<UserPolicy> userPolicies = userPolicyService.getUserPoliciesByUserId(1L);

		assertEquals(2, userPolicies.size());
		assertTrue(userPolicies.contains(userPolicy1));
		assertTrue(userPolicies.contains(userPolicy2));

		verify(userPolicyRepository, times(1)).findAllByUser_UserId(1L);
	}

	@Test
	void testGetUserPolicyByUserPolicyId() {
		when(userPolicyRepository.findById(anyLong())).thenReturn(Optional.of(userPolicy1));

		Optional<UserPolicy> userPolicy = userPolicyService.getUserPolicyByUserPolicyId(1L);

		assertTrue(userPolicy.isPresent());
		assertEquals(userPolicy1.getUserPolicyId(), userPolicy.get().getUserPolicyId());

		verify(userPolicyRepository, times(1)).findById(1L);
	}
	@Test
	void testIncrementPremiumCount() {
	
	    Long id = 1L;
	    UserPolicy policy = new UserPolicy(id, id, 0, id, "Policy A", 100, null, null, null, id, null, null);
	    when(userPolicyRepository.findById(id)).thenReturn(Optional.of(policy));
	    when(userPolicyRepository.save(any(UserPolicy.class))).thenAnswer(invocation -> invocation.getArgument(0));


	    UserPolicy updatedPolicy = userPolicyService.incrementPremiumCount(id);


	    assertEquals(101, updatedPolicy.getPremiumCount());
	    verify(userPolicyRepository, times(1)).findById(id);
	    verify(userPolicyRepository, times(1)).save(policy); 
	}


	@Test
	void testIncrementPremiumCountPolicyNotFound() {
	
	    Long id = 3L;
	    when(userPolicyRepository.findById(id)).thenReturn(Optional.empty());

	    UserPolicy result = userPolicyService.incrementPremiumCount(id);

	    assertNull(result, "Expected result to be null when policy is not found");
	    verify(userPolicyRepository, times(1)).findById(id);
	    verify(userPolicyRepository, never()).save(any());
	}

}
