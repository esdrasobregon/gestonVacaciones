/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.comprobante;
import javax.swing.Icon;

/**
 *
 * @author programador1
 */
public class comprobanteLogica {
    
    public static boolean addComprobante(String comprobanteName, String fileRoute) {
        return datos.crudComprobantes.addComprobante(comprobanteName, fileRoute);
    }
    
    public static comprobante getComprobante(String comprobanteName) {
        return datos.crudComprobantes.getComprobante(comprobanteName);
    }
    
    public static Icon getImagenComprobante(int idComprobante) {
        return datos.crudComprobantes.getImagenComprobante(idComprobante);
    }

    public static comprobante getComprobante(int idComprobante) {
        return datos.crudComprobantes.getComprobante(idComprobante);
    }
}
