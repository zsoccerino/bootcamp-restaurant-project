package com.gfa.common.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class EmailResponseDto extends ResponseDto {

  public final String to;
  public final String from;
  public final String subject;
  public final String text;

  @JsonCreator
  public EmailResponseDto(String to, String from, String subject, String text) {
    this.to = to;
    this.from = from;
    this.subject = subject;
    this.text = text;
  }

  public String getTo() {
    return to;
  }

  public String getFrom() {
    return from;
  }

  public String getSubject() {
    return subject;
  }

  public String getText() {
    return text;
  }
}
