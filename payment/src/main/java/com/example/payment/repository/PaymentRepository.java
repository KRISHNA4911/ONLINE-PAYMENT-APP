/* package com.example.payment.repository;

import com.example.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find by Razorpay orderId
    Payment findByOrderId(String orderId);

    // Fetch all successful donors
    List<Payment> findByStatus(String status);

    //  Fetch successful donors (latest first)
    List<Payment> findByStatusOrderByIdDesc(String status);
}
*/
package com.example.payment.repository;

import com.example.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Existing
    Payment findByOrderId(String orderId);

    List<Payment> findByStatus(String status);

    List<Payment> findByStatusOrderByIdDesc(String status);

    // 🔐 ADMIN – delete by orderId
    void deleteByOrderId(String orderId);
}
