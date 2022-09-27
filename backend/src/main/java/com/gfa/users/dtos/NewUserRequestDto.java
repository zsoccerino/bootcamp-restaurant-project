package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.common.dtos.RequestDto;

public class NewUserRequestDto extends RequestDto {
  public final String username;
  public final String email;
  public final String password;

  @JsonCreator
  public NewUserRequestDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
