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
public class cambiosPermisos {

    private permisos original, modifiacdo;

    public cambiosPermisos(permisos original, permisos permisoCabiado) {
        this.original = original;
        this.modifiacdo = permisoCabiado;
    }

    public permisos getOriginal() {
        return original;
    }

    public void setOriginal(permisos permiso) {
        this.original = permiso;
    }

    public permisos getModificado() {
        return modifiacdo;
    }

    public void setMoificado(permisos permisoCabiado) {
        this.modifiacdo = permisoCabiado;
    }

    public static int getIndexOnTheList(permisos permisoOriginal, ArrayList<cambiosPermisos> listaPermisosModificados) {
        int count = 0;
        boolean found = false;
        int index = -1;
        while (!found && count < listaPermisosModificados.size()) {
            permisos p = listaPermisosModificados.get(count).getOriginal();
            if (permisoOriginal.getIdPermiso() == p.getIdPermiso()) {
                index = count;
                found = true;
            }
            count++;
        }
        return index;
    }
}
