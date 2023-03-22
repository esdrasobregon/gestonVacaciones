/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Usuario;
import entidades.permisos;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author programador1
 */
public class permisosLogica {

    public static boolean addPermiso(permisos un) {
        return datos.crudPermisos.addPermiso(un);
    }

    public static boolean addPermisoComprobante(permisos un) {
        return datos.crudPermisos.addPermisoComprobante(un);
    }

    public static ArrayList<permisos> getPermisos() {
        return datos.crudPermisos.getPermisos();
    }
    public static ArrayList<permisos> getPermisosPorDepartamento(int idDepartamento) {
        return datos.crudPermisos.getPermisosPorDepartamento(idDepartamento);
    }

    public static ArrayList<permisos> getPermisosPendientes() {
        return datos.crudPermisos.getPermisosPendientes();
    }

    public static ArrayList<permisos> getParaVacacionesPorFechas(Usuario us, Date fechaini, Date fechaFin) {
        return datos.crudPermisos.getParaVacacionesPorFechas(us, fechaini, fechaFin);
    }

    public static ArrayList<permisos> getPermisosPorEmpleado(Usuario us) {
        return datos.crudPermisos.getPermisosPorEmpleado(us);
    }

    public static boolean updatePermiso(permisos un) {
        return datos.crudPermisos.updatePermiso(un);
    }

    public static ArrayList<permisos> getAllPermisosPorEmpleado(Usuario us) {
        return datos.crudPermisos.getAllPermisosPorEmpleado(us);
    }

    public static int getContadorPermisosPorTipoEstadoPuesto(int idpuesto, int idtipo, int estado) {
        return datos.crudPermisos.getContadorPermisosPorTipoEstadoPuesto(idpuesto, idtipo, estado);
    }

    public static boolean deletePermiso(permisos permisoActual) {
        return datos.crudPermisos.deltetePermiso(permisoActual);
    }

    public static boolean addPermisoEliminado(permisos p) {
        return datos.crudPermisos.addPermisoEliminado(p);
    }
}
