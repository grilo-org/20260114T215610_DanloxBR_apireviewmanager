package com.bringto.api.Application.config.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtil {

    private static final String ALGO = "AES/CBC/PKCS5Padding";
    private final SecretKeySpec keySpec;
    private final SecureRandom random = new SecureRandom();

    public CryptoUtil(String secret) {
        byte[] keyBytes = secret.getBytes();
        byte[] key = new byte[16];
        System.arraycopy(keyBytes, 0, key, 0, Math.min(keyBytes.length, key.length));
        this.keySpec = new SecretKeySpec(key, "AES");
    }

    public String encrypt(String plain) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plain.getBytes("UTF-8"));
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(cipherText);
        byte[] iv = new byte[16];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        byte[] enc = new byte[combined.length - iv.length];
        System.arraycopy(combined, iv.length, enc, 0, enc.length);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] original = cipher.doFinal(enc);
        return new String(original, "UTF-8");
    }
}
