package com.gfa.users.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.InvalidTokenException;
import com.gfa.users.exceptions.NonExistingUserException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.exceptions.EmptyPasswordException;
import com.gfa.users.exceptions.PasswordResetTokenExpiredException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PasswordResetServiceTest {

  private static final String VALID_EMAIL = "john.doe@gmail.com";
  private static final String VALID_PASSWORD = "9bhz34G%.kIhe7";
  private static final String VALID_TOKEN = "g289dkju327dfbhlk38dh3k3";
  @Autowired PasswordResetService passwordResetService;
  @MockBean UserRepository userRepository;

  @Test
  void request_password_reset_with_non_existing_user_throws_exception() {
    when(userRepository.findUserByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
    assertThrows(
        NonExistingUserException.class,
        () -> {
          passwordResetService.sendPasswordResetEmail(new EmailRequestDto(VALID_EMAIL));
        });
  }

  @Test
  void request_password_reset_with_non_verified_email_throws_exception() {
    when(userRepository.findUserByEmail(VALID_EMAIL)).thenReturn(Optional.of(new User()));
    assertThrows(
        UnverifiedEmailException.class,
        () -> {
          passwordResetService.sendPasswordResetEmail(new EmailRequestDto(VALID_EMAIL));
        });
  }

  @Test
  void request_password_reset_with_invalid_email_throws_exception() {
    when(userRepository.findUserByEmail("email")).thenReturn(Optional.of(new User()));
    assertThrows(
        InvalidEmailException.class,
        () -> {
          passwordResetService.sendPasswordResetEmail(new EmailRequestDto("email"));
        });
  }

  @Test
  void reset_password_with_empty_password_throws_exception() {
    when(userRepository.findUserByForgottenPasswordToken(VALID_TOKEN)).thenReturn(Optional.of(new User()));
    assertThrows(
        EmptyPasswordException.class,
        () -> {
          passwordResetService.resetPassword(new PasswordResetRequestDto(null), VALID_TOKEN);
        });
  }

  @Test
  void reset_password_with_short_password_throws_exception() {
    when(userRepository.findUserByForgottenPasswordToken(VALID_TOKEN)).thenReturn(Optional.of(new User()));
    assertThrows(
        ShortPasswordException.class,
        () -> {
          passwordResetService.resetPassword(new PasswordResetRequestDto("pass"), VALID_TOKEN);
        });
  }

  @Test
  void reset_password_with_invalid_token_throws_exception() {
    when(userRepository.findUserByForgottenPasswordToken(VALID_TOKEN)).thenReturn(Optional.empty());
    assertThrows(
        InvalidTokenException.class,
        () -> {
          passwordResetService.resetPassword(
              new PasswordResetRequestDto(VALID_PASSWORD), VALID_TOKEN);
        });
  }

  @Test
  void reset_password_with_expired_token_throws_exception() {
    User user = new User();
    user.setForgottenPasswordTokenExpiresAt(LocalDateTime.now().minusSeconds(300));
    when(userRepository.findUserByForgottenPasswordToken(VALID_TOKEN)).thenReturn(Optional.of(user));
    assertThrows(
        PasswordResetTokenExpiredException.class,
        () -> {
          passwordResetService.resetPassword(
              new PasswordResetRequestDto(VALID_PASSWORD), VALID_TOKEN);
        });
  }
}
