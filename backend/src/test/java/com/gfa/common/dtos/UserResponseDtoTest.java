package com.gfa.foxdining.common.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gfa.users.dtos.UserResponseDto;
import com.gfa.users.models.User;
import org.junit.jupiter.api.Test;

public class UserResponseDtoTest {
  @Test
  void can_create_UserResponseDto() {
    User user = new User();
    UserResponseDto userResponseDto = new UserResponseDto(user);
    assertEquals(user, userResponseDto.user);
  }
}
