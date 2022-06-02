package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProvinceRepo extends JpaRepository<Province, Integer> {
    Optional<Province> findById(Integer id);

    @Override
    List<Province> findAll();
}
