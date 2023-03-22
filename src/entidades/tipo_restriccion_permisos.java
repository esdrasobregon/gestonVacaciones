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
public class tipo_restriccion_permisos {

    int idtipo_restriccion_permisos;
    String decripcion;

    public tipo_restriccion_permisos() {
    }

    public tipo_restriccion_permisos(int idtipo_restriccion_permisos, String decripcion) {
        this.idtipo_restriccion_permisos = idtipo_restriccion_permisos;
        this.decripcion = decripcion;
    }

    public int getIdtipo_restriccion_permisos() {
        return idtipo_restriccion_permisos;
    }

    public void setIdtipo_restriccion_permisos(int idtipo_restriccion_permisos) {
        this.idtipo_restriccion_permisos = idtipo_restriccion_permisos;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public static tipo_restriccion_permisos getTipoRestrinccion(String val, ArrayList<tipo_restriccion_permisos> tiporestricciones) {
        tipo_restriccion_permisos p = null;
        boolean found = false;
        int count = 0;
        while (count < tiporestricciones.size() && !found) {
            if (tiporestricciones.get(count).getDecripcion().equalsIgnoreCase(val)) {
                p = tiporestricciones.get(count);
                found = true;
            }
            count++;
        }
        return p;
    }

    public static tipo_restriccion_permisos getTipoRestrinccionPorId(int idtipo, ArrayList<tipo_restriccion_permisos> tiporestricciones) {
        tipo_restriccion_permisos p = null;
        boolean found = false;
        int count = 0;
        while (count < tiporestricciones.size() && !found) {
            if (tiporestricciones.get(count).getIdtipo_restriccion_permisos() == idtipo) {
                p = tiporestricciones.get(count);
                found = true;
            }
            count++;
        }
        return p;

    }

}
