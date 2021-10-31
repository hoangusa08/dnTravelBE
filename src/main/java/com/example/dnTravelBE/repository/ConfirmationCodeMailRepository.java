package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.ConfirmationCodeEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationCodeMailRepository extends JpaRepository<ConfirmationCodeEmail, Integer> {
}
