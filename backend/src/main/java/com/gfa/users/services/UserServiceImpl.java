package com.gfa.users.services;

import com.gfa.common.dtos.IdRequestDto;
import com.gfa.common.dtos.NewUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.utils.EmailUtils;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.EmptyEmailException;
import com.gfa.users.exceptions.EmptyPasswordException;
import com.gfa.users.exceptions.EmptyRequestBodyException;
import com.gfa.users.exceptions.EmptyUsernameException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import com.gfa.users.models.User;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final String DEFAULT_USERNAME_MIN_LENGTH = "4";
  private static final String DEFAULT_PASSWORD_MIN_LENGTH = "12";

  private final Environment environment;
  private final EmailUtils emailUtils;
  private final TotpManagerService totpManagerService;

  @Autowired
  public UserServiceImpl(
      Environment environment, EmailUtils emailUtils, TotpManagerService totpManagerService) {
    this.environment = environment;
    this.emailUtils = emailUtils;
    this.totpManagerService = totpManagerService;
  }

  @Override
  public ResponseEntity<ResponseDto> index() {
    return null;
  }

  @Override
  public ResponseEntity<ResponseDto> show(IdRequestDto dto) {
    return null;
  }

  @Override
  public UserResponseDto store(NewUserRequestDto dto) {

    validateRequest(dto);
    emailUtils.validate(dto.email);
    // already existing username error and user creation failure error will be added after
    // repositories will be set

    SecureRandom secureRandomGenerator = new SecureRandom();
    int randomToken = secureRandomGenerator.nextInt();

    LocalDateTime verificationTokenExpiration = LocalDateTime.now();

    User user =
        new User(
            dto,
            String.valueOf(randomToken),
            verificationTokenExpiration.plusSeconds(
                Long.parseLong(
                    Objects.requireNonNull(
                        environment.getProperty(
                            "config.security.token.expiration.email_verification")))));

    if (user.isMfa()) {
      user.setSecret(totpManagerService.generateSecret());
    }

    return new UserResponseDto(user);

    //  return ResponseEntity.status(201).body(new StatusResponseDto("ok"));
  }

  private void validateRequest(NewUserRequestDto dto) {
    if (dto == null) {
      throw new EmptyRequestBodyException();
    }
    if (dto.username == null || dto.username.isEmpty()) {
      throw new EmptyUsernameException();
    }
    if (dto.password == null || dto.password.isEmpty()) {
      throw new EmptyPasswordException();
    }
    if (dto.email == null || dto.email.isEmpty()) {
      throw new EmptyEmailException();
    }
    if (dto.username.length()
        < Integer.parseInt(
            Objects.requireNonNull(
                environment.getProperty(
                    "config.security.username.min_length", DEFAULT_USERNAME_MIN_LENGTH)))) {
      throw new ShortUsernameException();
    }
    if (dto.password.length()
        < Integer.parseInt(
            Objects.requireNonNull(
                environment.getProperty(
                    "config.security.password.min_length", DEFAULT_PASSWORD_MIN_LENGTH)))) {
      throw new ShortPasswordException();
    }
  }
}
