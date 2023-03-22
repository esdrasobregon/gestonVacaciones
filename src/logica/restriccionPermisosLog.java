/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.departamento;
import entidades.puesto;
import entidades.restriccionPermisos;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class restriccionPermisosLog {

    public static boolean addRestriccion(restriccionPermisos r) {
        return datos.crudRestriccionPermisos.addRestriccion(r);
    }

    public static ArrayList<restriccionPermisos> getrestriccionPermisosPorDepPuesto(int iddepartamento, int idpuesto) {
        return datos.crudRestriccionPermisos.getrestriccionPermisosPorDepPuesto(iddepartamento, idpuesto);
    }

    public static boolean actualizarVigencias(restriccionPermisos un) {
        return datos.crudRestriccionPermisos.actualizarVigencias(un);
    }

    public static ArrayList<restriccionPermisos> getrestriccionPermisosVigentes() {
        return datos.crudRestriccionPermisos.getrestriccionPermisosVigentes();
    }
}
