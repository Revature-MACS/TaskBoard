package com.example.TaskBoard.util;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

public class PasswordUtility {

    // This acts as a singleton implementation
    private static final SecretKey SECRET_KEY = initSecretKeyAndInitVector();
    private static GCMParameterSpec INIT_VECTOR;

    private static SecretKey initSecretKeyAndInitVector(){
        if(SECRET_KEY == null){
            Properties envVars = new Properties();
            Path envFile = Paths.get(".env");
            try(InputStream inputStream = Files.newInputStream(envFile)){
                envVars.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String ivString = envVars.getProperty("INIT_VECTOR");
            INIT_VECTOR = new GCMParameterSpec(128, ivString.getBytes(StandardCharsets.UTF_8));

            String keyText = envVars.getProperty("PASS_SECRET_KEY");

            if(keyText.length() != 32){
                throw new IllegalArgumentException("Password secret key is expected to be exactly 32 bytes long. Please check your key and try again.");
            }

            return new SecretKeySpec(keyText.getBytes(StandardCharsets.UTF_8), "AES");
        }

        return SECRET_KEY;
    }

    public static String encryptPassword(String password, String salt){

        // These few steps could throw 6 unique exceptions!
        try{
            // Create cipher
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY, INIT_VECTOR);

            // Encrypt the password and add salt
            return Base64.getEncoder().encodeToString(
                    cipher.doFinal((password + salt).getBytes())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSalt(){
        StringBuilder salt = new StringBuilder();
        byte[] randBytes = new byte[32];
        new SecureRandom().nextBytes(randBytes);

        for (byte randByte : randBytes) {
            salt.append(randByte);
        }

        return salt.toString();
    }
}
