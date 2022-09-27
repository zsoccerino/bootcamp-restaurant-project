package com.gfa.users.services;

import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.StatusResponseDto;

import java.util.List;

public interface PermissionService {

  List<PermissionResponseDto> findAll();

  PermissionResponseDto store(PermissionRequestDto permissionRequestDto);

  PermissionResponseDto show(Long id);

  PermissionResponseDto update(PermissionRequestDto permissionRequestDto);

  StatusResponseDto destroy(Long id);
}
