package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.entity.Status;
import com.example.dnTravelBE.entity.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TourRepo extends PagingAndSortingRepository<Tour, Integer> {

    int countAllByStatus(Status status);

    Optional<Tour> findById(Integer id);

//    @Query(value = "SELECT  * FROM tour WHERE status_id = ?1 AND (LOWER(unaccent(name)) LIKE  unaccent(?2) OR LOWER(unaccent(description)) LIKE unaccent(?2))", nativeQuery = true)
    List<Tour> findAllByStatusId(Integer statusId, Pageable pageable);

}
