package com.example.anajsetu.repository;

import com.example.anajsetu.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByUserId(int userId);

    List<Payment> findByStatus(String status);

    List<Payment> findByPaymentType(String paymentType);

    List<Payment> findByRazorpayOrderId(String razorpayOrderId);

    List<Payment> findByRazorpayPaymentId(String razorpayPaymentId);

}