package com.gfa.users.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewUserRequestDtoTest {

  @Test
  void can_create_dto() {
    NewUserRequestDto newUserRequestDto =
        new NewUserRequestDto("John123456", "johm@gmail.com", "password123");
    assertEquals("John123456", newUserRequestDto.username);
    assertEquals("johm@gmail.com", newUserRequestDto.email);
    assertEquals("password123", newUserRequestDto.password);
    assertEquals(newUserRequestDto, newUserRequestDto);
    assertEquals(newUserRequestDto.username.length(), 10);
    assertEquals(newUserRequestDto.password.length(), 11);
  }
}
