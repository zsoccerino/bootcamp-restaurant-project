package com.gfa.users.repositories;

import com.gfa.users.models.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
  Optional<EmailVerification> findByToken(String token);
}
