package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TourRepo extends JpaRepository<Tour, Integer> {

    int countAllByStatus(StatusEnum statusEnum);

    Optional<Tour> findById(Integer id);
}
