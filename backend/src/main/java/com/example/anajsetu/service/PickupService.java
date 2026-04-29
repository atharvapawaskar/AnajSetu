package com.example.anajsetu.service;

import com.example.anajsetu.model.Pickup;
import com.example.anajsetu.model.Claim;
import com.example.anajsetu.repository.PickupRepository;
import com.example.anajsetu.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PickupService {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private ClaimRepository claimRepository;

    public Pickup createPickup(Pickup pickup) {
        pickup.setStatus("SCHEDULED");
        return pickupRepository.save(pickup);
    }

    public List<Pickup> getAllPickups() {
        return pickupRepository.findAll();
    }

    public Optional<Pickup> getPickupById(int id) {
        return pickupRepository.findById(id);
    }

    public Optional<Pickup> getPickupByClaim(int claimId) {
        return pickupRepository.findByClaimId(claimId);
    }

    public List<Pickup> getPickupsByStatus(String status) {
        return pickupRepository.findByStatus(status);
    }

    public Pickup markAsPickedUp(int id) {
        Optional<Pickup> pickup = pickupRepository.findById(id);
        if (pickup.isPresent()) {
            pickup.get().setStatus("IN_TRANSIT");
            pickup.get().setPickedUpAt(LocalDateTime.now());

            Optional<Claim> claim = claimRepository.findById(
                pickup.get().getClaim().getId()
            );
            if (claim.isPresent()) {
                claim.get().setStatus("PICKED_UP");
                claimRepository.save(claim.get());
            }

            return pickupRepository.save(pickup.get());
        }
        return null;
    }

    public Pickup markAsDelivered(int id) {
        Optional<Pickup> pickup = pickupRepository.findById(id);
        if (pickup.isPresent()) {
            pickup.get().setStatus("DELIVERED");
            pickup.get().setDeliveredAt(LocalDateTime.now());
            return pickupRepository.save(pickup.get());
        }
        return null;
    }

    public void deletePickup(int id) {
        pickupRepository.deleteById(id);
    }

}