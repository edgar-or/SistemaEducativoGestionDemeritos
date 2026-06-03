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
//public class ArbolBinarioBusqueda<T> extends ArbolBinario {
//
//    public ArbolBinarioBusqueda() {
//        super();
//    }
//
//    public <T extends Comparable> void insertar(T dato) {
//        super.setRaiz(insertar(dato, super.getRaiz()));
//
//    }
//
//    private <T extends Comparable> Nodo insertar(T dato, Nodo r) {
//        if (r == null) {
//            r = new Nodo(dato);
//        } else if (dato.compareTo(r.getDato()) < 0) {
//            Nodo izd;
//            izd = insertar(dato, r.getrIzda());
//            r.setrIzda(izd);
//        } else if (dato.compareTo(r.getDato()) > 0) {
//            Nodo Drch;
//            Drch = insertar(dato, r.getrDrch());
//            r.setrDrch(Drch);
//
//        } else {
//            System.out.println(" ---------------- Duplicado ----------------");
//        }
//        return r;
//    }
//
//    public ArrayList NID() {
//        ArrayList a = new ArrayList();
//        return preOrdenNID(super.getRaiz(), a);
//    }
//
//    public ArrayList IND() {
//        ArrayList a = new ArrayList();
//        return inOrdenIND(super.getRaiz(), a);
//    }
//
//    public ArrayList IDN() {
//        ArrayList a = new ArrayList();
//        return postOrdenIDN(super.getRaiz(), a);
//    }
//
//    public <T extends Comparable> void eliminar(T dato) {
//        super.setRaiz(eliminar(dato, super.getRaiz()));
//
//    }
//
//    private <T extends Comparable> Nodo eliminar(T dato, Nodo r) {
//        if (r == null) {
//            System.out.println("No hay para eliminar");
//        } else if (dato.compareTo(r.getDato()) < 0) {
//            Nodo izd; 
//                   izd=  eliminar(dato, r.getrIzda());
//                   r.setrIzda(izd);
//        }
//        else if (dato.compareTo(r.getDato()) > 0) {
//            Nodo dere; 
//            
//            dere= eliminar(dato, r.getrDrch());
//            r.setrDrch(dere);
//        } else {
//            Nodo q;
//            q = r;
//            if (q.getrIzda() == null) {
//                r = q.getrDrch();
//            } else if (q.getrDrch() == null) {
//                r = q.getrIzda();
//            } else {
//                q = aplicarReglaDosHijos(q);
//            }
//
//            q = null;
//        }
//
//        return r;
//    }
//
//    private Nodo aplicarReglaDosHijos(Nodo actual) {
//        Nodo aux, ant;
//        ant = actual;
//        aux = actual.getrIzda();
//        while (aux.getrDrch() != null) {
//            ant = aux;
//            aux = aux.getrDrch();
//        }
//        actual.setDato(aux.getDato());
//        if (ant == actual) {
//            ant.setrIzda(aux.getrIzda());
//        } else {
//            ant.setrDrch(aux.getrIzda());
//        }
//        return aux;
//    }
//    
//    
//     public List<Nodo> obtenerNodosConDosHijos(){
//         
//         List<Nodo> lista = new ArrayList<>();
//        
//        return obtenerNodosConDosHijos(super.getRaiz(), lista);
//        
//    }
//    
// private List<Nodo> obtenerNodosConDosHijos(Nodo r, List<Nodo> lista) {
//    if (r == null) return null;
//
//    if (r.getrIzda() != null && r.getrDrch() != null) {
//        lista.add(r);
//    }
//
//    obtenerNodosConDosHijos(r.getrIzda(), lista);
//    obtenerNodosConDosHijos(r.getrDrch(), lista);
//    return lista; 
//}
//    
//    public int contarPadres() {
//    return contarPadres(super.getRaiz());
//}
//
//private int contarPadres(Nodo r) {
//    if (r == null) {
//        return 0;
//    }
//
//    int contador = 0;
//
//    if (r.getrIzda() != null || r.getrDrch() != null) {
//        contador = 1;
//    }
//
//    return contador 
//            + contarPadres(r.getrIzda()) 
//            + contarPadres(r.getrDrch());
//}
//
//
//    //longitud Arbol
//
//public int longitudArbol(){
//    return longitudArbol(super.getRaiz());
//}
//
//    private int longitudArbol(Nodo r){
//        int ramaMasLarga = 0; 
//        if (r == null) {
//            return 0; 
//        }else{
//            int alturaIzq = longitudArbol(r.getrIzda()); 
//            int alturaDrch = longitudArbol(r.getrDrch()); 
//             ramaMasLarga=  1 + Math.max(alturaIzq, alturaDrch);
//
//        }
//        return ramaMasLarga;
//    }
//
//}
public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> {

    public ArbolBinarioBusqueda() {
        super();
    }

    public void insertar(T dato) {
        super.setRaiz(insertar(dato, super.getRaiz()));
    }

    private Nodo<T> insertar(T dato, Nodo<T> r) {
        if (r == null) {
            r = new Nodo<>(dato);
        } else if (dato.compareTo(r.getDato()) < 0) {
            r.setrIzda(insertar(dato, r.getrIzda()));
        } else if (dato.compareTo(r.getDato()) > 0) {
            r.setrDrch(insertar(dato, r.getrDrch()));
        } else {
            System.out.println(" ---------------- Duplicado ----------------");
        }
        return r;
    }

    public ArrayList<T> NID() {
        ArrayList<T> a = new ArrayList<>();
        return preOrdenNID(super.getRaiz(), a);
    }

    public ArrayList<T> IND() {
        ArrayList<T> a = new ArrayList<>();
        return inOrdenIND(super.getRaiz(), a);
    }

    public ArrayList<T> IDN() {
        ArrayList<T> a = new ArrayList<>();
        return postOrdenIDN(super.getRaiz(), a);
    }

    public Nodo<T> buscar(T dato) {
        return buscar(dato, super.getRaiz());
    }

    private Nodo<T> buscar(T dato, Nodo<T> r) {
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

    public void eliminar(T dato) {
        super.setRaiz(eliminar(dato, super.getRaiz()));
    }

    private Nodo<T> eliminar(T dato, Nodo<T> r) {
        if (r == null) {
            System.out.println("No hay para eliminar");
        } else if (dato.compareTo(r.getDato()) < 0) {
            r.setrIzda(eliminar(dato, r.getrIzda()));
        } else if (dato.compareTo(r.getDato()) > 0) {
            r.setrDrch(eliminar(dato, r.getrDrch()));
        } else {
            Nodo<T> q = r;
            if (q.getrIzda() == null) {
                r = q.getrDrch();
            } else if (q.getrDrch() == null) {
                r = q.getrIzda();
            } else {
                q = aplicarReglaDosHijos(q);
            }
            q = null;
        }
        return r;
    }

    private Nodo<T> aplicarReglaDosHijos(Nodo<T> actual) {
        Nodo<T> aux, ant;
        ant = actual;
        aux = actual.getrIzda();
        while (aux.getrDrch() != null) {
            ant = aux;
            aux = aux.getrDrch();
        }
        actual.setDato(aux.getDato());
        if (ant == actual) {
            ant.setrIzda(aux.getrIzda());
        } else {
            ant.setrDrch(aux.getrIzda());
        }
        return aux;
    }

    public List<Nodo<T>> obtenerNodosConDosHijos() {
        List<Nodo<T>> lista = new ArrayList<>();
        return obtenerNodosConDosHijos(super.getRaiz(), lista);
    }

    private List<Nodo<T>> obtenerNodosConDosHijos(Nodo<T> r, List<Nodo<T>> lista) {
        if (r == null) {
            return lista;
        }
        if (r.getrIzda() != null && r.getrDrch() != null) {
            lista.add(r);
        }
        obtenerNodosConDosHijos(r.getrIzda(), lista);
        obtenerNodosConDosHijos(r.getrDrch(), lista);
        return lista;
    }

    public int contarPadres() {
        return contarPadres(super.getRaiz());
    }

    private int contarPadres(Nodo<T> r) {
        if (r == null) {
            return 0;
        }
        int contador = 0;
        if (r.getrIzda() != null || r.getrDrch() != null) {
            contador = 1;
        }
        return contador + contarPadres(r.getrIzda()) + contarPadres(r.getrDrch());
    }

    public int longitudArbol() {
        return longitudArbol(super.getRaiz());
    }

    private int longitudArbol(Nodo<T> r) {
        if (r == null) {
            return 0;
        }
        int alturaIzq = longitudArbol(r.getrIzda());
        int alturaDrch = longitudArbol(r.getrDrch());
        return 1 + Math.max(alturaIzq, alturaDrch);
    }
}
