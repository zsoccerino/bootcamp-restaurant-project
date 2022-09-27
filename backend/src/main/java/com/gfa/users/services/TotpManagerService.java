package com.gfa.users.services;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import com.gfa.users.exceptions.UnableToGenerateQrKeyException;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotpManagerService implements TotpManager {

  @Autowired
  public TotpManagerService() {}

  @Override
  public String generateSecret() {

    SecretGenerator secretGenerator = new DefaultSecretGenerator(32);
    return secretGenerator.generate();
  }

  @Override
  public String getUriForImage(String secret) {
    QrData data =
        new QrData.Builder()
            .label("Two-factor-auth-test")
            .secret(secret)
            .issuer("exampleTwoFactor")
            .algorithm(HashingAlgorithm.SHA1)
            .digits(6)
            .period(30)
            .build();

    QrGenerator generator = new ZxingPngQrGenerator();
    byte[] imageData = new byte[0];

    try {
      imageData = generator.generate(data);
    } catch (QrGenerationException e) {
      throw new UnableToGenerateQrKeyException();
    }

    String mimeType = generator.getImageMimeType();

    return getDataUriForImage(imageData, mimeType);
  }

  @Override
  public boolean verifyCode(String code, String secret) {
    TimeProvider timeProvider = new SystemTimeProvider();
    CodeGenerator codeGenerator = new DefaultCodeGenerator();
    CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    //  codeGenerator.generate()
    return verifier.isValidCode(secret, code);
  }
}
