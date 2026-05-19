/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import DAO.AlumnoDAO;

public class ModeloAlumno {

    private int nie;
    private String nombre;
    private String apelliddos;
    private int idGrado;
    private String duiEncargado;
    private int totalPuntos;

    public ModeloAlumno(int nie, String nombre, String apelliddos, int idGrado, String duiEncargado, int totalPuntos) {
        this.nie = nie;
        this.nombre = nombre;
        this.apelliddos = apelliddos;
        this.idGrado = idGrado;
        this.duiEncargado = duiEncargado;
        this.totalPuntos = totalPuntos;
    }

    public int getNie() {
        return nie;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApelliddos() {
        return apelliddos;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public String getDuiEncargado() {
        return duiEncargado;
    }

    public void setNie(int nie) {
        this.nie = nie;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApelliddos(String apelliddos) {
        this.apelliddos = apelliddos;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setDuiEncargado(String duiEncargado) {
        this.duiEncargado = duiEncargado;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }

}
