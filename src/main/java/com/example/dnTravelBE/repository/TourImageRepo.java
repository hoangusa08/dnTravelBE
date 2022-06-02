package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TourImageRepo extends JpaRepository<TourImage, Integer> {

}
