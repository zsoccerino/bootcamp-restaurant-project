package com.gfa.users.advice;

import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.users.exceptions.EmptyEmailException;
import com.gfa.users.exceptions.EmptyPasswordException;
import com.gfa.users.exceptions.EmptyRequestBodyException;
import com.gfa.users.exceptions.EmptyRoleException;
import com.gfa.users.exceptions.EmptyUsernameException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidTokenException;
import com.gfa.users.exceptions.RoleAlreadyExistsException;
import com.gfa.users.exceptions.RoleNotFoundException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import com.gfa.users.exceptions.UnableToGenerateQrKeyException;
import com.gfa.users.exceptions.VerificationTokenExpiredException;
import com.gfa.users.exceptions.VerificationTokenRequiredException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  private final Environment environment;

  public ControllerExceptionHandler(Environment environment) {
    this.environment = environment;
  }

  @ExceptionHandler(VerificationTokenRequiredException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto verificationTokenRequired() {
    return new ErrorResponseDto(environment.getProperty("config.errors.token.verification.empty"));
  }

  @ExceptionHandler(InvalidTokenException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto invalidToken() {
    return new ErrorResponseDto(environment.getProperty("config.errors.invalid_token"));
  }

  @ExceptionHandler(EmailAlreadyVerifiedException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto emailAlreadyVerified() {
    return new ErrorResponseDto(environment.getProperty("config.errors.email.verified.true"));
  }

  @ExceptionHandler(VerificationTokenExpiredException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto tokenExpiredException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.token.expired"));
  }

  @ExceptionHandler(EmptyUsernameException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmptyUsernameException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.username_required"));
  }

  @ExceptionHandler(EmptyRequestBodyException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmptyRequestBodyException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.requestBody_required"));
  }

  @ExceptionHandler(EmptyPasswordException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmptyPasswordException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.password_required"));
  }

  @ExceptionHandler(EmptyEmailException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmptyEmailException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.email_required"));
  }

  @ExceptionHandler(ShortUsernameException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleShortUsernameException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.username_is_short"));
  }

  @ExceptionHandler(ShortPasswordException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleShortPasswordException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.password_is_short"));
  }

  @ExceptionHandler(InvalidEmailException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidEmailException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.email_is_invalid"));
  }

  @ExceptionHandler(UnableToGenerateQrKeyException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleUnableToGenerateQrKeyException() {
    return new ErrorResponseDto("unable to generate QrCode");
  }

  @ExceptionHandler(EmptyRoleException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmptyRoleException() {
    return new ErrorResponseDto(environment.getProperty("config.errors.role_required"));
  }

  @ExceptionHandler(RoleAlreadyExistsException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleRoleAlreadyExistsException() {
    return new ErrorResponseDto("Role already exists");
  }

  @ExceptionHandler(InvalidIdException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleInvalidIdException() {
    return new ErrorResponseDto("Invalid Id");
  }

  @ExceptionHandler(RoleNotFoundException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleRoleNotFoundException() {
    return new ErrorResponseDto("Role not found");
  }
}
