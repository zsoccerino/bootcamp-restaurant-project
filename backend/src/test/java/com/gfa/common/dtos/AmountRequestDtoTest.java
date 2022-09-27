package com.gfa.common.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountRequestDtoTest {

  @Test
  void can_create_id_amount_dto() {
    AmountRequestDto dto = new AmountRequestDto(15L, 10);
    assertEquals(15L, dto.id);
    assertEquals(10, dto.amount);
  }
}
