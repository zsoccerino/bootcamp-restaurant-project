package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.common.dtos.IdRequestDto;
import com.gfa.users.models.Role;

public class RoleResponseDto extends IdRequestDto {
  public final String name;

  @JsonCreator
  public RoleResponseDto(Long id, String name) {
    super(id);
    this.name = name;
  }

  @JsonCreator
  public RoleResponseDto(Role role) {
    super(role);
    this.name = role.getName();
  }
}
