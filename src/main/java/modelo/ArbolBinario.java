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
public class ArbolBinario<T extends Comparable<T>> {

    private Nodo<T> raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<T> raiz) {
        this.raiz = raiz;
    }

    protected ArrayList<T> preOrdenNID(Nodo<T> r, ArrayList<T> a) {
        if (r != null) {
            a.add(r.getDato());
            preOrdenNID(r.getrIzda(), a);
            preOrdenNID(r.getrDrch(), a);
        }
        return a;
    }

    protected ArrayList<T> inOrdenIND(Nodo<T> r, ArrayList<T> a) {
        if (r != null) {
            inOrdenIND(r.getrIzda(), a);
            a.add(r.getDato());
            inOrdenIND(r.getrDrch(), a);
        }
        return a;
    }

    protected ArrayList<T> postOrdenIDN(Nodo<T> r, ArrayList<T> a) {
        if (r != null) {
            postOrdenIDN(r.getrIzda(), a);
            postOrdenIDN(r.getrDrch(), a);
            a.add(r.getDato());
        }
        return a;
    }

    public int altura(Nodo<T> r) {
        if (r == null) {
            return 0;
        } else if (isHoja(r)) {
            return 1;
        } else {
            int ra = (r.getrIzda() == null) ? 0 : altura(r.getrIzda());
            int rb = (r.getrDrch() == null) ? 0 : altura(r.getrDrch());
            return Math.max(ra, rb) + 1;
        }
    }

    public boolean isHoja(Nodo<T> r) {
        return (r.getrIzda() == null) && (r.getrDrch() == null);
    }

    public int calculaPesoArbol(Nodo<T> r) {
        if (r == null) {
            return 0;
        } else {
            return 1 + calculaPesoArbol(r.getrIzda()) + calculaPesoArbol(r.getrDrch());
        }
    }
}
