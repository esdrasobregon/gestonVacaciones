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
public class usuarioGestionVacaciones {

    Usuario us;
    int idusuarioGestionVacaciones;
    Date fecha_creacion, fecha_modificacion;
    int estado, idtipo_usuario, idDepartamento;

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

    public int getIdusuarioGestionVacaciones() {
        return idusuarioGestionVacaciones;
    }

    public void setIdusuarioGestionVacaciones(int idusuarioGestionVacaciones) {
        this.idusuarioGestionVacaciones = idusuarioGestionVacaciones;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdtipo_usuario() {
        return idtipo_usuario;
    }

    public void setIdtipo_usuario(int idtipo_usuario) {
        this.idtipo_usuario = idtipo_usuario;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

}
