package com.gfa.users.services;

import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.dtos.TeamRequestDto;
import com.gfa.users.dtos.TeamResponseDto;
import java.util.List;

public interface TeamService {
  List<TeamResponseDto> findAll();

  TeamResponseDto store(TeamRequestDto dto);

  TeamResponseDto show(Long id);

  TeamResponseDto update(TeamRequestDto dto);

  StatusResponseDto destroy(Long id);
}
