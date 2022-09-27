package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.common.dtos.RequestDto;

public class PasswordResetRequestDto extends RequestDto {

  public final String password;

  @JsonCreator
  public PasswordResetRequestDto(String password) {
    this.password = password;
  }
}
