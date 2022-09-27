package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class RoleRequestDto extends RequestDto {
  public final String name;
  public final Long id;

  @JsonCreator
  public RoleRequestDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
