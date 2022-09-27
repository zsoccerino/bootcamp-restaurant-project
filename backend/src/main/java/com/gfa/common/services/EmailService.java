package com.gfa.common.services;

import com.gfa.common.exceptions.InvalidEmailException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  public void verify(String email) {

    try {
      InternetAddress internetAddress = new InternetAddress(email);
      internetAddress.validate();

    } catch (AddressException e) {
      throw new InvalidEmailException();
    }
  }
}
