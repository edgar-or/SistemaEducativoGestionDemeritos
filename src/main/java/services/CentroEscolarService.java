/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import DAO.CentroEscolarDAO;
import modelo.ModeloCentroEscolar;

/**
 *
 * @author ayala
 */
public class CentroEscolarService {

    private CentroEscolarDAO daoCE = new CentroEscolarDAO();

    public void insertarCE(ModeloCentroEscolar c) throws Exception {
        daoCE.insertarCentroEscolar(c);
    }

}
