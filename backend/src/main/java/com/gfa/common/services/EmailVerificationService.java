package com.gfa.common.services;

import com.gfa.common.dtos.EmailVerificationResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.utils.EmailUtils;
import com.gfa.common.utils.JwtUtil;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.users.exceptions.InvalidTokenException;
import com.gfa.users.exceptions.UsernameNotFoundException;
import com.gfa.users.exceptions.VerificationTokenExpiredException;
import com.gfa.users.exceptions.VerificationTokenRequiredException;
import com.gfa.users.models.EmailVerification;
import com.gfa.users.models.User;
import com.gfa.users.repositories.EmailVerificationRepository;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

  private final EmailVerificationRepository emailVerificationRepository;
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final EmailUtils emailUtils;

  public EmailVerificationService(
      EmailVerificationRepository emailVerificationRepository,
      UserRepository userRepository,
      EmailService emailService,
      EmailUtils emailUtils) {
    this.emailVerificationRepository = emailVerificationRepository;
    this.userRepository = userRepository;
    this.emailService = emailService;
    this.emailUtils = emailUtils;
  }

  public EmailVerificationResponseDto verify(String token) {
    if (token == null || token.isEmpty()) {
      throw new VerificationTokenRequiredException();
    }
    EmailVerification emailVerification =
        emailVerificationRepository.findByToken(token).orElseThrow(InvalidTokenException::new);

    if (isEmailVerified(emailVerification)) {
      throw new EmailAlreadyVerifiedException();
    }
    if (isTokenExpired(emailVerification)) {
      throw new VerificationTokenExpiredException();
    }

    User user = emailVerification.getUser();
    user.setVerifiedAt(LocalDateTime.now());
    userRepository.save(user);

    return new EmailVerificationResponseDto("OK");
  }

  private boolean isEmailVerified(EmailVerification emailVerification) {
    User user = emailVerification.getUser();
    return user.getVerifiedAt() != null;
  }

  private boolean isTokenExpired(EmailVerification emailVerification) {
    LocalDateTime current = LocalDateTime.now();
    return current.isAfter(emailVerification.getExpiresAt());
  }

  public ResponseDto resend(String email) {

    emailService.verify(email);
    User user = userRepository.findUserByEmail(email).orElseThrow(UsernameNotFoundException::new);
    if (Objects.isNull(user.getVerifiedAt())) {
      JwtUtil jwtUtil = new JwtUtil();
      String token = jwtUtil.generateToken(user.getUsername());
      emailUtils.sendHtmlEmail(
          email,
          "Fox dining",
          "Resend verification email",
          "<a>http://localhost:8080/email//verify/{token?" + token + "}</a>");
      return new EmailVerificationResponseDto("OK");
    } else {
      throw new EmailAlreadyVerifiedException();
    }
  }
}
