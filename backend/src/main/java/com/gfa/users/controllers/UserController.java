package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.IdRequestDto;
import com.gfa.common.dtos.NewUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.NonExistingUserException;
import com.gfa.users.exceptions.UnverifiedEmailException;
import com.gfa.users.dtos.PasswordResetRequestDto;
import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.exceptions.EmptyPasswordException;
import com.gfa.users.exceptions.InvalidTokenException;
import com.gfa.users.exceptions.PasswordResetTokenExpiredException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.services.PasswordResetService;
import com.gfa.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class UserController {
  private final UserService userService;
  private final PasswordResetService passwordResetService;

  @Autowired
  public UserController(UserService userService, PasswordResetService passwordResetService) {
    this.userService = userService;
    this.passwordResetService = passwordResetService;
  }

  @GetMapping("/")
  public ResponseEntity<ResponseDto> index() {
    return userService.index();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> show(@PathVariable IdRequestDto dto) {
    return userService.show(dto);
  }

  @PostMapping("/register")
  public ResponseEntity<? extends UserResponseDto> registerUser(
      @RequestBody NewUserRequestDto dto) {
    return new ResponseEntity<>(userService.store(dto), HttpStatus.OK);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<? extends ResponseDto> sendPasswordResetEmail(
      @RequestBody EmailRequestDto emailRequestDto) {
    ResponseDto dto = passwordResetService.sendPasswordResetEmail(emailRequestDto);
    try {
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (InvalidEmailException invalidEmailException) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid email"), HttpStatus.BAD_REQUEST);
    } catch (NonExistingUserException nonExistingUserException) {
      return new ResponseEntity<>(new StatusResponseDto("ok"), HttpStatus.OK);
    } catch (UnverifiedEmailException unverifiedEmailException) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Unverified email!"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<? extends ResponseDto> resetPassword(
      @RequestBody PasswordResetRequestDto passwordResetRequestDto, @PathVariable String token) {
    ResponseDto dto = passwordResetService.resetPassword(passwordResetRequestDto, token);
    try {
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (EmptyPasswordException emptyPasswordException) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Password is required"), HttpStatus.BAD_REQUEST);
    } catch (ShortPasswordException shortPasswordException) {
      return new ResponseEntity<>(
          new ErrorResponseDto("Password must be at least 8 characters long"),
          HttpStatus.BAD_REQUEST);
    } catch (InvalidTokenException invalidTokenException) {
      return new ResponseEntity<>(new ErrorResponseDto("Invalid Token"), HttpStatus.BAD_REQUEST);
    } catch (PasswordResetTokenExpiredException passwordResetTokenExpiredException) {
      return new ResponseEntity<>(new ErrorResponseDto("Expired Token"), HttpStatus.BAD_REQUEST);
    }
  }
}
