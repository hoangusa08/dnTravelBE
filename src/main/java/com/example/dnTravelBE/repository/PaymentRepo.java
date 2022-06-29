package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Account;
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

    @Query(value = "select sum(total)  from payment where status =?1", nativeQuery = true)
    Integer countAllByStatus( String status);

    @Query(value = "select sum(total)  from payment where status =?1 and provider_id=?2", nativeQuery = true)
    Integer countAllByStatusAndProvider( String status, Integer providerId);


    @Query(value = "SELECT * FROM payment WHERE status = ?1 and create_at BETWEEN ?2 AND ?3 ", nativeQuery = true)
    List<Payment> findAllByStatusAndDAndCreateAt(String  status, LocalDate start, LocalDate end);

    @Query(value = "SELECT * FROM payment WHERE status = ?1 and create_at BETWEEN ?2 AND ?3 and provider_id=?4", nativeQuery = true)
    List<Payment> findAllByStatusAndDAndCreateAtAndProvider(String  status, LocalDate start, LocalDate end, Integer providerId);

    @Query(value = "select product, sum(amount) \n" +
            "       from payment \n" +
            "       group by tour_id \n" +
            "       order by sum(amount) desc \n" +
            "       limit 6;", nativeQuery = true)
    List<Payment> getListTopPayment();
}
