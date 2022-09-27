package com.gfa.users.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gfa.common.dtos.EmailVerificationResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.common.services.EmailVerificationService;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.users.exceptions.InvalidTokenException;
import com.gfa.users.exceptions.VerificationTokenExpiredException;
import com.gfa.users.exceptions.VerificationTokenRequiredException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WithMockUser
@WebMvcTest(EmailVerificationRestController.class)
class EmailVerificationRestControllerTest {

  @MockBean private EmailVerificationService emailVerificationService;
  @Autowired private MockMvc mockMvc;

  @Test
  void can_verify_user_email() throws Exception {

    EmailVerificationResponseDto emailVerificationResponseDto =
        new EmailVerificationResponseDto("ok");

    Mockito.when(emailVerificationService.verify(Mockito.any()))
        .thenReturn(emailVerificationResponseDto);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/email/verify/123456789"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("ok")));
  }

  @Test
  void verify_with_null_or_empty_token_shows_error() throws Exception {

    Mockito.when(emailVerificationService.verify(Mockito.any()))
        .thenThrow(VerificationTokenRequiredException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/email/verify/"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Verification token is missing!")));
  }

  @Test
  void verify_invalid_token_shows_error() throws Exception {

    Mockito.when(emailVerificationService.verify(Mockito.any()))
        .thenThrow(InvalidTokenException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/email/verify/123456789"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid token!")));
  }

  @Test
  void verify_already_verified_email_shows_error() throws Exception {

    Mockito.when(emailVerificationService.verify(Mockito.any()))
        .thenThrow(EmailAlreadyVerifiedException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/email/verify/123456789"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Email has already been verified!")));
  }

  @Test
  void verify_with_expired_token_shows_error() throws Exception {

    Mockito.when(emailVerificationService.verify(Mockito.any()))
        .thenThrow(VerificationTokenExpiredException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/email/verify/123456789"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Token expired!")));
  }

  @Test
  void can_resend_email_ok() throws Exception {

    Mockito.when(emailVerificationService.resend(Mockito.any()))
        .thenReturn(new EmailVerificationResponseDto("ok"));

    mockMvc
        .perform(
            get("/email/verify/resend")
                .content("{\"email\": \"john.doe@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("ok")));
  }

  @Test
  void resend_with_invalid_email_throws_exception() throws Exception {

    Mockito.when(emailVerificationService.resend(Mockito.any()))
        .thenThrow(new InvalidEmailException());

    mockMvc
        .perform(
            get("/email/verify/resend")
                .content("{\"email\": \"john.doegmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid email")));
  }

  @Test
  void resend_to_verified_email_throws_exception() throws Exception {

    Mockito.when(emailVerificationService.resend(Mockito.any()))
        .thenThrow(new EmailAlreadyVerifiedException());

    mockMvc
        .perform(
            get("/email/verify/resend")
                .content("{\"email\": \"john@doegmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Email already verified!")));
  }
}
