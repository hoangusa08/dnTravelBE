package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByAccount_Id(Integer accountId);
}
