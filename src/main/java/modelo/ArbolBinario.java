/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayala
 */
public class ArbolBinario<T> {

    private Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    protected ArrayList<T> preOrdenNID(Nodo r, ArrayList a) {
        if (r != null) {
            a.add(r.getDato());
            preOrdenNID(r.getrIzda(), a);
            preOrdenNID(r.getrDrch(), a);
        }

        return a;
    }

    protected ArrayList<T> inOrdenIND(Nodo r, ArrayList a) {
        if (r != null) {

            inOrdenIND(r.getrIzda(), a);
            a.add(r.getDato());
            inOrdenIND(r.getrDrch(), a);
        }

        return a;
    }

    protected ArrayList<T> postOrdenIDN(Nodo r, ArrayList a) {
        if (r != null) {

            postOrdenIDN(r.getrIzda(), a);
            postOrdenIDN(r.getrDrch(), a);
            a.add(r.getDato());
        }

        return a;
    }

    public <T extends Comparable> Nodo buscar(T dato) {
        return buscar(dato, raiz);
    }

    private <T extends Comparable> Nodo buscar(T dato, Nodo r) {

        if (r == null) {
            return null;

        } else if (dato.compareTo(r.getDato()) < 0) {
            return buscar(dato, r.getrIzda());
        } else if (dato.compareTo(r.getDato()) > 0) {
            return buscar(dato, r.getrDrch());
        } else {
            return r;
        }
    }

    public int altura(Nodo r) {
        if (r == null) {
            return 0;
        } else if (isHoja(r)) {
            return 1;
        } else {
            int ra = (r.getrIzda() == null) ? 0 : altura(r.getrIzda());
            int rb = (r.getrDrch()== null) ? 0 : altura(r.getrDrch());
            return Math.max(ra, rb)+1; 
        }
    }
    
    public boolean isHoja(Nodo r){
        return (r.getrIzda()==null) && (r.getrDrch()==null); 
    }
    
    
    //metodos desarrollados a partir de la guia
    
    public int calculaPesoArbol(Nodo r){
        if (r== null) {
            return 0; 
        }else{
            int peso = 1 + calculaPesoArbol(r.getrIzda()) + calculaPesoArbol(r.getrDrch()); 
            return peso; 
        }
    }
    
    
   
 
    
    
    public int maximoValor(Nodo r) {
    if (isHoja(r)) {
        return (int) r.getDato();
    }
    
        int maxIzda = (r.getrIzda() != null) ? maximoValor(r.getrIzda()) : (int) r.getDato();
    int maxDrch = (r.getrDrch() != null) ? maximoValor(r.getrDrch()) : (int) r.getDato();
    
    return Math.max((int) r.getDato(), Math.max(maxIzda, maxDrch));
    
}
    
    
            
            
            
    //fin metodos de guia 
    
    

}
