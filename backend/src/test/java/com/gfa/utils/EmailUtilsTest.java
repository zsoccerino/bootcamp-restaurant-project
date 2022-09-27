package com.gfa.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gfa.common.dtos.EmailResponseDto;
import com.gfa.common.utils.EmailUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest
class EmailUtilsTest {

  @Autowired EmailUtils emailUtils;

  AutoCloseable autoCloseable;

  @BeforeEach
  public void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void sendTextMailOk() {
    EmailResponseDto message = emailUtils.sendTextEmail("you", "me", "test", "test text");

    assertEquals("you", message.getTo());
    assertEquals("me", message.getFrom());
    assertEquals("test", message.getSubject());
    assertEquals("test text", message.getText());
  }

  @Test
  void sendHtmlMailOk() {
    EmailResponseDto message = emailUtils.sendHtmlEmail("you", "me", "test", "<h1>Test text</h1>");

    assertEquals("you", message.getTo());
    assertEquals("me", message.getFrom());
    assertEquals("test", message.getSubject());
    assertEquals("<h1>Test text</h1>", message.getText());
  }
}
