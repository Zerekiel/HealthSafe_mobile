package com.eipteam.healthsafe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

class GetBytesOfGeneratedSymmetricKey {

    public static String keyGen() {

        try {

            String algorithm = "DESede";

            // create a key generator
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);

            // generate a key
            SecretKey key = keyGen.generateKey();

            // get the raw key bytes
            byte[] keyBytes = key.getEncoded();

            System.out.println("Key Length: " + keyBytes.length);

            // construct a secret key from the given byte array
            SecretKey keyFromBytes = new SecretKeySpec(keyBytes, algorithm);

            System.out.println("Keys Equal: " + key.equals(keyFromBytes));
            String s = new String(keyBytes);
            return (s);
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm:" + e.getMessage());
            return null;
        }

    }

}

public class HillCipher {
    public static int[][] create_key_matrix(String key) {
        int[][] tab = new int[2][2];
        int idx = 0;

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = key.charAt(idx);
                idx += 1;
            }
        }
        return tab;
    }

    public static String[] make_sized_tab(String message, int size) {

        List<String> parts = new ArrayList<String>();

        int i = 0;
        while (i < message.length()) {
            parts.add(message.substring(i, i + size));
            i += size;
        }
        String[] tab = parts.toArray(new String[0]);
        return tab;
    }

    public static int[][] create_message_matrix(String message) {
        int[][] tab = new int[2][1];
        String[] matrix = make_sized_tab(message, 2);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 1; j++) {
                tab[i][j] = matrix[i].charAt(j);
            }
    // 2, 1
        }
        return tab;

    }

    public static void main(String message) {
        String key = GetBytesOfGeneratedSymmetricKey.keyGen();
        int[][] key_matrix = create_key_matrix(key);
        int[][] message_matrix = create_message_matrix(message);
    }
}