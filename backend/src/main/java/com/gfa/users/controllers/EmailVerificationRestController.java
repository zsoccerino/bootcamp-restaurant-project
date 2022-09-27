package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailVerificationResponseDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.services.EmailVerificationService;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email")
public class EmailVerificationRestController {

  private final EmailVerificationService emailVerificationService;

  public EmailVerificationRestController(EmailVerificationService emailVerificationService) {
    this.emailVerificationService = emailVerificationService;
  }

  @GetMapping({"/verify/{token}", "/verify"})
  public ResponseEntity<EmailVerificationResponseDto> verify(
      @PathVariable(required = false) String token) {

    return new ResponseEntity<>(emailVerificationService.verify(token), HttpStatus.OK);
  }

  @GetMapping("/verify/resend")
  public ResponseEntity<ResponseDto> resend(@RequestBody String email) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(emailVerificationService.resend(email));
    } catch (InvalidEmailException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ErrorResponseDto("Invalid email"));
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.OK).body(new EmailVerificationResponseDto("ok"));
    } catch (EmailAlreadyVerifiedException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ErrorResponseDto("Email already verified!"));
    }
  }
}
