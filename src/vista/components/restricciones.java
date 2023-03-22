/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.components;

import entidades.Usuario;
import entidades.departamento;
import entidades.puesto;
import entidades.restriccionPermisos;
import entidades.tipo_restriccion_permisos;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import logica.funcionesComunes;
import vista.util.jComboboxComun;

/**
 *
 * @author programador1
 */
public class restricciones extends javax.swing.JPanel {

    /**
     * Creates new form restricciones
     */
    public restricciones() {
        initComponents();
        vista.util.botonComun.mouseHoverListener(this.btnGuardarRestrinccion);
        this.jLabel4.setVisible(false);
        this.txtMaxSolic.setVisible(false);
        LocalDate dt = funcionesComunes.calcNextTuesday();
        Date d = funcionesComunes.getDateFromLocalDate(dt);
        this.dtFinRestric.setMinSelectableDate(d);
        this.jDateChooser1.setMinSelectableDate(d);
    }

    public void chooseTipoRestriccion(ArrayList<tipo_restriccion_permisos> tiporestricciones) {
        String val = cmbTipoRestric.getSelectedItem().toString();
        tipo_restriccion_permisos idtipo = tipo_restriccion_permisos.getTipoRestrinccion(val, tiporestricciones);

        if (idtipo.getIdtipo_restriccion_permisos() == 2) {
            this.jLabel4.setVisible(true);
            this.txtMaxSolic.setVisible(true);
            this.jLabel2.setVisible(true);
            jDateChooser1.setVisible(false);
            dtFinRestric.setVisible(false);
        } else {
            this.jLabel4.setVisible(false);
            this.txtMaxSolic.setVisible(false);
            this.jLabel2.setVisible(false);
            jDateChooser1.setVisible(true);
            dtFinRestric.setVisible(true);
        }
    }

    public void showRestriccionesPorPuesto(ArrayList<departamento> alldepartamentos, ArrayList<restriccionPermisos> restricciones, ArrayList<tipo_restriccion_permisos> tiporestricciones, JTextArea txtareaInformacion) {
        try {
            String dep = this.cmbDepartamentos.getSelectedItem().toString();
            String puest = this.cmbPuestos.getSelectedItem().toString();
            System.out.println("dep: " + dep + " puest: " + puest);
            departamento d = entidades.departamento.getDepartamento(alldepartamentos, dep);
            puesto p = entidades.puesto.getPuesto(d.puestos, puest);
            System.out.println("puesto: " + p.getDescripcion());
            restriccionPermisos r1 = entidades.restriccionPermisos.getRestriccion(restricciones, d.getId_Departamento(), p.getId_Puesto(), 1);

            restriccionPermisos r2 = entidades.restriccionPermisos.getRestriccion(restricciones, d.getId_Departamento(), p.getId_Puesto(), 2);
            String info = "";
            if (r1 != null) {
                info = getInfoRestricciones(r1, tiporestricciones) + "\n";
            }
            if (r2 != null) {
                info += getInfoRestricciones(r2, tiporestricciones);
            }

            txtareaInformacion.setText(info);

        } catch (Exception e) {
            System.out.println("vista.mantenimiento02.cmbPuestosActionPerformed()");
            System.out.println("error: " + e.getMessage());
        }
    }

    public restriccionPermisos getrestriccionesPermisosForm1(Usuario usuarioActual, ArrayList<tipo_restriccion_permisos> tiporestricciones, ArrayList<departamento> alldepartamentos, ArrayList<puesto> puestos) {

        try {
            String val = cmbTipoRestric.getSelectedItem().toString();
            tipo_restriccion_permisos idtipo = tipo_restriccion_permisos.getTipoRestrinccion(val, tiporestricciones);
            System.out.println("tipo " + idtipo.getDecripcion());
            restriccionPermisos r = new restriccionPermisos();
            if (idtipo.getIdtipo_restriccion_permisos() == 2) {
                int cant = Integer.parseInt(this.txtMaxSolic.getText());
                r.setCantidad_maxima(cant);
                r.setFecha_inicio(new Date());
                r.setFecha_fin(new Date());
            } else {
                r.setCantidad_maxima(0);
                r.setFecha_inicio(new Date(this.jDateChooser1.getDate().getYear(), this.jDateChooser1.getDate().getMonth(), this.jDateChooser1.getDate().getDate()));
                r.setFecha_fin(new Date(this.dtFinRestric.getDate().getYear(), this.dtFinRestric.getDate().getMonth(), this.dtFinRestric.getDate().getDate()));
            }

            r.setId(usuarioActual.getId());
            r.setIdEmpleado(usuarioActual.getIdEmpleado());
            r.setFecha_creacion(new Date());

            r.setVigente(1);

            String dep = this.cmbDepartamentos.getSelectedItem().toString();
            System.out.println("dep " + dep);
            String puest = this.cmbPuestos.getSelectedItem().toString();
            departamento d = departamento.getDepartamento(alldepartamentos, dep);
            System.out.println("dep id " + d.getId_Departamento());
            r.setIddepartamento(d.getId_Departamento());
            puesto p = puesto.getPuesto(puestos, puest);
            r.setIdpuesto(p.getId_Puesto());
            System.out.println("dep: " + dep + " pues: " + puest + " restriccion: " + idtipo.getDecripcion() + " idtipo: " + idtipo.getIdtipo_restriccion_permisos());
            r.setIdtipo_restriccion_permisos(idtipo.getIdtipo_restriccion_permisos());
            return r;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Por favor llene el formulario correctamente");
            return null;
        }

    }

    public String getInfoRestricciones(restriccionPermisos e, ArrayList<tipo_restriccion_permisos> tiporestricciones) {
        tipo_restriccion_permisos tipo = entidades.tipo_restriccion_permisos.getTipoRestrinccionPorId(e.getIdtipo_restriccion_permisos(), tiporestricciones);

        String info = "Tipo: " + tipo.getDecripcion() + "\n";
        info += "Fecha creación: " + funcionesComunes.getCommunDates(e.getFecha_creacion()) + "\n";

        if (tipo.getIdtipo_restriccion_permisos() > 1) {
            info += "Cantidad máxima: " + e.getCantidad_maxima() + "\n";
        } else {
            info += "Fecha inicio: " + funcionesComunes.getCommunDates(e.getFecha_inicio()) + "\n";
            info += "Fecha fin: " + funcionesComunes.getCommunDates(e.getFecha_fin()) + "\n";
        }
        System.out.println(info);
        return info;
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbDepartamentos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbPuestos = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbTipoRestric = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        dtFinRestric = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtMaxSolic = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardarRestrinccion = new javax.swing.JButton();
        top = new javax.swing.JPanel();
        down = new javax.swing.JPanel();
        rigth = new javax.swing.JPanel();
        left = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        center.setBackground(new java.awt.Color(255, 255, 255));
        center.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "FORMULARIO RESTRICCIONES"));
        center.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        jLabel1.setText("DEPARTAMENTOS");
        jPanel1.add(jLabel1);

        jComboboxComun.setCmbLook(cmbDepartamentos);
        jPanel1.add(cmbDepartamentos);

        jLabel2.setText("PUESTOS");
        jPanel1.add(jLabel2);

        jComboboxComun.setCmbLook(cmbPuestos);
        jPanel1.add(cmbPuestos);

        jLabel3.setText("TIPO DE RESTICCIÓN");
        jPanel1.add(jLabel3);

        jComboboxComun.setCmbLook(cmbTipoRestric);
        jPanel1.add(cmbTipoRestric);

        jDateChooser1.setBorder(javax.swing.BorderFactory.createTitledBorder("INICIO"));
        jPanel1.add(jDateChooser1);

        dtFinRestric.setBorder(javax.swing.BorderFactory.createTitledBorder("FIN"));
        jPanel1.add(dtFinRestric);

        jLabel4.setText("CANTIDAD MÁXIMA");
        jPanel1.add(jLabel4);
        jPanel1.add(txtMaxSolic);

        center.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(448, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        center.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setPreferredSize(new java.awt.Dimension(448, 50));

        btnGuardarRestrinccion.setBackground(new java.awt.Color(204, 255, 255));
        btnGuardarRestrinccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-save-30.png"))); // NOI18N
        btnGuardarRestrinccion.setText("GUARDAR");
        btnGuardarRestrinccion.setToolTipText("Guardar cambios");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnGuardarRestrinccion)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnGuardarRestrinccion)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        center.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        add(center, java.awt.BorderLayout.CENTER);

        top.setBackground(new java.awt.Color(255, 255, 255));
        top.setMaximumSize(new java.awt.Dimension(508, 30));
        top.setMinimumSize(new java.awt.Dimension(508, 30));
        top.setPreferredSize(new java.awt.Dimension(508, 30));

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(top, java.awt.BorderLayout.PAGE_START);

        down.setBackground(new java.awt.Color(255, 255, 255));
        down.setMaximumSize(new java.awt.Dimension(508, 30));
        down.setMinimumSize(new java.awt.Dimension(508, 30));
        down.setPreferredSize(new java.awt.Dimension(508, 30));

        javax.swing.GroupLayout downLayout = new javax.swing.GroupLayout(down);
        down.setLayout(downLayout);
        downLayout.setHorizontalGroup(
            downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        downLayout.setVerticalGroup(
            downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(down, java.awt.BorderLayout.PAGE_END);

        rigth.setBackground(new java.awt.Color(255, 255, 255));
        rigth.setMaximumSize(new java.awt.Dimension(30, 327));
        rigth.setMinimumSize(new java.awt.Dimension(30, 327));
        rigth.setPreferredSize(new java.awt.Dimension(30, 327));

        javax.swing.GroupLayout rigthLayout = new javax.swing.GroupLayout(rigth);
        rigth.setLayout(rigthLayout);
        rigthLayout.setHorizontalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        rigthLayout.setVerticalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        add(rigth, java.awt.BorderLayout.LINE_END);

        left.setBackground(new java.awt.Color(255, 255, 255));
        left.setMaximumSize(new java.awt.Dimension(30, 327));
        left.setMinimumSize(new java.awt.Dimension(30, 327));
        left.setPreferredSize(new java.awt.Dimension(30, 327));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        add(left, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGuardarRestrinccion;
    private javax.swing.JPanel center;
    public javax.swing.JComboBox<String> cmbDepartamentos;
    public javax.swing.JComboBox<String> cmbPuestos;
    public javax.swing.JComboBox<String> cmbTipoRestric;
    private javax.swing.JPanel down;
    public com.toedter.calendar.JDateChooser dtFinRestric;
    public com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel left;
    private javax.swing.JPanel rigth;
    private javax.swing.JPanel top;
    public javax.swing.JTextField txtMaxSolic;
    // End of variables declaration//GEN-END:variables
}
