/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Usuario;
import entidades.puesto;
import java.util.ArrayList;
import javax.swing.JTable;
import entidades.usuarioGestionVacaciones;
import entidades.controlCambioListas;
import entidades.departamento;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author programador1
 */
public class mantUsuariosTreads implements Runnable {

    JTable tbEmpleados;
    ArrayList<Usuario> posiblesUsuariosAdministradores;
    ArrayList<puesto> puestos;
    ArrayList<entidades.usuarioGestionVacaciones> listaJefes;
    ArrayList<controlCambioListas> cambioListaJefes;
    javax.swing.JButton btnSave;
    JComboBox cmbDepartamentos;
    ArrayList<departamento> alldepartamentos;

    public mantUsuariosTreads(JTable tbEmpleados, ArrayList<Usuario> posiblesUsuariosAdministradores, ArrayList<puesto> puestos, ArrayList<usuarioGestionVacaciones> listaJefes, ArrayList<controlCambioListas> cambioListaJefes, JButton btnSave, JComboBox cmbDepartamentos, ArrayList<departamento> alldepartamentos) {
        this.tbEmpleados = tbEmpleados;
        this.posiblesUsuariosAdministradores = posiblesUsuariosAdministradores;
        this.puestos = puestos;
        this.listaJefes = listaJefes;
        this.cambioListaJefes = cambioListaJefes;
        this.btnSave = btnSave;
        this.cmbDepartamentos = cmbDepartamentos;
        this.alldepartamentos = alldepartamentos;
    }

    @Override
    public void run() {
        btnSave.setVisible(false);
        //vista.components.jTableHandler.clearJTable(tbEmpleados);
        String dep = cmbDepartamentos.getSelectedItem().toString();
        departamento d = departamento.getDepartamento(alldepartamentos, dep);
        getPosiblesUsuariosAdministradores(d.getId_Departamento());
        if (posiblesUsuariosAdministradores != null) {

            loadTablePosiblesUsuariosAdministradores();
            loadListaCambiosJefes();

        }
    }

    /**
     * this function loads on the table the employes info for a departamento, so
     * that the admin usser could make a selecction on the ones on charge of the
     * same departamento
     */
    public void loadTablePosiblesUsuariosAdministradores() {

        vista.components.jTableHandler.addRowTablePosiblesUsuariosAdministradores(this.tbEmpleados, this.posiblesUsuariosAdministradores, this.puestos);
        selectEncargadosTbEmpleados();
        getSelectedEmpleadosFromTheTable();
    }

    /**
     * this method loads from the database the usuarios data for a given
     * idDepartamento
     */
    private void getPosiblesUsuariosAdministradores(int idDepartamento) {
        this.posiblesUsuariosAdministradores = logica.UsuarioLogica.getAllUsuariosActivosPorDepartamento(idDepartamento);
    }

    /**
     * this method loop through the table tbempleados and selects the rows whith
     * the encargados data
     */
    private void selectEncargadosTbEmpleados() {

        for (int i = 0; i < this.tbEmpleados.getRowCount(); i++) {  // Loop through the rows
            if (!this.listaJefes.isEmpty()) {

                for (usuarioGestionVacaciones us : listaJefes) {
                    if (us.getUs().getIdEmpleado() == posiblesUsuariosAdministradores.get(i).getIdEmpleado()) {
                        this.tbEmpleados.setValueAt(true, i, 2);

                    }
                }
            }
        }

    }

    /**
     * this method look for jefes on the list posiblesUsuariosAdministradores to
     * set the start values to show to the admin usser
     */
    private void loadListaCambiosJefes() {
        this.cambioListaJefes = new ArrayList<>();
        for (int i = 0; i < posiblesUsuariosAdministradores.size(); i++) {
            controlCambioListas ug = new controlCambioListas();
            ug.setIndex(i);
            ug.setBefore(false);
            ug.setAfter(false);
            cambioListaJefes.add(ug);
            for (usuarioGestionVacaciones us : listaJefes) {

                if (us.getUs().getIdEmpleado() == posiblesUsuariosAdministradores.get(i).getIdEmpleado()) {
                    ug.setBefore(true);
                    ug.setAfter(true);
                    System.out.println("jefe " + posiblesUsuariosAdministradores.get(i).getNombre() + " " + posiblesUsuariosAdministradores.get(i).getApellido1());
                }
            }
        }

        System.err.println("logica.mantUsuariosTreads.loadListaCambiosJefes()");

    }

    /**
     * this method loop through the table tbempleados and prints the rows whith
     * the encargados data, this is just for checking purposes
     */
    private void getSelectedEmpleadosFromTheTable() {
        for (int i = 0; i < this.tbEmpleados.getRowCount(); i++) {  // Loop through the rows

            if (this.tbEmpleados.getValueAt(i, 2) != null) {

                System.out.println("selected " + this.tbEmpleados.getValueAt(i, 0));
            }
        }
    }
}
