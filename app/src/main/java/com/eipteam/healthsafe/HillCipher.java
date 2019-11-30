package com.eipteam.healthsafe;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // create a matrix of size
    // (square_root(length_of_matrix)) * (length / square_root(length_of_matrix)
    public static int[][] create_key_matrix(String key) {
        int key_len = key.length();
        int x = (int)Math.sqrt(key_len);
        int y = key_len / x;
        int[][] tab = new int[y][x];
        int idx = 0;

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                tab[i][j] = key.charAt(idx);
                idx += 1;
            }
        }
        return tab;
    }

    protected static String complete_matrix(String str, int length, char charToFill) {
        char[] array = new char[str.length() + length];

        for (int i = 0; i < str.length(); i++) {
            array[i] = str.charAt(i);
        }
        int new_len = str.length() + length;
        for (int i = str.length(); i < new_len ; i++) {
            array[i] = '0';
        }

        return new String(array);
    }

    public static int[][] create_message_matrix(String message) {
        int msg_len = message.length();
        int old = msg_len;
        // if message length is not a perfect square
        while (checkPerfectSquare(msg_len) == false) msg_len += 1;
        int diff = msg_len - old;
        // enlarge message and fill with 00 to have a perfect square length
        String adjusted_msg = complete_matrix(message, diff, '0');

        int x = (int)Math.sqrt(adjusted_msg.length());
        int y = adjusted_msg.length() / x;

        int idx = 0; //index of adjusted_msg
        int[][] matrix = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                matrix[i][j] = adjusted_msg.charAt(idx);
                idx += 1;
            }
        }
        return matrix;
    }

    public static int[][] convertIntegers(List<Integer> integers)
    {
        int[][] ret = new int[integers.size()][];
        for (int i=0; i < ret.length; i++)
        {
            for(int x = 0; x < 2; x++) {
             //   ret[i] = integers.get(i).intValue();
            }
        }
        return ret;
    }
    public static int[][] hill(int[][] key, int[] line) {
        int len_line = line.length;

/*
        ArrayList<Integer> list = new ArrayList<Integer>();
        int val;
        for (int y = 0; y < 2; y++) {
            val = 0;
            for(int x = 0; x < 2; x++) {
                val += key[y][x] * line[x];
            }
            list.add(val % 127);
        }
        System.out.println("list size" + list.size());
        System.out.println("LIST : " + list);
*/



        int[][] matrix_product = new int[len_line][len_line];
        int prod;
        for (int y = 0; y < key.length; y++) {
            prod = 0;
            for(int x = 0; x < key.length; x++) {
                prod += key[y][x] * line[x];
                matrix_product[y][x] = prod % 127;
                System.out.println(matrix_product[y][x]);
            }
        }


        // int[] tab = convertIntegers(list);

        return matrix_product;
    }

    public static void matrix_mult(int[][] key_matrix, int[][]message_matrix) {

 //       matrix_msg_x = message_matrix[0].length;
//        matrix_msg_y = message_matrix.length;
//        int size = size_y + size_x;

        for (int y = 1; y < message_matrix.length; y++) {
//            matrix[y] = hill(key_matrix, message_matrix[y]);
            hill(key_matrix, message_matrix[y]);

        }
     //   return matrix;
    }

    static boolean checkPerfectSquare(double x)
    {

        // finding the square root of given number
        double sq = Math.sqrt(x);

        /* Math.floor() returns closest integer value, for
         * example Math.floor of 984.1 is 984, so if the value
         * of sq is non integer than the below expression would
         * be non-zero.
         */
        return ((sq - Math.floor(sq)) == 0);
    }

    public static int main(String message) {
        String key ="abcd";
        if (!checkPerfectSquare(key.length())) {
            System.out.println("WRONG KEY : length of the key should be a perfect square root");
            return 1;
        }
//        String key = GetBytesOfGeneratedSymmetricKey.keyGen();
        int[][] key_matrix = create_key_matrix(key);
        int[][] message_matrix = create_message_matrix(message);
        matrix_mult(key_matrix, message_matrix);
        return 0;
    }
}