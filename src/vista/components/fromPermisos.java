/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.components;

import entidades.Usuario;
import entidades.comprobante;
import entidades.departamento;
import entidades.permisos;
import entidades.puesto;
import entidades.restriccionPermisos;
import entidades.tipoPermisos;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import logica.comprobanteLogica;
import logica.funcionesComunes;
import logica.repostory;
import vista.util.jComboboxComun;
import vista.videoFrame;

/**
 *
 * @author programador1
 */
public class fromPermisos extends javax.swing.JPanel {

    /**
     * Creates new form fromPermisos
     */
    Usuario usuarioActual;
    ArrayList<tipoPermisos> listaTipoPermisos;
    ArrayList<permisos> myListaPer;
    boolean imageTaked;
    BufferedImage imagenComprobante;
    boolean solicitarComprobante;
    JLabel informationLb, lbDifDias;
    JTextArea txtVacPend;
    ArrayList<restriccionPermisos> restriccionesParaUsuarioActual;
    permisos permisoActual;
    public boolean solcitudProcesada;
    public int contVacaciones;

    public fromPermisos() {
        initComponents();
        vista.util.botonComun.mouseHoverListener(this.btnAprobar);
        vista.util.botonComun.mouseHoverListener(this.btnDenegar);
        vista.util.botonComun.mouseHoverListener(this.btnEnviar);
        vista.util.botonComun.mouseHoverListener(this.btnVisto);
        this.cmbTipoPermiso.addActionListener(event -> {
            setFormSolicitudesOnTipoChange();
        });
        this.rbTrabmanyana.setSelected(true);
        this.solcitudProcesada = false;
        Date d = funcionesComunes.calcNextNameDayDate("martes");
        this.dtFechainico.setMinSelectableDate(d);
        this.dtFechaFin.setMinSelectableDate(d);
    }

    private boolean fechasRepetidas() {

        boolean res = false;

        Date dIn = funcionesComunes.getFechaFromDtChooser(this.dtFechainico);
        Date dfin = funcionesComunes.getFechaFromDtChooser(this.dtFechaFin);

        try {
            boolean in = funcionesComunes.fechasRepetidas(dIn, myListaPer);
            boolean fin = funcionesComunes.fechasRepetidas(dfin, myListaPer);
            if (in || fin) {
                res = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Revice las fechas por favor");
            System.out.println("error: " + e.getMessage());

        }

        return res;
    }

    /**
     * this method shows on the GUI the given info for a usser
     *
     * @param us is an usuario object
     * @param departamentosConSolicitudes is a departamentos list
     * @param puestos is a puestos list
     */
    public void mostrarInfoEmpleado(Usuario us, ArrayList<departamento> departamentosConSolicitudes, ArrayList<puesto> puestos) {
        this.txtId.setText("Código: " + us.getIdEmpleado());
        this.txtNombreCompleto.setText("Nombre: " + us.getNombre() + " " + us.getApellido1() + " " + us.getApellido2());
        this.txtDepartamento.setText("Departamento: " + entidades.departamento.getNombreDepartamento(departamentosConSolicitudes, us.getDepartamento()));
        this.txtPuesto.setText("Puesto: " + entidades.puesto.getNombrePuesto(puestos, us.getIdPuesto()));
    }

    /**
     * this method checks the trabajar (ma;abna tarde) options
     */
    private void rbTrabajarManyanaChange() {
        if (this.rbTrabmanyana.isSelected()) {
            this.rbTrabTarde.setSelected(false);
        } else {
            this.rbTrabTarde.setSelected(true);
        }
    }

    /**
     * this method sets the permisos form according to the selected tipo
     * permisos
     */
    private void setFormSolicitudesOnTipoChange() {
        try {
            String selected = this.cmbTipoPermiso.getSelectedItem().toString();
            //JOptionPane.showMessageDialog(null, selected);
            int index = this.cmbTipoPermiso.getSelectedIndex();
            switch (index) {

                case 0:
                    System.out.println("selected: " + selected);
                    setVacacionesForm();
                    break;
                case 1:
                    System.out.println("selected: " + selected);
                    setVacacionesForm();
                    break;
                case 8:
                    System.out.println("selected: " + selected);
                    this.cmbDiaSug.setVisible(true);
                    this.solicitarComprobante = false;
                    this.rbTrabmanyana.setVisible(false);
                    this.rbTrabTarde.setVisible(false);
                    break;

                default:
                    System.out.println("selected: " + selected);
                    setCitasForm();
                    break;
            }
        } catch (Exception e) {
            System.out.println("vista.mantenimiento02.setFormSolicitudesOnTipoChange()");
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * this method sets the permisos form to create a citas permisos
     */
    private void setCitasForm() {
        this.cmbDiaSug.setVisible(false);
        this.solicitarComprobante = true;
        this.rbTrabmanyana.setVisible(true);
        this.rbTrabTarde.setVisible(true);
        this.dtFechaFin.setVisible(false);
        this.center.setVisible(true);
    }

    /**
     * this method sets the permisos form to create a vacaciones permisos
     */
    private void setVacacionesForm() {
        this.rbTrabTarde.setVisible(false);
        this.rbTrabmanyana.setVisible(false);
        this.cmbDiaSug.setVisible(false);
        this.solicitarComprobante = false;
        this.dtFechaFin.setVisible(true);
        this.center.setVisible(false);
    }

    /**
     * this method clean the permisos form
     */
    public void clearForm() {
        this.txtId.setText("");
        this.txtNombreCompleto.setText("");
        this.txtDepartamento.setText("");
        this.txtPuesto.setText("");
        this.lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultImage.png")));
        this.cmbTipoPermiso.setSelectedIndex(0);
    }

    /**
     * this method takes information to prepare the permisos form with this
     * info, so the usser checks it and edit it
     *
     * @param permisoActual this is the permisos to edit
     * @param indexTipo is the tipo permsos indexTipo
     */
    public void prepararFormularioParaEditar(permisos permisoActual, int indexTipo) {

        this.lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultImage.png")));
        this.dtFechainico.setDate(permisoActual.getFecha_inicio());
        this.dtFechaFin.setDate(permisoActual.getFecha_fin());
        cmbTipoPermiso.setSelectedIndex(indexTipo);
        if (permisoActual.getTrabajar() == 1) {
            this.rbTrabmanyana.setSelected(true);
        } else {
            this.rbTrabTarde.setSelected(true);
        }

        try {
            if (permisoActual.getIdComprobante() > 0) {
                this.lbImage.setIcon(new ImageIcon(repostory.getComprobantesPath() + permisoActual.getIdComprobante() + ".jpeg"));
            }
        } catch (Exception e) {

            System.out.println("vista.components.fromPermisos.prepararFormularioParaEditar()");
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * this method takes all the information nedded by this panel to work
     *
     * @param informationLb is a jlabel, nedded to show info to the usser
     * @param usuarioActual is actual usser information
     * @param listaTipoPermisos is a tipo permisos list
     * @param myListaPer is the actual usser info about permisos
     * @param lbDifDias is a jlabel needed to show info to the usser
     * @param txtVacPend is a jtextarea to show info to the usser
     * @param restriccionesParaUsuarioActual is a resticiones list for the
     * actual usser
     */
    public void setParameters(Usuario usuarioActual,
            ArrayList<tipoPermisos> listaTipoPermisos,
            ArrayList<permisos> myListaPer,
            JLabel informationLb, JLabel lbDifDias,
            JTextArea txtVacPend,
            ArrayList<restriccionPermisos> restriccionesParaUsuarioActual) {
        this.usuarioActual = usuarioActual;
        this.listaTipoPermisos = listaTipoPermisos;
        this.myListaPer = myListaPer;
        this.informationLb = informationLb;
        this.lbDifDias = lbDifDias;
        this.txtVacPend = txtVacPend;
        this.restriccionesParaUsuarioActual = restriccionesParaUsuarioActual;
        jComboboxComun.loadComboboxTipoPermiso(cmbTipoPermiso, listaTipoPermisos);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        center = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbImage = new javax.swing.JLabel();
        north = new javax.swing.JPanel();
        northCenter = new javax.swing.JPanel();
        pnlUsuarioInfo = new javax.swing.JPanel();
        txtNombreCompleto = new javax.swing.JLabel();
        txtId = new javax.swing.JLabel();
        txtDepartamento = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTipoPermiso = new javax.swing.JComboBox<>();
        dtFechainico = new com.toedter.calendar.JDateChooser();
        dtFechaFin = new com.toedter.calendar.JDateChooser();
        cmbDiaAct = new javax.swing.JComboBox<>();
        cmbDiaSug = new javax.swing.JComboBox<>();
        rbTrabmanyana = new javax.swing.JRadioButton();
        rbTrabTarde = new javax.swing.JRadioButton();
        northNorth = new javax.swing.JPanel();
        northEast = new javax.swing.JPanel();
        northsouth = new javax.swing.JPanel();
        nothLeft = new javax.swing.JPanel();
        south = new javax.swing.JPanel();
        btnEnviar = new javax.swing.JButton();
        btnAprobar = new javax.swing.JButton();
        btnDenegar = new javax.swing.JButton();
        btnVisto = new javax.swing.JButton();
        rigth = new javax.swing.JPanel();
        left = new javax.swing.JPanel();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        setMaximumSize(new java.awt.Dimension(700, 500));
        setMinimumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(750, 520));
        setLayout(new java.awt.BorderLayout());

        center.setBackground(new java.awt.Color(255, 255, 255));
        center.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "CLICK PARA AGREGAR IMAGEN COMPROBANTE"));
        center.setMaximumSize(new java.awt.Dimension(400, 400));
        center.setMinimumSize(new java.awt.Dimension(250, 250));
        center.setPreferredSize(new java.awt.Dimension(250, 250));
        center.setLayout(new java.awt.GridLayout(1, 0));

        lbImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/defaultImage.png"))); // NOI18N
        lbImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbImageMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lbImage);

        center.add(jScrollPane1);

        add(center, java.awt.BorderLayout.CENTER);

        north.setBackground(new java.awt.Color(255, 255, 255));
        north.setMaximumSize(new java.awt.Dimension(400, 250));
        north.setMinimumSize(new java.awt.Dimension(400, 250));
        north.setPreferredSize(new java.awt.Dimension(400, 250));
        north.setLayout(new java.awt.BorderLayout());

        northCenter.setBackground(new java.awt.Color(255, 255, 255));
        northCenter.setPreferredSize(new java.awt.Dimension(461, 10));
        northCenter.setLayout(new java.awt.GridLayout(1, 0));

        pnlUsuarioInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "INFORMCIÓN USUARIO EN PROCESO"));
        pnlUsuarioInfo.setLayout(new java.awt.GridLayout(4, 1, 5, 5));
        pnlUsuarioInfo.add(txtNombreCompleto);
        pnlUsuarioInfo.add(txtId);
        pnlUsuarioInfo.add(txtDepartamento);
        pnlUsuarioInfo.add(txtPuesto);

        northCenter.add(pnlUsuarioInfo);

        pnlForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlForm.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "POR FAVOR LLENE EL FORMULARIO"));
        pnlForm.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        jLabel1.setText("TIPO");
        pnlForm.add(jLabel1);

        cmbTipoPermiso.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jComboboxComun.setCmbLook(cmbTipoPermiso);
        cmbTipoPermiso.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoPermisoItemStateChanged(evt);
            }
        });
        pnlForm.add(cmbTipoPermiso);

        dtFechainico.setBackground(new java.awt.Color(255, 255, 255));
        dtFechainico.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "INICIO"));
        pnlForm.add(dtFechainico);

        dtFechaFin.setBackground(new java.awt.Color(255, 255, 255));
        dtFechaFin.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "FIN"));
        pnlForm.add(dtFechaFin);

        cmbDiaAct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO" }));
        jComboboxComun.setCmbLook(cmbDiaAct);
        cmbDiaAct.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Día libre"));
        pnlForm.add(cmbDiaAct);

        jComboboxComun.setCmbLook(cmbDiaSug);
        cmbDiaSug.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO" }));
        cmbDiaSug.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Día sugerido"));
        pnlForm.add(cmbDiaSug);

        rbTrabmanyana.setText("TRAB MAÑANA");
        rbTrabmanyana.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbTrabmanyanaStateChanged(evt);
            }
        });
        pnlForm.add(rbTrabmanyana);

        rbTrabTarde.setText("TRAB TARDE");
        rbTrabTarde.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbTrabTardeStateChanged(evt);
            }
        });
        pnlForm.add(rbTrabTarde);

        northCenter.add(pnlForm);

        north.add(northCenter, java.awt.BorderLayout.CENTER);

        northNorth.setBackground(new java.awt.Color(255, 255, 255));
        northNorth.setPreferredSize(new java.awt.Dimension(439, 10));

        javax.swing.GroupLayout northNorthLayout = new javax.swing.GroupLayout(northNorth);
        northNorth.setLayout(northNorthLayout);
        northNorthLayout.setHorizontalGroup(
            northNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        northNorthLayout.setVerticalGroup(
            northNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        north.add(northNorth, java.awt.BorderLayout.PAGE_START);

        northEast.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout northEastLayout = new javax.swing.GroupLayout(northEast);
        northEast.setLayout(northEastLayout);
        northEastLayout.setHorizontalGroup(
            northEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        northEastLayout.setVerticalGroup(
            northEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        north.add(northEast, java.awt.BorderLayout.EAST);

        northsouth.setBackground(new java.awt.Color(255, 255, 255));
        northsouth.setPreferredSize(new java.awt.Dimension(439, 10));

        javax.swing.GroupLayout northsouthLayout = new javax.swing.GroupLayout(northsouth);
        northsouth.setLayout(northsouthLayout);
        northsouthLayout.setHorizontalGroup(
            northsouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        northsouthLayout.setVerticalGroup(
            northsouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        north.add(northsouth, java.awt.BorderLayout.SOUTH);

        nothLeft.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout nothLeftLayout = new javax.swing.GroupLayout(nothLeft);
        nothLeft.setLayout(nothLeftLayout);
        nothLeftLayout.setHorizontalGroup(
            nothLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        nothLeftLayout.setVerticalGroup(
            nothLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        north.add(nothLeft, java.awt.BorderLayout.WEST);

        add(north, java.awt.BorderLayout.PAGE_START);

        south.setBackground(new java.awt.Color(255, 255, 255));
        south.setMaximumSize(new java.awt.Dimension(500, 50));
        south.setMinimumSize(new java.awt.Dimension(500, 46));
        south.setPreferredSize(new java.awt.Dimension(500, 50));

        btnEnviar.setToolTipText("Click para enviar solicitud");
        btnEnviar.setBackground(new java.awt.Color(204, 255, 255));
        btnEnviar.setForeground(new java.awt.Color(0, 51, 51));
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-save-30.png"))); // NOI18N
        btnEnviar.setText("ENVIAR");
        btnEnviar.setBorderPainted(true);
        btnEnviar.setContentAreaFilled(true);
        south.add(btnEnviar);

        btnAprobar.setToolTipText("Aprobar solicitud");
        btnAprobar.setBackground(new java.awt.Color(204, 255, 255));
        btnAprobar.setForeground(new java.awt.Color(0, 51, 51));
        btnAprobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-facebook-like-30.png"))); // NOI18N
        btnAprobar.setText("APROBAR");
        south.add(btnAprobar);

        btnDenegar.setToolTipText("Denegar solicitud");
        btnDenegar.setBackground(new java.awt.Color(204, 255, 255));
        btnDenegar.setForeground(new java.awt.Color(0, 51, 51));
        btnDenegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-thumbs-down-30.png"))); // NOI18N
        btnDenegar.setText("DENEGAR");
        south.add(btnDenegar);

        btnVisto.setToolTipText("Click para dejar en visto");
        btnVisto.setBackground(new java.awt.Color(204, 255, 255));
        btnVisto.setForeground(new java.awt.Color(0, 51, 51));
        btnVisto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-double-tick-30.png"))); // NOI18N
        btnVisto.setText("VISTO");
        south.add(btnVisto);

        add(south, java.awt.BorderLayout.SOUTH);

        rigth.setBackground(new java.awt.Color(255, 255, 255));
        rigth.setPreferredSize(new java.awt.Dimension(20, 166));

        javax.swing.GroupLayout rigthLayout = new javax.swing.GroupLayout(rigth);
        rigth.setLayout(rigthLayout);
        rigthLayout.setHorizontalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        rigthLayout.setVerticalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 246, Short.MAX_VALUE)
        );

        add(rigth, java.awt.BorderLayout.LINE_END);

        left.setBackground(new java.awt.Color(255, 255, 255));
        left.setPreferredSize(new java.awt.Dimension(20, 166));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 246, Short.MAX_VALUE)
        );

        add(left, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void lbImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImageMouseClicked
        // TODO add your handling code here:

        videoFrame v = new videoFrame();
        v.setVisible(true);
        this.imageTaked = v.isImageTaked();

        v.b.addActionListener(e -> {
            if (v.webcam.isOpen()) {
                imageTaked = v.takeImage();
                imagenComprobante = v.getBufferedImage();
                if (this.imageTaked) {
                    this.lbImage.setIcon(new javax.swing.ImageIcon(this.imagenComprobante));
                    v.closeCamera();
                    v.dispose();
                }
            }

        });
    }//GEN-LAST:event_lbImageMouseClicked
    /**
     * this function takes all the steps nedded to create a new permisos
     * register on the database
     */
    public void procesarSolicitud() {

        String value = this.cmbTipoPermiso.getSelectedItem().toString();
        int tipoPermiso = entidades.tipoPermisos.getTipoPermiso(this.listaTipoPermisos, value);

        //vacaciones y permiso sin goce se tiene que validar la disponibilidad de fechas 
        if (tipoPermiso < 4) {
            this.solicitarComprobante = false;
            if (tipoPermiso == 1) {
                validarVacaciones();

            } else {
                validarFechasParaEnviarPermiso();
            }

        } else {
            this.solicitarComprobante = true;
            validarFechasParaEnviarPermiso();
        }
    }
    private void rbTrabmanyanaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbTrabmanyanaStateChanged
        // TODO add your handling code here:
        rbTrabajarManyanaChange();
    }//GEN-LAST:event_rbTrabmanyanaStateChanged

    private void rbTrabTardeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbTrabTardeStateChanged
        // TODO add your handling code here:
        rbTrabajarTardeChange();
    }//GEN-LAST:event_rbTrabTardeStateChanged

    private void cmbTipoPermisoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoPermisoItemStateChanged
        // TODO add your handling code here:
        cmbTipoPermiso.setToolTipText(cmbTipoPermiso.getSelectedItem().toString());
    }//GEN-LAST:event_cmbTipoPermisoItemStateChanged
    private void rbTrabajarTardeChange() {
        if (this.rbTrabTarde.isSelected()) {
            this.rbTrabmanyana.setSelected(false);
        } else {
            this.rbTrabmanyana.setSelected(true);
        }
    }

    /**
     * this method checks the dates selected by the usser, if the dates are
     * rigth it creates the permisos register on the database
     */
    private void validarFechasParaEnviarPermiso() {
        if (fechasValidas()) {
            if (this.solicitarComprobante) {
                comprobante c = agregarComprobante();
                addPermiso(c);
            } else {
                addPermiso(null);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fechas incorrectas");
        }

    }

    /**
     * this function creates a new permisos object taken the information
     * selected by the usser, the return it
     *
     * @return a permisos object
     */
    public permisos getPermisoForm() {
        String value = this.cmbTipoPermiso.getSelectedItem().toString();
        int tipo = entidades.tipoPermisos.getTipoPermiso(this.listaTipoPermisos, value);
        Date dIn = funcionesComunes.getFechaFromDtChooser(this.dtFechainico);
        Date dfin = funcionesComunes.getFechaFromDtChooser(this.dtFechaFin);
        LocalDate localDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date now = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        permisos p = new permisos();
        p.setFecha_inicio(dIn);
        if (tipo < 3) {
            p.setFecha_fin(dfin);
        } else {
            p.setFecha_fin(dIn);
        }
        p.setDia_libre(this.cmbDiaAct.getSelectedItem().toString());
        p.setFecha_creacion(now);

        p.setIdEmpleado(
                this.usuarioActual.getIdEmpleado());
        p.setId(
                this.usuarioActual.getId());
        p.setEstado(0);
        p.setIdTipo_permiso(tipo);

        p.setObserbaciones(
                "Día libre " + this.cmbDiaAct.getSelectedItem().toString());
        p.setTrabajar(getTrabajar());
        p.setIdpuesto(this.usuarioActual.getIdPuesto());
        p.setIdDepartamento(this.usuarioActual.getDepartamento());
        return p;
    }

    /**
     * this function checks if the usser has selected the tarde or ma;ana option
     * the it returns an representative int value
     *
     * @return 0 if the option does not apply, 1 if ma;ana option is selected, 2
     * if the tarde option is selected
     */
    private int getTrabajar() {
        int res = -1;
        if (this.rbTrabTarde.isVisible()) {
            if (this.rbTrabTarde.isSelected()) {
                res = 2;
            } else {
                res = 1;
            }
        }
        return res;
    }

    private void afterSolicitudSendMess(boolean result) {
        if (result) {
            this.solcitudProcesada = true;
            JOptionPane.showMessageDialog(null, "Solicitud realizada");
        } else {
            JOptionPane.showMessageDialog(null, "error");
        }
    }

    /**
     * this method adds a permisos and it's comprobante register, ( if the
     * permisos has a comprobante associated), on the database
     *
     * @param c is a comprobante object
     */
    private void addPermiso(comprobante c) {
        permisos p = getPermisoForm();
        if (c != null) {
            p.setIdComprobante(c.getIdComprobante());
            boolean result = logica.permisosLogica.addPermisoComprobante(p);
            afterSolicitudSendMess(result);
        } else {
            boolean result = logica.permisosLogica.addPermiso(p);
            afterSolicitudSendMess(result);
        }
    }

    private comprobante agregarComprobante() {
        comprobante c = null;

        if (this.imageTaked) {
            c = sendComprobante();
        }

        return c;
    }

    /**
     * this function creates a new comprobante object, save the register on the
     * database, deletes the image file associated and then return it
     *
     * @return a comprobante object
     */
    private comprobante sendComprobante() {
        comprobante c = null;
        try {
            String fileName = repostory.getImagesPath() + "test.jpeg";
            Date d = new Date();
            String comprobanteName = "" + this.usuarioActual.getIdEmpleado() + d.toString();
            this.lbImage.setIcon(new javax.swing.ImageIcon(this.imagenComprobante));

            boolean result = comprobanteLogica.addComprobante(comprobanteName, fileName);
            if (result) {
                deleteImage(fileName);
                c = logica.comprobanteLogica.getComprobante(comprobanteName);
            } else {
                JOptionPane.showMessageDialog(null, "Error agregando comprobante");
            }
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        return c;
    }

    /**
     * this method deletes an image file which fileName param is refering to
     *
     * @param fileName is URI associated to the file to delete
     */
    private void deleteImage(String fileName) {
        System.out.println(fileName);
        boolean flag = funcionesComunes.deleteFile(fileName);
        if (flag) {
            System.out.println("Image deleted");
        } else {
            System.out.println("Image not deleted");
        }
    }

    /**
     * this function checks if the dates are ok
     *
     * @return an int value, -2 if the date forms have a null value -1 if start
     * date is before the end date 0 if the dates are equal to each other 1 if
     * start is before the end
     */
    private boolean fechasValidas() {
        boolean valido = false;
        Date dIn = funcionesComunes.getFechaFromDtChooser(this.dtFechainico);
        Date dfin = funcionesComunes.getFechaFromDtChooser(this.dtFechaFin);
        String value = this.cmbTipoPermiso.getSelectedItem().toString();
        int tipo = entidades.tipoPermisos.getTipoPermiso(this.listaTipoPermisos, value);

        if (tipo < 3) {
            valido = fechasParaVacPerSinGoceValidas(dIn, dfin);
        } else {
            valido = fechasParaPerValidas(dIn);
        }

        return valido;
    }

    /**
     * this function cheks if the actual usser has othe permisos register for
     * the refered date (dIn), if it is the case returns a false boolean value,
     * else a true
     *
     * @param dIn is a Date object which would be check
     * @return a boolean value
     */
    private boolean fechasParaPerValidas(Date dIn) {
        boolean valido = false;
        if ((dIn != null)) {
            boolean fechasRepetidas = funcionesComunes.fechasRepetidas(dIn, myListaPer);
            if (!fechasRepetidas) {
                valido = true;
            } else {
                this.informationLb.setText("Ya tiene un permiso para las fechas solicitadas");
            }

        } else {
            this.informationLb.setText("Por favor ingrese las fechas correctamente");
        }
        return valido;
    }

    /**
     * this function cheks two dates associated to a permisos that the usser
     * want's to make, so it checks that those dates are rigth, so if they are
     * rigth returns a true boolean value, else false
     *
     * @param dIn is a Date object reffering to the permisos start date
     * @param dfin is a Date object reffering to the permisos end date
     * @return a boolean value
     */
    private boolean fechasParaVacPerSinGoceValidas(Date dIn, Date dfin) {
        boolean valido = false;
        if ((dIn != null) && (dfin != null)) {
            boolean b1 = funcionesComunes.fechasRepetidas(dIn, myListaPer);
            boolean b2 = funcionesComunes.fechasRepetidas(dfin, myListaPer);
            if (!b1 && !b2) {
                int menor = funcionesComunes.compareIniFinDates(dIn, dfin);
                if (menor < 1) {
                    valido = true;
                } else {
                    this.informationLb.setText("La fecha fin no puede ser menor al la de inicio");
                }
            } else {
                this.informationLb.setText("Ya tiene un permiso para las fechas solicitadas");
            }

        } else {
            this.informationLb.setText("Por favor ingrese las fechas correctamente");
        }
        return valido;
    }

    /**
     * this function checks if the dates are ok
     *
     * @return an int value, -2 if the date forms have a null value -1 if start
     * date is before the end date 0 if the dates are equal to each other 1 if
     * start is before the end
     */
    private boolean sinRestricciones() {
        boolean valido = false;
        Date dIn = funcionesComunes.getFechaFromDtChooser(this.dtFechainico);
        Date dfin = funcionesComunes.getFechaFromDtChooser(this.dtFechaFin);
        boolean permitir = restriccionPermisos.permitirVacacionesPorCantidadMaxima(contVacaciones, restriccionesParaUsuarioActual);
        boolean permitirPorFechas = restriccionPermisos.permitirVacacionesPorFechas(dIn, dfin, restriccionesParaUsuarioActual);
        if (permitir & permitirPorFechas) {

            valido = true;
        } else {
            this.txtVacPend.setForeground(Color.RED);
            JOptionPane.showMessageDialog(null, "Ya se alacanzó la cantidad permitida de vacaciones pendientes, o fechas restingidas");
        }

        return valido;
    }

    /**
     * this method checks if the vacaciones or permiso sin goce salario permisos
     * object is ok to create a register on the database, then creates the
     * register
     */
    private void validarVacaciones() {
        try {
            if (sinRestricciones() & fechasDisponibles() & lapsoPermitido()) {
                addPermiso(null);
            }
        } catch (Exception e) {
            System.out.println("vista.components.fromPermisos.validarVacaciones()");
            System.out.println("error " + e.getMessage());
            this.informationLb.setText("Por favor ingrese las fechas correctamente");
        }
    }

    /**
     * this function checks if a dates range is under a defined number of days,
     * if this is the case returns a true boolean value, else false
     *
     * @return a boolean value
     */
    private boolean lapsoPermitido() {
        boolean permitir = false;
        Date dIn = funcionesComunes.getFechaFromDtChooser(this.dtFechainico);
        Date dfin = funcionesComunes.getFechaFromDtChooser(this.dtFechaFin);
        if (fechasValidas()) {
            int dayslap = logica.funcionesComunes.getDifferenceIniFinDates(dIn, dfin);
            if (dayslap <= this.usuarioActual.getVacacionesPen()) {
                permitir = true;
            }
        } else {
            this.informationLb.setText("Revice las fechas por favor");
        }
        if (!permitir) {
            this.lbDifDias.setText("Vacaciones disponibles: " + this.usuarioActual.getVacacionesPen() + " días");
        }
        return permitir;
    }

    /**
     * this function checks on the database the next available date for a new
     * vacaciones permisos request, this for a given usser
     *
     * @return the number of permisos request on the pendientes state
     */
    private int getPermisosPorFechas(Date fechaini, Date fechaFin) {
        int count = 0;
        ArrayList<permisos> perlist = logica.permisosLogica.getParaVacacionesPorFechas(this.usuarioActual, fechaini, fechaFin);
        if (perlist != null) {
            count = perlist.size();
            this.informationLb.setText("Proxima fecha disponible es para despues del : " + perlist.get(count - 1).getFecha_fin());
        } else {
            System.out.println("No hay permisos para esa fecha");
            this.informationLb.setText("");
        }
        return count;
    }

    /**
     * this function checks if the actual vacaciones permisos is equal to the
     * max stablish number, if that is the case then the new vacaciones permisos
     * can't be allowed, so returns a boolean false value, else returns a true
     *
     * @return a boolean value
     */
    private boolean fechasDisponibles() {
        boolean valido = false;
        try {
            Date dIn = funcionesComunes.getFechaFromDtChooser(this.dtFechainico);
            Date dfin = funcionesComunes.getFechaFromDtChooser(this.dtFechaFin);
            int dif = getPermisosPorFechas(dIn, dfin);
            if (dif == 0) {

                valido = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fechas invalidas " + e.getMessage());
            valido = false;
        }

        return valido;
    }

    private boolean permisoRepetido() {
        String value = this.cmbTipoPermiso.getSelectedItem().toString();
        int tipo = entidades.tipoPermisos.getTipoPermiso(this.listaTipoPermisos, value);

        int count = 0;
        boolean found = false;
        if (this.myListaPer != null) {
            while (!found && count < this.myListaPer.size()) {
                if (tipo == this.myListaPer.get(count).getIdTipo_permiso()) {
                    found = true;
                    System.out.println("permiso repetido");
                }
                count++;
            }
        }
        return found;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAprobar;
    public javax.swing.JButton btnDenegar;
    public javax.swing.JButton btnEnviar;
    public javax.swing.JButton btnVisto;
    private javax.swing.JPanel center;
    public javax.swing.JComboBox<String> cmbDiaAct;
    public javax.swing.JComboBox<String> cmbDiaSug;
    public javax.swing.JComboBox<String> cmbTipoPermiso;
    public com.toedter.calendar.JDateChooser dtFechaFin;
    public com.toedter.calendar.JDateChooser dtFechainico;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lbImage;
    private javax.swing.JPanel left;
    private javax.swing.JPanel north;
    private javax.swing.JPanel northCenter;
    private javax.swing.JPanel northEast;
    private javax.swing.JPanel northNorth;
    private javax.swing.JPanel northsouth;
    private javax.swing.JPanel nothLeft;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlUsuarioInfo;
    public javax.swing.JRadioButton rbTrabTarde;
    public javax.swing.JRadioButton rbTrabmanyana;
    private javax.swing.JPanel rigth;
    private javax.swing.JPanel south;
    public javax.swing.JLabel txtDepartamento;
    public javax.swing.JLabel txtId;
    public javax.swing.JLabel txtNombreCompleto;
    public javax.swing.JLabel txtPuesto;
    // End of variables declaration//GEN-END:variables

}
