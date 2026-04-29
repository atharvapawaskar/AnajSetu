package com.example.anajsetu.repository;

import com.example.anajsetu.model.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Integer> {

    Optional<Pickup> findByClaimId(int claimId);

    List<Pickup> findByStatus(String status);

    List<Pickup> findByVolunteerName(String volunteerName);

}