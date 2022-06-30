package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Category;
import com.example.dnTravelBE.entity.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    List<Category> findAll();

    Optional<Category> findByName( String name);

    @Query(value = "select * FROM categories where name LIKE ?1 ", nativeQuery = true)
    List<Category> findAllByAdmin(String search, Pageable pageable);

    @Query(value = "select count(*) FROM categories where name LIKE ?1 ", nativeQuery = true)
    int countAll(String search);
}
