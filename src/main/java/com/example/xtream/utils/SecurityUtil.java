package com.example.xtream.utils;

import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class SecurityUtil {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    // key phải 16/24/32 bytes (ở đây demo 32 bytes)
    private static final byte[] KEY = "12345678901234567890123456789012".getBytes();

    public static String encrypt(String input) {
        try{
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 1. tạo IV random
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            // 2. init
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, "AES"), spec);

            // 3. encrypt
            byte[] encrypted = cipher.doFinal(input.getBytes());

            // 4. ghép IV + data
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

            // 5. base64
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    public static String decrypt(String cipherText) {
        try {
            byte[] decoded = Base64.getDecoder().decode(cipherText);

            // 1. tách IV
            byte[] iv = Arrays.copyOfRange(decoded, 0, IV_LENGTH);
            byte[] encrypted = Arrays.copyOfRange(decoded, IV_LENGTH, decoded.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // 2. init
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, "AES"), spec);

            // 3. decrypt
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }
}
