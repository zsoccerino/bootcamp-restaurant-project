package com.gfa.users.controllers;

import com.gfa.users.dtos.TeamRequestDto;
import com.gfa.users.services.TeamService;
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
public class TeamRestController {
  private final TeamService teamService;

  public TeamRestController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/teams")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> index() {
    return ResponseEntity.ok(teamService.findAll());
  }

  @PostMapping("/teams")
  public ResponseEntity<? extends Object> store(@RequestBody TeamRequestDto teamRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(teamService.store(teamRequestDto));
  }

  @GetMapping("/teams/{id}")
  public ResponseEntity<?> show(@PathVariable("id") String id) {
    return ResponseEntity.status(HttpStatus.OK).body(teamService.show(Long.parseLong(id)));
  }

  @PatchMapping("/teams/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") String id, @NotNull @RequestBody TeamRequestDto teamNameDto) {
    TeamRequestDto teamRequestDto = new TeamRequestDto(Long.parseLong(id), teamNameDto.name);
    return ResponseEntity.status(HttpStatus.OK).body(teamService.update(teamRequestDto));
  }

  @DeleteMapping("/teams/{id}")
  public ResponseEntity<?> destroy(@PathVariable("id") String id) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(teamService.destroy(Long.parseLong(id)));
  }
}
