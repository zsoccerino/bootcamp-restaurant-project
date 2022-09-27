package com.gfa.users.services;

import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.exceptions.DuplicatePermissionException;
import com.gfa.users.exceptions.PermissionIsMissingException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.models.Permission;
import com.gfa.users.repositories.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatabasePermissionService implements PermissionService {

  private final PermissionRepository permissionRepository;

  public DatabasePermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public List<PermissionResponseDto> findAll() {

    List<Permission> permissions = permissionRepository.findAll();

    List<PermissionResponseDto> permissionResponseDtos = new ArrayList<>();

    for (Permission permission : permissions) {
      PermissionResponseDto permissionResponseDto =
          new PermissionResponseDto(permission.getId(), permission.getAbility());
      permissionResponseDtos.add(permissionResponseDto);
    }
    return permissionResponseDtos;
  }

  @Override
  public PermissionResponseDto store(PermissionRequestDto permissionRequestDto) {

    String receivedAbility = permissionRequestDto.ability;

    if (receivedAbility.isEmpty()) {
      throw new PermissionIsMissingException();
    }
    if (permissionRepository.existsAllByAbility(receivedAbility)) {
      throw new DuplicatePermissionException();
    }

    Permission permission = permissionRepository.save(new Permission(permissionRequestDto.ability));
    return new PermissionResponseDto(permission);
  }

  @Override
  public PermissionResponseDto show(Long id) {

    Optional<Permission> permission = permissionRepository.findById(id);

    if (!permission.isPresent()) {
      throw new PermissionNotFoundException();
    }

    return new PermissionResponseDto(permission.get());
  }

  @Override
  public PermissionResponseDto update(PermissionRequestDto permissionRequestDto) {

    Optional<Permission> permissionOptional =
        permissionRepository.findById(permissionRequestDto.getId());
    Permission permission = new Permission(permissionRequestDto.id, permissionRequestDto.ability);
    if (!permissionOptional.isPresent()) {
      throw new PermissionNotFoundException();
    }
    if (permissionRepository.existsAllByAbility(permission.getAbility())
        || permission.getAbility().isEmpty()) {
      throw new DuplicatePermissionException();
    }
    permissionRepository.save(permission);

    return new PermissionResponseDto(permission);
  }

  @Override
  public StatusResponseDto destroy(Long id) {
    Optional<Permission> permissionOptional = permissionRepository.findById(id);

    if (!permissionOptional.isPresent()) {
      throw new PermissionNotFoundException();
    }
    permissionRepository.delete(permissionOptional.get());
    return new StatusResponseDto("ok");
  }
}
