package hr.carpazar.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;

@Service
public class HashService {
    public static String generateSHA512(String plainPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            byte[] bytes = plainPassword.getBytes();
            byte[] passwordDigest = messageDigest.digest(bytes);

            return bytesToHex(passwordDigest);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    public static Boolean comparePasswords(String userInput, String hashedPassword) {
        String userInputHashed = generateSHA512(userInput);
        return userInputHashed.equals(hashedPassword);
    }
}
