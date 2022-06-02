package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
    ArrayList<Schedule> findAllByTourId(Integer tourId);
}
