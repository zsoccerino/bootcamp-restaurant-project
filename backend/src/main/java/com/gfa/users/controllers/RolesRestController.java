package com.gfa.users.controllers;

import com.gfa.users.dtos.RoleRequestDto;
import com.gfa.users.services.RoleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RolesRestController {
  private final RoleService roleService;

  public RolesRestController(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping("/roles")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> index() {
    return ResponseEntity.ok(roleService.findAll());
  }

  @PostMapping("/roles")
  public ResponseEntity<? extends Object> store(@RequestBody RoleRequestDto roleRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(roleService.store(roleRequestDto));
  }

  @GetMapping("/roles/{id}")
  public ResponseEntity<?> show(@PathVariable("id") String id) {
    return ResponseEntity.status(HttpStatus.OK).body(roleService.show(Long.parseLong(id)));
  }

  @PatchMapping("/roles/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") String id, @NotNull @RequestBody RoleRequestDto roleNameDto) {
    RoleRequestDto roleRequestDto = new RoleRequestDto(Long.parseLong(id), roleNameDto.name);
    return ResponseEntity.status(HttpStatus.OK).body(roleService.update(roleRequestDto));
  }

  @DeleteMapping("/roles/{id}")
  public ResponseEntity<?> destroy(@PathVariable("id") String id) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(roleService.destroy(Long.parseLong(id)));
  }
}
