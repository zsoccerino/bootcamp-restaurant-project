package com.gfa.users.services;

public interface TotpManager {
  String generateSecret();

  String getUriForImage(String secret);

  boolean verifyCode(String code, String secret);
}
