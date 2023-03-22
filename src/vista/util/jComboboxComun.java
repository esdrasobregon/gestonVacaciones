/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.util;

import entidades.Usuario;
import entidades.departamento;
import entidades.puesto;
import entidades.tipoPermisos;
import entidades.tipoUsuario;
import entidades.tipo_restriccion_permisos;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author programador1
 */
public class jComboboxComun {

    public static void loadComboboxDepartamentos(JComboBox cmb, ArrayList<departamento> deps) {
        deps.forEach(e -> {
            cmb.addItem(e.getDescripcion());
        });
    }

    public static void loadComboboxPuestos(JComboBox cmb, ArrayList<puesto> puest) {
        puest.forEach(e -> {
            cmb.addItem(e.getDescripcion());
        });
        cmb.setSelectedIndex(0);
    }

    public static void comboboxRemoveAll(JComboBox cmb) {
        int itemCount = cmb.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            cmb.removeItemAt(0);
        }
    }

    public static void loadComboboxRestricciones(JComboBox cmbTipoRestric, ArrayList<tipo_restriccion_permisos> tiporestricciones) {
        tiporestricciones.forEach(e -> {
            cmbTipoRestric.addItem(e.getDecripcion());
        });
    }

    public static void loadComboboxTipoPermiso(JComboBox cmb, ArrayList<tipoPermisos> tipoPer) {
        tipoPer.forEach(e -> {
            cmb.addItem(e.getDescripcion());
        });

    }

    public static void setCmbLook(JComboBox cmb) {
        cmb.setBackground(Color.WHITE);
        cmb.setForeground(Color.BLACK);
    }

    public static void loadComboboxTipoAdministradores(JComboBox<String> cmbTipoUsuario, ArrayList<tipoUsuario> tipoUsuarios) {
        tipoUsuarios.forEach(e -> {
            cmbTipoUsuario.addItem(e.getDescripcion());
        });
    }
}
