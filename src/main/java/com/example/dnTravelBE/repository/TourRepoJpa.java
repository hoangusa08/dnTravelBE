package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TourRepoJpa extends JpaRepository<Tour, Integer> {
}