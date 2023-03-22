/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Auditoria_permiso;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class auditoria_permisoLogica {

    public static boolean addAuditoria(Auditoria_permiso un) {
        return datos.crudAuditoria_permiso.addAuditoria(un);
    }

    public static ArrayList<Auditoria_permiso> getHistorialAuditoria(int idpermiso) {
        return datos.crudAuditoria_permiso.getHistorialAuditoria(idpermiso);
    }
}
