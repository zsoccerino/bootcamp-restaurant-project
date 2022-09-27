package com.gfa.common.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdRequestDtoTest {

  @Test
  void can_create_id_dto() {
    IdRequestDto dto = new IdRequestDto(15L);
    assertEquals(15L, dto.id);
  }
}
