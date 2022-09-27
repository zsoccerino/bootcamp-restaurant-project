package com.gfa.users.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gfa.common.dtos.NewUserRequestDto;
import com.gfa.common.exceptions.InvalidEmailException;
import com.gfa.users.exceptions.EmptyEmailException;
import com.gfa.users.exceptions.EmptyPasswordException;
import com.gfa.users.exceptions.EmptyRequestBodyException;
import com.gfa.users.exceptions.EmptyUsernameException;
import com.gfa.users.exceptions.ShortPasswordException;
import com.gfa.users.exceptions.ShortUsernameException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class UserServiceTest {
  @Autowired UserService service;
  private static final String VALID_USERNAME = "johndoe";
  private static final String VALID_EMAIL = "john.doe@gmail.com";
  private static final String VALID_PASSWORD = "AaBbCcDd1!2@3#4$5%";

  @Test
  void store_with_no_request_body_throws_an_exception() {
    //  UserService service = new UserServiceImpl(null, null);
    assertThrows(
        EmptyRequestBodyException.class,
        () -> {
          service.store(null);
        });
  }

  @Test
  void creating_new_user_with_short_username_throws_exception() {
    // UserService service = new UserServiceImpl(null, null);
    assertThrows(
        ShortUsernameException.class,
        () -> {
          service.store(new NewUserRequestDto("a", VALID_EMAIL, VALID_PASSWORD));
        });
  }

  @Test
  void creating_new_user_with_short_password_throws_exception() {
    //  UserService service = new UserServiceImpl(null, null);
    assertThrows(
        ShortPasswordException.class,
        () -> {
          service.store(new NewUserRequestDto(VALID_USERNAME, VALID_EMAIL, "pass"));
        });
  }

  @Test
  void creating_new_user_with_empty_email_throws_exception() {
    //  UserService service = new UserServiceImpl(null, null);
    assertThrows(
        EmptyEmailException.class,
        () -> {
          service.store(new NewUserRequestDto(VALID_USERNAME, null, VALID_PASSWORD));
        });
    assertThrows(
        EmptyEmailException.class,
        () -> {
          service.store(new NewUserRequestDto(VALID_USERNAME, "", VALID_PASSWORD));
        });
  }

  @Test
  void creating_new_user_with_empty_password_throws_exception() {
    //  UserService service = new UserServiceImpl(null, null);
    assertThrows(
        EmptyPasswordException.class,
        () -> {
          service.store(new NewUserRequestDto(VALID_USERNAME, VALID_EMAIL, null));
        });
    assertThrows(
        EmptyPasswordException.class,
        () -> {
          service.store(new NewUserRequestDto(VALID_USERNAME, VALID_EMAIL, ""));
        });
  }

  @Test
  void creating_new_user_with_empty_username_throws_exception() {
    //   UserService service = new UserServiceImpl(null, null);
    assertThrows(
        EmptyUsernameException.class,
        () -> {
          service.store(new NewUserRequestDto(null, VALID_EMAIL, VALID_PASSWORD));
        });
    assertThrows(
        EmptyUsernameException.class,
        () -> {
          service.store(new NewUserRequestDto("", VALID_EMAIL, VALID_PASSWORD));
        });
  }

  @Test
  void creating_new_user_with_invalid_email_throws_exception() {
    // UserService service = new UserServiceImpl(null, new EmailUtils());
    assertThrows(
        InvalidEmailException.class,
        () -> {
          service.store(new NewUserRequestDto("abcdefghijklmnopqrst", "email", "passwordpassword"));
        });
  }
}
