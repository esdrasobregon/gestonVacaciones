/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 *
 * @author programador1
 */
public class Auditoria_permiso {

    private int idPermiso;
    private java.util.Date fecha_creacion;
    private int idEmpleadoResponsable;
    private int idResponsable;
    private int idEmpleadoSolicitante;
    private int idSolicitante;

    public int getIdEmpleadoResponsable() {
        return idEmpleadoResponsable;
    }

    public void setIdEmpleadoResponsable(int idEmpleadoResponsable) {
        this.idEmpleadoResponsable = idEmpleadoResponsable;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public int getIdEmpleadoSolicitante() {
        return idEmpleadoSolicitante;
    }

    public void setIdEmpleadoSolicitante(int idEmpleadoSolicitante) {
        this.idEmpleadoSolicitante = idEmpleadoSolicitante;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
    private int anterior_estado_permiso;
    private int nuevo_estado_permiso;
    private int idauditoria_permiso;
    private Usuario us;
    private int id_tipo_permiso;
    private java.util.Date fecha_modificacion;

    public int getId_tipo_permiso() {
        return id_tipo_permiso;
    }

    public void setId_tipo_permiso(int id_tipo_permiso) {
        this.id_tipo_permiso = id_tipo_permiso;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public int getIdauditoria_permiso() {
        return idauditoria_permiso;
    }

    public void setIdauditoria_permiso(int idauditoria_permiso) {
        this.idauditoria_permiso = idauditoria_permiso;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public int getIdpermiso() {
        return idPermiso;
    }

    public void setIdpermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public java.util.Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(java.util.Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public int getAnterior_estado_permiso() {
        return anterior_estado_permiso;
    }

    public void setAnterior_estado_permiso(int anterior_estado_permiso) {
        this.anterior_estado_permiso = anterior_estado_permiso;
    }

    public int getNuevo_estado_permiso() {
        return nuevo_estado_permiso;
    }

    public void setNuevo_estado_permiso(int nuevo_estado_permiso) {
        this.nuevo_estado_permiso = nuevo_estado_permiso;
    }

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

    /**
     * this function creates a new Auditoria_permiso object and the returns it
     *
     * @param anteriorEstado is the state before the permisos p was change
     * @param nuevoEstado is the state after the permisos p was change
     * @param p is a permisos objec who was updated
     * @param usuarioActual is a usuario object who is making the changes
     * @return a new Auditoria_permiso object
     */
    public static Auditoria_permiso getAuditoriaPermiso(int anteriorEstado, int nuevoEstado, permisos p, Usuario usuarioActual) {
        Auditoria_permiso au = new Auditoria_permiso();
        Date date1 = new Date();

        au.setFecha_modificacion(date1);
        au.setFecha_creacion(date1);
        au.setIdResponsable(usuarioActual.getId());
        au.setIdEmpleadoResponsable(usuarioActual.getIdEmpleado());
        au.setAnterior_estado_permiso(anteriorEstado);
        au.setNuevo_estado_permiso(nuevoEstado);
        au.setIdPermiso(p.getIdPermiso());
        au.setId_tipo_permiso(p.getIdTipo_permiso());
        au.setIdEmpleadoSolicitante(p.getIdEmpleado());
        au.setIdSolicitante(p.getId());
        return au;
    }
}
