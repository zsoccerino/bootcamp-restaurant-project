package com.gfa.users.services;

import com.gfa.common.dtos.IdRequestDto;
import com.gfa.common.dtos.NewUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.dtos.UserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
  ResponseEntity<ResponseDto> index();

  ResponseEntity<ResponseDto> show(IdRequestDto dto);

  public UserResponseDto store(NewUserRequestDto newUserRequestDto);
}
