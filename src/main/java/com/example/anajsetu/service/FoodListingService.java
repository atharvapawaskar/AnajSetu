package com.example.anajsetu.service;

import com.example.anajsetu.model.FoodListing;
import com.example.anajsetu.repository.FoodListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FoodListingService {

    @Autowired
    private FoodListingRepository foodListingRepository;

    public FoodListing createListing(FoodListing foodListing) {
        foodListing.setStatus("AVAILABLE");
        return foodListingRepository.save(foodListing);
    }

    public List<FoodListing> getAllListings() {
        return foodListingRepository.findAll();
    }

    public List<FoodListing> getAvailableListings() {
        return foodListingRepository.findByStatus("AVAILABLE");
    }

    public Optional<FoodListing> getListingById(int id) {
        return foodListingRepository.findById(id);
    }

    public List<FoodListing> getListingsByDonor(int donorId) {
        return foodListingRepository.findByDonorId(donorId);
    }

    public List<FoodListing> getListingsByFoodType(String foodType) {
        return foodListingRepository.findByFoodType(foodType);
    }

    public FoodListing updateListingStatus(int id, String status) {
        Optional<FoodListing> listing = foodListingRepository.findById(id);
        if (listing.isPresent()) {
            listing.get().setStatus(status);
            return foodListingRepository.save(listing.get());
        }
        return null;
    }

    public FoodListing updateListing(FoodListing foodListing) {
        return foodListingRepository.save(foodListing);
    }

    public void deleteListing(int id) {
        foodListingRepository.deleteById(id);
    }

}