package com.gfa.users.controllers;

import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.users.dtos.PermissionAbilityDto;
import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.exceptions.DuplicatePermissionException;
import com.gfa.users.exceptions.PermissionIsMissingException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.services.PermissionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
public class PermissionRestController {

  private final PermissionService permissionService;

  public PermissionRestController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> index() {
    return ResponseEntity.ok(permissionService.findAll());
  }

  @PostMapping("")
  public ResponseEntity<? extends Object> store(
      @RequestBody PermissionRequestDto permissionRequestDto) {

    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(permissionService.store(permissionRequestDto));
    } catch (PermissionIsMissingException e) {
      return ResponseEntity.badRequest().body(new ErrorResponseDto("Ability is required"));
    } catch (DuplicatePermissionException exc) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new ErrorResponseDto("Ability already exists"));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable("id") String id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(permissionService.show(Long.parseLong(id)));
    } catch (NumberFormatException exception) {
      return ResponseEntity.badRequest().body(new ErrorResponseDto("Invalid id"));
    } catch (PermissionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ErrorResponseDto("Permission not found"));
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") String id,
      @NotNull @RequestBody PermissionAbilityDto permissionAbilityDto) {
    try {
      PermissionRequestDto permissionRequestDto =
          new PermissionRequestDto(Long.parseLong(id), permissionAbilityDto.ability);
      return ResponseEntity.status(HttpStatus.OK)
          .body(permissionService.update(permissionRequestDto));
    } catch (NumberFormatException exception) {
      return ResponseEntity.badRequest().body(new ErrorResponseDto("Invalid id"));
    } catch (PermissionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ErrorResponseDto("Permission not found"));
    } catch (IllegalArgumentException | DuplicatePermissionException err) {
      return ResponseEntity.badRequest().body(new ErrorResponseDto("Invalid data"));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> destroy(@PathVariable("id") String id) {
    try {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(permissionService.destroy(Long.parseLong(id)));
    } catch (NumberFormatException exception) {
      return ResponseEntity.badRequest().body(new ErrorResponseDto("Invalid id"));
    } catch (PermissionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ErrorResponseDto("Permission not found"));
    }
  }
}
