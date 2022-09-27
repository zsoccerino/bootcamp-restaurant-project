package com.gfa.common.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailRequestDtoTest {

  @Test
  void can_create_email_dto() {
    EmailRequestDto dto = new EmailRequestDto("Feri@gmail.com");
    assertEquals("Feri@gmail.com", dto.email);
  }
}
