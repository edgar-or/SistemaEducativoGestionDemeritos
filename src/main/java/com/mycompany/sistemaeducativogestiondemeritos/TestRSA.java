/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaeducativogestiondemeritos;

import java.util.Base64;
import utileria.RSAGeneradorLlave;

/**
 *
 * @author ayala
 */
public class TestRSA {
    
    public static void main(String[] args) throws Exception {
        RSAGeneradorLlave generadorClaves = new RSAGeneradorLlave(); 
        System.out.println(Base64.getEncoder().encodeToString(
        
                generadorClaves.getClavePublica().getEncoded()
        ));
        System.out.println(Base64.getEncoder().encodeToString(
                generadorClaves.getClavePrivada().getEncoded()
        ));
        
    }
    
}
