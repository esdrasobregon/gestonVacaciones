/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import entidades.Auditoria_permiso;
import entidades.Usuario;
import entidades.cambiosPermisos;
import entidades.controlCambioListas;
import entidades.departamento;
import entidades.permisos;
import entidades.puesto;
import entidades.restriccionPermisos;
import entidades.tipoPermisos;
import entidades.tipoUsuario;
import entidades.tipo_restriccion_permisos;
import entidades.usuarioGestionVacaciones;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import logica.funcionesComunes;
import logica.informationMessages;
import logica.permisosThreads;
import vista.util.jComboboxComun;

/**
 *
 * @author programador1
 */
public class mantenimientoVacaciones extends javax.swing.JFrame {

    /**
     * Creates new form mantenimientoVacaciones
     */
    Icon iconComprobanteActual;
    permisos permisoActual;
    Usuario usuarioPermiso;
    Usuario usuarioActual;
    departamento departUsuarioActual;
    ArrayList<departamento> departamentosConSolicitudes;
    ArrayList<departamento> alldepartamentos;
    ArrayList<puesto> puestos;
    ArrayList<tipoPermisos> listaTipoPermisos;
    BufferedImage imagenComprobante;
    boolean solicitarComprobante;
    ArrayList<permisos> myListaPer;
    ArrayList<permisos> listaPerPend;
    ArrayList<permisos> subListaPerPend;
    ArrayList<Usuario> empleConPermsPend;
    ArrayList<Usuario> subEmpleConPermsPend;
    ArrayList<tipo_restriccion_permisos> tiporestricciones;
    ArrayList<restriccionPermisos> listarestricciones;
    ArrayList<restriccionPermisos> restriccionesParaUsuarioActual;
    int contVacaciones;
    int tabSelected;
    boolean refrescando;
    boolean verPendientes;
    TreePath jTreeDpLastPath;
    ArrayList<cambiosPermisos> listaPermisosModificados;
    ArrayList<Usuario> posiblesUsuariosAdministradores;
    ArrayList<tipoUsuario> tipoUsuarios;
    ArrayList<usuarioGestionVacaciones> listaJefes;
    ArrayList<controlCambioListas> cambioListaJefes;
    int changes;
    Runnable usuariosThread;
    logica.connection testConnection;

    public mantenimientoVacaciones() {
        initComponents();
    }

    public mantenimientoVacaciones(Usuario us) {
        initComponents();
        this.usuarioActual = us;
        this.setSize(new Dimension(1010, 700));
        this.setLocationRelativeTo(null);
        setGlobalInfo();
        setFormulariosComunes();
        setTipoUsuarioForm();

    }
// <editor-fold defaultstate="collapsed" desc="localSettings">

    /**
     * this method loads from the database the all ussers commun info
     */
    private void setGlobalInfo() {
        this.listaPermisosModificados = new ArrayList<>();
        this.verPendientes = true;
        this.contVacaciones = logica.permisosLogica.getContadorPermisosPorTipoEstadoPuesto(this.usuarioActual.getIdPuesto(), 1, 0);
        fromPermisos1.contVacaciones = this.contVacaciones;
        this.refrescando = false;
        this.subEmpleConPermsPend = new ArrayList<>();
        this.subListaPerPend = new ArrayList<>();
        this.departUsuarioActual = logica.departamentoLog.getDepartamentosPorId(this.usuarioActual.getDepartamento());
        this.listaTipoPermisos = logica.tipoPermisosLogica.getTipoPermisos();
        this.puestos = logica.puestoLogica.getAllPuestos();
        this.restriccionesParaUsuarioActual = logica.restriccionPermisosLog.getrestriccionPermisosPorDepPuesto(this.usuarioActual.getDepartamento(), this.usuarioActual.getIdPuesto());
        this.listaJefes = logica.usuarioGestionVacaciones.getAllUsuarioVacaciones();
        this.listaTipoPermisos = logica.tipoPermisosLogica.getTipoPermisos();
        this.myListaPer = logica.permisosLogica.getPermisosPorEmpleado(usuarioActual);
    }

    /**
     * this method sets all the preparations for the admin interface
     */
    private void setAdminMainInterface() {

        removeFormFromTheMainContent();

        this.btnUndoChange.setVisible(false);
        this.btnSave.setVisible(false);
        this.btnNotificaion.setVisible(false);
        this.rbPendientes.setSelected(true);
        this.lbUsusarioActual.setText("@" + this.usuarioActual.getNombre() + " " + this.usuarioActual.getApellido1());
        vista.util.commonPanel.handleResizePanel(this.rigth, 0, 600);
        vista.util.botonComun.mouseHoverListener(this.btnRefresh);
        vista.util.botonComun.mouseHoverListener(this.btnInfo);
        vista.util.botonComun.mouseHoverListener(this.btnHelp);
        vista.util.botonComun.mouseHoverListener(this.btnNotificaion);
        vista.util.botonComun.mouseHoverListener(this.btnUndoChange);
        vista.util.botonComun.mouseHoverListener(this.btnSave);
        setRestriccionesForm();
        setDepConSolicitudes();
        loadTreeDepartamentos();
        setPermisosForm();
        setHistorialForm();
        setInfoPermisosForm();
        setFormMantenimientoEncargados();

    }

    /**
     * this method loads the empleConPermsPend ussers list given the
     * listaPerPend list
     */
    private void cargarListaEmpConPermPend() {
        this.empleConPermsPend = new ArrayList<>();
        if (this.listaPerPend != null) {
            this.listaPerPend.forEach(e -> {
                Usuario us = logica.UsuarioLogica.getUsuario(e.getIdEmpleado());
                this.empleConPermsPend.add(us);
                if (e.getIdComprobante() > 0) {
                    logica.comprobanteLogica.getImagenComprobante(e.getIdComprobante());
                    System.out.println("comprobante titulo: " + e.getIdComprobante());
                }
            });
        }
    }

    /**
     * this method loads form the database the info nedded for an admin usser
     */
    private void setAdminVariables() {
        this.changes = 0;
        this.listarestricciones = logica.restriccionPermisosLog.getrestriccionPermisosVigentes();
        setListaPermPend();
        cargarListaEmpConPermPend();

        this.alldepartamentos = logica.departamentoLog.getDepartamentos();
        this.alldepartamentos.forEach(e -> {
            System.out.println("getting records for: " + e.getDescripcion());
            e.puestos = logica.puestoLogica.getPuestoPorIdDep(e.getId_Departamento());
        });
        this.tiporestricciones = logica.tipo_restriccion_permisos_log.getAllTipos();
        this.posiblesUsuariosAdministradores = new ArrayList<>();
        this.tipoUsuarios = logica.tipoUsuariosLogica.getAllTipoUsuarios();

        this.cambioListaJefes = new ArrayList<>();
        this.usuariosThread = new Runnable() {
            @Override
            public void run() {
                btnLoading.setVisible(true);
                mantenimientoUsuarios1.btnSave.setVisible(false);
                vista.components.jTableHandler.clearJTable(mantenimientoUsuarios1.tbEmpleados);
                String dep = mantenimientoUsuarios1.cmbDepartamentos.getSelectedItem().toString();
                departamento d = departamento.getDepartamento(alldepartamentos, dep);
                getPosiblesUsuariosAdministradores(d.getId_Departamento());
                if (posiblesUsuariosAdministradores != null) {

                    loadTablePosiblesUsuariosAdministradores();
                    loadListaCambiosJefes();

                }
                mantenimientoUsuarios1.btnSerch.setVisible(false);
                btnLoading.setVisible(false);
            }
        };

    }

    /**
     * this method loads all the information for the permisos pendientes which
     * is different form usser tipe
     */
    private void setListaPermPend() {
        boolean encargado = esEncargadoDepartamento();
        if (encargado) {
            int idDepartamento = this.usuarioActual.getDepartamento();
            this.listaPerPend = logica.permisosLogica.getPermisosPorDepartamento(idDepartamento);
        } else {
            this.listaPerPend = logica.permisosLogica.getPermisosPendientes();
        }
    }

    /**
     * this function loads on the table the employes info for a departamento, so
     * that the admin usser could make a selecction on the ones on charge of the
     * same departamento
     */
    public void loadTablePosiblesUsuariosAdministradores() {

        vista.components.jTableHandler.addRowTablePosiblesUsuariosAdministradores(this.mantenimientoUsuarios1.tbEmpleados, this.posiblesUsuariosAdministradores, this.puestos);
        selectEncargadosTbEmpleados();
        getSelectedEmpleadosFromTheTable();
    }

    /**
     * this method loop through the table tbempleados and selects the rows whith
     * the encargados data
     */
    private void selectEncargadosTbEmpleados() {

        for (int i = 0; i < this.mantenimientoUsuarios1.tbEmpleados.getRowCount(); i++) {  // Loop through the rows
            if (!this.listaJefes.isEmpty()) {

                for (usuarioGestionVacaciones us : listaJefes) {
                    if (us.getUs().getIdEmpleado() == posiblesUsuariosAdministradores.get(i).getIdEmpleado()) {
                        this.mantenimientoUsuarios1.tbEmpleados.setValueAt(true, i, 2);

                    }
                }
            }
        }

    }

    /**
     * this method loop through the table tbempleados and prints the rows whith
     * the encargados data, this is just for checking purposes
     */
    private void getSelectedEmpleadosFromTheTable() {
        for (int i = 0; i < this.mantenimientoUsuarios1.tbEmpleados.getRowCount(); i++) {  // Loop through the rows

            if (this.mantenimientoUsuarios1.tbEmpleados.getValueAt(i, 2) != null) {

                System.out.println("selected " + this.mantenimientoUsuarios1.tbEmpleados.getValueAt(i, 0));
            }
        }
    }

    /**
     * this method removes the last tab in the mainContent tabbed panel, it's
     * purpose is to remove the permisos form, but still removes the last one on
     * the list
     */
    private void removeFormFromTheMainContent() {
        try {
            int mainContentlength = this.pnlMainContent.getTabCount();
            if (mainContentlength > 4 || (esEncargadoDepartamento() && mainContentlength > 1)) {
                int count = this.pnlMainContent.getComponentCount() - 1;
                this.pnlMainContent.remove(count);
                this.pnlMainContent.revalidate();
                this.pnlMainContent.repaint();
                System.out.println("form permisos removed");
            }

        } catch (Exception e) {
            System.out.println("vista.mantenimientoVacaciones.removeFormFromTheMainContent()");
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * this method sets the values and functions on the listarestricciones panel
     */
    private void setRestriccionesForm() {
        vista.util.jComboboxComun.loadComboboxDepartamentos(this.restricciones1.cmbDepartamentos, alldepartamentos);
        vista.util.jComboboxComun.loadComboboxRestricciones(this.restricciones1.cmbTipoRestric, tiporestricciones);

        jComboboxComun.loadComboboxPuestos(restricciones1.cmbPuestos, this.alldepartamentos.get(0).puestos);
        this.restricciones1.cmbDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    int index = restricciones1.cmbDepartamentos.getSelectedIndex();
                    departamento d = alldepartamentos.get(index);
                    jComboboxComun.comboboxRemoveAll(restricciones1.cmbPuestos);
                    jComboboxComun.loadComboboxPuestos(restricciones1.cmbPuestos, d.puestos);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + e.getMessage());
                }
            }
        });
        this.restricciones1.btnGuardarRestrinccion.addActionListener((e) -> {
            restriccionPermisos r = restricciones1.getrestriccionesPermisosForm1(usuarioActual, tiporestricciones, alldepartamentos, puestos);

            if (r != null) {
                boolean flag = logica.restriccionPermisosLog.actualizarVigencias(r);

                if (flag) {
                    boolean res = logica.restriccionPermisosLog.addRestriccion(r);
                    if (res) {
                        JOptionPane.showMessageDialog(null, "Hecho");
                    } else {
                        JOptionPane.showMessageDialog(null, "error");
                    }
                }
            }
        });
        this.restricciones1.cmbTipoRestric.addActionListener(e -> {
            restricciones1.chooseTipoRestriccion(tiporestricciones);
        });
        this.restricciones1.cmbPuestos
                .addItemListener(new java.awt.event.ItemListener() {
                    public void itemStateChanged(java.awt.event.ItemEvent evt) {
                        restricciones1.showRestriccionesPorPuesto(alldepartamentos, listarestricciones, tiporestricciones, txtareaInformacion);
                    }
                });
    }

    /**
     * this method sets the values and functions on the infopermisos panel
     */
    private void setInfoPermisosForm() {
        this.pnlInfoPermiso1.btnEditar.addActionListener(e -> {
            prepararEditarPermiso();
        });

        this.pnlInfoPermiso1.btnAprobarDirecto.addActionListener(e -> {
            if (accionPermisoActualValida()) {
                procesarCambiosPermisosLocalmente(2);
                //procesarCambiosPermisoActual(2);
            }
        });

        this.pnlInfoPermiso1.btnDelete.addActionListener(e -> {
            if (this.permisoActual != null) {
                if (this.permisoActual.getEstado() == 0) {
                    //deletetePermisoProcess();
                    procesarCambiosPermisosLocalmente(-1);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar permiso porque ya ha sido revisado");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
            }
        });

        this.pnlInfoPermiso1.btnVisto.addActionListener((e) -> {
            if (accionPermisoActualValida()) {
                procesarCambiosPermisosLocalmente(1);
                // procesarCambiosPermisoActual(1);
            }
        });
        this.pnlInfoPermiso1.btnDenegar.addActionListener((e) -> {
            procesarCambiosPermisosLocalmente(3);
            //denegarSolicitud();
        });
    }

    private void preparePermisosChanges() {
        int prevestado = this.permisoActual.getEstado();
        this.permisoActual.setEstado(1);
        permisos permisoModificado = permisos.getPermisoFromPermiso(this.permisoActual);
        //boolean result = logica.permisosLogica.procesarCambiosPermisoActual(permisoActual);
        recordarCambio(permisoModificado);
        removeFromListPermPend();
        this.pnlInfoPermiso1.clearForm();
        this.fromPermisos1.clearForm();
        JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");
        removeFormFromTheMainContent();
        this.pnlMainContent.setSelectedIndex(0);
        setSelectedJTreeDepartamentos();
    }

    /**
     * this method sets the values and functions on the permisos panel
     */
    private void setPermisosForm() {
        this.fromPermisos1.btnEnviar.setVisible(false);
        //form solicitudes events
        this.fromPermisos1.btnAprobar.addActionListener((e) -> {
            aprobarSolicitud();
        });
        this.fromPermisos1.btnDenegar.addActionListener((e) -> {
            denegarSolicitud();
        });
        this.fromPermisos1.btnVisto.addActionListener((e) -> {
            solicitudVista();
        });
    }

    /**
     * this method sets the values and functions on the historial panel
     */
    private void showHistorialForAUsser(ArrayList<permisos> lista) {

        if (lista != null) {
            vista.components.jTableHandler.addRowTablePermisos(this.historialPermisos1.tbHistorial, lista, this.listaTipoPermisos);
        }
    }

    /**
     * this method sets the historial panel info and functions
     */
    private void setHistorialForm() {

        this.historialPermisos1.btnBuscarHistorial.addActionListener((e) -> {
            showHistorialForAUsser(this.historialPermisos1.serchHistorialPermisos());
        });
        this.historialPermisos1.txtCodigoHistorial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                    showHistorialForAUsser(historialPermisos1.serchHistorialPermisos());
                }

            }
        });
        this.historialPermisos1.tbHistorial.getSelectionModel().addListSelectionListener((e) -> {
            String historial = this.historialPermisos1.getHistorialPermiso();
            this.txtareaInformacion.setText(historial);
        });
    }

    private void addlistenerToMantenimientoUsuariosCmbDepartamentos() {
        this.mantenimientoUsuarios1.cmbDepartamentos.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {

                /*
                btnLoading.setVisible(true);
                mantenimientoUsuarios1.btnSave.setVisible(false);
                vista.components.jTableHandler.clearJTable(mantenimientoUsuarios1.tbEmpleados);
                String dep = mantenimientoUsuarios1.cmbDepartamentos.getSelectedItem().toString();
                departamento d = departamento.getDepartamento(alldepartamentos, dep);
                getPosiblesUsuariosAdministradores(d.getId_Departamento());
                if (posiblesUsuariosAdministradores != null) {

                    loadTablePosiblesUsuariosAdministradores();
                    loadListaCambiosJefes();

                }
                btnLoading.setVisible(false);
                 */
                vista.components.jTableHandler.clearJTable(mantenimientoUsuarios1.tbEmpleados);
                mantenimientoUsuarios1.btnSerch.setVisible(true);
            }

        });
        this.mantenimientoUsuarios1.cmbDepartamentos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Thread t = new Thread(usuariosThread);
                t.start();
            }
        });
        this.mantenimientoUsuarios1.btnSerch.addActionListener(e -> {
            //mantUsuariosTreads mant = new mantUsuariosTreads(mantenimientoUsuarios1.tbEmpleados, posiblesUsuariosAdministradores, puestos, listaJefes, cambioListaJefes, mantenimientoUsuarios1.btnSave, mantenimientoUsuarios1.cmbDepartamentos, alldepartamentos);
            Thread t = new Thread(this.usuariosThread);
            t.start();
        });

    }

    /**
     * this method loads from the database the usuarios data for a given
     * idDepartamento
     */
    private void getPosiblesUsuariosAdministradores(int idDepartamento) {
        this.posiblesUsuariosAdministradores = logica.UsuarioLogica.getAllUsuariosActivosPorDepartamento(idDepartamento);
    }

    /**
     * this funtion keps tabs of the changes made on the permisos list, so that
     * the changes could be undone
     *
     * @return the tottal of the changes made
     */
    private int getcambiosTbEmpleados() {

        int cambios = 0;
        if (!this.cambioListaJefes.isEmpty()) {
            for (int i = 0; i < this.cambioListaJefes.size(); i++) {
                if (cambioListaJefes.get(i).isAfter() != cambioListaJefes.get(i).isBefore()) {
                    cambios++;
                }
            }

        }
        return cambios;
    }

    /**
     * this function sets the function and info on the mantenimientUsuarios
     * panel
     */
    private void setMantenimietoUsuriosForm() {
        this.listaJefes = logica.usuarioGestionVacaciones.getAllUsuarioVacaciones();
        loadListaCambiosJefes();
        vista.components.jTableHandler.clearJTable(this.mantenimientoUsuarios1.tbEmpleados);
        loadTablePosiblesUsuariosAdministradores();
        this.mantenimientoUsuarios1.btnSave.setVisible(false);
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

        System.out.println("vista.mantenimientoVacaciones.loadListaCambiosJefes()");

    }

    /**
     * this method sets the defoult values for the mantenimientoUsuarios panel
     */
    private void setFormMantenimientoEncargados() {
        vista.util.jComboboxComun.loadComboboxDepartamentos(this.mantenimientoUsuarios1.cmbDepartamentos, alldepartamentos);
        this.mantenimientoUsuarios1.tbEmpleados.getModel().addTableModelListener(e -> {
            try {
                int rowIndex = this.mantenimientoUsuarios1.tbEmpleados.getSelectedRow();
                int colIndex = this.mantenimientoUsuarios1.tbEmpleados.getSelectedColumn();
                System.out.println("row " + rowIndex + " columnd " + colIndex);

                boolean flag = !this.cambioListaJefes.get(rowIndex).isAfter();
                this.cambioListaJefes.get(rowIndex).setAfter(flag);
                if (getcambiosTbEmpleados() > 0) {
                    this.mantenimientoUsuarios1.btnSave.setVisible(true);
                } else {
                    this.mantenimientoUsuarios1.btnSave.setVisible(false);
                }

            } catch (Exception er) {
                System.err.println("vista.mantenimientoVacaciones.setFormMantenimientoUsuarios()" + " error " + er.getMessage());

            }
        });
        addlistenerToMantenimientoUsuariosCmbDepartamentos();
        this.mantenimientoUsuarios1.btnSave.addActionListener((e) -> {

            this.cambioListaJefes.forEach(cj -> {
                if (cj.isAfter() != cj.isBefore()) {
                    Usuario us = this.posiblesUsuariosAdministradores.get(cj.getIndex());

                    if (!cj.isBefore() && cj.isAfter()) {
                        this.mantenimientoUsuarios1.addUsuarioGestionVacaciones(us);
                    } else if (cj.isBefore() && !cj.isAfter()) {
                        this.mantenimientoUsuarios1.deleteUsuarioGestionVacaciones(us);
                    }

                }

            });
            setMantenimietoUsuriosForm();

        });

    }

    /**
     * this method sets the information on the interface so that it can be show
     * to e usser
     */
    private void setFormulariosComunes() {
        this.btnSave.setVisible(false);
        jTreeDpLastPath = null;
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/images/icons8-traveler-30.png"));
        setIconImage(icon.getImage());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date nextTuesday = Date.from(funcionesComunes.calcNextTuesday().atStartOfDay(defaultZoneId).toInstant());
        System.out.println("next tuesday " + nextTuesday);
        this.rbEscazu.setSelected(true);
        this.rbTana.setSelected(false);
        this.solicitarComprobante = true;

        this.btnLoading.setVisible(false);
        loadCmbTipPermisos();

        setVacacionesForm();
        this.fromPermisos1.setParameters(usuarioActual,
                listaTipoPermisos,
                myListaPer,
                informationLb, lbDifDias,
                txtVacPend,
                restriccionesParaUsuarioActual);
        this.fromPermisos1.btnEnviar.addActionListener(e -> {
            fromPermisos1.procesarSolicitud();
            if (fromPermisos1.solcitudProcesada) {
                login l = new login();
                this.dispose();
                l.setVisible(true);
            }
        });
        //logica.connection.testConnection(this.usuarioActual.getIdEmpleado(), lbServer, continueBackgroundProcess);
        testConnection = new logica.connection(this.usuarioActual.getIdEmpleado(), lbServer);
        Thread t = new Thread(testConnection);
        t.start();
        // you can use WindowAdapter and implement only the methods you need
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent et) {
                System.out.println("Window closing");
                afterClosing();
            }
        });
    }

    /**
     * this method load on the GUI the actual usser info
     */
    private void showVacacionesInfo() {
        String info = "Vacaciones disponibles: " + this.usuarioActual.getVacacionesPen() + " días \n";
        if (this.restriccionesParaUsuarioActual != null) {
            for (restriccionPermisos r : this.restriccionesParaUsuarioActual) {
                if (r.getIdtipo_restriccion_permisos() == 2) {
                    info += "Cantidad máxima: " + r.getCantidad_maxima() + "\n";
                } else {
                    info += "No se permiten vacaciones entre las fechas\n"
                            + funcionesComunes.getCommunDates(r.getFecha_inicio())
                            + "\nhasta " + funcionesComunes.getCommunDates(r.getFecha_fin()) + "\n";
                }
            }
        }
        info += "Solicitudes de vaciones perndientes: " + this.contVacaciones + "\n";
        this.txtVacPend.setText(info);

    }

    /**
     * this function validates which usser type is logged in, then set the form
     * components acordingly
     */
    private void setTipoUsuarioForm() {
        //3059, 3206, 3273, 2768 rrhh
        if (this.usuarioActual.getDepartamento() == 9 && usuarioActual.getIdPuesto() == 21) {
            setUsuarioAdmin();
        } else {
            if (esEncargadoDepartamento()) {
                setEncargadoVariables();
                setEncargadoform();
            } else {
                setUsuarioComun();
            }

        }
    }

    /**
     * this method loads from the database the info needded for a encargado type
     * usser
     */
    private void setEncargadoVariables() {
        setListaPermPend();
        cargarListaEmpConPermPend();
        int idDepartamento = this.usuarioActual.getDepartamento();
        departamento d = logica.departamentoLog.getDepartamentosPorId(idDepartamento);
        this.alldepartamentos = new ArrayList<>();
        this.alldepartamentos.add(d);
        this.alldepartamentos.forEach(e -> {
            System.out.println("getting records for: " + e.getDescripcion());
            e.puestos = logica.puestoLogica.getPuestoPorIdDep(e.getId_Departamento());
        });
        this.tiporestricciones = logica.tipo_restriccion_permisos_log.getAllTipos();
        this.posiblesUsuariosAdministradores = new ArrayList<>();
        this.tipoUsuarios = logica.tipoUsuariosLogica.getAllTipoUsuarios();

        this.cambioListaJefes = new ArrayList<>();
    }

    /**
     * this method loads on the interface the info needded for an encargado type
     * usser
     */
    private void setEncargadoform() {

        removeFormFromTheMainContent();
        this.btnUndoChange.setVisible(false);
        this.btnNotificaion.setVisible(false);
        this.rbPendientes.setSelected(true);
        this.lbUsusarioActual.setText("@" + this.usuarioActual.getNombre() + " " + this.usuarioActual.getApellido1());
        vista.util.commonPanel.handleResizePanel(this.rigth, 0, 600);
        vista.util.botonComun.mouseHoverListener(this.btnRefresh);
        vista.util.botonComun.mouseHoverListener(this.btnNotificaion);
        vista.util.botonComun.mouseHoverListener(this.btnUndoChange);
        setDepConSolicitudes();
        loadTreeDepartamentos();
        setPermisosForm();
        setInfoPermisosForm();
        this.setResizable(false);
        this.pnlMainContent.remove(pnlAdminRestricciones);
        this.pnlMainContent.remove(mantenimientoEncargados);
        this.pnlMainContent.remove(this.pnlHistorial);
        this.pnlInfoPermiso1.btnAprobarDirecto.setVisible(false);
        this.fromPermisos1.btnAprobar.setVisible(false);
        this.fromPermisos1.btnDenegar.setVisible(true);
        this.fromPermisos1.btnVisto.setVisible(true);
    }

    /**
     * this function checks if the actual usser is an encargado type, if in fact
     * it is returns a true boolean value, else an false
     *
     * @return a boolean value
     */
    private boolean esEncargadoDepartamento() {

        boolean found = false;
        int count = 0;
        while (count < this.listaJefes.size() && !found) {
            usuarioGestionVacaciones us = this.listaJefes.get(count);
            if (us.getUs().getIdEmpleado() == this.usuarioActual.getIdEmpleado()) {
                found = true;
            }
            count++;
        }
        System.out.println("encargado " + found);
        return found;
    }

    /**
     * this function set the GUI for an admin usser this function is tobe called
     * when the logged usser is an admin one
     */
    private void setUsuarioAdmin() {
        setAdminVariables();
        setAdminMainInterface();
    }

    /**
     * this function set the GUI for an commun usser this function is tobe
     * called when the logged usser is not an admin one
     */
    private void setUsuarioComun() {

        this.setResizable(false);
        this.pnlMainContent.remove(pnlAdminPerm);
        this.pnlMainContent.remove(pnlAdminRestricciones);
        this.pnlMainContent.remove(mantenimientoEncargados);
        this.infEmppnl.remove(misAdminOpciones);
        this.infEmppnl.remove(menuToolBar);
        this.remove(this.left);
        this.pnlMainContent.remove(this.pnlHistorial);
        vista.util.commonPanel.handleResizePanel(this.rigth, 300, 600);
        mostrarInfoPermisos();
        ArrayList<departamento> lista = new ArrayList<>();
        lista.add(departUsuarioActual);
        fromPermisos1.mostrarInfoEmpleado(usuarioActual, lista, puestos);
        showVacacionesInfo();
        this.fromPermisos1.btnAprobar.setVisible(false);
        this.fromPermisos1.btnDenegar.setVisible(false);
        this.fromPermisos1.btnVisto.setVisible(false);
        this.toolBarUsserHelp.setVisible(false);

    }

    /**
     * this method loads on the tree departamentos the records in the
     * departamentos list
     */
    private void loadTreeDepartamentos() {
        DefaultMutableTreeNode deps = new DefaultMutableTreeNode("DEPARTAMENTOS");

        this.departamentosConSolicitudes.forEach(e -> {

            DefaultMutableTreeNode p = new DefaultMutableTreeNode(e.getDescripcion());
            e.puestos.forEach(pe -> {

                p.add(new DefaultMutableTreeNode(pe.getDescripcion()));

            });
            deps.add(p);

        });
        DefaultTreeModel model = new DefaultTreeModel(deps);
        this.departamentosJTree.setModel(model);
        vista.util.JTreeUtil.setTreeExpandedState(this.departamentosJTree, true);
    }

    /**
     * this method set all the needded preparations to make a vacaciones
     * solicitud, this on the form permisos
     */
    private void setVacacionesForm() {
        this.solicitarComprobante = false;
    }

    /**
     * this method loads from the database the puestos records and asing those
     * records on the departamentos puestos property list
     */
    private void loadPuestosPorDepartamento() {

        this.empleConPermsPend.forEach(e -> {
            int idPuesto = e.getIdPuesto();
            this.departamentosConSolicitudes.forEach(d -> {
                puesto p = null;
                boolean found = entidades.puesto.puestoExists(d.puestos, idPuesto);
                if (!found) {
                    if (e.getDepartamento() == d.getId_Departamento()) {
                        p = logica.puestoLogica.getPuestoPorId(idPuesto);
                    }
                }
                if (p != null) {
                    d.puestos.add(p);
                }
            });
        });

    }

    /**
     * this method checks from all departamentos which one have some permisos,
     * in the case that this departamento have permisos is added to the list
     * that is to be show to the usser on the screen
     */
    private void loadDepPuestConSolicitudes() {
        this.departamentosConSolicitudes = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < empleConPermsPend.size(); i++) {
            departamento d = null;
            if (id != empleConPermsPend.get(i).getDepartamento()) {
                id = empleConPermsPend.get(i).getDepartamento();

                boolean found = entidades.departamento.departamentoExist(departamentosConSolicitudes, id);
                if (!found) {

                    d = logica.departamentoLog.getDepartamentosPorId(id);
                }

            }
            if (d != null) {

                System.out.println("dep con solicitudes " + d.getDescripcion());
                departamentosConSolicitudes.add(d);
            }
        }

    }

    /**
     * this method sets the departamentos and puestos whith soicitudes
     * pendientes to be showed on the usser interfae
     */
    private void setDepConSolicitudes() {
        loadDepPuestConSolicitudes();
        loadPuestosPorDepartamento();
    }

    /**
     * this method loads the permisos list on the usser interface
     */
    private void mostrarInfoPermisos() {
        String infr = "";
        if (this.myListaPer != null) {
            for (int i = 0; i < this.myListaPer.size(); i++) {
                String description = entidades.tipoPermisos.getDescriptionPermiso(this.listaTipoPermisos, this.myListaPer.get(i).getIdTipo_permiso());
                infr = infr + "Su permiso para " + description
                        + "\nFecha inicial " + funcionesComunes.getCommunDates(this.myListaPer.get(i).getFecha_inicio())
                        + "\nHasta " + funcionesComunes.getCommunDates(this.myListaPer.get(i).getFecha_fin())
                        + "\nEstado solicitud: ";
                infr = infr + entidades.tipoPermisos.getEstadoPermiso(this.myListaPer.get(i).getEstado());
                if (this.myListaPer.get(i).getEstado() > 0) {
                    infr = infr + "\nFavor presentarse a RRHH";
                }
                infr = infr + "\n";
                infr = infr + "\n";
            }
        }
        this.txtareaInformacion.setText(infr);
    }

    /**
     * this method loads the idTipoPermiso permisos list on the jlist
     * idTipoPermiso permisos
     */
    private void loadCmbTipPermisos() {
        DefaultListModel model = new DefaultListModel();
        this.jListTipoPermisos.setModel(model);
        if (this.listaTipoPermisos != null) {
            this.listaTipoPermisos.forEach(e -> {
                System.out.println("tipo: " + e.getDescripcion());
                model.addElement(e.getDescripcion());
            });
        }
        this.jListTipoPermisos.setSelectedIndex(0);
    }

    /**
     * this method adds an element to to the jlist permisos and to the
     * background permisos list (subListaPerPend)
     *
     * @param e is a permisos element
     * @param model DefaultListModel must be from jlist permisos
     * @param us is an usuario object
     */
    private void addElementTojlistPermisosPendientes(DefaultListModel model, permisos e, Usuario us) {
        this.subEmpleConPermsPend.add(us);
        this.subListaPerPend.add(e);
        model.addElement(us.getNombre() + " " + us.getPrimer_Apellido());
    }

    /**
     * this method controls the elements that must be show on on the jlist
     * permisos according to the usser selecction, and it corresponding
     * background info
     *
     * @param e is a permisos element
     * @param model DefaultListModel must be from jlist permisos
     * @param us is an usuario object
     */
    private void setListaVistoOrPendiente(DefaultListModel model, permisos e, Usuario us) {
        if (this.verPendientes) {
            System.out.println("perm pendientes");
            if (e.getEstado() == 0) {
                addElementTojlistPermisosPendientes(model, e, us);
            }
        } else if (e.getEstado() == 1) {
            System.out.println("perm vistos");
            addElementTojlistPermisosPendientes(model, e, us);
        }
    }

    /**
     * this method loads the jlistpermisos, therefore loops through the
     * subListaPerPend elements to filter them as the usser has seleccted, and
     * then shows on the list
     *
     * @param d is a departamento object, selected by the usser
     * @param idpuest is a puesto id, seleccted by the usser
     * @param tipo is the permisos tipo, selected by the usser
     */
    private void loadJlistPermisos(departamento d, int idpuest, int tipo) {
        DefaultListModel model = new DefaultListModel();
        this.jlistPermisos.setModel(model);

        this.listaPerPend.forEach(e -> {
            Usuario us = entidades.Usuario.getUsuario(empleConPermsPend, e.getIdEmpleado());
            if (filtrosPermisosValidado(e, tipo, d, us, idpuest)) {

                System.out.println(us.getNombre() + " " + us.getPrimer_Apellido());
                setListaVistoOrPendiente(model, e, us);

            }

        });
        this.jlistPermisos.setSelectedIndex(0);
    }

    /**
     * this function validates the filters seleccted by the usser for a permiso
     * object, if the filters are validated returns a true boolean values else
     * returns a false
     *
     * @param us is a usuario object
     * @param e is a permisos object
     * @param tipo is a tipo permisos id
     * @param d is a departamento object
     * @param idpuest is a puesto id
     */
    private boolean filtrosPermisosValidado(permisos e, int tipo, departamento d, Usuario us, int idpuest) {

        boolean res = false;
        if (tipoPermisoSeleccionado(e, tipo)) {
            if (departAndPuestSelected(d, us, idpuest)) {
                res = true;
            }
        }
        return res;

    }

    /**
     * this function validates the tipo de permisos seleccted by the usser for a
     * permiso object, if the filters are validated returns a true boolean
     * values else returns a false
     *
     * @param e is a permisos object
     * @param tipo is a tipo permisos id
     */
    private boolean tipoPermisoSeleccionado(permisos e, int tipo) {
        boolean res = false;
        if (e.getIdTipo_permiso() == tipo) {
            res = true;
        }
        return res;
    }

    /**
     * this function validates the departamento and puesto seleccted by the
     * usser for a permiso boject, if the filters are validated returns a true
     * boolean values else returns a false
     *
     * @param us is a usuario object
     * @param d is a departamento object
     * @param idpuest is a puesto id
     */
    private boolean departAndPuestSelected(departamento d, Usuario us, int idpuest) {
        boolean res = false;
        if (us.getDepartamento() == d.getId_Departamento() && us.getIdPuesto() == idpuest) {
            res = true;
        }
        return res;
    }

    /**
     * this methos removes the index selected on the jlistPermisos use it when
     * the permiso estado chage
     */
    private void removeSelectedFromListaPermisos() {

        try {

            DefaultListModel model = (DefaultListModel) jlistPermisos.getModel();
            int index = jlistPermisos.getSelectedIndex();
            model.removeElementAt(index);

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
//</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        top = new javax.swing.JPanel();
        botton = new javax.swing.JPanel();
        lbUsusarioActual = new javax.swing.JLabel();
        informationLb = new javax.swing.JLabel();
        lbDifDias = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        left = new javax.swing.JPanel();
        scroolDep = new javax.swing.JScrollPane();
        departamentosJTree = new javax.swing.JTree();
        scroolOpciones = new javax.swing.JScrollPane();
        jListTipoPermisos = new javax.swing.JList<>();
        rigth = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtareaInformacion = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtVacPend = new javax.swing.JTextArea();
        tittlepnl = new javax.swing.JPanel();
        infEmppnl = new javax.swing.JPanel();
        misAdminOpciones = new javax.swing.JToolBar();
        checkMiPropioPermiso = new javax.swing.JCheckBox();
        menuToolBar = new javax.swing.JToolBar();
        btnRefresh = new javax.swing.JButton();
        btnNotificaion = new javax.swing.JButton();
        btnUndoChange = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        menuPnl = new javax.swing.JPanel();
        rbEscazu = new javax.swing.JRadioButton();
        rbTana = new javax.swing.JRadioButton();
        toolBarUsserHelp = new javax.swing.JToolBar();
        btnLoading = new javax.swing.JButton();
        btnInfo = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbServer = new javax.swing.JLabel();
        pnlMainContent = new javax.swing.JTabbedPane();
        pnlAdminPerm = new javax.swing.JPanel();
        adminRigthPnl = new javax.swing.JPanel();
        adminTopPnl = new javax.swing.JPanel();
        adminCentPnl = new javax.swing.JPanel();
        admcntTopPnl = new javax.swing.JPanel();
        lbname = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        pnlInfoPermiso1 = new vista.components.pnlInfoPermiso();
        adminLeftPnl = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        rbVistos = new javax.swing.JRadioButton();
        rbPendientes = new javax.swing.JRadioButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jlistPermisos = new javax.swing.JList<>();
        adminButomPnl = new javax.swing.JPanel();
        jpbPermisos = new javax.swing.JProgressBar();
        pnlAdminRestricciones = new javax.swing.JPanel();
        restricciones1 = new vista.components.restricciones();
        pnlHistorial = new javax.swing.JPanel();
        historialPermisos1 = new vista.components.getHistorialPermiso();
        mantenimientoEncargados = new javax.swing.JPanel();
        mantenimientoUsuarios1 = new vista.components.mantenimientoUsuarios();
        pnlSolicitudes = new javax.swing.JPanel();
        fromPermisos1 = new vista.components.fromPermisos();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mantenimiento Gestion Vacaciones");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1100, 700));

        top.setBackground(new java.awt.Color(255, 255, 255));
        top.setMinimumSize(new java.awt.Dimension(600, 300));
        getContentPane().add(top, java.awt.BorderLayout.NORTH);

        botton.setBackground(new java.awt.Color(255, 255, 255));
        botton.setMinimumSize(new java.awt.Dimension(600, 40));
        botton.setPreferredSize(new java.awt.Dimension(600, 40));
        botton.setLayout(new java.awt.GridLayout(1, 4, 5, 5));
        botton.add(lbUsusarioActual);

        informationLb.setForeground(new java.awt.Color(255, 51, 51));
        botton.add(informationLb);

        lbDifDias.setForeground(new java.awt.Color(255, 51, 51));
        botton.add(lbDifDias);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/return.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(null);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        botton.add(btnSalir);

        getContentPane().add(botton, java.awt.BorderLayout.SOUTH);

        left.setBackground(new java.awt.Color(255, 255, 255));
        left.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2), "OPCIONES"));
        left.setPreferredSize(new java.awt.Dimension(300, 600));
        left.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        departamentosJTree.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "DEPARTAMENTOS/PUESTOS"));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("DEPARTAMENTOS");
        departamentosJTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        departamentosJTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                departamentosJTreeValueChanged(evt);
            }
        });
        departamentosJTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        scroolDep.setViewportView(departamentosJTree);

        left.add(scroolDep);

        jListTipoPermisos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "TIPO PERMISOS"));
        jListTipoPermisos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListTipoPermisos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListTipoPermisos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListTipoPermisosValueChanged(evt);
            }
        });
        scroolOpciones.setViewportView(jListTipoPermisos);

        left.add(scroolOpciones);

        getContentPane().add(left, java.awt.BorderLayout.WEST);

        rigth.setBackground(new java.awt.Color(255, 255, 255));
        rigth.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2), "INFORMACIÓN RELEVANTE"));
        rigth.setMinimumSize(new java.awt.Dimension(0, 0));
        rigth.setPreferredSize(new java.awt.Dimension(220, 600));
        rigth.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        txtareaInformacion.setEditable(false);
        txtareaInformacion.setColumns(20);
        txtareaInformacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtareaInformacion.setRows(5);
        jScrollPane1.setViewportView(txtareaInformacion);

        rigth.add(jScrollPane1);

        txtVacPend.setEditable(false);
        txtVacPend.setColumns(20);
        txtVacPend.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVacPend.setRows(5);
        jScrollPane4.setViewportView(txtVacPend);

        rigth.add(jScrollPane4);

        getContentPane().add(rigth, java.awt.BorderLayout.EAST);

        tittlepnl.setPreferredSize(new java.awt.Dimension(600, 60));
        tittlepnl.setLayout(new java.awt.GridLayout(1, 0));

        infEmppnl.setBackground(new java.awt.Color(255, 255, 255));
        infEmppnl.setPreferredSize(new java.awt.Dimension(300, 117));
        infEmppnl.setLayout(new java.awt.GridLayout(1, 0));

        misAdminOpciones.setBackground(new java.awt.Color(255, 255, 255));
        misAdminOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "MI PERMISO"));
        misAdminOpciones.setRollover(true);

        checkMiPropioPermiso.setToolTipText("Seleccione para pedir su propio permiso");
        checkMiPropioPermiso.setFocusable(false);
        checkMiPropioPermiso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        checkMiPropioPermiso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        checkMiPropioPermiso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMiPropioPermisoActionPerformed(evt);
            }
        });
        misAdminOpciones.add(checkMiPropioPermiso);

        infEmppnl.add(misAdminOpciones);

        menuToolBar.setBackground(new java.awt.Color(255, 255, 255));
        menuToolBar.setRollover(true);
        menuToolBar.setBorderPainted(false);

        btnRefresh.setToolTipText("Refresca toda la interfaz");
        btnRefresh.setBackground(new java.awt.Color(204, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-update-left-rotation-30.png"))); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        menuToolBar.add(btnRefresh);

        btnNotificaion.setToolTipText("Ver cambios realizados");
        btnNotificaion.setBackground(new java.awt.Color(204, 255, 255));
        btnNotificaion.setForeground(new java.awt.Color(0, 204, 0));
        btnNotificaion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/checkList.png"))); // NOI18N
        btnNotificaion.setFocusable(false);
        btnNotificaion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNotificaion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotificaionActionPerformed(evt);
            }
        });
        menuToolBar.add(btnNotificaion);

        btnUndoChange.setToolTipText("Deshacer cambio");
        btnUndoChange.setBackground(new java.awt.Color(204, 255, 255));
        btnUndoChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-undo-30.png"))); // NOI18N
        btnUndoChange.setFocusable(false);
        btnUndoChange.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUndoChange.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUndoChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoChangeActionPerformed(evt);
            }
        });
        menuToolBar.add(btnUndoChange);

        btnSave.setBackground(new java.awt.Color(204, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-save-30.png"))); // NOI18N
        btnSave.setToolTipText("Guardar cambios");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        menuToolBar.add(btnSave);

        infEmppnl.add(menuToolBar);

        menuPnl.setBackground(new java.awt.Color(255, 255, 255));
        menuPnl.setLayout(new java.awt.GridLayout(1, 0));

        rbEscazu.setBackground(new java.awt.Color(255, 255, 255));
        rbEscazu.setSelected(true);
        rbEscazu.setText("ESCAZÚ");
        rbEscazu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEscazuActionPerformed(evt);
            }
        });
        menuPnl.add(rbEscazu);

        rbTana.setBackground(new java.awt.Color(255, 255, 255));
        rbTana.setText("SANTA ANA");
        rbTana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTanaActionPerformed(evt);
            }
        });
        menuPnl.add(rbTana);

        infEmppnl.add(menuPnl);

        toolBarUsserHelp.setBackground(new java.awt.Color(255, 255, 255));
        toolBarUsserHelp.setRollover(true);
        toolBarUsserHelp.setBorderPainted(false);
        toolBarUsserHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-loading-bar.gif"))); // NOI18N
        btnLoading.setFocusable(false);
        btnLoading.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLoading.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarUsserHelp.add(btnLoading);

        btnInfo.setBackground(new java.awt.Color(204, 255, 255));
        btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-info-squared-30.png"))); // NOI18N
        btnInfo.setToolTipText("Mas información");
        btnInfo.setFocusable(false);
        btnInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoActionPerformed(evt);
            }
        });
        toolBarUsserHelp.add(btnInfo);

        btnHelp.setBackground(new java.awt.Color(204, 255, 255));
        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-help-30.png"))); // NOI18N
        btnHelp.setToolTipText("Ayuda");
        btnHelp.setFocusable(false);
        btnHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        toolBarUsserHelp.add(btnHelp);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lbServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-servers-group-20.png"))); // NOI18N
        lbServer.setToolTipText("Conectado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addComponent(lbServer)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbServer)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        toolBarUsserHelp.add(jPanel1);

        infEmppnl.add(toolBarUsserHelp);

        tittlepnl.add(infEmppnl);

        getContentPane().add(tittlepnl, java.awt.BorderLayout.PAGE_START);

        pnlMainContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainContent.setToolTipText("");
        pnlMainContent.setPreferredSize(new java.awt.Dimension(600, 673));
        pnlMainContent.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pnlMainContentStateChanged(evt);
            }
        });

        pnlAdminPerm.setBackground(new java.awt.Color(255, 255, 255));
        pnlAdminPerm.setLayout(new java.awt.BorderLayout());

        adminRigthPnl.setBackground(new java.awt.Color(255, 255, 255));
        adminRigthPnl.setPreferredSize(new java.awt.Dimension(30, 232));

        javax.swing.GroupLayout adminRigthPnlLayout = new javax.swing.GroupLayout(adminRigthPnl);
        adminRigthPnl.setLayout(adminRigthPnlLayout);
        adminRigthPnlLayout.setHorizontalGroup(
            adminRigthPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        adminRigthPnlLayout.setVerticalGroup(
            adminRigthPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );

        pnlAdminPerm.add(adminRigthPnl, java.awt.BorderLayout.EAST);

        adminTopPnl.setBackground(new java.awt.Color(255, 255, 255));
        adminTopPnl.setPreferredSize(new java.awt.Dimension(600, 10));

        javax.swing.GroupLayout adminTopPnlLayout = new javax.swing.GroupLayout(adminTopPnl);
        adminTopPnl.setLayout(adminTopPnlLayout);
        adminTopPnlLayout.setHorizontalGroup(
            adminTopPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );
        adminTopPnlLayout.setVerticalGroup(
            adminTopPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        pnlAdminPerm.add(adminTopPnl, java.awt.BorderLayout.NORTH);

        adminCentPnl.setBackground(new java.awt.Color(255, 255, 255));
        adminCentPnl.setPreferredSize(new java.awt.Dimension(600, 400));
        adminCentPnl.setLayout(new java.awt.BorderLayout());

        admcntTopPnl.setBackground(new java.awt.Color(255, 255, 255));
        admcntTopPnl.setPreferredSize(new java.awt.Dimension(353, 20));

        javax.swing.GroupLayout admcntTopPnlLayout = new javax.swing.GroupLayout(admcntTopPnl);
        admcntTopPnl.setLayout(admcntTopPnlLayout);
        admcntTopPnlLayout.setHorizontalGroup(
            admcntTopPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(admcntTopPnlLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(lbname)
                .addContainerGap(322, Short.MAX_VALUE))
        );
        admcntTopPnlLayout.setVerticalGroup(
            admcntTopPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(admcntTopPnlLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lbname)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        adminCentPnl.add(admcntTopPnl, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(353, 20));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        adminCentPnl.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(5, 303));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        adminCentPnl.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(5, 360));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        adminCentPnl.add(jPanel8, java.awt.BorderLayout.WEST);
        adminCentPnl.add(pnlInfoPermiso1, java.awt.BorderLayout.CENTER);

        pnlAdminPerm.add(adminCentPnl, java.awt.BorderLayout.CENTER);

        adminLeftPnl.setBackground(new java.awt.Color(255, 255, 255));
        adminLeftPnl.setPreferredSize(new java.awt.Dimension(200, 600));
        adminLeftPnl.setLayout(new java.awt.GridLayout(1, 0));

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "SOLICITUDES"));

        rbVistos.setText("VISTOS");
        rbVistos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVistosActionPerformed(evt);
            }
        });

        rbPendientes.setSelected(true);
        rbPendientes.setText("PENDIENTES");
        rbPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPendientesActionPerformed(evt);
            }
        });

        jlistPermisos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "SOLICITANTES"));
        jlistPermisos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlistPermisos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlistPermisosValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jlistPermisos);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbVistos, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbVistos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminLeftPnl.add(jPanel13);

        pnlAdminPerm.add(adminLeftPnl, java.awt.BorderLayout.WEST);

        adminButomPnl.setBackground(new java.awt.Color(255, 255, 255));
        adminButomPnl.setPreferredSize(new java.awt.Dimension(600, 10));
        adminButomPnl.setLayout(new java.awt.GridLayout(1, 0));

        jpbPermisos.setBackground(new java.awt.Color(204, 255, 255));
        jpbPermisos.setValue(100);
        adminButomPnl.add(jpbPermisos);

        pnlAdminPerm.add(adminButomPnl, java.awt.BorderLayout.SOUTH);

        pnlMainContent.addTab("ADMINISTRAR PERMISOS", pnlAdminPerm);

        pnlAdminRestricciones.setBackground(new java.awt.Color(255, 255, 255));
        pnlAdminRestricciones.setPreferredSize(new java.awt.Dimension(600, 300));
        pnlAdminRestricciones.add(restricciones1);

        pnlMainContent.addTab("RESTRICCIONES", pnlAdminRestricciones);

        pnlHistorial.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pnlHistorialComponentResized(evt);
            }
        });
        pnlHistorial.add(historialPermisos1);

        pnlMainContent.addTab("HISTORIAL", pnlHistorial);

        mantenimientoEncargados.add(mantenimientoUsuarios1);

        pnlMainContent.addTab("USUARIOS", mantenimientoEncargados);

        pnlSolicitudes.setBackground(new java.awt.Color(255, 255, 255));
        pnlSolicitudes.add(fromPermisos1);

        pnlMainContent.addTab("SOLICITUDES", pnlSolicitudes);

        getContentPane().add(pnlMainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbEscazuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEscazuActionPerformed
        // TODO add your handling code here:
        if (this.rbEscazu.isSelected()) {
            this.rbTana.setSelected(false);
        } else {
            this.rbTana.setSelected(true);
        }
    }//GEN-LAST:event_rbEscazuActionPerformed

    private void rbTanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTanaActionPerformed
        // TODO add your handling code here:
        if (this.rbTana.isSelected()) {
            this.rbEscazu.setSelected(false);
        } else {
            this.rbEscazu.setSelected(true);
        }

    }//GEN-LAST:event_rbTanaActionPerformed


    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:

        afterClosing();
    }//GEN-LAST:event_btnSalirActionPerformed
    /**
     * this method creates a login windows and the dispose of the current window
     */
    private void comebakLogin() {
        login log = new login();
        log.setVisible(true);
        this.dispose();
    }

    /**
     * this method checks if there are changes to save, if there are confirms if
     * the usser whats to save after clossing
     */
    private void afterClosing() {
        this.testConnection.terminateProcess();
        String[] options = {"Guardar y cerrar", "Cerrar"};
        if (listaPermisosModificados.isEmpty()) {
            comebakLogin();
        } else {

            int selection = JOptionPane.showOptionDialog(null, "Guardar y cerrar", "Tienes cambios sin guardar", 0, 3, null, options, options[0]);
            switch (selection) {
                case 0:
                    saveChanges();
                    comebakLogin();
                    break;
                default:
                    comebakLogin();
                    break;
            }

        }
    }

    /**
     * this method takes the selected permisos info, looks for the related usser
     * and then shows it's info on the pnlInfoPermiso1
     */
    private void mostrarPermisoParaEditar() {

        try {
            int index = this.jlistPermisos.getSelectedIndex();
            this.usuarioPermiso = this.subEmpleConPermsPend.get(index);

            pnlInfoPermiso1.mostrarPermisoParaEditar(jlistPermisos, usuarioPermiso, subEmpleConPermsPend, subListaPerPend, permisoActual, listaTipoPermisos, iconComprobanteActual, txtareaInformacion);
            this.fromPermisos1.mostrarInfoEmpleado(usuarioPermiso, this.departamentosConSolicitudes, this.puestos);
            this.permisoActual = permisos.getPermisoFromPermiso(this.subListaPerPend.get(index));
            if (this.permisoActual.getIdComprobante() > 0) {
                this.iconComprobanteActual = logica.comprobanteLogica.getImagenComprobante(this.permisoActual.getIdComprobante());
            }
        } catch (Exception e) {
            System.out.println("vista.mantenimiento02.mostrarPermisoParaEditar()");
            System.out.println("error: " + e.getMessage());
            this.txtareaInformacion.setText("");
        }

    }

    /**
     * this method clears the jlistPermisos info
     */
    private void clearJlistPermisos() {
        DefaultListModel listModel = (DefaultListModel) jlistPermisos.getModel();
        listModel.removeAllElements();
    }
    private void pnlMainContentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pnlMainContentStateChanged
        // TODO add your handling code here:
        int tabIndex = this.pnlMainContent.getSelectedIndex();
        //JOptionPane.showMessageDialog(null, "tab selected " + tabIndex);
        switch (tabIndex) {
            case 0:
                removeFormFromTheMainContent();
                vista.util.commonPanel.handleResizePanel(this.left, 220, 600);
                vista.util.commonPanel.handleResizePanel(this.rigth, 0, 600);
                break;
            case 1:
                if (!esEncargadoDepartamento()) {
                    removeFormFromTheMainContent();
                }
                vista.util.commonPanel.handleResizePanel(this.left, 0, 600);
                vista.util.commonPanel.handleResizePanel(this.rigth, 220, 600);
                break;
            case 2:
                removeFormFromTheMainContent();
                vista.util.commonPanel.handleResizePanel(this.rigth, 230, 600);
                vista.util.commonPanel.handleResizePanel(this.left, 0, 600);
                break;
            case 3:
                removeFormFromTheMainContent();
                vista.util.commonPanel.handleResizePanel(this.left, 0, 600);
                vista.util.commonPanel.handleResizePanel(this.rigth, 0, 600);
                break;
            case 4:
                vista.util.commonPanel.handleResizePanel(this.left, 0, 600);
                vista.util.commonPanel.handleResizePanel(this.rigth, 0, 600);
                break;

            default:
                removeFormFromTheMainContent();
                vista.util.commonPanel.handleResizePanel(this.rigth, 220, 600);
                vista.util.commonPanel.handleResizePanel(this.left, 220, 600);
                break;

        }

    }//GEN-LAST:event_pnlMainContentStateChanged

    /**
     * this method takes usser selected info, this to show this info on the
     * permisos form
     */
    private void prepararEditarPermiso() {
        if (this.permisoActual != null) {
            this.fromPermisos1.mostrarInfoEmpleado(this.usuarioPermiso, departamentosConSolicitudes, puestos);
            int index = this.jListTipoPermisos.getSelectedIndex();
            this.fromPermisos1.prepararFormularioParaEditar(this.permisoActual, index);
            if (esEncargadoDepartamento()) {
                this.fromPermisos1.btnAprobar.setVisible(false);
            }
            this.pnlMainContent.addTab("SOLICITUDES", pnlSolicitudes);
            int count = this.pnlMainContent.getComponentCount() - 1;
            this.pnlMainContent.setSelectedIndex(count);

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
            this.pnlMainContent.setSelectedIndex(0);
        }
    }

    /**
     * this method removes from the permisos list the changed or deleted
     * permisos register
     */
    private void removeFromListPermPend() {
        int index = entidades.permisos.getIndexOnTheList(this.listaPerPend, this.permisoActual);
        if (index > -1) {
            removeSelectedFromListaPermisos();
            this.refrescando = true;
            if (permisoActual.getEstado() > 1 || permisoActual.getEstado() == -1) {
                this.listaPerPend.remove(index);
                System.out.println("index eliminado " + index);
            } else {
                this.listaPerPend.set(index, permisoActual);
                permisos.setPermisoOntheList(permisoActual, listaPerPend);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
            removeFormFromTheMainContent();
            this.pnlMainContent.setSelectedIndex(0);
        }

    }

    /**
     * this method saves temporally the permisos information changes, so those
     * changes can be reversed if necesary
     */
    private void recordarCambio(permisos permisoEstadoAnterior) {
        this.btnUndoChange.setVisible(true);
        this.btnSave.setVisible(true);
        int index = entidades.permisos.getIndexOnTheList(this.listaPerPend, permisoEstadoAnterior);
        permisos permisoOriginal = permisos.getPermisoFromPermiso(this.listaPerPend.get(index));
        cambiosPermisos pc = new cambiosPermisos(permisoOriginal, permisoEstadoAnterior);
        System.out.println("estado permiso actual " + permisoOriginal.getEstado() + " estado anterior " + permisoEstadoAnterior.getEstado());
        //System.out.println("fecha permiso actual " + permisoEstadoAnterior.getFecha_inicio() + " fecha anterior " + permisoOriginal.getFecha_inicio());
        this.listaPermisosModificados.add(pc);
        this.btnNotificaion.setText(this.listaPermisosModificados.size() + "");
        this.changes = this.listaPermisosModificados.size();
        //this.btnSave.setText("" + changes);
    }

    /**
     * this method saves temporally the permisos information changes, so those
     * changes can be reversed if necesary
     */
    private void recordarDeleted(permisos permisoOrigial, permisos permisoEstadoAnterior) {
        this.btnUndoChange.setVisible(true);
        this.btnSave.setVisible(true);
        cambiosPermisos pc = new cambiosPermisos(permisoEstadoAnterior, permisoOrigial);
        //JOptionPane.showMessageDialog(null, "estado permiso actual " + pc.getModificado().getEstado() + " estado anterior " + pc.getOriginal().getEstado());
        System.out.println("estado permiso actual " + pc.getModificado().getEstado() + " estado anterior " + pc.getOriginal().getEstado());
        this.listaPermisosModificados.add(pc);
        this.btnNotificaion.setText(this.listaPermisosModificados.size() + "");
        this.changes = this.listaPermisosModificados.size();
        //this.btnSave.setText("" + changes);
    }

    /**
     * this function checks if the permisoActual is not null and it does not
     * asocciated with the actual usser
     *
     * @return a boolean true value if the conditions are met, false if it not
     */
    private boolean accionPermisoActualValida() {
        boolean res = false;

        if (this.permisoActual != null) {
            if (permisoActual.getIdEmpleado() != this.usuarioActual.getIdEmpleado()) {
                res = true;
            } else {
                JOptionPane.showMessageDialog(null, "Usted no puede procesar su propio permiso");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
        }
        return res;
    }

    /**
     * this method follow step by step the process to update a permisos
     * register. It creates an auditoria register for the changes, makes the
     * updates on the database, theb save the locally changes, so the changes
     * would be reverted in the same usser session
     *
     * @param nuevoEstado is the new state for the permisos register
     */
    private void procesarCambiosPermisoActual(int nuevoEstado) {

        boolean flag = addAuditoria(nuevoEstado, this.permisoActual);
        if (flag) {
            this.permisoActual.setEstado(nuevoEstado);
            permisos permisoModificado = permisos.getPermisoFromPermiso(this.permisoActual);
            boolean result = updatePermiso(permisoActual);
            if (result) {
                afterChangesProcess(permisoModificado);
                JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }

    }

    /**
     * this method follow step by step the process to update a permisos
     * register. It creates an auditoria register for the changes, makes the
     * updates on the database, theb save the locally changes, so the changes
     * would be reverted in the same usser session
     *
     * @param nuevoEstado is the new state for the permisos register
     */
    private void procesarCambiosPermisoEnBD(permisos p, int nuevoEstado) {

        boolean flag = addAuditoria(nuevoEstado, p);
        if (flag) {
            //p.setEstado(nuevoEstado);
            permisos permisoModificado = permisos.getPermisoFromPermiso(p);
            permisoModificado.setEstado(nuevoEstado);
            boolean result = updatePermiso(permisoModificado);
            if (result) {
                //afterChangesProcess(permisoModificado);
                JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }

    }

    /**
     * this method follow step by step the process to update a permisos
     * register. It creates an auditoria register for the changes, makes the
     * updates on the database, theb save the locally changes, so the changes
     * would be reverted in the same usser session
     *
     * @param nuevoEstado is the new state for the permisos register
     */
    private void procesarCambiosPermisosLocalmente(int nuevoEstado) {

        if (nuevoEstado == -1) {
            deletetePermisoProcess();
        } else {
            if (nuevoEstado != this.permisoActual.getEstado()) {
                //in this case permisoActual is not the same as the one on the list
                this.permisoActual.setEstado(nuevoEstado);
                afterChangesProcess(this.permisoActual);
            }
        }

    }

    /**
     * this function updates a permisos register on the database, and the
     * returns a boolean value
     *
     * @return a boolean true value if the process is a success, false if it is
     * not
     */
    private boolean updatePermiso(permisos p) {
        return logica.permisosLogica.updatePermiso(p);
    }

    /**
     * this function adds an auditoria register on the database, and the returns
     * a boolean value
     *
     * @return a boolean true value if the process is a success, false if it is
     * not
     */
    private boolean addAuditoria(int nuevoEstado, permisos p) {
        Auditoria_permiso au = entidades.Auditoria_permiso.getAuditoriaPermiso(p.getEstado(), nuevoEstado, p, this.usuarioActual);
        boolean res = logica.auditoria_permisoLogica.addAuditoria(au);
        return res;
    }

    /**
     * this method handles the internal process after changes made on the
     * permisos information by the usser, and then changes the permisos info
     * shows on the GUI
     *
     * @param permisoModificado is a permisos object who has been updated
     */
    private void afterChangesProcess(permisos permisoModificado) {
        recordarCambio(permisoModificado);
        removeFromListPermPend();
        cargarListaEmpConPermPend();
        setDepConSolicitudes();
        loadTreeDepartamentos();
        clearJlistPermisos();
        this.refrescando = false;
        this.permisoActual = null;
        this.pnlInfoPermiso1.clearForm();
        this.fromPermisos1.clearForm();
        removeFormFromTheMainContent();
        this.pnlMainContent.setSelectedIndex(0);
        setSelectedJTreeDepartamentos();
    }

    /**
     * this method handles the internal process after changes made on the
     * permisos information by the usser, and then changes the permisos info
     * shows on the GUI
     *
     * @param permisoModificado is a permisos object who has been updated
     */
    private void afterDeleteProcess(permisos permisoOriginal, permisos permisoModificado) {
        recordarDeleted(permisoOriginal, permisoModificado);
        this.refrescando = true;
        removeFromList(permisoModificado);
        removeSelectedFromListaPermisos();
        cargarListaEmpConPermPend();
        setDepConSolicitudes();
        loadTreeDepartamentos();
        clearJlistPermisos();
        this.refrescando = false;
        this.permisoActual = null;
        this.pnlInfoPermiso1.clearForm();
        this.fromPermisos1.clearForm();
        removeFormFromTheMainContent();
        this.pnlMainContent.setSelectedIndex(0);
        setSelectedJTreeDepartamentos();
    }

    /**
     * this method removes from the permisos list the changed or deleted
     * permisos register
     */
    private void removeFromList(permisos p) {
        int index = entidades.permisos.getIndexOnTheList(this.listaPerPend, this.permisoActual);
        this.listaPerPend.remove(index);
        System.out.println("index eliminado " + index);

    }

    private void updatePermiso01(int anteriorEstado) {
        if (this.permisoActual != null) {
            Auditoria_permiso au = entidades.Auditoria_permiso.getAuditoriaPermiso(anteriorEstado, permisoActual.getEstado(), permisoActual, this.usuarioActual);
            boolean flag = logica.auditoria_permisoLogica.addAuditoria(au);
            if (flag) {
                boolean result = logica.permisosLogica.updatePermiso(permisoActual);
                if (result) {
                    JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
            removeFormFromTheMainContent();
            this.pnlMainContent.setSelectedIndex(0);
        }
    }

    /**
     * this function reverse a change made on a permisos register on the
     * database
     *
     * @param nuevoEstado is an int that represents the change made before, it
     * can be a -1 delete, 1 update
     * @return a -1 if the process reverted was a delete and a 1 if it was an
     * update
     */
    private int reversarCambio(permisos p, int nuevoEstado) {
        int resultado = -1;
        Auditoria_permiso au = entidades.Auditoria_permiso.getAuditoriaPermiso(nuevoEstado, p.getEstado(), p, this.usuarioActual);
        boolean flag = logica.auditoria_permisoLogica.addAuditoria(au);
        if (flag) {
            boolean responce = false;
            if (nuevoEstado == -1) {
                responce = logica.permisosLogica.addPermisoEliminado(p);
            } else {
                responce = logica.permisosLogica.updatePermiso(p);
            }

            resultado = responce ? 1 : -1;

        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }

        return resultado;
    }

    /**
     * this method handles the deleting process of a permisos register, that
     * includes creates an auditoria register, delete the permisos register,
     * record the temporally the change, and show the changes to the usser on
     * the GUI
     */
    private void deletetePermisoProcess() {
        int nuevoEstado = -1;

        boolean flag = addAuditoria(nuevoEstado, permisoActual);
        if (flag) {
            permisos p = permisos.getPermisoFromPermiso(this.permisoActual);
            int index = entidades.permisos.getIndexOnTheList(this.listaPerPend, this.permisoActual);
            permisos permisoOriginal = this.listaPerPend.get(index);
            permisoOriginal.setEstado(nuevoEstado);
            boolean result = logica.permisosLogica.deletePermiso(p);

            if (result) {
                afterDeleteProcess(permisoOriginal, p);
                //JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");

            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }

    }

    /**
     * this method handles the deleting process of a permisos register, that
     * includes creates an auditoria register, delete the permisos register,
     * record the temporally the change, and show the changes to the usser on
     * the GUI
     */
    private void deletetePermisoProcess01(permisos p) {
        int nuevoEstado = -1;

        boolean flag = addAuditoria(nuevoEstado, p);
        if (flag) {
            //int prevestado = this.permisoActual.getEstado();
            //permisos p = this.permisoActual;
            //p.setEstado(nuevoEstado);
            boolean result = logica.permisosLogica.deletePermiso(p);
            if (result) {
                //p.setEstado(-1);
                afterChangesProcess(p);
                JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");

            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }

    }

    /**
     * this method handles the change of state process of a permisos register
     */
    private void solicitudVista() {
        if (accionPermisoActualValida()) {
            permisos p = fromPermisos1.getPermisoForm();
            this.permisoActual.setFecha_inicio(p.getFecha_inicio());
            this.permisoActual.setFecha_fin(p.getFecha_fin());
            procesarCambiosPermisosLocalmente(1);
            //procesarCambiosPermisoActual(1);
        } else {
            removeFormFromTheMainContent();
            this.pnlMainContent.setSelectedIndex(0);
        }
    }

    /**
     * this method creates a new thread to make an permisos register update on
     * the database
     */
    private void procesarHilo(int tipoAccion, permisos p) {
        permisos permisoModificado = permisos.getPermisoFromPermiso(p);
        permisosThreads prth = new permisosThreads(tipoAccion, permisoModificado, usuarioActual, this.jpbPermisos);
        Thread t = new Thread(prth);
        t.start();

    }
    private void departamentosJTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_departamentosJTreeValueChanged
        // TODO add your handling code here:
        setPermisosProcess();
    }//GEN-LAST:event_departamentosJTreeValueChanged

    /**
     * this method prepare the GUI to show the filtered permisos info
     */
    private void setPermisosProcess() {
        this.pnlInfoPermiso1.clearForm();
        this.permisoActual = null;

        if (departamentosJTreeValidado()) {
            prepareListToShow();
        } else {
            cleanJlistPermisosWhenNotValidNode();
        }
    }

    /**
     * this method sets the path selected on the departamentosJTree on the
     * jTreeDpLastPath variable
     */
    private void setTreeDepPath() {
        this.jTreeDpLastPath = departamentosJTree.getSelectionPath();
        DefaultMutableTreeNode selectedElement
                = (DefaultMutableTreeNode) departamentosJTree.getSelectionPath().getLastPathComponent();
        System.out.println("tree departamentos " + selectedElement.getUserObject());
    }

    /**
     * this function checks the parameters to show the permisos information has
     * been selected correctly, if this is the case returns a boolean true, else
     * returns a false
     */
    private boolean departamentosJTreeValidado() {
        boolean res = false;
        try {
            if (!this.refrescando) {
                setTreeDepPath();
                int path = jTreeDpLastPath.getPathCount();
                if (path > 2) {
                    res = true;
                }
            } else {
                System.out.println("Refrescando informacion...");
            }
        } catch (Exception e) {
            System.out.println("vista.mantenimientoVacaciones.departamentosJTreeValidado()");
            System.out.println("error refrescando informacion de permisos: " + e.getMessage());
            res = false;
        }
        return res;

    }

    /**
     * this method looks for changes on the listaPermisosModificados list, and
     * the makes the changes in the database in a new thread
     */
    private void saveChanges() {
        boolean empty = this.listaPermisosModificados.isEmpty();
        if (!empty) {
            this.jpbPermisos.setMinimum(0);
            this.jpbPermisos.setMaximum(listaPermisosModificados.size());
            this.jpbPermisos.setValue(0);
            this.listaPermisosModificados.forEach(e -> {
                System.out.println("permiso id " + e.getOriginal().getIdPermiso() + " estado " + e.getOriginal().getEstado() + " nuevo estado " + e.getModificado().getEstado());
                int nuevoEstado = e.getModificado().getEstado();
                if (nuevoEstado == -1) {
                    System.out.print("eliminar permiso id " + e.getOriginal().getIdPermiso() + " estado " + e.getOriginal().getEstado() + " nuevo estado " + e.getModificado().getEstado() + "\n"
                            + "inicio " + e.getOriginal().getFecha_inicio() + " fin " + e.getModificado().getFecha_fin());
                    this.permisoActual = e.getOriginal();
                    //deletetePermisoProcess();
                    setProgressBarUp();
                } else {
                    System.out.print("actualizar permiso id " + e.getOriginal().getIdPermiso() + " estado " + e.getOriginal().getEstado() + " nuevo estado " + e.getModificado().getEstado() + "\n"
                            + "inicio " + e.getOriginal().getFecha_inicio() + " fin " + e.getModificado().getFecha_fin());

                    //procesarCambiosPermisoEnBD(e.getOriginal(), nuevoEstado);
                    procesarHilo(nuevoEstado, e.getOriginal());
                }

            });

        } else {
            JOptionPane.showMessageDialog(null, "No hay cambios");
        }
        this.btnSave.setVisible(false);
    }

    /**
     * this method sets the actual seleccted path for the departamentosJTree
     */
    private void setSelectedJTreeDepartamentos() {

        try {
            departamentosJTree.setSelectionPath(jTreeDpLastPath);
            departamentosJTree.scrollPathToVisible(jTreeDpLastPath);
        } catch (Exception e) {
            System.out.println("error selecting on the list departamentos " + e.getMessage());
        }

    }

    /**
     * this method takes all serching parameters made by the usser to show on
     * the jListPermisos the permisos that have those parameter
     */
    private void prepareListToShow() {
        String tipoPermisosDesc = this.jListTipoPermisos.getSelectedValue();
        int idTipoPermiso = entidades.tipoPermisos.getTipoPermiso(listaTipoPermisos, tipoPermisosDesc);
        String puestDesc = jTreeDpLastPath.getPathComponent(2).toString();
        String depDesc = jTreeDpLastPath.getPathComponent(1).toString();
        departamento d = entidades.departamento.getDepartamento(departamentosConSolicitudes, depDesc);
        try {
            int idpuest = entidades.puesto.getPuesto(d.puestos, puestDesc).getId_Puesto();
            if (d != null) {
                System.out.println("departamento: " + depDesc + " puesto: " + puestDesc + " tipo permiso: " + tipoPermisosDesc);
                if (this.listaPerPend != null) {
                    this.subEmpleConPermsPend = new ArrayList<>();
                    this.subListaPerPend = new ArrayList<>();
                    loadJlistPermisos(d, idpuest, idTipoPermiso);
                } else {
                    cleanJlistPermisosWhenNotValidNode();
                }

            } else {
                cleanJlistPermisosWhenNotValidNode();
            }
        } catch (Exception e) {
            System.out.println("vista.mantenimientoVacaciones.prepareListToShow()");
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * this method clears the info for the jlistpermisos, subEmpleConPermsPend
     * and subListaPerPend list, use this method when the selected jlistpermisos
     * node is a departamentos tittle
     */
    private void cleanJlistPermisosWhenNotValidNode() {
        try {
            this.subEmpleConPermsPend = new ArrayList<>();
            this.subListaPerPend = new ArrayList<>();
            clearJlistPermisos();
        } catch (Exception e) {
            System.out.println("vista.mantenimientoVacaciones.cleanJlistPermisosWhenNotValidNode()");
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * this method handles the change of state process of a permisos register
     */
    private void aprobarSolicitud() {
        if (accionPermisoActualValida()) {
            permisos p = this.fromPermisos1.getPermisoForm();
            this.permisoActual.setFecha_inicio(p.getFecha_inicio());
            this.permisoActual.setFecha_fin(p.getFecha_fin());
            this.permisoActual.setIdTipo_permiso(p.getIdTipo_permiso());
            this.permisoActual.setTrabajar(p.getTrabajar());
            this.permisoActual.setObserbaciones(p.getObserbaciones());
            //procesarCambiosPermisosLocalmente(2);
            procesarCambiosPermisoActual(2);
        } else {
            removeFormFromTheMainContent();
            this.pnlMainContent.setSelectedIndex(0);
        }
    }


    private void jlistPermisosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlistPermisosValueChanged
        // TODO add your handling code here:
        System.out.println("vista.mantenimiento02.jlistPermisosPendientesMouseClicked()");
        mostrarPermisoParaEditar();

    }//GEN-LAST:event_jlistPermisosValueChanged
    /**
     * this method handles the change of state process of a permisos register to
     * deny
     */
    private void denegarSolicitud() {
        if (accionPermisoActualValida()) {
            //procesarCambiosPermisoActual(3);
            procesarCambiosPermisosLocalmente(3);
        } else {
            removeFormFromTheMainContent();
            this.pnlMainContent.setSelectedIndex(0);
        }
    }
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        this.testConnection.terminateProcess();
        mantenimientoVacaciones mant = new mantenimientoVacaciones(this.usuarioActual);
        mant.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRefreshActionPerformed
    private void setAdminSolicitudesForm() {
        System.out.println("vista.mantenimientoVacaciones.setAdminSolicitudesForm()");
        this.fromPermisos1.clearForm();
        this.fromPermisos1.btnEnviar.setVisible(true);
        this.fromPermisos1.btnAprobar.setVisible(false);
        this.fromPermisos1.btnDenegar.setVisible(false);
        this.fromPermisos1.btnVisto.setVisible(false);
        //mostrarInfoEmpleado();
        this.fromPermisos1.mostrarInfoEmpleado(usuarioActual, departamentosConSolicitudes, puestos);
        mostrarInfoPermisos();
        showVacacionesInfo();
        this.left.setVisible(false);
        this.pnlMainContent.remove(pnlAdminPerm);
        this.pnlMainContent.remove(this.pnlAdminRestricciones);
        this.pnlMainContent.remove(this.pnlHistorial);
        this.pnlMainContent.remove(this.mantenimientoEncargados);
        this.pnlMainContent.addTab("SOLICITUDES", pnlSolicitudes);
        vista.util.commonPanel.handleResizePanel(this.rigth, 300, 600);
    }
    private void checkMiPropioPermisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMiPropioPermisoActionPerformed
        // TODO add your handling code here:
        if (this.checkMiPropioPermiso.isSelected()) {
            setAdminSolicitudesForm();
        } else {

            setAdminTabInterface();

        }
    }//GEN-LAST:event_checkMiPropioPermisoActionPerformed

    /**
     * this method sets the main content tabs after using the permisos form
     *
     */
    private void setAdminTabInterface() {
        System.out.println("vista.mantenimientoVacaciones.setAdminInterface()");
        this.fromPermisos1.clearForm();
        this.fromPermisos1.btnEnviar.setVisible(false);
        this.fromPermisos1.btnAprobar.setVisible(true);
        this.fromPermisos1.btnDenegar.setVisible(true);
        this.fromPermisos1.btnVisto.setVisible(true);
        this.left.setVisible(true);
        removeFormFromTheMainContent();
        this.txtareaInformacion.setText("");
        vista.util.commonPanel.handleResizePanel(this.rigth, 0, 600);
        boolean encargado = esEncargadoDepartamento();
        this.pnlMainContent.remove(pnlSolicitudes);
        if (encargado) {
            //JEFE
            this.pnlMainContent.addTab("ADMINISTRAR PERMISOS", pnlAdminPerm);
        } else {
            //RRHH
            this.pnlMainContent.addTab("ADMINISTRAR PERMISOS", pnlAdminPerm);
            this.pnlMainContent.addTab("RESTRICCIONES", pnlAdminRestricciones);
            this.pnlMainContent.addTab("HISTORIAL", this.pnlHistorial);
            this.pnlMainContent.addTab("USUARIOS", this.mantenimientoEncargados);
        }

    }

    private void jListTipoPermisosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListTipoPermisosValueChanged
        // TODO add your handling code here:
        setPermisosProcess();
    }//GEN-LAST:event_jListTipoPermisosValueChanged

    private void rbVistosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVistosActionPerformed
        // TODO add your handling code here:
        if (this.rbVistos.isSelected()) {
            this.rbPendientes.setSelected(false);
            this.verPendientes = false;
        } else {
            this.rbPendientes.setSelected(true);
            this.verPendientes = true;
        }

        setPermisosProcess();
    }//GEN-LAST:event_rbVistosActionPerformed

    private void rbPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPendientesActionPerformed
        // TODO add your handling code here:
        if (this.rbPendientes.isSelected()) {
            this.rbVistos.setSelected(false);
            this.verPendientes = true;
        } else {
            this.rbVistos.setSelected(true);
            this.verPendientes = false;
        }
        setPermisosProcess();
    }//GEN-LAST:event_rbPendientesActionPerformed

    private void pnlHistorialComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlHistorialComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlHistorialComponentResized

    private void btnNotificaionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificaionActionPerformed
        // TODO add your handling code here:
        if (!this.listaPermisosModificados.isEmpty()) {
            this.listaPermisosModificados.forEach((e) -> {
                System.out.println("primer estado " + e.getOriginal().getEstado());

                System.out.println("cambio estado " + e.getModificado().getEstado());
                System.out.println("estado permiso actual " + e.getModificado().getEstado() + " estado anterior " + e.getOriginal().getEstado());
                System.out.println("fecha permiso actual " + e.getModificado().getFecha_inicio() + " fecha anterior " + e.getOriginal().getFecha_inicio());

            });

        } else {
            JOptionPane.showMessageDialog(null, "No hay cambios");
        }
    }//GEN-LAST:event_btnNotificaionActionPerformed

    private void btnUndoChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoChangeActionPerformed
        // TODO add your handling code here:
        if (!listaPermisosModificados.isEmpty()) {

            int lastIndex = this.listaPermisosModificados.size() - 1;
            cambiosPermisos p = this.listaPermisosModificados.get(lastIndex);
            permisos per = p.getOriginal();
            int result = reversarCambio(p.getOriginal(), p.getModificado().getEstado());
            if (result > -1) {
                int index = entidades.permisos.getIndexOnTheList(this.listaPerPend, per);
                if (index == -1) {
                    this.listaPerPend.add(per);
                }
                this.permisoActual = permisos.getPermisoFromPermiso(per);
                afterUndoChanges(lastIndex);
                this.changes = this.listaPermisosModificados.size();
                //this.btnSave.setText("" + changes);
                //JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");

            }

        } else {
            this.btnUndoChange.setVisible(false);
            this.btnSave.setVisible(false);
        }

    }//GEN-LAST:event_btnUndoChangeActionPerformed
    /**
     * this method follows the steps to ensure that the information and GUI are
     * correct after undoing some changes on the permisos information
     *
     * @param lastIndex is the index that must be removed from the
     * listaPermisosModificados
     */
    private void afterUndoChanges(int lastIndex) {
        removeFromListPermPend();
        cargarListaEmpConPermPend();
        setDepConSolicitudes();
        loadTreeDepartamentos();
        clearJlistPermisos();
        this.refrescando = false;
        this.permisoActual = null;
        setSelectedJTreeDepartamentos();
        this.listaPermisosModificados.remove(lastIndex);
        if (this.listaPermisosModificados.isEmpty()) {
            this.btnUndoChange.setVisible(false);
            this.btnSave.setVisible(false);
            this.btnNotificaion.setText("");
        } else {
            this.btnNotificaion.setText(this.listaPermisosModificados.size() + "");
        }
    }
    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        // TODO add your handling code here:
        int index = this.pnlMainContent.getSelectedIndex();
        boolean encargado = esEncargadoDepartamento();
        boolean miPermiso = this.checkMiPropioPermiso.isSelected();
        informationMessages.getVentGuideMessage(index, encargado, miPermiso);
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoActionPerformed
        // TODO add your handling code here:
        int index = this.pnlMainContent.getSelectedIndex();
        boolean encargado = esEncargadoDepartamento();
        boolean miPermiso = this.checkMiPropioPermiso.isSelected();
        informationMessages.getVentInfoMessage(index, encargado, miPermiso);
    }//GEN-LAST:event_btnInfoActionPerformed
    private void setProgressBarUp() {
        int value = this.jpbPermisos.getValue();
        value++;
        this.jpbPermisos.setValue(value);
    }
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveChanges();

    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mantenimientoVacaciones.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mantenimientoVacaciones.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mantenimientoVacaciones.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mantenimientoVacaciones.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mantenimientoVacaciones().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel admcntTopPnl;
    private javax.swing.JPanel adminButomPnl;
    private javax.swing.JPanel adminCentPnl;
    private javax.swing.JPanel adminLeftPnl;
    private javax.swing.JPanel adminRigthPnl;
    private javax.swing.JPanel adminTopPnl;
    private javax.swing.JPanel botton;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnLoading;
    private javax.swing.JButton btnNotificaion;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUndoChange;
    private javax.swing.JCheckBox checkMiPropioPermiso;
    private javax.swing.JTree departamentosJTree;
    private vista.components.fromPermisos fromPermisos1;
    private vista.components.getHistorialPermiso historialPermisos1;
    private javax.swing.JPanel infEmppnl;
    private javax.swing.JLabel informationLb;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jListTipoPermisos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JList<String> jlistPermisos;
    private javax.swing.JProgressBar jpbPermisos;
    private javax.swing.JLabel lbDifDias;
    private javax.swing.JLabel lbServer;
    private javax.swing.JLabel lbUsusarioActual;
    private javax.swing.JLabel lbname;
    private javax.swing.JPanel left;
    private javax.swing.JPanel mantenimientoEncargados;
    private vista.components.mantenimientoUsuarios mantenimientoUsuarios1;
    private javax.swing.JPanel menuPnl;
    private javax.swing.JToolBar menuToolBar;
    private javax.swing.JToolBar misAdminOpciones;
    private javax.swing.JPanel pnlAdminPerm;
    private javax.swing.JPanel pnlAdminRestricciones;
    private javax.swing.JPanel pnlHistorial;
    private vista.components.pnlInfoPermiso pnlInfoPermiso1;
    private javax.swing.JTabbedPane pnlMainContent;
    private javax.swing.JPanel pnlSolicitudes;
    private javax.swing.JRadioButton rbEscazu;
    private javax.swing.JRadioButton rbPendientes;
    private javax.swing.JRadioButton rbTana;
    private javax.swing.JRadioButton rbVistos;
    private vista.components.restricciones restricciones1;
    private javax.swing.JPanel rigth;
    private javax.swing.JScrollPane scroolDep;
    private javax.swing.JScrollPane scroolOpciones;
    private javax.swing.JPanel tittlepnl;
    private javax.swing.JToolBar toolBarUsserHelp;
    private javax.swing.JPanel top;
    private javax.swing.JTextArea txtVacPend;
    private javax.swing.JTextArea txtareaInformacion;
    // End of variables declaration//GEN-END:variables

}
