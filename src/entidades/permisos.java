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
public class permisos {
    
    int idPermiso, idEmpleado, id, estado, idComprobante, idTipo_permiso, trabajar, idpuesto, idDepartamento;
    String obserbaciones, dia_libre;
    Date fecha_inicio, fecha_fin, fecha_creacion;
    
    public permisos() {
    }
    
    public permisos(int idPermiso, int idEmpleado, int id, int estado, int idComprobante, int idTipo_permiso, Date fecha_inicio, Date fecha_fin) {
        this.idPermiso = idPermiso;
        this.idEmpleado = idEmpleado;
        this.id = id;
        this.estado = estado;
        this.idComprobante = idComprobante;
        this.idTipo_permiso = idTipo_permiso;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }
    
    public int getIdDepartamento() {
        return idDepartamento;
    }
    
    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    public int getIdPermiso() {
        return idPermiso;
    }
    
    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }
    
    public int getIdEmpleado() {
        return idEmpleado;
    }
    
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getEstado() {
        return estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public int getIdComprobante() {
        return idComprobante;
    }
    
    public void setIdComprobante(int idComprobante) {
        this.idComprobante = idComprobante;
    }
    
    public int getIdTipo_permiso() {
        return idTipo_permiso;
    }
    
    public void setIdTipo_permiso(int idTipo_permiso) {
        this.idTipo_permiso = idTipo_permiso;
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
    
    public static int getIndexOnTheList(ArrayList<permisos> listaPerPend, permisos permisoActual) {
        int result = -1;
        boolean found = false;
        int count = 0;
        while (!found && count < listaPerPend.size()) {
            if (listaPerPend.get(count).getIdPermiso() == permisoActual.getIdPermiso()) {
                found = true;
                result = count;
            }
            count++;
        }
        return result;
    }

    public static permisos getPermisoOnTheList(ArrayList<permisos> listaPerPend, int id) {
        permisos result = null;
        boolean found = false;
        int count = 0;
        while (!found && count < listaPerPend.size()) {
            if (listaPerPend.get(count).getIdPermiso() == id) {
                found = true;
                result = listaPerPend.get(count);
            }
            count++;
        }
        return result;
    }
    
    public String getDia_libre() {
        return dia_libre;
    }
    
    public void setDia_libre(String dia_libre) {
        this.dia_libre = dia_libre;
    }
    
    public Date getFecha_creacion() {
        return fecha_creacion;
    }
    
    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    public int getTrabajar() {
        return trabajar;
    }
    
    public void setTrabajar(int trabajar) {
        this.trabajar = trabajar;
    }
    
    public String getObserbaciones() {
        return obserbaciones;
    }
    
    public void setObserbaciones(String obserbaciones) {
        this.obserbaciones = obserbaciones;
    }
    
    public int getIdpuesto() {
        return idpuesto;
    }
    
    public void setIdpuesto(int idpuesto) {
        this.idpuesto = idpuesto;
    }
    
    public static String getDescripcionEstado(int idEstado) {
        String result = "";
        
        switch (idEstado) {
            case -1:
                result = "Eliminado";
                break;
            case 0:
                result = "Pendiente";
                break;
            case 1:
                result = "Visto";
                break;
            case 2:
                result = "Aprobado";
                break;
            case 3:
                result = "Denegado";
                break;
        }
        return result;
    }
    
    public static void setPermisoOntheList(permisos permisoActual, ArrayList<permisos> listaPerPend) {
        
        int count = 0;
        boolean found = false;
        
        while (!found && count < listaPerPend.size()) {
            
            if (permisoActual.getIdPermiso() == listaPerPend.get(count).getIdPermiso()) {
                listaPerPend.get(count).setEstado(permisoActual.getEstado());
                listaPerPend.get(count).setFecha_fin(permisoActual.getFecha_fin());
                listaPerPend.get(count).setIdTipo_permiso(permisoActual.getIdTipo_permiso());
                System.out.println("record changed correctlly");
            }
            count++;
        }
    }
    
    public static permisos getPermisoFromPermiso(permisos p) {
        
        permisos per = new permisos();
        per.setId(p.getId());
        per.setIdEmpleado(p.getIdEmpleado());
        per.setDia_libre(p.getDia_libre());
        per.setEstado(p.getEstado());
        per.setFecha_creacion(p.getFecha_creacion());
        per.setFecha_fin(p.getFecha_fin());
        per.setFecha_inicio(p.getFecha_inicio());
        per.setIdComprobante(p.getIdComprobante());
        per.setIdTipo_permiso(p.getIdTipo_permiso());
        per.setIdPermiso(p.getIdPermiso());
        per.setIdpuesto(p.getIdpuesto());
        per.setObserbaciones(p.getObserbaciones());
        per.setTrabajar(p.getTrabajar());
        return per;
    }
    
    public static String getDescripcionTrabajar(int index) {
        String res = "no aplica";
        switch (index) {
            case 1:
                res = "temprano";
                break;
            case 2:
                res = "tarde";
                break;
            
        }
        return res;
    }
}
