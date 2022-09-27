package com.gfa.users.services;

import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.dtos.TeamRequestDto;
import com.gfa.users.dtos.TeamResponseDto;
import com.gfa.users.exceptions.EmptyRequestBodyException;
import com.gfa.users.exceptions.EmptyTeamException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.TeamAlreadyExistsException;
import com.gfa.users.exceptions.TeamNotFoundException;
import com.gfa.users.models.Team;
import com.gfa.users.repositories.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DatabaseTeamService implements TeamService {
  private final TeamRepository teamRepository;

  public DatabaseTeamService(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public List<TeamResponseDto> findAll() {
    List<Team> teams = teamRepository.findAll();
    List<TeamResponseDto> teamResponseDtos = new ArrayList<>();
    for (Team team : teams) {
      TeamResponseDto teamResponseDto = new TeamResponseDto(team.getId(), team.getName());
      teamResponseDtos.add(teamResponseDto);
    }
    return teamResponseDtos;
  }

  @Override
  public TeamResponseDto store(TeamRequestDto dto) {

    String receivedName = dto.name;

    if (dto == null) {
      throw new EmptyRequestBodyException();
    }
    if (receivedName == null || receivedName.isEmpty()) {
      throw new EmptyTeamException();
    }
    if (teamRepository.existsAllByName(receivedName)) {
      throw new TeamAlreadyExistsException();
    }

    Team team = teamRepository.save(new Team(dto.name));
    return new TeamResponseDto(team);
  }

  @Override
  public TeamResponseDto show(Long id) {

    Team team = teamRepository.findById(id).orElseThrow(TeamNotFoundException::new);

    if (id < 1) {
      throw new InvalidIdException();
    }

    return new TeamResponseDto(team);
  }

  @Override
  public TeamResponseDto update(TeamRequestDto dto) {

    Optional<Team> roleOptional = teamRepository.findById(dto.id);
    Team team = new Team(dto.id, dto.name);
    if (!roleOptional.isPresent()) {
      throw new EmptyTeamException();
    }
    if (dto.id < 1) {
      throw new InvalidIdException();
    }
    if (!teamRepository.existsAllByName(team.getName()) || team.getName().isEmpty()) {
      throw new TeamNotFoundException();
    }
    teamRepository.save(team);

    return new TeamResponseDto(team);
  }

  @Override
  public StatusResponseDto destroy(Long id) {
    Optional<Team> roleOptional = teamRepository.findById(id);

    if (!roleOptional.isPresent()) {
      throw new EmptyTeamException();
    }
    if (id < 1) {
      throw new InvalidIdException();
    }
    teamRepository.delete(roleOptional.get());
    return new StatusResponseDto("ok");
  }
}
