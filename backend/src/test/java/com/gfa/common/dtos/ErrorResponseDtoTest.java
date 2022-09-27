package com.gfa.common.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseDtoTest {

  @Test
  void can_create_errorMessage_dto() {
    ErrorResponseDto dto = new ErrorResponseDto("Wrong!");
    assertEquals("Wrong!", dto.message);
  }
}
