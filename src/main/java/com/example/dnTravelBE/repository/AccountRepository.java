package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account , Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findById(Integer id);

    Optional<Account> findByCustomerId(Integer customerId);
}
