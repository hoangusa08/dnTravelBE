package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Status;
import com.example.dnTravelBE.constant.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByName(StatusEnum statusEnum);
}
