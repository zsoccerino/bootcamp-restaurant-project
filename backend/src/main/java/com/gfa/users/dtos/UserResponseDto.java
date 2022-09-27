package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.users.models.User;

public class UserResponseDto {
  public final User user;

  @JsonCreator
  public UserResponseDto(User user) {
    this.user = user;
  }
}
