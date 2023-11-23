package hr.carpazar.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {
    public static String generateSHA512(String plainPassword){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            byte[] bytes = plainPassword.getBytes();
            byte[] passwordDigest = messageDigest.digest(bytes);

            StringBuilder stringBuilder = new StringBuilder();

            for(byte b: passwordDigest){
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException noSuchAlgorithmException){
            System.out.println("nepostojeci algoritam"); // <---- handleat nepostojanje algoritma
            return null;
        }
    }
}
