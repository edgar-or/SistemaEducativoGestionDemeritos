/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utileria;

import java.util.Collections;

/**
 *
 * @author estud
 */

public class ArbolB<T extends Comparable<T>> {

    private PaginaB<T> raiz;
    private int orden;

    public ArbolB() {
    }
    
    

    public ArbolB(int orden) {
        if (orden < 2) {
            System.out.println("El orden debe ser mayor a 2");
        }
        this.orden = orden;
        raiz = null;
    }

    public T buscar(T clave) {
        return buscarRecursivo(raiz, clave);
    }

    private T buscarRecursivo(PaginaB<T> pagina, T clave) {
        if (pagina == null) {
            return null;
        }

        int i = buscarPosicion(pagina, clave);

        if (i < pagina.getClaves().size() && clave.compareTo(pagina.getClaves().get(i)) == 0) {
            return pagina.getClaves().get(i);
        }

        // Si está vacía o es hoja y no se encontró
        if (pagina.isHoja()) {
            return null;
        }

        return buscarRecursivo(pagina.getHijos().get(i), clave);
    }

    public void insertar(T clave) {
        if (buscar(clave) != null) {
            System.out.println("Clave existe");
            return;
        }

        if (raiz == null) {
            raiz = new PaginaB<>(true);
            raiz.getClaves().add(clave);
            return;
        }
        
        insertarRecursivo(null, raiz, clave, -1);
        
        // Si la raíz se llenó después de la inserción, se debe dividir
        if (raiz.getClaves().size() > 2 * orden) {
            PaginaB<T> nuevaRaiz = new PaginaB<>(false);
            nuevaRaiz.getHijos().add(raiz);
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
    }

    private void insertarRecursivo(PaginaB<T> padre, PaginaB<T> pagina, T clave, int indiceHijo) {
        if (pagina.isHoja()) {
            pagina.getClaves().add(clave);
            // Para ordenar ArrayList
            Collections.sort(pagina.getClaves());
        } else {
            int pos = buscarPosicion(pagina, clave);
            PaginaB<T> hijo = pagina.getHijos().get(pos);
            insertarRecursivo(pagina, hijo, clave, pos);
        }

        // Se verifica si la página actual excede el límite de claves
        if (pagina.getClaves().size() > 2 * orden && padre != null) {
            dividirHijo(padre, indiceHijo);
        }
    }

    private int buscarPosicion(PaginaB<T> pagina, T clave) {
        int i = 0;
        while (i < pagina.getClaves().size() && clave.compareTo(pagina.getClaves().get(i)) > 0) {
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

        // Claves para el lado izquierdo
        for (int i = 0; i < indiceCentral; i++) {
            izquierdo.getClaves().add(hijo.getClaves().get(i));
        }

        // Claves para el lado derecho
        for (int i = indiceCentral + 1; i < hijo.getClaves().size(); i++) {
            derecho.getClaves().add(hijo.getClaves().get(i));
        }

        // Si no es hoja, también hay que repartir los hijos
        if (!hijo.isHoja()) {
            for (int i = 0; i <= indiceCentral; i++) {
                izquierdo.getHijos().add(hijo.getHijos().get(i)); // CORREGIDO: getHijos() en vez de getClaves()
            }
            for (int i = indiceCentral + 1; i < hijo.getHijos().size(); i++) {
                derecho.getHijos().add(hijo.getHijos().get(i)); // CORREGIDO: getHijos() en vez de getClaves()
            }
        }

        // Reconectar con el padre
        padre.getHijos().set(indiceHijo, izquierdo); // El izquierdo reemplaza al hijo original
        padre.getHijos().add(indiceHijo + 1, derecho); // CORREGIDO: El derecho se inserta en la siguiente posición
        padre.getClaves().add(indiceHijo, claveCentral);
    }

    public void mostrar() {
        mostrarRecursivo(raiz);
    }

    private void mostrarRecursivo(PaginaB<T> pagina) {
        if (pagina == null) {
            return;
        }
        int i;
        for (i = 0; i < pagina.getClaves().size(); i++) {
            if (!pagina.isHoja()) { // CORREGIDO: Se recorren los hijos si NO es hoja
                mostrarRecursivo(pagina.getHijos().get(i));
            }
            System.out.println(pagina.getClaves().get(i) + "");
        }
        // CORREGIDO: Recorrer el último hijo
        if (!pagina.isHoja()) {
            mostrarRecursivo(pagina.getHijos().get(i));
        }
    }

    public void mostrarNiveles() {
        int altura = obtenerAltura(raiz);
        for (int i = 0; i < altura; i++) {
            mostrarNivelesRecursivo(raiz, i); // CORREGIDO: Pasar 'i' en lugar de '0'
            System.out.println("");
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

    private void mostrarNivelesRecursivo(PaginaB<T> pagina, int nivel) {
        if (pagina == null) {
            return;
        }
        if (nivel == 0) {
            System.out.print(pagina.getClaves() + " "); // Imprimir en la misma línea para el nivel
        } else {
            for (PaginaB<T> hijo : pagina.getHijos()) {
                mostrarNivelesRecursivo(hijo, nivel - 1);
            }
        }
    }
}