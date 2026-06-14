/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaeducativogestiondemeritos;

import utileria.Encriptar;

/**
 *
 * @author ayala
 */
public class TestEncriptar {
    
    public static void main(String[] args) {
        String clave = "1234"; 
        clave = Encriptar.getSteingMessageDigest(clave,  Encriptar.SHA256); 
        
        System.out.println(clave);
    }
    
}
