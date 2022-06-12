package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Provider;
import com.example.dnTravelBE.entity.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    Optional<Provider> findByAccount_Id(Integer account_id);

    int countAllByStatus(Status status);
    Optional<Provider> findById( Integer id);
    @Query(value = "select * FROM provider where status_id = ?1 AND name_company LIKE ?2", nativeQuery = true)
    List<Provider> findAllByStatusId(Integer statusId, String search, Pageable pageable);

    @Query(value = "select count(*) FROM provider where status_id = ?1 AND name_company LIKE ?2", nativeQuery = true)
    int countAllByStatusId(Integer statusId, String search);
}
