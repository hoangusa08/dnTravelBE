package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    Optional<Provider>  findByAccount_Id(Integer account_id);
}
