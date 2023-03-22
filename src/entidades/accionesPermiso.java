/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class accionesPermiso {

    public boolean visto, aprobar, denegar, eliminar;
    int idPermiso;

    public accionesPermiso(int idpermiso) {
        visto = aprobar = denegar = eliminar = false;
        this.idPermiso = idpermiso;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public static accionesPermiso getAccionesPermisoByIdPermiso(ArrayList<accionesPermiso> listaAcciones, int idPermiso) {
        accionesPermiso result = null;
        boolean found = false;
        int count = 0;
        while (!found && count < listaAcciones.size()) {
            if (listaAcciones.get(count).getIdPermiso() == idPermiso) {
                found = true;
                result = listaAcciones.get(count);
            }
            count++;
        }
        return result;
    }
}
