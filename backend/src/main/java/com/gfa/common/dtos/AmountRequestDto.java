package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AmountRequestDto extends IdRequestDto {

  public final Integer amount;

  @JsonCreator
  public AmountRequestDto(Long id, Integer amount) {
    super(id);
    this.amount = amount;
  }
}
