/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.ModeloAlumno;
import modelo.ModeloCentroEscolar;
import services.AlumnoService;
import services.CentroEscolarService;

/**
 *
 * @author ayala
 */
public class ControladorCentroEscolar {
    
    private String codigoCE; 
    private String nombreCentroEscolar;
    private String numeroTel;
    private String departamento;
    private String municipio;
    private String caserio;
    private String calle;
    private String distrito;
    private String correoElectronico;

    
    
    
    
    public void insertar (){
        
        try {
                ModeloCentroEscolar centro = new ModeloCentroEscolar("123", "CE Tecoluca", "7645-9087", "San Vicente", "Tecoluca", "Canton La Esperanza", "asdas", "San Vicente sur", "ceteco@gmail.com"); 

            CentroEscolarService service = new CentroEscolarService(); 
            service.insertarCE(centro);
        } catch (Exception e) {
        }
        
        
    }    
}
