/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utileria;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ayala
 */
public class Encriptar {

    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    
    
    private static String toHexadecimal(byte[] digest) {
        String hash = "";

        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length()==1)

                hash += "0"; 
            hash += Integer.toHexString(b); 
        }
        return hash; 

    }

    public static String getSteingMessageDigest(String cadena, String algoritmo) {
        byte[] digest = null;
        byte[] buffer = cadena.getBytes();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error creando digest");
        }

        return toHexadecimal(digest);

    }

    

}
