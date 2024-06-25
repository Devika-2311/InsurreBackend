package com.example.controller;


import com.example.model.UserPolicy;

import com.example.service.UserPolicyService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3003", allowCredentials = "true")



@RestController
@RequestMapping("/user-policies")
public class UserPolicyController {


  
    private final UserPolicyService userPolicyService;

    @Autowired
    public UserPolicyController( UserPolicyService userPolicyService) {
       
        this.userPolicyService = userPolicyService;
    }


    @PostMapping("/create")
    public Long createUserPolicy(@RequestBody UserPolicy userPolicy) {

UserPolicy createdUserPolicy = userPolicyService.createUserPolicy(userPolicy);

// Return the ID of the created user policy
return createdUserPolicy.getUserPolicyId();
        
        
    }


    
   
    @GetMapping("/user/{userId}")
    public List<UserPolicy> getPoliciesById(@PathVariable Long userId) {
      
       
        return userPolicyService.getUserPoliciesByUserId(userId);
    }
  
     
    

    @GetMapping("/get")
    public List<UserPolicy> getAllUserPolicies() {
        return userPolicyService.getAllUserPolicies();
    }

    @GetMapping("/userpolicy/{userId}")
    public List<UserPolicy> getUserPoliciesByUserId(@PathVariable Long userId) {
        return userPolicyService.getUserPoliciesByUserId(userId);
    }
    @PutMapping("/increment/{id}")
    public ResponseEntity<UserPolicy> incrementPremiumCount(@PathVariable Long id) {
        try {
            UserPolicy updatedUserPolicy = userPolicyService.incrementPremiumCount(id);
            return ResponseEntity.ok(updatedUserPolicy);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
