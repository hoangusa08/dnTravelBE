package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentRepo extends JpaRepository<Payment, Integer> {

    List<Payment> findByCustomerIdAndStatus ( Integer customerId , String status);

    List<Payment> findByProviderIdAndStatus ( Integer providerId, String status);

    Optional<Payment> findById (Integer id);

    @Query(value = "select sum(total)  from payment where  status != 'CANCEL' && status != 'CUS_CANCEL'", nativeQuery = true)
    Integer countAllByStatus();

    @Query(value = "select sum(total)  from payment where status =?1 and provider_id=?2", nativeQuery = true)
    Integer countAllByStatusAndProvider( String status, Integer providerId);


    @Query(value = "SELECT * FROM payment WHERE  status != 'CANCEL' && status != 'CUS_CANCEL'and create_at BETWEEN ?1 AND ?2 ", nativeQuery = true)
    List<Payment> findAllByStatusAndDAndCreateAt(LocalDate start, LocalDate end);

    @Query(value = "SELECT * FROM payment WHERE status != 'CANCEL' && status != 'CUS_CANCEL' and create_at BETWEEN ?1 AND ?2 and provider_id=?3", nativeQuery = true)
    List<Payment> findAllByStatusAndDAndCreateAtAndProvider(LocalDate start, LocalDate end, Integer providerId);

    @Query(value = "SELECT id, COUNT(*) AS cnt FROM payment\n" +
            "GROUP BY tour_id\n" +
            "ORDER BY cnt DESC LIMIT 6", nativeQuery = true)
    List<Integer> getListTopPayment();


    @Query(value = "select (sum(adult_number)+sum(children_number)) from payment where tour_id=?1 and schedule_id=?2 and status in ( 'WAITING', 'APPROVE')", nativeQuery = true)
    Integer countByTourIdAndScheduleId(Integer tourId, Integer scheduleId);

    @Query(value = "select * from payment where tour_id=?1 and customer_id=?2 and schedule_id=?3 and status != 'CUS_CANCEL'", nativeQuery = true)
    Optional<Payment> findByTourIdAndCustomerIdAndScheduleId(Integer tourId, Integer customerId, Integer scheduleId);

    @Query(value = "select * from payment where provider_id = ?1 and status!= 'CANCEl' && status != 'CUS_CANCEL'", nativeQuery = true)
    List<Payment> findByProviderId(Integer providerId);
}
