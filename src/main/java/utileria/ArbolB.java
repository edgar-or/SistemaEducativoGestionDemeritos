/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utileria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArbolB<T extends Comparable<T>> {

    private PaginaB<T> raiz;
    private int orden;

    public ArbolB(int orden) {
        if (orden < 2) {
            System.out.println("El orden debe ser >= 2 ");
        }
        this.orden = orden;
        this.raiz = null;
    }

    public T buscar(T clave) {
        return buscarRecursivo(raiz, clave);

    }

    private T buscarRecursivo(PaginaB<T> pagina, T clave) {
        if (pagina == null) {
            return null;
        }
        int i = 0;
        while (i < pagina.getClaves().size()
                && clave.compareTo(pagina.getClaves().get(i)) > 0) {
            i++;
        }
        if (i < pagina.getClaves().size()
                && clave.compareTo(pagina.getClaves().get(i)) == 0) {
            return pagina.getClaves().get(i);
        }
        if (pagina.isHoja()) {
            return null;
        }
        return buscarRecursivo(pagina.getHijos().get(i), clave);
    }

    public void insertar(T clave) {
        if (buscar(clave) != null) {
            System.out.println("Clave duplicada no permitida: " + clave);
            return;
        }
        if (raiz == null) {
            raiz = new PaginaB(true);
            raiz.getClaves().add(clave);
            return;
        }
        insertarRecursivo(null, raiz, clave, -1);
        if (raiz.getClaves().size() > 2 * orden) {
            PaginaB<T> nuevaRaiz = new PaginaB<>(false);
            nuevaRaiz.getHijos().add(raiz);
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
    }

    private void insertarRecursivo(PaginaB<T> padre, PaginaB<T> pagina, T clave, int indiceHijo
    ) {
        if (pagina.isHoja()) {
            pagina.getClaves().add(clave);
            Collections.sort(pagina.getClaves());
            if (pagina.getClaves().size() > 2 * orden && padre != null) {
                dividirHijo(padre, indiceHijo);
            }
            return;
        }
        int pos = buscarPosicion(pagina, clave);
        PaginaB<T> hijo = pagina.getHijos().get(pos);
        insertarRecursivo(pagina, hijo, clave, pos);
    }

    private int buscarPosicion(PaginaB<T> pagina, T clave) {
        int i = 0;
        while (i < pagina.getClaves().size()
                && clave.compareTo(pagina.getClaves().get(i)) > 0) {
            i++;
        }
        return i;
    }

    private void dividirHijo(PaginaB<T> padre, int indiceHijo) {
        PaginaB<T> hijo = padre.getHijos().get(indiceHijo);
        PaginaB<T> izquierdo = new PaginaB<>(hijo.isHoja());
        PaginaB<T> derecho = new PaginaB<>(hijo.isHoja());
        int indiceCentral = orden;
        T claveCentral = hijo.getClaves().get(indiceCentral);
        for (int i = 0; i < indiceCentral; i++) {
            izquierdo.getClaves().add(hijo.getClaves().get(i));

        }
        for (int i = indiceCentral + 1; i
                < hijo.getClaves().size(); i++) {
            derecho.getClaves().add(hijo.getClaves().get(i));
        }
        if (!hijo.isHoja()) {
            for (int i = 0; i <= orden; i++) {
                izquierdo.getHijos().add(hijo.getHijos().get(i));
            }
            for (int i = orden + 1; i < hijo.getHijos().size(); i++) {
                derecho.getHijos().add(hijo.getHijos().get(i));
            }
        }

        padre.getHijos().set(indiceHijo, izquierdo);
        padre.getHijos().add(indiceHijo + 1, derecho);
        padre.getClaves().add(indiceHijo, claveCentral);
    }

    public void recorrer() {
        recorrerRecursivo(raiz);
        System.out.println();
    }

    private void recorrerRecursivo(PaginaB<T> pagina) {
        if (pagina == null) {
            return;
        }
        int i;
        for (i = 0; i < pagina.getClaves().size(); i++) {
            if (!pagina.isHoja()) {
                recorrerRecursivo(pagina.getHijos().get(i));
            }
            System.out.print(pagina.getClaves().get(i) + " ");
        }
        if (!pagina.isHoja()) {
            recorrerRecursivo(pagina.getHijos().get(i));
        }
    }

    public void recorrerPorNiveles() {
        int altura = obtenerAltura(raiz);

        for (int nivel = 0; nivel < altura; nivel++) {
            imprimirNivel(raiz, nivel);
            System.out.println();
        }
    }

    private int obtenerAltura(PaginaB<T> pagina) {
        if (pagina == null) {
            return 0;
        }
        if (pagina.isHoja()) {
            return 1;
        }
        return 1 + obtenerAltura(pagina.getHijos().get(0));
    }

    private void imprimirNivel(PaginaB<T> pagina, int nivel) {
        if (pagina == null) {
            return;
        }
        if (nivel == 0) {
            System.out.print(pagina.getClaves() + " ");
        } else {
            for (PaginaB<T> hijo : pagina.getHijos()) {
                imprimirNivel(hijo, nivel - 1);
            }

        }
    }

    public T eliminar(T clave) {
        if (raiz == null) {
            return null;
        }
        T eliminada = eliminarRecursivo(raiz, clave);
        if (raiz.getClaves().isEmpty() && !raiz.isHoja()) {
            raiz = raiz.getHijos().get(0);
        }
        if (raiz.getClaves().isEmpty() && raiz.isHoja()) {
            raiz = null;
        }
        return eliminada;
    }

    private T eliminarRecursivo(PaginaB<T> pagina, T clave) {
        int indice = buscarPosicion(pagina, clave);
        if (indice < pagina.getClaves().size()
                && clave.compareTo(pagina.getClaves().get(indice)) == 0) {

            if (pagina.isHoja()) {
                return pagina.getClaves().remove(indice);
            }
            return eliminarDePaginaInterna(pagina, indice);
        }
        if (pagina.isHoja()) {
            return null;
        }
        PaginaB<T> hijo = pagina.getHijos().get(indice);
        T eliminada = eliminarRecursivo(hijo, clave);

        if (eliminada != null && hijo.getClaves().size() < orden) {
            corregirDeficit(pagina, indice);
        }
        return eliminada;
    }

    private T eliminarDePaginaInterna(PaginaB<T> pagina, int indiceClave) {
        T claveOriginal = pagina.getClaves().get(indiceClave);
        PaginaB<T> hijoIzquierdo = pagina.getHijos().get(indiceClave);
        PaginaB<T> hijoDerecho = pagina.getHijos().get(indiceClave + 1);
        //Antecesor
        if (hijoIzquierdo.getClaves().size() > orden) {
            T antecesor = eliminarMayor(hijoIzquierdo);
            pagina.getClaves().set(indiceClave, antecesor);
            if (hijoIzquierdo.getClaves().size() < orden) {
                corregirDeficit(pagina, indiceClave + 1);
            }
            return claveOriginal;
        }
        if (hijoIzquierdo.getClaves().size() > orden) {
            T antecesor = eliminarMenor(hijoDerecho);
            pagina.getClaves().set(indiceClave, antecesor);
            if (hijoIzquierdo.getClaves().size() < orden) {
                corregirDeficit(pagina, indiceClave + 1);
            }

        }
        return claveOriginal;

    }

    private T eliminarMayor(PaginaB<T> pagina) {
        if (pagina.isHoja()) {
            return pagina.getClaves().remove(pagina.getClaves().size() - 1);

        }
        int ultimoHijo = pagina.getHijos().size() - 1;
        PaginaB<T> hijo = pagina.getHijos().get(ultimoHijo);
        T mayor = eliminarMayor(hijo);
        if (hijo.getClaves().size() < orden) {
            corregirDeficit(pagina, ultimoHijo);
        }
        return mayor;
    }

    private T eliminarMenor(PaginaB<T> pagina) {
        if (pagina.isHoja()) {
            return pagina.getClaves().remove(0);

        }
        PaginaB<T> hijo = pagina.getHijos().get(0);
        T menor = eliminarMenor(hijo);
        if (hijo.getClaves().size() < orden) {
            corregirDeficit(pagina, 0);
        }
        return menor;
    }

    private void corregirDeficit(PaginaB<T> padre, int indiceHijo) {
        //Prestar al hermano izquierdo
        PaginaB<T> hijo = padre.getHijos().get(indiceHijo);
        if (indiceHijo > 0) {
            PaginaB<T> hermanoIzquierdo = padre.getHijos().get(indiceHijo);
            if (hermanoIzquierdo.getClaves().size() > orden) {
                prestarDesdeIzquierdo(padre, indiceHijo);
                return;
            }

        }
        if (indiceHijo < padre.getHijos().size() - 1) {
            PaginaB<T> hermanoDerecho = padre.getHijos().get(indiceHijo + 1);
            if (hermanoDerecho.getClaves().size() > orden) {
                prestarDesdeDerecho(padre, indiceHijo);
                return;
            }
        }
        if (indiceHijo > 0) {
            fusionarConIzquierdo(padre, indiceHijo);

        } else {
            fusionarConDerecho(padre, indiceHijo);
        }
    }

    private void prestarDesdeIzquierdo(PaginaB<T> padre, int indiceHijo) {
        PaginaB<T> hijo = padre.getHijos().get(indiceHijo);
        PaginaB<T> izquierdo = padre.getHijos().get(indiceHijo - 1);
        hijo.getClaves().add(0, padre.getClaves().get(indiceHijo - 1));
        T mayorIzquierda = izquierdo.getClaves().remove(izquierdo.getClaves().size() - 1);
        padre.getClaves().set(indiceHijo - 1, mayorIzquierda);
        if (!izquierdo.isHoja()) {
            PaginaB<T> ultimoDesc = izquierdo.getHijos().remove(izquierdo.getHijos().size() - 1);
            hijo.getHijos().add(0, ultimoDesc);
        }
    }

    private void prestarDesdeDerecho(PaginaB<T> padre, int indiceHijo) {
        PaginaB<T> hijo = padre.getHijos().get(indiceHijo);
        PaginaB<T> derecho = padre.getHijos().get(indiceHijo + 1);
        hijo.getClaves().add(padre.getClaves().get(indiceHijo));
        T menorDerecha = derecho.getClaves().remove(0);
        padre.getClaves().set(indiceHijo, menorDerecha);
        if (!derecho.isHoja()) {
            PaginaB<T> primerDesc = derecho.getHijos().remove(0);
            hijo.getHijos().add(primerDesc);
        }
    }

    private void fusionarConDerecho(PaginaB<T> padre, int indiceIzquierdo) {
        PaginaB<T> izquierdo = padre.getHijos().get(indiceIzquierdo);
        PaginaB<T> derecho = padre.getHijos().get(indiceIzquierdo + 1);
        T separadora = padre.getClaves().remove(indiceIzquierdo);
        izquierdo.getClaves().add(separadora);
        izquierdo.getClaves().addAll(derecho.getClaves());
        if (!derecho.isHoja()) {
            izquierdo.getHijos().addAll(derecho.getHijos());
        }

        padre.getHijos().remove(indiceIzquierdo + 1);
    }

    private void fusionarConIzquierdo(PaginaB<T> padre, int indiceDerecho) {
        PaginaB<T> izquierdo = padre.getHijos().get(indiceDerecho - 1);
        PaginaB<T> derecho = padre.getHijos().get(indiceDerecho);
        T separadora = padre.getClaves().remove(indiceDerecho - 1);
        izquierdo.getClaves().add(separadora);
        izquierdo.getClaves().addAll(derecho.getClaves());
        if (!derecho.isHoja()) {
            izquierdo.getHijos().addAll(derecho.getHijos());

        }
        padre.getHijos().remove(indiceDerecho);
    }

    public List<T> obtenerListaOrdenada() {
        List<T> lista = new ArrayList<>();
        recorrerParaLista(raiz, lista);
        return lista;
    }

    private void recorrerParaLista(PaginaB<T> pagina, List<T> lista) {
        if (pagina == null) {
            return;
        }
        int i;
        for (i = 0; i < pagina.getClaves().size(); i++) {
            if (!pagina.isHoja()) {
                recorrerParaLista(pagina.getHijos().get(i), lista);
            }
            lista.add(pagina.getClaves().get(i));
        }
        if (!pagina.isHoja()) {
            recorrerParaLista(pagina.getHijos().get(i), lista);
        }
    }

}
