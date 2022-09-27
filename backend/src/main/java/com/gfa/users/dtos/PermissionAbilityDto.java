package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionAbilityDto {

  @JsonProperty("ability")
  public final String ability;

  @JsonCreator
  public PermissionAbilityDto(String ability) {
    this.ability = ability;
  }
}
