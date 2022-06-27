package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account , Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findById(Integer id);

    Optional<Account> findByCustomerId(Integer customerId);

    int countAllByRoleName(AccountRole accountRole);

    @Query(value = "SELECT * FROM account WHERE role_id = ?1 and create_at BETWEEN ?2 AND ?3 ", nativeQuery = true)
    List<Account> findAllByRoleNameAndDAndCreateAt(Integer roleId, LocalDate start, LocalDate end);
}
