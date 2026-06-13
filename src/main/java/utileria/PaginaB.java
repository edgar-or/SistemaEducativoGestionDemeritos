/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utileria;

import java.util.ArrayList;

/**
 *
 * @author estud
 */
public class PaginaB  <T extends Comparable<T>> {
    private ArrayList<T> claves;
    private ArrayList<PaginaB<T>> hijos;
    private boolean hoja;

    public PaginaB(boolean hoja) {
        this.hoja = hoja;
        claves= new ArrayList();
        hijos= new ArrayList();
    }

    public ArrayList<T> getClaves() {
        return claves;
    }

    public void setClaves(ArrayList<T> claves) {
        this.claves = claves;
    }

    public ArrayList<PaginaB<T>> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<PaginaB<T>> hijos) {
        this.hijos = hijos;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }
    
    
    
}
