package com.example.anajsetu.controller;

import com.example.anajsetu.model.FoodListing;
import com.example.anajsetu.service.FoodListingService;
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
@RequestMapping("/api/listings")
@CrossOrigin(origins = "http://localhost:3000")
public class FoodListingController {

    @Autowired
    private FoodListingService foodListingService;

    // CREATE LISTING
    @PostMapping
    public ResponseEntity<FoodListing> createListing(@RequestBody FoodListing foodListing) {
        FoodListing savedListing = foodListingService.createListing(foodListing);
        return new ResponseEntity<>(savedListing, HttpStatus.CREATED);
    }

    // GET ALL LISTINGS
    @GetMapping
    public ResponseEntity<List<FoodListing>> getAllListings() {
        List<FoodListing> listings = foodListingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // GET AVAILABLE LISTINGS
    @GetMapping("/available")
    public ResponseEntity<List<FoodListing>> getAvailableListings() {
        List<FoodListing> listings = foodListingService.getAvailableListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // GET LISTING BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getListingById(@PathVariable int id) {
        Optional<FoodListing> listing = foodListingService.getListingById(id);
        if (listing.isPresent()) {
            return new ResponseEntity<>(listing.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Listing not found", HttpStatus.NOT_FOUND);
    }

    // GET LISTINGS BY DONOR
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<FoodListing>> getListingsByDonor(@PathVariable int donorId) {
        List<FoodListing> listings = foodListingService.getListingsByDonor(donorId);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // GET LISTINGS BY FOOD TYPE
    @GetMapping("/type/{foodType}")
    public ResponseEntity<List<FoodListing>> getListingsByFoodType(@PathVariable String foodType) {
        List<FoodListing> listings = foodListingService.getListingsByFoodType(foodType);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // UPDATE LISTING STATUS
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<?> updateListingStatus(@PathVariable int id, @PathVariable String status) {
        FoodListing listing = foodListingService.updateListingStatus(id, status);
        if (listing != null) {
            return new ResponseEntity<>(listing, HttpStatus.OK);
        }
        return new ResponseEntity<>("Listing not found", HttpStatus.NOT_FOUND);
    }

    // UPDATE LISTING
    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(@PathVariable int id, @RequestBody FoodListing foodListing) {
        foodListing.setId(id);
        FoodListing updatedListing = foodListingService.updateListing(foodListing);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }

    // DELETE LISTING
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListing(@PathVariable int id) {
        foodListingService.deleteListing(id);
        return new ResponseEntity<>("Listing deleted successfully", HttpStatus.OK);
    }

}