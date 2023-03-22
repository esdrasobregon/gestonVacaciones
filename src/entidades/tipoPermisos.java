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
public class tipoPermisos {

    public int getIdTipo_permiso() {
        return idTipo_permiso;
    }

    public void setIdTipo_permiso(int idTipo_permiso) {
        this.idTipo_permiso = idTipo_permiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public tipoPermisos(int idTipo_permiso, String descripcion) {
        this.idTipo_permiso = idTipo_permiso;
        this.descripcion = descripcion;
    }

    int idTipo_permiso;
    String descripcion;

    public tipoPermisos() {
    }

    /**
     * this function returns the permiso id from the local list
     * listaTipoPermisos
     *
     * @return tipo int
     */
    public static int getTipoPermiso(ArrayList<tipoPermisos> listaTipoPermisos, String value) {
        int tipo = -1;
        int count = 0;
        boolean found = false;
        while (!found && count < listaTipoPermisos.size()) {
            if (value.equalsIgnoreCase(listaTipoPermisos.get(count).getDescripcion())) {
                tipo = listaTipoPermisos.get(count).getIdTipo_permiso();
                System.out.println("tipo permiso: " + listaTipoPermisos.get(count).getIdTipo_permiso());
                found = true;
            }
            count++;
        }
        return tipo;

    }

    public static String getDescriptionPermiso(ArrayList<tipoPermisos> listaTipoPermisos, int id) {
        String tipo = "";
        int count = 0;
        boolean found = false;
        while (!found && count < listaTipoPermisos.size()) {
            if (id == listaTipoPermisos.get(count).getIdTipo_permiso()) {
                tipo = listaTipoPermisos.get(count).getDescripcion();
                System.out.println("tipo permiso: " + listaTipoPermisos.get(count).getIdTipo_permiso());
                found = true;
            }
            count++;
        }
        return tipo;

    }

    public static String getEstadoPermiso(int estado) {
        String result = "";
        switch (estado) {
            case 0:
                result = "Pendiente";
                break;
            case 1:
                result = "Revisado";
                break;
            case 2:
                result = "Por aprobar";
                break;
            default:
                System.out.print("Out of options");
                break;
        }
        return result;
    }

    public static int getIndexTipo(ArrayList<tipoPermisos> listaTipoPermisos, int id) {
        int tipo = -1;
        int count = 0;
        boolean found = false;
        while (!found && count < listaTipoPermisos.size()) {
            if (id == listaTipoPermisos.get(count).getIdTipo_permiso()) {
                tipo = count;
                System.out.println("tipo permiso: " + listaTipoPermisos.get(count).getIdTipo_permiso());
                found = true;
            }
            count++;
        }
        return tipo;

    }
}
