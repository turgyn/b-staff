package kz.berekebank.qrauth.utils;

import kz.berekebank.qrauth.dto.GenerateQrRq;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryptor {
    private static final String SECRET_KEY = "9C287B4F876FFFE="; // 128 bit key
    private static final String ALGORITHM = "AES";
    private static final String DELIMETER = "%del#";

    public static String encrypt(GenerateQrRq reqData) throws Exception{
        return encrypt(reqData.getPcId() + DELIMETER + reqData.getUsername() + DELIMETER + System.currentTimeMillis()/1000);
    }

    public static GenerateQrRq decrypt(String encryptedText) throws Exception {
        String text = decryptIn(encryptedText);
        System.out.println(text);
        String[] v = text.split(DELIMETER);
        System.out.println("8");
        System.out.println(v.length);
        for (String s: v) {
            System.out.println("7" + s + "9");
        }

        String pcId = v[0];
        String userId = v[1];
        String qrCreationTimeStamp = v[2];
        long creationtimestamp = Long.parseLong(qrCreationTimeStamp);

        return new GenerateQrRq(pcId, userId, creationtimestamp);
    }


    private static String encrypt(String plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decryptIn(String encryptedText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
