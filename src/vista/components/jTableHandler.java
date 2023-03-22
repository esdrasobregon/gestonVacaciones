/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.components;

import entidades.Usuario;
import entidades.permisos;
import entidades.puesto;
import entidades.tipoPermisos;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.funcionesComunes;

/**
 *
 * @author programador1
 */
public class jTableHandler {

    public static void addRowTablePermisos(JTable tbHistorial, ArrayList<permisos> lista, ArrayList<tipoPermisos> tipos) {
        DefaultTableModel model = (DefaultTableModel) tbHistorial.getModel();
        int index = 0;
        for (permisos un : lista) {
            String tipo = entidades.tipoPermisos.getDescriptionPermiso(tipos, un.getIdTipo_permiso());
            String estado = entidades.permisos.getDescripcionEstado(un.getEstado());
            model.addRow(new Object[]{
                un.getIdPermiso(),
                funcionesComunes.getCommunDates(un.getFecha_creacion()),
                funcionesComunes.getCommunDates(un.getFecha_inicio()),
                funcionesComunes.getCommunDates(un.getFecha_fin()),
                estado,
                un.getIdComprobante(),
                tipo
            });
            tbHistorial.setRowHeight(index, 20);
            index++;
        }

    }

    public static void clearJTable(JTable tb) {
        DefaultTableModel dtm = (DefaultTableModel) tb.getModel();
        dtm.setRowCount(0);
    }

    public static void addRowTablePosiblesUsuariosAdministradores(JTable tbEmpleados, ArrayList<Usuario> posiblesUsuariosAdministradores, ArrayList<puesto> puestos) {
        DefaultTableModel model = (DefaultTableModel) tbEmpleados.getModel();
        int index = 0;
        for (Usuario un : posiblesUsuariosAdministradores) {
            System.out.println(un.getNombre() + " " + un.getApellido1() + " departamento" + un.getDepartamento()
                    + " puesto " + un.getIdPuesto() + " idempleado " + un.getIdEmpleado());
            model.addRow(new Object[]{
                un.getNombre() + " " + un.getApellido1() + " "
                + un.getApellido2(), puesto.getNombrePuesto(puestos, un.getIdPuesto()), false
            }
            );
            tbEmpleados.setRowHeight(index, 20);
            index++;
        }
    }
}
