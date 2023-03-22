/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Auditoria_permiso;
import entidades.Usuario;
import entidades.permisos;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author programador1
 */
public class permisosThreads implements Runnable {

    private int processTipe;
    permisos p;
    Usuario usuarioActual;
    JProgressBar jpbPermisos;

    public permisosThreads(int processTipe, permisos p, Usuario usuarioActual) {
        this.processTipe = processTipe;
        this.p = p;
        this.usuarioActual = usuarioActual;

    }

    public permisosThreads(int processTipe, permisos p, Usuario usuarioActual, JProgressBar jpbPermisos) {
        this.processTipe = processTipe;
        this.p = p;
        this.usuarioActual = usuarioActual;
        this.jpbPermisos = jpbPermisos;
    }

    @Override
    public void run() {

        if (this.processTipe == -1) {
            deletetePermiso(p);
        } else {
            updatePermiso(this.processTipe, p);
        }
    }

    private void deletetePermiso(permisos permisoActual) {
        int nuevoEstado = -1;
        if (permisoActual != null) {
            if (permisoActual.getEstado() == 0) {
                Auditoria_permiso au = getAuditoriaPermiso(permisoActual.getEstado(), nuevoEstado, permisoActual);
                boolean flag = logica.auditoria_permisoLogica.addAuditoria(au);
                if (flag) {

                    boolean result = logica.permisosLogica.deletePermiso(permisoActual);
                    if (result) {
                        setProgressBarUp();
                        //JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");
                        //this.pnlMainContent.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar permiso porque ya ha sido revisado");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
            //this.pnlMainContent.setSelectedIndex(0);
        }
    }

    private void updatePermiso(int nuevoEstado, permisos permisoActual) {

        if (permisoActual != null) {
            Auditoria_permiso au = getAuditoriaPermiso(permisoActual.getEstado(), nuevoEstado, permisoActual);
            boolean flag = logica.auditoria_permisoLogica.addAuditoria(au);
            if (flag) {
                permisoActual.setEstado(nuevoEstado);
                boolean result = logica.permisosLogica.updatePermiso(permisoActual);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Hecho");
                    setProgressBarUp();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
        }
    }

    private void setProgressBarUp() {
        int value = this.jpbPermisos.getValue();
        value++;
        this.jpbPermisos.setValue(value);
    }

    private Auditoria_permiso getAuditoriaPermiso(int anteriorEstado, int nuevoEsado, permisos p) {
        Auditoria_permiso au = new Auditoria_permiso();
        Date date1 = new Date();

        au.setFecha_modificacion(date1);
        au.setFecha_creacion(date1);
        au.setIdResponsable(this.usuarioActual.getId());
        au.setIdEmpleadoResponsable(this.usuarioActual.getIdEmpleado());
        au.setAnterior_estado_permiso(anteriorEstado);
        au.setNuevo_estado_permiso(nuevoEsado);
        au.setIdPermiso(p.getIdPermiso());
        au.setId_tipo_permiso(p.getIdTipo_permiso());
        au.setIdEmpleadoSolicitante(p.getIdEmpleado());
        au.setIdSolicitante(p.getId());
        return au;
    }
}
