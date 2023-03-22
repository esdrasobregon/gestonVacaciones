/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import entidades.Auditoria_permiso;
import entidades.Usuario;
import entidades.accionesPermiso;
import entidades.departamento;
import entidades.permisos;
import entidades.puesto;
import entidades.tipoPermisos;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import logica.permisosThreads;
import vista.util.jComboboxComun;

/**
 *
 * @author programador1
 */
public class testGestionPermisos extends javax.swing.JFrame {

    Icon iconComprobanteActual;
    ArrayList<departamento> departamentosConSolicitudes;
    ArrayList<departamento> alldepartamentos;
    ArrayList<permisos> listaPerPend;
    ArrayList<Usuario> empleConPermsPend;
    ArrayList<accionesPermiso> listaAcciones;
    ArrayList<tipoPermisos> listaTipoPermisos;
    ArrayList<Usuario> subEmpleConPermsPend;
    ArrayList<permisos> subListaPerPend;
    Usuario usuarioActual;
    Usuario usuarioPermiso;
    permisos permisoActual;
    int count = 0;
    boolean refresh;
    boolean refrescando;
    boolean verPendientes;

    /**
     * Creates new form testGestionPermisos
     */
    public testGestionPermisos() {
        initComponents();
        this.usuarioActual = new Usuario();
        this.usuarioActual.setId(3246);
        this.usuarioActual.setIdEmpleado(3059);
        refresh = false;
        refrescando = true;
        this.lbCount.setText(count + "");
        loadInfo();
        loadInterfaceInfo();
        addLiateners();
        refrescando = false;

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

    private void addLiateners() {
        cmbDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setCmbPuestos();
            }
        });
    }

    private void setCmbPuestos() {
        try {
            int index = cmbDepartamentos.getSelectedIndex();
            departamento d = departamentosConSolicitudes.get(index);
            jComboboxComun.comboboxRemoveAll(cmbPuestos);
            jComboboxComun.loadComboboxPuestos(cmbPuestos, d.puestos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void loadInfo() {
        this.subEmpleConPermsPend = new ArrayList<>();
        this.listaPerPend = logica.permisosLogica.getPermisosPendientes();
        cargarListaEmpPermPend();
        loadDepPuestConSolicitudes();
        this.listaTipoPermisos = logica.tipoPermisosLogica.getTipoPermisos();
        this.alldepartamentos = logica.departamentoLog.getDepartamentos();
        this.alldepartamentos.forEach(e -> {
            System.out.println("getting records for: " + e.getDescripcion());
            e.puestos = logica.puestoLogica.getPuestoPorIdDep(e.getId_Departamento());
        });
        loadPuestosPorDepartamento();
    }

    private void loadInterfaceInfo() {

        addRowTablePosiblesUsuariosAdministradores();
        addItemsCmbDepartamentos();
        setCmbPuestos();
        addItemsCmbTipoPermisos();

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

    private void addItemsCmbDepartamentos() {

        if (!this.departamentosConSolicitudes.isEmpty()) {

            this.departamentosConSolicitudes.forEach(e -> {
                this.cmbDepartamentos.addItem(e.getDescripcion());
            });
        }
    }

    private void clearTbPermisos() {
        this.refresh = !refresh;
        vista.components.jTableHandler.clearJTable(tbPermisos);
    }

    private void addItemsCmbTipoPermisos() {

        if (!this.listaTipoPermisos.isEmpty()) {

            this.listaTipoPermisos.forEach(e -> {
                this.cmbTipoPermisos.addItem(e.getDescripcion());
            });
        }
    }

    private void mostrarPermisoParaEditar() {

        try {
            int index = this.cmbDepartamentos.getSelectedIndex();
            this.usuarioPermiso = this.subEmpleConPermsPend.get(index);

            //pnlInfoPermiso1.mostrarPermisoParaEditar(jlistPermisos, usuarioPermiso, subEmpleConPermsPend, subListaPerPend, permisoActual, listaTipoPermisos, iconComprobanteActual, txtareaInformacion);
            //this.fromPermisos1.mostrarInfoEmpleado(usuarioPermiso, this.departamentosConSolicitudes, this.puestos);
            this.permisoActual = permisos.getPermisoFromPermiso(this.subListaPerPend.get(index));
            if (this.permisoActual.getIdComprobante() > 0) {
                this.iconComprobanteActual = logica.comprobanteLogica.getImagenComprobante(this.permisoActual.getIdComprobante());
            }
        } catch (Exception e) {
            System.out.println("vista.mantenimiento02.mostrarPermisoParaEditar()");
            System.out.println("error: " + e.getMessage());

        }

    }

    /**
     * this method adds an element to to the jlist permisos and to the
     * background permisos list (subListaPerPend)
     *
     * @param e is a permisos element
     * @param model DefaultListModel must be from jlist permisos
     * @param us is an usuario object
     */
    private void addElementTojlistPermisosPendientes(permisos e, Usuario us) {
        this.subEmpleConPermsPend.add(us);
        this.subListaPerPend.add(e);
    }

    private void prepararPnlInformacion() {
        this.permisoActual = null;
        loadDepartamentosPuestosConPermisosPorTipo();
    }

    private void loadDepartamentosPuestosConPermisosPorTipo() {

        try {
            if (!this.refrescando) {
                clearTbPermisos();

                String puestDesc = cmbPuestos.getSelectedItem().toString();
                String tipoPermisoDesc = cmbTipoPermisos.getSelectedItem().toString();
                System.out.println("vista.mantenimiento02.permisosListValueChanged()");
                int tipo = entidades.tipoPermisos.getTipoPermiso(listaTipoPermisos, tipoPermisoDesc);
                System.out.println("tipo: " + tipoPermisoDesc);

                departamento d = entidades.departamento.getDepartamento(departamentosConSolicitudes, this.cmbDepartamentos.getSelectedItem().toString());
                puesto pues = entidades.puesto.getPuesto(d.puestos, puestDesc);

                String descDep = d.getDescripcion();
                String descPues = pues.getDescripcion();
                setListaPermisosDynamic(descDep, descPues, tipo);
                //loadTreeDepartamentos();
                System.out.println("departamento: " + descDep);
                System.out.println("puesto: " + descPues);
            } else {
                System.out.println("Refrescando informacion...");
            }
        } catch (Exception e) {
            System.out.println("vista.testGestionPermisos.loadDepartamentosPuestosConPermisosPorTipo()");
            System.out.println("error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
        }
    }

    /**
     * this method controls the steps that must be done when the usser choose an
     * element fron the permisos jlist
     */
    private void setListaPermisosDynamic(String descDep, String descPuesto, int tipo) {

        this.subEmpleConPermsPend = new ArrayList<>();
        this.subListaPerPend = new ArrayList<>();
        this.listaPerPend.forEach(e -> {

            if (e.getIdTipo_permiso() == tipo) {
                Usuario us = entidades.Usuario.getUsuario(empleConPermsPend, e.getIdEmpleado());
                departamento d = entidades.departamento.getDepartamento(departamentosConSolicitudes, descDep);
                int idpest = entidades.puesto.getPuesto(d.puestos, descPuesto).getId_Puesto();
                if (us.getDepartamento() == d.getId_Departamento() && us.getIdPuesto() == idpest) {
                    System.out.println(us.getNombre() + " " + us.getPrimer_Apellido());
                    choosePermisoVistoPendiente(e, us);
                }
            }

        });

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
    private void choosePermisoVistoPendiente(permisos e, Usuario us) {
        if (this.verPendientes) {
            System.out.println("perm pendientes");
            if (e.getEstado() == 0) {
                this.subEmpleConPermsPend.add(us);
                this.subListaPerPend.add(e);
                System.out.println("perm ");
                addRowTablePosiblesUsuariosAdministradores(us, e, 0);

            }
        } else if (e.getEstado() == 1) {
            System.out.println("perm vistos");
            this.subEmpleConPermsPend.add(us);
            this.subListaPerPend.add(e);
            addRowTablePosiblesUsuariosAdministradores(us, e, 0);
        }
        System.out.println("vista.testGestionPermisos.choosePermisoVistoPendiente()");
    }

    private void cargarListaEmpPermPend() {
        this.empleConPermsPend = new ArrayList<>();
        this.listaAcciones = new ArrayList<>();
        if (this.listaPerPend != null) {
            this.listaPerPend.forEach(e -> {
                Usuario us = logica.UsuarioLogica.getUsuario(e.getIdEmpleado());
                accionesPermiso ac = new accionesPermiso(e.getIdPermiso());
                this.empleConPermsPend.add(us);
                this.listaAcciones.add(ac);
                if (e.getIdComprobante() > 0) {
                    logica.comprobanteLogica.getImagenComprobante(e.getIdComprobante());
                    System.out.println("comprobante titulo: " + e.getIdComprobante());
                }
            });
        }
    }

    private void addRowTablePosiblesUsuariosAdministradores() {
        DefaultTableModel model = (DefaultTableModel) this.tbPermisos.getModel();
        int index = 0;
        for (permisos p : this.listaPerPend) {
            accionesPermiso ac = this.listaAcciones.get(index);
            Usuario un = empleConPermsPend.get(index);
            model.addRow(new Object[]{
                p.getIdPermiso(), un.getNombre() + " " + un.getApellido1(),
                ac.visto,
                ac.aprobar,
                ac.denegar,
                ac.eliminar
            }
            );
            tbPermisos.setRowHeight(index, 20);
            index++;
        }

        this.tbPermisos.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                refresh = !refresh;
                if (refresh) {

                    int rowIndex = tbPermisos.getSelectedRow();
                    int colIndex = tbPermisos.getSelectedColumn();
                    int id = Integer.parseInt(tbPermisos.getValueAt(rowIndex, 0).toString());
                    System.out.println("row " + rowIndex + " columnd " + colIndex);
                    permisos p = permisos.getPermisoOnTheList(listaPerPend, id);
                    accionesPermiso ac = accionesPermiso.getAccionesPermisoByIdPermiso(listaAcciones, p.getIdPermiso());
                    boolean flag = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, colIndex).toString());
                    if (flag) {
                        count++;
                        for (int i = 2; i < 6; i++) {
                            refresh = true;
                            if (colIndex != i) {
                                tbPermisos.setValueAt(!flag, rowIndex, i);
                            }

                        }
                    } else {
                        count--;
                    }
                    lbCount.setText(count + "");
                    ac.visto = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 2).toString());
                    ac.aprobar = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 3).toString());
                    ac.denegar = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 4).toString());
                    ac.eliminar = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 5).toString());
                    refresh = false;
                }
            }
        });
    }

    private void addRowTablePosiblesUsuariosAdministradores(Usuario us, permisos p, int index) {
        System.out.println("vista.testGestionPermisos.addRowTablePosiblesUsuariosAdministradores()");
        DefaultTableModel model = (DefaultTableModel) this.tbPermisos.getModel();

        model.addRow(new Object[]{
            p.getIdPermiso(), us.getNombre() + " " + us.getApellido1(), //ac.visto,
        //ac.aprobar,
        //ac.denegar,
        //ac.eliminar
        }
        );
        tbPermisos.setRowHeight(index, 20);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        center = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPermisos = new javax.swing.JTable();
        top = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        rbPendientes = new javax.swing.JRadioButton();
        rbVistos = new javax.swing.JRadioButton();
        cmbTipoPermisos = new javax.swing.JComboBox<>();
        cmbDepartamentos = new javax.swing.JComboBox<>();
        cmbPuestos = new javax.swing.JComboBox<>();
        jToolBar2 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lbCount = new javax.swing.JLabel();
        down = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        rigth = new javax.swing.JPanel();
        left = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        center.setLayout(new java.awt.GridLayout(1, 0));

        tbPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "VISTO", "APROBAR", "DENEGAR", "ELIMINAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbPermisos);
        if (tbPermisos.getColumnModel().getColumnCount() > 0) {
            tbPermisos.getColumnModel().getColumn(0).setResizable(false);
            tbPermisos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbPermisos.getColumnModel().getColumn(1).setResizable(false);
            tbPermisos.getColumnModel().getColumn(1).setPreferredWidth(300);
            tbPermisos.getColumnModel().getColumn(2).setResizable(false);
            tbPermisos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(3).setResizable(false);
            tbPermisos.getColumnModel().getColumn(3).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(4).setResizable(false);
            tbPermisos.getColumnModel().getColumn(4).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(5).setResizable(false);
            tbPermisos.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        center.add(jScrollPane1);

        getContentPane().add(center, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        rbPendientes.setText("PENDIENTES");
        rbPendientes.setFocusable(false);
        rbPendientes.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        rbPendientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPendientesActionPerformed(evt);
            }
        });
        jToolBar1.add(rbPendientes);

        rbVistos.setText("VISTOS");
        rbVistos.setFocusable(false);
        rbVistos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        rbVistos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbVistos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVistosActionPerformed(evt);
            }
        });
        jToolBar1.add(rbVistos);

        jToolBar1.add(cmbTipoPermisos);

        cmbDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentosActionPerformed(evt);
            }
        });
        cmbDepartamentos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbDepartamentosPropertyChange(evt);
            }
        });
        jToolBar1.add(cmbDepartamentos);

        jToolBar1.add(cmbPuestos);

        jToolBar2.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-save-30.png"))); // NOI18N
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-undo-30.png"))); // NOI18N
        jButton1.setToolTipText("Deshacer cambios");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lbCount.setText("0");

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(btnSave)
                .addComponent(lbCount)
                .addComponent(jButton1)
                .addContainerGap(739, Short.MAX_VALUE))
            .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLayout.createSequentialGroup()
                    .addContainerGap(106, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(25, Short.MAX_VALUE)))
            .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(topLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(topLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lbCount))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLayout.createSequentialGroup()
                    .addContainerGap(37, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
            .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(topLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(top, java.awt.BorderLayout.PAGE_START);

        jButton2.setText("enviar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout downLayout = new javax.swing.GroupLayout(down);
        down.setLayout(downLayout);
        downLayout.setHorizontalGroup(
            downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 931, Short.MAX_VALUE)
            .addGroup(downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(downLayout.createSequentialGroup()
                    .addGap(428, 428, 428)
                    .addComponent(jButton2)
                    .addContainerGap(431, Short.MAX_VALUE)))
        );
        downLayout.setVerticalGroup(
            downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(downLayout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(jButton2)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );

        getContentPane().add(down, java.awt.BorderLayout.PAGE_END);

        rigth.setPreferredSize(new java.awt.Dimension(100, 277));

        javax.swing.GroupLayout rigthLayout = new javax.swing.GroupLayout(rigth);
        rigth.setLayout(rigthLayout);
        rigthLayout.setHorizontalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        rigthLayout.setVerticalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        getContentPane().add(rigth, java.awt.BorderLayout.LINE_END);

        left.setPreferredSize(new java.awt.Dimension(200, 277));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        getContentPane().add(left, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:

        for (int i = 0; i < this.listaPerPend.size(); i++) {
            int accion = -2;
            accionesPermiso ac = this.listaAcciones.get(i);
            accion = ac.visto ? 1 : accion;
            accion = ac.aprobar ? 2 : accion;
            accion = ac.denegar ? 3 : accion;
            accion = ac.eliminar ? -1 : accion;
            permisos p = permisos.getPermisoOnTheList(listaPerPend, ac.getIdPermiso());
            switch (accion) {
                case 1:
                    //JOptionPane.showMessageDialog(null, "visto");
                    //updatePermiso(1, p);
                    procesar(1, p);
                    break;
                case 2:
                    //updatePermiso(2, p);
                    procesar(2, p);
                    break;
                case 3:
                    //updatePermiso(3, p);
                    procesar(3, p);
                    break;
                case -1:
                    //deletetePermiso(p);
                    procesar(-1, p);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "ninguna accion");
                    break;
            }
        }


    }//GEN-LAST:event_btnSaveActionPerformed
    private void procesar(int tipoAccion, permisos p) {
        /*permisosThreads prth = new permisosThreads(tipoAccion, p, usuarioActual, lbCount);
        Thread t = new Thread(prth);
        t.start();*/

    }
    private void cmbDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartamentosActionPerformed

    private void rbPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPendientesActionPerformed
        this.permisoActual = null;
        //this.pnlInfoPermiso1.clearForm();
        if (this.rbPendientes.isSelected()) {
            this.rbVistos.setSelected(false);
            this.verPendientes = true;
        } else {
            this.rbVistos.setSelected(true);
            this.verPendientes = false;
        }
        loadDepartamentosPuestosConPermisosPorTipo();
    }//GEN-LAST:event_rbPendientesActionPerformed

    private void rbVistosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVistosActionPerformed
        // TODO add your handling code here:
        this.permisoActual = null;
        //this.pnlInfoPermiso1.clearForm();
        if (this.rbVistos.isSelected()) {
            this.rbPendientes.setSelected(false);
            this.verPendientes = false;
        } else {
            this.rbPendientes.setSelected(true);
            this.verPendientes = true;
        }
        loadDepartamentosPuestosConPermisosPorTipo();
    }//GEN-LAST:event_rbVistosActionPerformed

    private void cmbDepartamentosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbDepartamentosPropertyChange
        // TODO add your handling code here:
        prepararPnlInformacion();
    }//GEN-LAST:event_cmbDepartamentosPropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //refrescando = !refrescando;
        loadDepartamentosPuestosConPermisosPorTipo();
        //refrescando = !refrescando;
    }//GEN-LAST:event_jButton2ActionPerformed
    private void updatePermiso(int nuevoEstado, permisos permisoActual) {
        permisoActual.setEstado(nuevoEstado);
        if (permisoActual != null) {
            Auditoria_permiso au = getAuditoriaPermiso(permisoActual.getEstado(), nuevoEstado, permisoActual);
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
                        JOptionPane.showMessageDialog(null, "El proceso se ha realizado correctamente");
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(testGestionPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(testGestionPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(testGestionPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(testGestionPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new testGestionPermisos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel center;
    private javax.swing.JComboBox<String> cmbDepartamentos;
    private javax.swing.JComboBox<String> cmbPuestos;
    private javax.swing.JComboBox<String> cmbTipoPermisos;
    private javax.swing.JPanel down;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbCount;
    private javax.swing.JPanel left;
    private javax.swing.JRadioButton rbPendientes;
    private javax.swing.JRadioButton rbVistos;
    private javax.swing.JPanel rigth;
    private javax.swing.JTable tbPermisos;
    private javax.swing.JPanel top;
    // End of variables declaration//GEN-END:variables
}
