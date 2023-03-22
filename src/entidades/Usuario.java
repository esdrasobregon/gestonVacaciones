/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author programador1
 */
public class Usuario {

    int id;
    int IdEmpleado;
    String nombre;
    String Identificacion;
    String Primer_Apellido;
    String Segundo_Apellido;
    int activo;
    String IdTarjeta;
    Date fechaIngreso;
    int departamento;
    int idPuesto;
    int VacacionesPen, VacacionesPeriodoActual, VacacionesAcumuladas;

    public Usuario(int id, int IdEmpleado, String nombre, String Identificacion, String Primer_Apellido, String Segundo_Apellido, int activo, String IdTarjeta, Date fechaIngreso, int departamento, int idPuesto, Date FechaIngreso, int VacacionesPen, int VacacionesPeriodoActual, int VacacionesAcumuladas) {
        this.id = id;
        this.IdEmpleado = IdEmpleado;
        this.nombre = nombre;
        this.Identificacion = Identificacion;
        this.Primer_Apellido = Primer_Apellido;
        this.Segundo_Apellido = Segundo_Apellido;
        this.activo = activo;
        this.IdTarjeta = IdTarjeta;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
        this.idPuesto = idPuesto;
        this.VacacionesPen = VacacionesPen;
        this.VacacionesPeriodoActual = VacacionesPeriodoActual;
        this.VacacionesAcumuladas = VacacionesAcumuladas;
    }

    public Usuario() {
    }

    public String getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(String Identificacion) {
        this.Identificacion = Identificacion;
    }

    public String getPrimer_Apellido() {
        return Primer_Apellido;
    }

    public void setPrimer_Apellido(String Primer_Apellido) {
        this.Primer_Apellido = Primer_Apellido;
    }

    public String getSegundo_Apellido() {
        return Segundo_Apellido;
    }

    public void setSegundo_Apellido(String Segundo_Apellido) {
        this.Segundo_Apellido = Segundo_Apellido;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getIdTarjeta() {
        return IdTarjeta;
    }

    public void setIdTarjeta(String IdTarjeta) {
        this.IdTarjeta = IdTarjeta;
    }

    public int getVacacionesPen() {
        return VacacionesPen;
    }

    public void setVacacionesPen(int VacacionesPen) {
        this.VacacionesPen = VacacionesPen;
    }

    public int getVacacionesPeriodoActual() {
        return VacacionesPeriodoActual;
    }

    public void setVacacionesPeriodoActual(int VacacionesPeriodoActual) {
        this.VacacionesPeriodoActual = VacacionesPeriodoActual;
    }

    public int getVacacionesAcumuladas() {
        return VacacionesAcumuladas;
    }

    public void setVacacionesAcumuladas(int VacacionesAcumuladas) {
        this.VacacionesAcumuladas = VacacionesAcumuladas;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return Primer_Apellido;
    }

    public void setApellido1(String apellido1) {
        this.Primer_Apellido = apellido1;
    }

    public String getApellido2() {
        return Segundo_Apellido;
    }

    public void setApellido2(String apellido2) {
        this.Segundo_Apellido = apellido2;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public static boolean usuarioExist(ArrayList<Usuario> usuarios, int d) {
        boolean found = false;
        int count = 0;
        while (count < usuarios.size() && !found) {
            if (usuarios.get(count).getIdEmpleado() == d) {
                found = true;
            }
            count++;
        }
        return found;
    }

    public static Usuario getUsuario(ArrayList<Usuario> usuarios, int id) {
        Usuario us = null;
        boolean found = false;
        int count = 0;
        while (count < usuarios.size() && !found) {
            if (usuarios.get(count).getIdEmpleado() == id) {
                us = usuarios.get(count);
                found = true;
            }
            count++;
        }
        return us;
    }
}
