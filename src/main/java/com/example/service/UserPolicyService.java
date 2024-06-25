package com.example.service;

import com.example.model.UserPolicy;
import com.example.repo.UserPolicyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPolicyService {

    private final UserPolicyRepo userPolicyRepository;

    @Autowired
    public UserPolicyService(UserPolicyRepo userPolicyRepository) {
        this.userPolicyRepository = userPolicyRepository;
    }

    public UserPolicy createUserPolicy(UserPolicy userPolicy) {
        return userPolicyRepository.save(userPolicy);
    }

    public List<UserPolicy> getAllUserPolicies() {
        return userPolicyRepository.findAll();
    }

    public List<UserPolicy> getUserPoliciesByUserId(Long userId) {
        return userPolicyRepository.findAllByUser_UserId(userId);
    }

    public Optional<UserPolicy> getUserPolicyByUserPolicyId(Long userPolicyId) {
        return userPolicyRepository.findById(userPolicyId);
    }
    public Optional<UserPolicy> getPolicyById(Long id) {
        return userPolicyRepository.findById(id);
    }
    public UserPolicy createOrUpdatePolicy(UserPolicy userPolicy) {
        return userPolicyRepository.save(userPolicy);
    }
    public UserPolicy incrementPremiumCount(Long id) {
        Optional<UserPolicy> optionalUserPolicy = getPolicyById(id);
        if (optionalUserPolicy.isPresent()) {
            UserPolicy userPolicy = optionalUserPolicy.get();
            userPolicy.setPremiumCount(userPolicy.getPremiumCount() + 1);
            return createOrUpdatePolicy(userPolicy);
        } else {
            return null; // Or Optional.empty()
        }
    }
}
