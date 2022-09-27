package com.gfa.users.services;

import com.gfa.users.dtos.RoleRequestDto;
import com.gfa.users.dtos.RoleResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import java.util.List;

public interface RoleService {
  List<RoleResponseDto> findAll();

  RoleResponseDto store(RoleRequestDto dto);

  RoleResponseDto show(Long id);

  RoleResponseDto update(RoleRequestDto dto);

  StatusResponseDto destroy(Long id);
}
