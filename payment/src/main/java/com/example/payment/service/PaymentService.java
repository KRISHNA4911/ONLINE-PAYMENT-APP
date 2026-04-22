/*  package com.example.payment.service;

import com.example.payment.entity.Payment;
import com.example.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // Constructor Injection (BEST PRACTICE)
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 1️⃣ SAVE PAYMENT WHEN ORDER IS CREATED
    public Payment savePayment(Payment payment) {

       //  This method is called from:
       //  POST /create-order

     //    At this time:
     //    - orderId is generated
    //     - userName is present
    //     - amount is present
    //     - status = CREATED


        return paymentRepository.save(payment);
    }

    // 2️⃣ UPDATE PAYMENT ON SUCCESS
    public Payment updateSuccess(String orderId, String paymentId) {


     //    This method is called from:
      //   POST /payment-success

     //    Logic:
     //    - Find record using orderId
     //    - Update paymentId
     //    - Update status = SUCCESS


        Payment payment = paymentRepository.findByOrderId(orderId);

        payment.setPaymentId(paymentId);
        payment.setStatus("SUCCESS");

        return paymentRepository.save(payment);
    }

    // 3️⃣ UPDATE PAYMENT ON FAILURE
    public Payment updateFailed(String orderId) {


     //    This method is called from:
     //    POST /payment-failed

     //    Logic:
       //  - Find record using orderId
        // - Update status = FAILED


        Payment payment = paymentRepository.findByOrderId(orderId);

        payment.setStatus("FAILED");

        return paymentRepository.save(payment);
    }
    // 4️⃣ GET ALL SUCCESSFUL DONORS ✅ (NEW METHOD)
    public List<Payment> getSuccessfulDonors() {
        return paymentRepository.findByStatusOrderByIdDesc("SUCCESS");
    }
}
*/
package com.example.payment.service;

import com.example.payment.entity.Payment;
import com.example.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 1️⃣ SAVE PAYMENT
    public Payment savePayment(Payment payment) {
    double amount = payment.getAmount();

    // Validate limits
    if (amount < 1.0 || amount > (Integer.MAX_VALUE / 100.0)) {
        // This stops the execution and "bubbles up" the error
        throw new RuntimeException("Invalid amount: Please enter a value between ₹1 and ₹2.1Cr");
    }

    return paymentRepository.save(payment);
}

    

    // 2️⃣ PAYMENT SUCCESS
    public Payment updateSuccess(String orderId, String paymentId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        payment.setPaymentId(paymentId);
        payment.setStatus("SUCCESS");
        return paymentRepository.save(payment);
    }

    // 3️⃣ PAYMENT FAILED
    public Payment updateFailed(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        payment.setStatus("FAILED");
        return paymentRepository.save(payment);
    }

    // 4️⃣ GET SUCCESSFUL DONORS
    public List<Payment> getSuccessfulDonors() {
        return paymentRepository.findByStatusOrderByIdDesc("SUCCESS");
    }

    // 🔐 ================= ADMIN METHODS =================

    // 🔍 Search by orderId
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    // ✏️ Update name by orderId
    @Transactional
    public Payment updateUserName(String orderId, String newName) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        payment.setUserName(newName);
        return paymentRepository.save(payment);
    }

    // ❌ Delete single user by orderId
    @Transactional
    public void deleteByOrderId(String orderId) {
        paymentRepository.deleteByOrderId(orderId);
    }

    // 🗑️ Delete all users
    @Transactional
    public void deleteAllPayments() {
        paymentRepository.deleteAll();
    }
}
