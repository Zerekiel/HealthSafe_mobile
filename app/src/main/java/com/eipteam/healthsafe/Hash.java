package com.eipteam.healthsafe;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

public class Hash {

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String hashString(String str) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA256");
            final byte[] hashbytes = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            String hashedStr = bytesToHex(hashbytes);

            final MessageDigest dig = MessageDigest.getInstance("SHA3_256");
            final byte[] hashbytes2 = dig.digest(
                    hashedStr.getBytes(StandardCharsets.UTF_8));
            String sha3_256hex = bytesToHex(hashbytes2);
            Log.d("HASHED STRING", sha3_256hex);
            //return hashedStr;
            return sha3_256hex;

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return null;
    }
}
