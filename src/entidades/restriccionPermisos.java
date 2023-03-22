/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to permitir this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import logica.funcionesComunes;

/**
 *
 * @author programador1
 */
public class restriccionPermisos {

    public int getIdrestriccion_permisos() {
        return idrestriccion_permisos;
    }

    public void setIdrestriccion_permisos(int idrestriccion_permisos) {
        this.idrestriccion_permisos = idrestriccion_permisos;
    }

    int idrestriccion_permisos, idtipo_restriccion_permisos, cantidad_maxima, vigente, idpuesto, iddepartamento, idEmpleado, id;
    Date fecha_creacion, fecha_inicio, fecha_fin;

    public restriccionPermisos() {
    }

    public int getIdtipo_restriccion_permisos() {
        return idtipo_restriccion_permisos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdtipo_restriccion_permisos(int idtipo_restriccion_permisos) {
        this.idtipo_restriccion_permisos = idtipo_restriccion_permisos;
    }

    public int getCantidad_maxima() {
        return cantidad_maxima;
    }

    public void setCantidad_maxima(int cantidad_maxima) {
        this.cantidad_maxima = cantidad_maxima;
    }

    public int getVigente() {
        return vigente;
    }

    public void setVigente(int vigente) {
        this.vigente = vigente;
    }

    public int getIdpuesto() {
        return idpuesto;
    }

    public void setIdpuesto(int idpuesto) {
        this.idpuesto = idpuesto;
    }

    public int getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    /**
     * this function returns the permiso id from the local list restricciones
     *
     * @param restricciones is a restriccionPermisos list
     * @param indepart is a id for a departamento object
     * @param idPuesto is a id for a puesto object
     * @param tipoRestr is a id tipoRestriccion object
     * @return tipo int
     */
    public static restriccionPermisos getRestriccion(ArrayList<restriccionPermisos> restricciones, int indepart, int idPuesto, int tipoRestr) {
        restriccionPermisos tipo = null;
        int count = 0;
        boolean found = false;
        while (!found && count < restricciones.size()) {
            if (indepart == restricciones.get(count).getIddepartamento() && (idPuesto == restricciones.get(count).getIdpuesto()) && (tipoRestr == restricciones.get(count).getIdtipo_restriccion_permisos())) {
                tipo = restricciones.get(count);
                System.out.println("found");
                found = true;
            }
            count++;
        }
        return tipo;

    }

    /**
     * this function determine if the vacaciones permiso number is inferior to
     * the maxime number determinated
     *
     * @param solicitudesActuales is the actual number of vacaciones permiso
     * @param lista is restriccionPermisos list from where must be determinated
     * @return a boolean true value if the solicitudesActuales param is inferior
     * than the restriction
     */
    public static boolean permitirVacacionesPorCantidadMaxima(int solicitudesActuales, ArrayList<restriccionPermisos> lista) {
        boolean permitir = true;
        if (lista != null) {
            boolean found = false;
            int count = 0;
            while (!found && count < lista.size()) {
                restriccionPermisos r = lista.get(count);

                if (r.getIdtipo_restriccion_permisos() == 2) {
                    found = true;
                    if (r.cantidad_maxima <= solicitudesActuales) {
                        permitir = false;

                    }
                }
                count++;
            }
        }
        return permitir;
    }

    /**
     * this function checks that the range of dates for a vacaciones permiso is
     * before or after the dates restriccion, that is if the restrictin exists
     *
     * @param inic is a date object, it defines the start date for the vacations
     * @param fin is a date object, it defines the end date for the vacations
     * @param lista is the restriccionPermisos list
     * @return a boolean value, true if the vacation are not in the restriction
     * range, false otherwise
     */
    public static boolean permitirVacacionesPorFechas(Date inic, Date fin, ArrayList<restriccionPermisos> lista) {

        boolean permitir = false;
        if (!lista.isEmpty()) {
            boolean found = false;
            int count = 0;

            while (!found && count < lista.size()) {
                restriccionPermisos r = lista.get(count);

                if (r.getIdtipo_restriccion_permisos() == 1) {
                    found = true;
                    int perInicio = funcionesComunes.compareIniFinDates(inic, r.getFecha_inicio());
                    int perFin = funcionesComunes.compareIniFinDates(fin, r.getFecha_inicio());
                    if (perInicio == -1 & perFin == -1) {
                        permitir = true;
                    } else {
                        perInicio = funcionesComunes.compareIniFinDates(inic, r.getFecha_fin());
                        perFin = funcionesComunes.compareIniFinDates(fin, r.getFecha_fin());
                        if (perInicio == 1 & perFin == 1) {
                            permitir = true;
                        }
                    }
                }

                count++;
            }

        } else {

            permitir = true;
        }
        return permitir;
    }

}
