package com.gfa.common.dtos;

public class ErrorResponseDto extends ResponseDto {

  public final String message;

  public ErrorResponseDto(String message) {
    this.message = message;
  }
}
