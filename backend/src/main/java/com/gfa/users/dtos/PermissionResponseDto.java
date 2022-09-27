package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfa.common.dtos.IdRequestDto;
import com.gfa.users.models.Permission;

public class PermissionResponseDto extends IdRequestDto {

  @JsonCreator
  public PermissionResponseDto(Long id, String ability) {
    super(id);
    this.ability = ability;
  }

  @JsonCreator
  public PermissionResponseDto(Permission permission) {
    super(permission);
    this.ability = permission.getAbility();
  }

  @JsonProperty("ability")
  public final String ability;

  @Override
  public Long getId() {
    return super.getId();
  }
}
