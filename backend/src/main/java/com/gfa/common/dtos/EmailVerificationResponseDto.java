package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class EmailVerificationResponseDto extends ResponseDto {

  public final String status;

  @JsonCreator
  public EmailVerificationResponseDto(String status) {
    this.status = status;
  }
}
