/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author renec
 */
public class ModeloConducta {

   private int idTipo;
   private String tipo;
   private String descripcion;
   private int puntos;
   private ArrayList<ModeloMovimientoConducta> movimientoConducta;
   
    public ModeloConducta() {
    }

    public ModeloConducta(int idTipo, String tipo, String descripcion, int puntos, ArrayList<ModeloMovimientoConducta> movimientoConducta) {
        this.idTipo = idTipo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.movimientoConducta = movimientoConducta;
    }

    

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public ArrayList<ModeloMovimientoConducta> getMovimientoConducta() {
        return movimientoConducta;
    }

    public void setMovimientoConducta(ArrayList<ModeloMovimientoConducta> movimientoConducta) {
        this.movimientoConducta = movimientoConducta;
    }

    
    @Override
    public String toString() {
        return descripcion ;
    }
   
   
}
