package com.example.anajsetu.controller;

import com.example.anajsetu.model.Claim;
import com.example.anajsetu.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "http://localhost:3000")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    // CREATE CLAIM
    @PostMapping
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
        Claim savedClaim = claimService.createClaim(claim);
        return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
    }

    // GET ALL CLAIMS
    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        List<Claim> claims = claimService.getAllClaims();
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    // GET CLAIM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimById(@PathVariable int id) {
        Optional<Claim> claim = claimService.getClaimById(id);
        if (claim.isPresent()) {
            return new ResponseEntity<>(claim.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Claim not found", HttpStatus.NOT_FOUND);
    }

    // GET CLAIMS BY NGO
    @GetMapping("/ngo/{ngoId}")
    public ResponseEntity<List<Claim>> getClaimsByNgo(@PathVariable int ngoId) {
        List<Claim> claims = claimService.getClaimsByNgo(ngoId);
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    // GET CLAIMS BY LISTING
    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Claim>> getClaimsByListing(@PathVariable int listingId) {
        List<Claim> claims = claimService.getClaimsByListing(listingId);
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    // GET CLAIMS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Claim>> getClaimsByStatus(@PathVariable String status) {
        List<Claim> claims = claimService.getClaimsByStatus(status);
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    // UPDATE CLAIM STATUS
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<?> updateClaimStatus(@PathVariable int id, @PathVariable String status) {
        Claim claim = claimService.updateClaimStatus(id, status);
        if (claim != null) {
            return new ResponseEntity<>(claim, HttpStatus.OK);
        }
        return new ResponseEntity<>("Claim not found", HttpStatus.NOT_FOUND);
    }

    // DELETE CLAIM
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClaim(@PathVariable int id) {
        claimService.deleteClaim(id);
        return new ResponseEntity<>("Claim deleted successfully", HttpStatus.OK);
    }

}