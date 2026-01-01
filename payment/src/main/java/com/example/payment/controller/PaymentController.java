/*  package com.example.payment.controller;

import com.example.payment.entity.Payment;
import com.example.payment.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 1️⃣ CREATE ORDER + SAVE INITIAL DATA
    @PostMapping("/create-order")
    public Payment createOrder(@RequestBody Payment paymentRequest) throws Exception {

        RazorpayClient client =
                new RazorpayClient(keyId, keySecret);

        JSONObject request = new JSONObject();
        request.put("amount", paymentRequest.getAmount() * 100); // rupees → paise
        request.put("currency", "INR");

        Order order = client.orders.create(request);

        Payment payment = new Payment();
        payment.setUserName(paymentRequest.getUserName());
        payment.setAmount(paymentRequest.getAmount());
        payment.setOrderId(order.get("id"));
        payment.setStatus("CREATED");

        return paymentService.savePayment(payment);
    }

    // 2️⃣ PAYMENT SUCCESS
    @PostMapping("/payment-success")
    public Payment paymentSuccess(@RequestBody Payment paymentRequest) {
        return paymentService.updateSuccess(
                paymentRequest.getOrderId(),
                paymentRequest.getPaymentId()
        );
    }

    // 3️⃣ PAYMENT FAILED
    @PostMapping("/payment-failed")
    public Payment paymentFailed(@RequestBody Payment paymentRequest) {
        return paymentService.updateFailed(paymentRequest.getOrderId());
    }


    // 2️⃣ API to get all successful donors
    @GetMapping("/donors")
    @ResponseBody
    public List<Payment> getAllSuccessfulDonors() {
        return paymentService.getSuccessfulDonors();
    }
}
*/
package com.example.payment.controller;

import com.example.payment.entity.Payment;
import com.example.payment.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 1️⃣ CREATE ORDER
    @PostMapping("/create-order")
    public Payment createOrder(@RequestBody Payment paymentRequest) throws Exception {

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject request = new JSONObject();
        request.put("amount", paymentRequest.getAmount() * 100);
        request.put("currency", "INR");

        Order order = client.orders.create(request);

        Payment payment = new Payment();
        payment.setUserName(paymentRequest.getUserName());
        payment.setAmount(paymentRequest.getAmount());
        payment.setOrderId(order.get("id"));
        payment.setStatus("CREATED");

        return paymentService.savePayment(payment);
    }

    // 2️⃣ PAYMENT SUCCESS
    @PostMapping("/payment-success")
    public Payment paymentSuccess(@RequestBody Payment paymentRequest) {
        return paymentService.updateSuccess(
                paymentRequest.getOrderId(),
                paymentRequest.getPaymentId()
        );
    }

    // 3️⃣ PAYMENT FAILED
    @PostMapping("/payment-failed")
    public Payment paymentFailed(@RequestBody Payment paymentRequest) {
        return paymentService.updateFailed(paymentRequest.getOrderId());
    }

    // 4️⃣ DONORS PAGE
    @GetMapping("/donors")
    public List<Payment> getAllSuccessfulDonors() {
        return paymentService.getSuccessfulDonors();
    }

    // 🔐 ================= ADMIN APIs =================

    // 🔍 Search by orderId
    @GetMapping("/admin/payment/{orderId}")
    public Payment getPayment(@PathVariable String orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    // ✏️ Update name by orderId
    @PutMapping("/admin/update-name/{orderId}")
    public Payment updateName(@PathVariable String orderId,
                              @RequestParam String name) {
        return paymentService.updateUserName(orderId, name);
    }

    // ❌ Delete one user
    @DeleteMapping("/admin/delete/{orderId}")
    public String deleteUser(@PathVariable String orderId) {
        paymentService.deleteByOrderId(orderId);
        return "User deleted successfully";
    }

    // 🗑️ Delete all users
    @DeleteMapping("/admin/delete-all")
    public String deleteAllUsers() {
        paymentService.deleteAllPayments();
        return "All users deleted successfully";
    }
}



