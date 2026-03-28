package com.example.anajsetu.service;

import com.example.anajsetu.model.Claim;
import com.example.anajsetu.model.FoodListing;
import com.example.anajsetu.repository.ClaimRepository;
import com.example.anajsetu.repository.FoodListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private FoodListingRepository foodListingRepository;

    public Claim createClaim(Claim claim) {
        claim.setStatus("PENDING");
        claim.setClaimedAt(LocalDateTime.now());

        Optional<FoodListing> listing = foodListingRepository.findById(
            claim.getFoodListing().getId()
        );
        if (listing.isPresent()) {
            listing.get().setStatus("CLAIMED");
            foodListingRepository.save(listing.get());
        }

        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(int id) {
        return claimRepository.findById(id);
    }

    public List<Claim> getClaimsByNgo(int ngoId) {
        return claimRepository.findByNgoId(ngoId);
    }

    public List<Claim> getClaimsByListing(int listingId) {
        return claimRepository.findByFoodListingId(listingId);
    }

    public List<Claim> getClaimsByStatus(String status) {
        return claimRepository.findByStatus(status);
    }

    public Claim updateClaimStatus(int id, String status) {
        Optional<Claim> claim = claimRepository.findById(id);
        if (claim.isPresent()) {
            claim.get().setStatus(status);
            return claimRepository.save(claim.get());
        }
        return null;
    }

    public void deleteClaim(int id) {
        claimRepository.deleteById(id);
    }

}