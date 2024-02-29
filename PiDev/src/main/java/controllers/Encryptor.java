package controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    public String encryptString(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messagedigest = md.digest(input.getBytes());
        BigInteger bigint = new BigInteger(1,messagedigest);
        return bigint.toString(16);
    }
}
