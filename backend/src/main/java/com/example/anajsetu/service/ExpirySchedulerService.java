package com.example.anajsetu.service;

import com.example.anajsetu.model.Compost;
import com.example.anajsetu.model.FoodListing;
import com.example.anajsetu.repository.CompostRepository;
import com.example.anajsetu.repository.FoodListingRepository;
import com.example.anajsetu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpirySchedulerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodListingRepository foodListingRepository;

    @Autowired
    private CompostRepository compostRepository;

    // Runs every 2 minutes
    @Scheduled(fixedRate = 120000)
    @Transactional
    public void redirectExpiredListings() {

        // Find all AVAILABLE listings whose pickup deadline has passed
        List<FoodListing> expired = foodListingRepository
            .findByStatusAndPickupDeadlineBefore("AVAILABLE", LocalDateTime.now());

        for (FoodListing listing : expired) {

            // Update listing status to REDIRECTED
            listing.setStatus("REDIRECTED");
            foodListingRepository.save(listing);

            // Create compost record
            Compost compost = new Compost();
            compost.setFoodListing(listing);

            // Set the compost center user — replace 25 with your actual compost center user id
            userRepository.findById(24).ifPresent(compost::setCenter);

            compost.setStatus("REDIRECTED");
            compost.setRedirectedAt(LocalDateTime.now());
            compostRepository.save(compost);

            System.out.println("Auto-redirected listing " + listing.getId()
                + " [" + listing.getFoodName() + "] to compost center");
        }

        if (expired.isEmpty()) {
            System.out.println("Scheduler ran — no expired listings found.");
        }
    }
}