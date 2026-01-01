package com.example.payment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")  // table name in DB
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // DB primary key

    @Column(nullable = false)
    private String userName;  // User entered name

    @Column(nullable = false)
    private String orderId;   // Razorpay orderId

    private String paymentId; // Razorpay paymentId (may be null initially)

    @Column(nullable = false)
    private int amount;       // Donation amount

    @Column(nullable = false)
    private String status;    // CREATED / SUCCESS / FAILED

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

