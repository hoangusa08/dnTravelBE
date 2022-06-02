package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    List<Category> findAll();
}
