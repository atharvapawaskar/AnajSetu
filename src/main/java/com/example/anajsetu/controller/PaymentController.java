package com.example.anajsetu.controller;

import com.example.anajsetu.model.Payment;
import com.example.anajsetu.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    // GET RAZORPAY KEY ID (Frontend needs this to open payment popup)
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("keyId", razorpayKeyId);
        return new ResponseEntity<>(config, HttpStatus.OK);
    }

    // CREATE RAZORPAY ORDER
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            Double amount = Double.valueOf(request.get("amount").toString());
            String currency = request.getOrDefault("currency", "INR").toString();

            JSONObject order = paymentService.createOrder(amount, currency);

            Map<String, String> response = new HashMap<>();
            response.put("orderId", order.getString("id"));
            response.put("amount", String.valueOf(order.getInt("amount")));
            response.put("currency", order.getString("currency"));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // SAVE PAYMENT (called after order creation, before payment)
    @PostMapping("/save")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        Payment saved = paymentService.savePayment(payment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // CONFIRM PAYMENT (called after user completes payment on frontend)
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody Map<String, String> request) {
        String orderId = request.get("razorpayOrderId");
        String paymentId = request.get("razorpayPaymentId");
        String signature = request.get("razorpaySignature");

        Payment payment = paymentService.confirmPayment(orderId, paymentId, signature);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        }
        return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
    }

    // GET ALL PAYMENTS
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    // GET PAYMENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
    }

    // GET PAYMENTS BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable int userId) {
        return new ResponseEntity<>(paymentService.getPaymentsByUser(userId), HttpStatus.OK);
    }

    // GET PAYMENTS BY TYPE
    @GetMapping("/type/{paymentType}")
    public ResponseEntity<List<Payment>> getPaymentsByType(@PathVariable String paymentType) {
        return new ResponseEntity<>(paymentService.getPaymentsByType(paymentType), HttpStatus.OK);
    }

    // GET PAYMENTS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        return new ResponseEntity<>(paymentService.getPaymentsByStatus(status), HttpStatus.OK);
    }

}