package com.example.repo;

import com.example.model.PolicyDocument;
import com.example.model.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyDocumentRepo extends JpaRepository<PolicyDocument, Long> {

   
    @Query("SELECT pd FROM PolicyDocument pd WHERE pd.policyType = 'Auto'")
    List<PolicyDocument> findAutoPolicies();
    @Query("SELECT pd FROM PolicyDocument pd WHERE pd.policyType = 'Term'")
	List<PolicyDocument> findTermPolicies();
    @Query("SELECT pd FROM PolicyDocument pd WHERE pd.policyType = 'Health'")
	List<PolicyDocument> findHealthPolicies();
}
