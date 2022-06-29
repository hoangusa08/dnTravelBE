package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.*;
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

    @Query(value = "select count(*) from tour where status_id = ?1 and name like ?2" , nativeQuery = true)
    int countAllByStatusAndSearch( Integer statusId , String search);

    @Query(value = "select count(*) from tour where status_id = ?1 and provider_id =?3 and name like ?2 AND is_delete = ?4" , nativeQuery = true)
    int countAllByProviderAndStatusAndSearch( Integer statusId , String search, Integer providerId, boolean isDelete);

    @Query(value = "select count(*) from tour where provider_id =?1 and name like ?2 and is_delete = ?3 ", nativeQuery = true)
    int countAllByDelete( Integer providerId, String keyword , boolean stt);

    @Query(value = "select count(*) from tour where status_id =?1 and is_delete = ?2 ", nativeQuery = true)
    int countAllByAdmin( Integer statusId, boolean stt);

    @Query(value = "select * from tour where start_location=?1 and status_id =?2 and is_delete = ?3 ", nativeQuery = true)
    List<Tour> findAllByProvinceAndStatusAndDelete(Integer provinceId, Integer statusid , boolean delete);

    @Query(value = "select * from tour where category_id=?1 and is_delete = ?2 and status_id=?3", nativeQuery = true)
    List<Tour> findAllByCategoryIdAndStatusIdAndDelete(Integer categoryId, boolean delete, Integer statusId);

    @Query(value = "select * from tour where start_location=?1 and is_delete = ?2  and status_id=?3", nativeQuery = true)
    List<Tour> findAllByProvinceIdAnAndStatusIdAndDelete(Integer provinceId, boolean delete, Integer statusId);

    @Query(value = "select count(*) from tour where provider_id = ?1 and status_id =?2 and is_delete = ?3 ", nativeQuery = true)
    Integer countAllByProvider( Integer providerId, Integer statusId, boolean stt);
}
