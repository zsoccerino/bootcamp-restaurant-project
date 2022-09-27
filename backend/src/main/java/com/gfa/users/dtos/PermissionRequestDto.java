package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfa.common.dtos.IdRequestDto;

public class PermissionRequestDto extends IdRequestDto {

  public PermissionRequestDto(Long id, String ability) {
    super(id);
    this.ability = ability;
  }

  @JsonProperty("ability")
  public final String ability;
}
