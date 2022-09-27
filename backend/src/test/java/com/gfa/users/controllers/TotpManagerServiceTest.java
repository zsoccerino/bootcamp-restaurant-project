// package com.gfa.foxdining.controllers;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
// import com.gfa.users.services.TotpManager;
// import com.gfa.users.services.TotpManagerService;
// import dev.samstevens.totp.secret.DefaultSecretGenerator;
// import dev.samstevens.totp.secret.SecretGenerator;
// import org.junit.jupiter.api.Test;
//
// public class TotpManagerServiceTest {
//  @Test
//  void can_generate_secret() {
//    TotpManager service = new TotpManagerService();
//    assertNotEquals("somerandomcode", service.generateSecret());
//  }
//
//  @Test
//  void can_generate_QR_code_uri() {
//    TotpManager service = new TotpManagerService();
//    SecretGenerator secretGenerator = new DefaultSecretGenerator(32);
//    assertEquals("", service.getUriForImage(secretGenerator.generate()));
//  }
//  @Test
//  void can_verify_the_code() {
//    TotpManager service = new TotpManagerService();
//    SecretGenerator secretGenerator = new DefaultSecretGenerator(32);
//    boolean exp = false;
//    assertEquals(exp, service.verifyCode("abcdef",secretGenerator.generate() ));
//  }
// }
