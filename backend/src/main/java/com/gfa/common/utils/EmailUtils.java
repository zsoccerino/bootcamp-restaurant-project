package com.gfa.common.utils;

import com.gfa.common.dtos.EmailResponseDto;
import com.gfa.common.exceptions.InvalidEmailException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

  private final JavaMailSender emailSender;

  @Autowired
  public EmailUtils(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  public EmailResponseDto sendTextEmail(String to, String from, String subject, String text) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom(from);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);

    return new EmailResponseDto(to, from, subject, text);
  }

  public EmailResponseDto sendHtmlEmail(String to, String from, String subject, String text) {

    MimeMessage message = emailSender.createMimeMessage();

    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text, true);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    emailSender.send(message);
    return new EmailResponseDto(to, from, subject, text);
  }

  public void validate(String email) {

    try {
      InternetAddress internetAddress = new InternetAddress(email);
      internetAddress.validate();
    } catch (AddressException e) {
      throw new InvalidEmailException();
    }
  }
}
