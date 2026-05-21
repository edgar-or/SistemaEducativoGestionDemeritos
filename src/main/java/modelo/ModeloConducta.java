/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloConducta {

    private String tipo;
    private String descripcion;
    private int idTipoConducta;
    private int puntos;

    public ModeloConducta(String tipo, String descripcion, int idTipoConducta, int puntos) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.idTipoConducta = idTipoConducta;
        this.puntos = puntos;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdTipoConducta() {
        return idTipoConducta;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdTipoConducta(int idTipoConducta) {
        this.idTipoConducta = idTipoConducta;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}
