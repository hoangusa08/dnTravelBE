package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByAccount_Id(Integer accountId);
    Optional<Customer> findById(Integer id);

    @Query(value = "select * FROM customer where full_name LIKE ?1", nativeQuery = true)
    List<Customer> findAllByFullName(String fullName , Pageable pageable);

    @Query(value = "select count(*) FROM customer where full_name LIKE ?1", nativeQuery = true)
    int countAllByFullName(String fullName);
}
