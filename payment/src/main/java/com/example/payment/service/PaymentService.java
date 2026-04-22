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
    double amountInRupees = payment.getAmount();

    // 1. Check if amount is less than ₹1
    if (amountInRupees < 1.0) {
        return "Minimum payment amount is ₹1.00";
    }

    // 2. Check if amount exceeds Integer Max when converted to paise
    // Max Rupees allowed is approx 21,474,836.47
    if (amountInRupees > (Integer.MAX_VALUE / 100.0)) {
        return "Amount exceed the maximum amount limit";
    }

    // If valid, save to DB
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
