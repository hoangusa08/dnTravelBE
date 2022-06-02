package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByAccount_Id(Integer accountId);
    Optional<Customer> findById(Integer id);
}
