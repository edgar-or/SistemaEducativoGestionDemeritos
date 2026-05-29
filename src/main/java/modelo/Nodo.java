/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ayala
 */
public class Nodo <T>{
    private T dato ;
    private Nodo rIzda; 
    private Nodo rDrch; 
    private int altura; //solo para guardar alturas en arbol AVL

    public Nodo(T dato) {
        this.dato = dato;
        rIzda = null;
        rDrch = null; 
        altura = 0; 
    }

    public Nodo(T dato, Nodo rIzda, Nodo rDrch) {
        this.dato = dato;
        this.rIzda = rIzda;
        this.rDrch = rDrch;
        altura = 0; 
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo getrIzda() {
        return rIzda;
    }

    public void setrIzda(Nodo rIzda) {
        this.rIzda = rIzda;
    }

    public Nodo getrDrch() {
        return rDrch;
    }

    public void setrDrch(Nodo rDrch) {
        this.rDrch = rDrch;
    }

    @Override
    public String toString() {
        return dato+ " ";
    }
    
    
    
    
    
    
    
}
