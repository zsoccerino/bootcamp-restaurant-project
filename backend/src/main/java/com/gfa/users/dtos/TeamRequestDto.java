package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TeamRequestDto extends RequestDto {
  public final String name;
  public final Long id;

  @JsonCreator
  public TeamRequestDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
