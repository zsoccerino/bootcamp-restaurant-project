package com.gfa.users.services;

import com.gfa.users.dtos.RoleRequestDto;
import com.gfa.users.dtos.RoleResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.exceptions.EmptyRequestBodyException;
import com.gfa.users.exceptions.EmptyRoleException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.RoleAlreadyExistsException;
import com.gfa.users.exceptions.RoleNotFoundException;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRoleService implements RoleService {
  private final RoleRepository roleRepository;

  public DatabaseRoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public List<RoleResponseDto> findAll() {
    List<Role> roles = roleRepository.findAll();
    List<RoleResponseDto> roleResponseDtos = new ArrayList<>();
    for (Role role : roles) {
      RoleResponseDto roleResponseDto = new RoleResponseDto(role.getId(), role.getName());
      roleResponseDtos.add(roleResponseDto);
    }
    return roleResponseDtos;
  }

  @Override
  public RoleResponseDto store(RoleRequestDto dto) {

    String receivedName = dto.name;

    if (dto == null) {
      throw new EmptyRequestBodyException();
    }
    if (receivedName == null || receivedName.isEmpty()) {
      throw new EmptyRoleException();
    }
    if (roleRepository.existsAllByName(receivedName)) {
      throw new RoleAlreadyExistsException();
    }

    Role role = roleRepository.save(new Role(dto.name));
    return new RoleResponseDto(role);
  }

  @Override
  public RoleResponseDto show(Long id) {

    Optional<Role> role = roleRepository.findById(id);

    if (!role.isPresent()) {
      throw new EmptyRoleException();
    }
    if (id < 1) {
      throw new InvalidIdException();
    }

    return new RoleResponseDto(role.get());
  }

  @Override
  public RoleResponseDto update(RoleRequestDto dto) {

    Optional<Role> roleOptional = roleRepository.findById(dto.id);
    Role role = new Role(dto.id, dto.name);
    if (!roleOptional.isPresent()) {
      throw new EmptyRoleException();
    }
    if (dto.id < 1) {
      throw new InvalidIdException();
    }
    if (!roleRepository.existsAllByName(role.getName()) || role.getName().isEmpty()) {
      throw new RoleNotFoundException();
    }
    roleRepository.save(role);

    return new RoleResponseDto(role);
  }

  @Override
  public StatusResponseDto destroy(Long id) {
    Optional<Role> roleOptional = roleRepository.findById(id);

    if (!roleOptional.isPresent()) {
      throw new EmptyRoleException();
    }
    if (id < 1) {
      throw new InvalidIdException();
    }
    roleRepository.delete(roleOptional.get());
    return new StatusResponseDto("ok");
  }
}
