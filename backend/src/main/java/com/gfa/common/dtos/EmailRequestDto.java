package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class EmailRequestDto extends RequestDto {

  public final String email;

  @JsonCreator
  public EmailRequestDto(String email) {
    this.email = email;
  }
}
