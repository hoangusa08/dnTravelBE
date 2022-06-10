package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Status;
import com.example.dnTravelBE.entity.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface TourRepo extends JpaRepository<Tour, Integer> {

    int countAllByStatus(Status status);

    Optional<Tour> findById(Integer id);

    @Query(value = "select * FROM tour where status_id = ?1 AND name LIKE ?2", nativeQuery = true)
    List<Tour> findAllByStatusId(Integer statusId, String search, Pageable pageable);

    @Query(value = "select * FROM tour where status_id = ?1 AND provider_id = ?2 AND name LIKE ?3 AND is_delete = ?4", nativeQuery = true)
    List<Tour> findAllByStatusIdAndProviderId(Integer statusId, Integer providerId , String search, boolean isDelete, Pageable pageable);

    @Query(value = "select * FROM tour where provider_id = ?1 AND name LIKE ?2 AND is_delete = ?3", nativeQuery = true)
    List<Tour> findAllByProviderIdAnDelete(Integer providerId , String search, boolean isDelete, Pageable pageable);
}
