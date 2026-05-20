/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.AñoSeccionDao;
import dto.SeccionGradoDto;
import java.util.List;
import javax.swing.JFrame;
import vista.VistaPrincipalMaestros;

/**
 *
 * @author renec
 */
public class ControladorPrinciplaMaestros {

    private VistaPrincipalMaestros visPrincipalMaaestros;

    public ControladorPrinciplaMaestros(VistaPrincipalMaestros visPrincipalMaaestros) {
        this.visPrincipalMaaestros = visPrincipalMaaestros;
        
        
    }

    public void iniciar() {
        visPrincipalMaaestros.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visPrincipalMaaestros.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visPrincipalMaaestros.setVisible(true);
        
        llenarComboSeccion(); 
    }
    
    public void llenarComboSeccion(){
        AñoSeccionDao dao = new AñoSeccionDao();
List<SeccionGradoDto> lista = dao.listarSeccionGrado();

for (SeccionGradoDto obj : lista) {
    visPrincipalMaaestros.comboSeccion.addItem(obj.getSeccion().getSeccion());
    visPrincipalMaaestros.comboGrado.addItem(obj.getGrado().getGrado());
}
    }
    
    
    
    
}
