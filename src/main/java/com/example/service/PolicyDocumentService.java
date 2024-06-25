package com.example.service;

import com.example.model.PolicyDocument;
import com.example.model.PolicyType;
import com.example.repo.PolicyDocumentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyDocumentService {

    private PolicyDocumentRepo policyDocumentRepository;

   
    public PolicyDocumentService(PolicyDocumentRepo policyDocumentRepo) {
        this.policyDocumentRepository = policyDocumentRepo;
    }

   

    public PolicyDocument createPolicyDocument(PolicyDocument policyDocument) {
        return policyDocumentRepository.save(policyDocument);
    }

   

    public List<PolicyDocument> getAllPolicyDocuments() {
        return policyDocumentRepository.findAll();
    }

    public Optional<PolicyDocument> getPolicyDocumentById(Long policyDetailsId) {
        return policyDocumentRepository.findById(policyDetailsId);
    }

    public List<PolicyDocument> getAutoPolicies() {
        return policyDocumentRepository.findAutoPolicies();
    }

    public List<PolicyDocument> getHealthPolicies() {
     
    	 return policyDocumentRepository.findHealthPolicies();
    }
    public List<PolicyDocument> getTermPolicies() {
        return policyDocumentRepository.findTermPolicies();
    }
   
}
