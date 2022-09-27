package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;

public class IdRequestDto extends RequestDto {

  public final Long id;

  @JsonCreator
  public IdRequestDto(Long id) {
    this.id = id;
  }

  public IdRequestDto(Permission permission) {

    this.id = permission.getId();
  }

  public IdRequestDto(Role role) {

    this.id = role.getId();
  }

  public Long getId() {
    return id;
  }
}
