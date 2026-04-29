package com.example.anajsetu.service;

import com.example.anajsetu.model.OtpToken;
import com.example.anajsetu.repository.OtpTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Value("${fast2sms.api.key}")
    private String fast2smsApiKey;

    // ─────────────────────────────────────────────────────────
    // 1. GENERATE — creates a random 6-digit OTP
    // ─────────────────────────────────────────────────────────
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // ─────────────────────────────────────────────────────────
    // 2. SAVE OTP — marks old OTPs as used, saves new one to DB
    // ─────────────────────────────────────────────────────────
    @Transactional
    public String saveOtp(String phone, OtpToken.Purpose purpose, Integer listingId) {

        // Mark all old unused OTPs for this phone+purpose as used
        List<OtpToken> oldTokens = otpTokenRepository
                .findAllByPhoneAndPurposeAndIsUsedFalse(phone, purpose);
        for (OtpToken old : oldTokens) {
            old.setUsed(true);
            otpTokenRepository.save(old);
        }

        // Generate and save new OTP
        String otp = generateOtp();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);
        OtpToken token = new OtpToken(phone, otp, purpose, listingId, expiresAt);
        otpTokenRepository.save(token);

        System.out.println("=============================");
        System.out.println("OTP for " + phone + " : " + otp);
        System.out.println("=============================");

        return otp;
    }

    // ─────────────────────────────────────────────────────────
    // 3. SEND OTP — saves to DB first, then sends SMS
    //    NOT @Transactional so SMS failure doesn't rollback DB save
    // ─────────────────────────────────────────────────────────
    public void sendOtp(String phone, OtpToken.Purpose purpose, Integer listingId) {
        String otp = saveOtp(phone, purpose, listingId); // DB saved first
        sendViaSms(phone, otp);                          // SMS failure won't undo DB save
    }

    // ─────────────────────────────────────────────────────────
    // 4. VERIFY — checks if OTP matches and is still valid
    // ─────────────────────────────────────────────────────────
    @Transactional
    public boolean verifyOtp(String phone, String otp, OtpToken.Purpose purpose) {

        // Find the latest unused OTP for this phone+purpose
        Optional<OtpToken> optionalToken =
                otpTokenRepository.findTopByPhoneAndPurposeAndIsUsedFalseOrderByCreatedAtDesc(phone, purpose);

        // No OTP found → invalid
        if (optionalToken.isEmpty()) {
            return false;
        }

        OtpToken token = optionalToken.get();

        // Check if expired
        if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
            return false;
        }

        // Check if OTP matches
        if (!token.getOtp().equals(otp.trim())) {
            return false;
        }

        // Mark as used so it can't be reused
        token.setUsed(true);
        otpTokenRepository.save(token);

        return true;
    }

    // ─────────────────────────────────────────────────────────
    // 5. SEND SMS — Fast2SMS (commented out until verified)
    // ─────────────────────────────────────────────────────────
    private void sendViaSms(String phone, String otp) {
        // Fast2SMS is disabled until website verification is complete.
        // OTP is already printed in saveOtp() above — check Spring Boot terminal.

        /*
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://www.fast2sms.com/dev/bulkV2" +
                    "?authorization=" + fast2smsApiKey +
                    "&route=otp" +
                    "&variables_values=" + otp +
                    "&flash=0" +
                    "&numbers=" + phone;

            HttpHeaders headers = new HttpHeaders();
            headers.set("cache-control", "no-cache");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            System.out.println("SMS sent! Response: " + response.getBody());

        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
        */
    }
}