package com.example.controller;

import com.example.model.PolicyDocument;
import com.example.model.PolicyType;
import com.example.model.UserPolicy;
import com.example.service.PolicyDocumentService;
import com.example.service.UserPolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PolicyDocumentControllerTest {

    @Mock
    private PolicyDocumentService policyDocumentService;

    @Mock
    private UserPolicyService userPolicyService;

    @InjectMocks
    private PolicyDocumentController policyDocumentController;

    private PolicyDocument policyDocument;
    private UserPolicy userPolicy;
    private MockMultipartFile nomineeProof;
    private MockMultipartFile cheatSheet;
    private MockMultipartFile healthReport;
    Long userPolicyId;
    private MultipartFile mockFile = mock(MultipartFile.class);

    @BeforeEach
    public void setUp() {
        policyDocument = new PolicyDocument();
        policyDocument.setPolicyDetailsId(1L);
        policyDocument.setPolicyType(PolicyType.TERM);

        userPolicy = new UserPolicy();
        userPolicy.setUserPolicyID(1L);

        nomineeProof = new MockMultipartFile("nomineeProof", "proof.png", "image/png", "some-image".getBytes());
        cheatSheet = new MockMultipartFile("cheatSheet", "cheatSheet.png", "image/png", "some-image".getBytes());
        healthReport = new MockMultipartFile("healthReport", "healthReport.png", "image/png", "some-image".getBytes());
    }
    

    @Test
    public void testCreatePolicyDocument1() throws Exception {
        
        PolicyType policyType = PolicyType.AUTO;
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "mock file content".getBytes());
        Long userPolicyId = 123L;

   
        when(userPolicyService.getUserPolicyByUserPolicyId(userPolicyId)).thenReturn(Optional.of(new UserPolicy()));

        when(policyDocumentService.createPolicyDocument(any())).thenReturn(new PolicyDocument());

        ResponseEntity<Object> response = policyDocumentController.createPolicyDocument(
                policyType, mockFile, userPolicyId, doc -> {
                    // Implement PolicyDocumentConsumer logic
                    doc.setNomineeName("Sample Nominee");
                    doc.setLicensePlateNo("ABC123");
                });

       
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        
        verify(policyDocumentService, times(1)).createPolicyDocument(any());
    }

    @Test
    public void testCreateTermPolicyDocument() throws IOException {
        MockMultipartFile nomineeProof = new MockMultipartFile("nomineeProof", "proof.jpg", "image/jpeg", "dummy data".getBytes());

        UserPolicy userPolicy = new UserPolicy();
        when(userPolicyService.getUserPolicyByUserPolicyId(any(Long.class))).thenReturn(Optional.of(userPolicy));

        PolicyDocument policyDocument = new PolicyDocument();
        policyDocument.setPolicyDetailsId(1L); 
        when(policyDocumentService.createPolicyDocument(any(PolicyDocument.class))).thenReturn(policyDocument);

        ResponseEntity<Object> response = policyDocumentController.createTermPolicyDocument(
                PolicyType.TERM, 50000.0, true, "John Doe", "Brother",
                "john@example.com", nomineeProof, 1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PolicyDocument createdPolicyDocument = (PolicyDocument) response.getBody();
        assertEquals(policyDocument.getPolicyDetailsId(), createdPolicyDocument.getPolicyDetailsId());
    }

    @Test
    public void testCreateAutoPolicyDocument() throws IOException {
        MockMultipartFile cheatSheet = new MockMultipartFile("cheatSheet", "cheatsheet.pdf", "application/pdf", "dummy data".getBytes());

        UserPolicy userPolicy = new UserPolicy();
        when(userPolicyService.getUserPolicyByUserPolicyId(any(Long.class))).thenReturn(Optional.of(userPolicy));

        PolicyDocument policyDocument = new PolicyDocument();
        policyDocument.setPolicyDetailsId(1L); // Set the ID
        when(policyDocumentService.createPolicyDocument(any(PolicyDocument.class))).thenReturn(policyDocument);

        ResponseEntity<Object> response = policyDocumentController.createAutoPolicyDocument(
                PolicyType.AUTO,
                "Model X",
                "XYZ1234",
                30000.0,
                "Personal",
                "SUV",
                25,
                cheatSheet,
                1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PolicyDocument createdPolicyDocument = (PolicyDocument) response.getBody();
        assertEquals(policyDocument.getPolicyDetailsId(), createdPolicyDocument.getPolicyDetailsId());
    }

    @Test
    public void testCreateHealthPolicyDocument() throws IOException {
        MockMultipartFile healthReport = new MockMultipartFile("healthReport", "healthReport.pdf", "application/pdf", "dummy data".getBytes());

        UserPolicy userPolicy = new UserPolicy();
        when(userPolicyService.getUserPolicyByUserPolicyId(any(Long.class))).thenReturn(Optional.of(userPolicy));

        PolicyDocument policyDocument = new PolicyDocument();
        policyDocument.setPolicyDetailsId(1L); // Set the ID
        when(policyDocumentService.createPolicyDocument(any(PolicyDocument.class))).thenReturn(policyDocument);

        ResponseEntity<Object> response = policyDocumentController.createHealthPolicyDocument(
                PolicyType.HEALTH,
                30,
                180.0,
                75.0,
                true,
                true,
                true,
                true,
                "Heart Disease",
                healthReport,
                1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PolicyDocument createdPolicyDocument = (PolicyDocument) response.getBody();
        assertEquals(policyDocument.getPolicyDetailsId(), createdPolicyDocument.getPolicyDetailsId());
    }

    @Test
    public void testGetAllPolicyDocuments() {
        when(policyDocumentService.getAllPolicyDocuments()).thenReturn(Collections.singletonList(policyDocument));

        List<PolicyDocument> result = policyDocumentController.getAllPolicyDocuments();

        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }

    @Test
    public void testGetPolicyDocumentById() {
        when(policyDocumentService.getPolicyDocumentById(1L)).thenReturn(Optional.of(policyDocument));

        ResponseEntity<PolicyDocument> response = policyDocumentController.getPolicyDocumentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(policyDocument, response.getBody());
    }

    @Test
    public void testGetTermPolicies() {
        when(policyDocumentService.getTermPolicies()).thenReturn(Collections.singletonList(policyDocument));

        ResponseEntity<List<PolicyDocument>> response = policyDocumentController.getTermPolicies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PolicyDocument> result = response.getBody();
        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }

    @Test
    public void testGetAutoPolicies() {
        policyDocument.setPolicyType(PolicyType.AUTO);
        when(policyDocumentService.getAutoPolicies()).thenReturn(Collections.singletonList(policyDocument));

        ResponseEntity<List<PolicyDocument>> response = policyDocumentController.getAutoPolicies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PolicyDocument> result = response.getBody();
        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }

    @Test
    public void testGetHealthPolicies() {
        policyDocument.setPolicyType(PolicyType.HEALTH);
        when(policyDocumentService.getHealthPolicies()).thenReturn(Collections.singletonList(policyDocument));

        ResponseEntity<List<PolicyDocument>> response = policyDocumentController.getHealthPolicies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PolicyDocument> result = response.getBody();
        assertEquals(1, result.size());
        assertEquals(policyDocument, result.get(0));
    }
}
