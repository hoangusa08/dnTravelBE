package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName (AccountRole role);
}
