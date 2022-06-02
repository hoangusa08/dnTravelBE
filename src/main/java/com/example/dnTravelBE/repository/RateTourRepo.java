package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.RateTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Repository
public interface RateTourRepo extends JpaRepository<RateTour, Integer> {
    ArrayList<RateTour> findAllByTourId(Integer tourId);

}
