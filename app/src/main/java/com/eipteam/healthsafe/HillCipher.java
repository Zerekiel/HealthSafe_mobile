package com.eipteam.healthsafe;


import java.security.NoSuchAlgorithmException;

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

    // create a key matrix of size
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

    // Add 0 at the end of matrix if message matrix is not a perfect square
    // and matrix has been extended
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

    //create a message matrix of size perfect square matrix
    public static int[][] create_message_matrix(String message) {
        int msg_len = message.length();
        int old = msg_len;
        // if message length is not a perfect square, enlarge size until perfect square len
        while (checkPerfectSquare(msg_len) == false) msg_len += 1;
        int diff = msg_len - old;
        // fill with 00 end of matrix if matrix extended
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

    public static int[] hill(int[][] key, int[] line) {
        int len_line = line.length;
        int[] matrix_product = new int[len_line];
        int prod;
        for (int y = 0; y < key.length; y++) {
            prod = 0;
            for(int x = 0; x < key.length; x++) {
                prod += key[y][x] * line[x];
            }
            matrix_product[y] = prod % 127;
        }
        return matrix_product;
    }

    public static int[][] matrix_mult(int[][] key_matrix, int[][]message_matrix) {

        int[][] matrix = message_matrix;
        for (int y = 0; y < message_matrix.length; y++) {
            matrix[y] = hill(key_matrix, message_matrix[y]);
        }
        return matrix;
    }

    static String get_meaning(int[][] matrix) {
        String meaning = "";
        char[][] mean = new char[matrix.length][matrix.length];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                mean[y][x] = (char)matrix[y][x];
                meaning += mean[y][x];
            }
        }
        return meaning;
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

    public static int get_delta(int[][] key) {
        int size = key.length;

        if (size == 2) {
            int val;
            //calculate the delta
            val = (key[0][0] * key[1][1]) - (key[0][1] * key[1][0]);
            val = ((val % 127) + 127) %127;
            System.out.println("DELTA : " + val);
            int delta = 0;
            for (int idx = 0; idx < 1000; idx++) {
                delta = val * idx;
                // get the inverse delta to multiply with the adjugate matrix of the key
                if (((delta % 127) + 127) % 127 == 1) return idx;
            }

        }
        else if (size == 3) {
            int x=key[0][0]*((key[1][1]*key[2][2])-(key[2][1]*key[1][2]));
            int y=-key[0][1]*((key[0][1]*key[2][2])-(key[2][0]*key[1][2]));
            int z=key[0][2]*((key[1][0]*key[2][1])-(key[1][1]*key[2][0]));

            int r=x+y+z;
            return r;
        }
        return 0;
    }

    public static int[][] get_cofactor(int[][] key) {
        int[][] cofactor_matrix = new int[key.length][key.length];

        cofactor_matrix[0][0] = (Math.abs(key[1][2]) * Math.abs(key[2][1]) - Math.abs(key[1][1]) * Math.abs(key[2][2]));
        cofactor_matrix[0][1] = -((Math.abs(key[1][0]) * Math.abs(key[2][2])) - (Math.abs(key[2][0]) * Math.abs(key[1][2])));
        cofactor_matrix[0][2] = (Math.abs(key[1][0]) * Math.abs(key[2][1])) - (Math.abs(key[2][0]) * Math.abs(key[1][1]));
        cofactor_matrix[1][0] = -((Math.abs(key[0][1]) * Math.abs(key[2][2])) - Math.abs((key[2][1]) * Math.abs(key[0][2])));
        cofactor_matrix[1][1] = (Math.abs(key[0][0]) * Math.abs(key[2][2])) - (Math.abs(key[2][0]) * Math.abs(key[0][2]));
        cofactor_matrix[1][2] = -((Math.abs(key[0][0]) * Math.abs(key[2][1])) - (Math.abs(key[2][0]) * Math.abs(key[0][1])));
        cofactor_matrix[2][0] = (Math.abs(key[0][1]) * Math.abs(key[1][2])) - (Math.abs(key[1][1]) * Math.abs(key[0][2]));
        cofactor_matrix[2][1] = -((Math.abs(key[0][0]) * Math.abs(key[1][2])) - (Math.abs(key[1][0]) * Math.abs(key[0][2])));
        cofactor_matrix[2][2] = (Math.abs(key[0][0]) * Math.abs(key[1][1])) - (Math.abs(key[1][0]) * Math.abs(key[0][1]));


        System.out.println(cofactor_matrix[0][0]);
        System.out.println(cofactor_matrix[0][1]);
        System.out.println(cofactor_matrix[0][2]);
        System.out.println(cofactor_matrix[1][0]);
        System.out.println(cofactor_matrix[1][1]);
        System.out.println(cofactor_matrix[1][2]);
        System.out.println(cofactor_matrix[2][0]);
        System.out.println(cofactor_matrix[2][1]);
        System.out.println(cofactor_matrix[2][2]);


        return cofactor_matrix;
    }

    public static int[][] get_adj(int[][] key) {
        int size = key.length;
        int[][] tab = new int[size][size];

        if (size == 2) {
            tab[0][0] = key[1][1];
            tab[0][1] = ((-key[0][1] % 127) + 127) % 127;
            tab[1][0] = ((-key[1][0] % 127) + 127) % 127;
            tab[1][1] = key[0][0];
        } else if (size == 3) {
            // get inverse of key
            tab = get_cofactor(key);
            // calcul the cofactor of each elem of matrix
            // invert the row and col of this matrix
            // inverse of the matrix :
            ;
        }
        return (tab);
    }

    public static int[][] multiply(int[][]adj, int delta) {
        int[][] tab = new int[adj.length][adj.length];
        for (int y = 0; y < adj.length; y++) {
            for (int x = 0; x < adj.length; x++) {
                tab[y][x] = Math.round(((adj[y][x] * delta) % 127));
            }
        }
        return tab;
    }

    public static int encrypt(String message) {
        String key ="abcd";
        if (!checkPerfectSquare(key.length())) {
            System.out.println("WRONG KEY : length of the key should be a perfect square root");
            return 1;
        }
//        String key = GetBytesOfGeneratedSymmetricKey.keyGen();
        int[][] key_matrix = create_key_matrix(key);
        int[][] message_matrix = create_message_matrix(message);
        int[][] matrix_product = matrix_mult(key_matrix, message_matrix);
        String encryoted = get_meaning(matrix_product);
        System.out.println(encryoted);
        return 0;
    }


    public static int decrypt(String message) {
        String key ="totototot";
        if (!checkPerfectSquare(key.length())) {
            System.out.println("WRONG KEY : length of the key should be a perfect square root");
            return 1;
        }
        // String key = GetBytesOfGeneratedSymmetricKey.keyGen();
        int[][] key_matrix = create_key_matrix(key);
        int [][] message_matrix = create_message_matrix(message);
        // calculate the delta from the key matrix
        int delta = get_delta(key_matrix);
        //  get the adjugate matrix of the key
        int[][] adj_matrix = get_adj(key_matrix);
        // multiply the adjugate matrix with the delta to obtain the inverse matrix of the key
        adj_matrix = multiply(adj_matrix, delta);

        // multiply the invert key and the encrypted message to obtain the decrypted message
        int[][] mult_matrix = matrix_mult(adj_matrix, message_matrix);
        String decrypt = get_meaning(mult_matrix);
        System.out.println(decrypt);
        return 0;
    }
}
