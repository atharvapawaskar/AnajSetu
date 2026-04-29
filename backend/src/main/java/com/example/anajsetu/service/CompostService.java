package com.example.anajsetu.service;

import com.example.anajsetu.model.Compost;
import com.example.anajsetu.model.FoodListing;
import com.example.anajsetu.repository.CompostRepository;
import com.example.anajsetu.repository.FoodListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompostService {

    @Autowired
    private CompostRepository compostRepository;

    @Autowired
    private FoodListingRepository foodListingRepository;

    public Compost createCompost(Compost compost) {
        compost.setStatus("PENDING");
        compost.setRedirectedAt(LocalDateTime.now());

        Optional<FoodListing> listing = foodListingRepository.findById(
            compost.getFoodListing().getId()
        );
        if (listing.isPresent()) {
            listing.get().setStatus("REDIRECTED");
            foodListingRepository.save(listing.get());
        }

        return compostRepository.save(compost);
    }

    public List<Compost> getAllCompost() {
        return compostRepository.findAll();
    }

    public Optional<Compost> getCompostById(int id) {
        return compostRepository.findById(id);
    }

    public List<Compost> getCompostByCenter(int centerId) {
        return compostRepository.findByCenterId(centerId);
    }

    public List<Compost> getCompostByStatus(String status) {
        return compostRepository.findByStatus(status);
    }

    public Compost markAsCollected(int id) {
        Optional<Compost> compost = compostRepository.findById(id);
        if (compost.isPresent()) {
            compost.get().setStatus("COLLECTED");
            compost.get().setCollectedAt(LocalDateTime.now());
            return compostRepository.save(compost.get());
        }
        return null;
    }

    public Compost markAsProcessed(int id, int biogasOutputKg) {
        Optional<Compost> compost = compostRepository.findById(id);
        if (compost.isPresent()) {
            compost.get().setStatus("PROCESSED");
            compost.get().setBiogasOutputKg(biogasOutputKg);
            return compostRepository.save(compost.get());
        }
        return null;
    }

    public void deleteCompost(int id) {
        compostRepository.deleteById(id);
    }

}