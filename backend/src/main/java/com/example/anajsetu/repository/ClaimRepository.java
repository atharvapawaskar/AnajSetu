package com.example.anajsetu.repository;

import com.example.anajsetu.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    List<Claim> findByNgoId(int ngoId);

    List<Claim> findByFoodListingId(int listingId);

    List<Claim> findByStatus(String status);

    List<Claim> findByNgoIdAndStatus(int ngoId, String status);

}