package com.gfa.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gfa.users.services.TotpManagerService;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class TotpManagerTest {

  //  @Autowired TotpManagerService totpManagerService;

  @Test
  void can_generate_secret_with_correct_length() {
    TotpManagerService totpManagerService = new TotpManagerService();
    boolean length = false;
    if (totpManagerService.generateSecret().length() == 32) {
      length = true;
    }
    assertEquals(true, length);
  }

  @Test
  void can_verify_code() throws CodeGenerationException {
    TotpManagerService totpManagerService = new TotpManagerService();
    SecretGenerator secretGenerator = new DefaultSecretGenerator(32);
    CodeGenerator codeGenerator = new DefaultCodeGenerator();
    String secret = secretGenerator.generate();
    String code = codeGenerator.generate(secret, 60 * 60 * 60);
    assertEquals(false, totpManagerService.verifyCode(code, secret));
  }
}
