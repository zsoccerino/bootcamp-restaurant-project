package com.gfa.users.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gfa.common.dtos.IdRequestDto;
import com.gfa.users.models.Team;

public class TeamResponseDto extends IdRequestDto {
  public final String name;

  @JsonCreator
  public TeamResponseDto(Long id, String name) {
    super(id);
    this.name = name;
  }

  @JsonCreator
  public TeamResponseDto(Team team) {
    super(team.getId());
    this.name = team.getName();
  }
}
