package com.example.service;
import com.example.model.PolicyDocument;
import com.example.model.PolicyType;
import com.example.repo.PolicyDocumentRepo;
import com.example.service.PolicyDocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PolicyDocumentServiceTest {

    @Mock
    private PolicyDocumentRepo policyDocumentRepo;

    @InjectMocks
    private PolicyDocumentService policyDocumentService;

    private PolicyDocument policyDocument;

    @BeforeEach
    public void setUp() {
        policyDocument = new PolicyDocument();
        policyDocument.setPolicyDetailsId(1L);
        policyDocument.setPolicyType(PolicyType.TERM);
    }

  

    @Test
    public void testCreatePolicyDocument() {
        when(policyDocumentRepo.save(any(PolicyDocument.class))).thenReturn(policyDocument);

        PolicyDocument result = policyDocumentService.createPolicyDocument(policyDocument);

        assertEquals(policyDocument, result);
    }

    @Test
    public void testGetTermPolicies() {
        when(policyDocumentRepo.findTermPolicies()).thenReturn(Collections.singletonList(policyDocument));

        List<PolicyDocument> result = policyDocumentService.getTermPolicies();

        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }

    @Test
    public void testGetAllPolicyDocuments() {
        when(policyDocumentRepo.findAll()).thenReturn(Collections.singletonList(policyDocument));

        List<PolicyDocument> result = policyDocumentService.getAllPolicyDocuments();

        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }

    @Test
    public void testGetPolicyDocumentById() {
        when(policyDocumentRepo.findById(1L)).thenReturn(Optional.of(policyDocument));

        Optional<PolicyDocument> result = policyDocumentService.getPolicyDocumentById(1L);

        assertTrue(result.isPresent());
        assertEquals(policyDocument, result.get());
    }

    @Test
    public void testGetAutoPolicies() {
        policyDocument.setPolicyType(PolicyType.AUTO);
        when(policyDocumentRepo.findAutoPolicies()).thenReturn(Collections.singletonList(policyDocument));

        List<PolicyDocument> result = policyDocumentService.getAutoPolicies();

        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }

    @Test
    public void testGetHealthPolicies() {
        policyDocument.setPolicyType(PolicyType.HEALTH);
        when(policyDocumentRepo.findHealthPolicies()).thenReturn(Collections.singletonList(policyDocument));

        List<PolicyDocument> result = policyDocumentService.getHealthPolicies();

        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }
}
