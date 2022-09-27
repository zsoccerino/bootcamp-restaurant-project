package com.gfa.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Configuration
public class MailConfig {

  private final Environment environment;

  @Autowired
  public MailConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public JavaMailSender setJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(environment.getProperty("spring.mail.host", "smtp.mailtrap.io"));
    mailSender.setPort(
        Integer.parseInt(
            Objects.requireNonNull(environment.getProperty("spring.mail.port", "2525"))));
    mailSender.setUsername(environment.getProperty("spring.mail.username"));
    mailSender.setPassword(environment.getProperty("spring.mail.password"));

    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.starttls.enable", "true");
    properties.setProperty("mail.debug", "true");
    mailSender.setJavaMailProperties(properties);
    return mailSender;
  }
}
