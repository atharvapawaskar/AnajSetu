package com.example.anajsetu.repository;

import com.example.anajsetu.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findTopByPhoneAndPurposeAndIsUsedFalseOrderByCreatedAtDesc(
            String phone, OtpToken.Purpose purpose
    );

    List<OtpToken> findAllByPhoneAndPurposeAndIsUsedFalse(
            String phone, OtpToken.Purpose purpose
    );

    void deleteAllByPhoneAndPurpose(String phone, OtpToken.Purpose purpose);
}