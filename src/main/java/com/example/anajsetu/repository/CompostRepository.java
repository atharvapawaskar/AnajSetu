package com.example.anajsetu.repository;

import com.example.anajsetu.model.Compost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompostRepository extends JpaRepository<Compost, Integer> {

    List<Compost> findByCenterId(int centerId);

    List<Compost> findByStatus(String status);

    List<Compost> findByFoodListingId(int listingId);

    List<Compost> findByCenterIdAndStatus(int centerId, String status);

}