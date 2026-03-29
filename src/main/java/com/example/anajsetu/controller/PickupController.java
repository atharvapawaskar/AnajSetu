package com.example.anajsetu.controller;

import com.example.anajsetu.model.Pickup;
import com.example.anajsetu.service.PickupService;
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
@RequestMapping("/api/pickups")
@CrossOrigin(origins = "http://localhost:3000")
public class PickupController {

    @Autowired
    private PickupService pickupService;

    // CREATE PICKUP
    @PostMapping
    public ResponseEntity<Pickup> createPickup(@RequestBody Pickup pickup) {
        Pickup savedPickup = pickupService.createPickup(pickup);
        return new ResponseEntity<>(savedPickup, HttpStatus.CREATED);
    }

    // GET ALL PICKUPS
    @GetMapping
    public ResponseEntity<List<Pickup>> getAllPickups() {
        List<Pickup> pickups = pickupService.getAllPickups();
        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    // GET PICKUP BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPickupById(@PathVariable int id) {
        Optional<Pickup> pickup = pickupService.getPickupById(id);
        if (pickup.isPresent()) {
            return new ResponseEntity<>(pickup.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Pickup not found", HttpStatus.NOT_FOUND);
    }

    // GET PICKUP BY CLAIM
    @GetMapping("/claim/{claimId}")
    public ResponseEntity<?> getPickupByClaim(@PathVariable int claimId) {
        Optional<Pickup> pickup = pickupService.getPickupByClaim(claimId);
        if (pickup.isPresent()) {
            return new ResponseEntity<>(pickup.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Pickup not found", HttpStatus.NOT_FOUND);
    }

    // GET PICKUPS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pickup>> getPickupsByStatus(@PathVariable String status) {
        List<Pickup> pickups = pickupService.getPickupsByStatus(status);
        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    // MARK AS PICKED UP
    @PutMapping("/{id}/pickedup")
    public ResponseEntity<?> markAsPickedUp(@PathVariable int id) {
        Pickup pickup = pickupService.markAsPickedUp(id);
        if (pickup != null) {
            return new ResponseEntity<>(pickup, HttpStatus.OK);
        }
        return new ResponseEntity<>("Pickup not found", HttpStatus.NOT_FOUND);
    }

    // MARK AS DELIVERED
    @PutMapping("/{id}/delivered")
    public ResponseEntity<?> markAsDelivered(@PathVariable int id) {
        Pickup pickup = pickupService.markAsDelivered(id);
        if (pickup != null) {
            return new ResponseEntity<>(pickup, HttpStatus.OK);
        }
        return new ResponseEntity<>("Pickup not found", HttpStatus.NOT_FOUND);
    }

    // DELETE PICKUP
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePickup(@PathVariable int id) {
        pickupService.deletePickup(id);
        return new ResponseEntity<>("Pickup deleted successfully", HttpStatus.OK);
    }

}