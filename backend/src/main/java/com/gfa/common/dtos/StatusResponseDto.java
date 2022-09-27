package com.gfa.common.dtos;

public class StatusResponseDto extends ResponseDto {
  public final String status;

  public StatusResponseDto(String status) {
    this.status = status;
  }
}
