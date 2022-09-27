package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.utils.EmailUtils;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.exceptions.EmptyPasswordException;
import com.gfa.users.exceptions.InvalidTokenException;
import com.gfa.users.exceptions.NonExistingUserException;
import com.gfa.users.exceptions.PasswordResetTokenExpiredException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
  private static final String DEFAULT_PASSWORD_MIN_LENGTH = "12";
  private final UserRepository userRepository;
  private final EmailUtils emailUtils;
  private final Environment environment;

  @Autowired
  public PasswordResetService(
      UserRepository userRepository, EmailUtils emailUtils, Environment environment) {
    this.userRepository = userRepository;
    this.emailUtils = emailUtils;
    this.environment = environment;
  }

  private void generatePasswordResetToken(User user) {
    String token = RandomString.make(30);
    user.setForgottenPasswordToken(token);
    userRepository.save(user);
  }

  private void setForgottenPasswordTokenExpiresAt(User user) {
    user.setForgottenPasswordTokenExpiresAt(LocalDateTime.now().plusSeconds(3600));
    userRepository.save(user);
  }

  private void sendEmail(User user) {
    String resetPasswordLink =
        "http://localhost/reset-password?token=" + user.getForgottenPasswordToken();
    String to = user.getEmail();
    String from = "";
    String subject = "Password Reset";
    String text =
        "<p>Click the link below to change your password:</p>"
            + "<a href="
            + resetPasswordLink
            + ">Reset my password.</a>";

    emailUtils.sendHtmlEmail(to, from, subject, text);
  }

  public ResponseDto sendPasswordResetEmail(EmailRequestDto emailRequestDto) {
    String email = emailRequestDto.email;
    User user = userRepository.findUserByEmail(email).orElseThrow(NonExistingUserException::new);
    emailUtils.validate(email);

    if (user == null) {
      throw new NonExistingUserException();
    } else if (user.getVerifiedAt() == null) {
      throw new UnverifiedEmailException();
    } else {
      generatePasswordResetToken(user);
      setForgottenPasswordTokenExpiresAt(user);
      userRepository.save(user);
      sendEmail(user);
      return new StatusResponseDto("ok");
    }
  }

  public ResponseDto resetPassword(PasswordResetRequestDto passwordResetRequestDto, String token) {
    String password = passwordResetRequestDto.password;
    User user = userRepository.findUserByForgottenPasswordToken(token).orElseThrow(InvalidTokenException::new);

    if (password == null) {
      throw new EmptyPasswordException();
    } else if (password.length()
        < Integer.parseInt(
            Objects.requireNonNull(
                environment.getProperty(
                    "config.security.password.min_length", DEFAULT_PASSWORD_MIN_LENGTH)))) {
      throw new ShortPasswordException();
    } else if (LocalDateTime.now().isAfter(user.getForgottenPasswordTokenExpiresAt())) {
      throw new PasswordResetTokenExpiredException();
    } else {
      user.setPassword(password);
      user.setForgottenPasswordToken("");
      userRepository.save(user);
      return new StatusResponseDto("ok");
    }
  }
}
