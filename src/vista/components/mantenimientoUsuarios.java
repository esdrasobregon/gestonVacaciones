/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.components;

import entidades.Usuario;
import entidades.usuarioGestionVacaciones;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class mantenimientoUsuarios extends javax.swing.JPanel {

    /**
     * Creates new form mantenimientoUsuarios
     */
    public mantenimientoUsuarios() {
        initComponents();
        vista.util.botonComun.mouseHoverListener(this.btnSave);
        vista.util.botonComun.mouseHoverListener(this.btnSerch);
        this.btnSave.setVisible(false);

    }

    public static void addUsuarioGestionVacaciones(Usuario us) {
        usuarioGestionVacaciones usg = new usuarioGestionVacaciones();
        usg.setUs(us);
        Date d = new Date();
        usg.setFecha_creacion(d);
        usg.setFecha_modificacion(d);
        usg.setEstado(1);
        usg.setIdtipo_usuario(2);
        boolean ress = logica.usuarioGestionVacacionesLogica.addUsuarioGestionVacaciones(usg);
        if (ress) {
            JOptionPane.showMessageDialog(null, us.getNombre() + " " + us.getPrimer_Apellido() + " es ahora un nuevo encargado");
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }
    }

    public static void deleteUsuarioGestionVacaciones(Usuario us) {
        boolean ress = logica.usuarioGestionVacacionesLogica.deleteUsuarioGestionVacaciones(us.getIdEmpleado());
        if (ress) {
            JOptionPane.showMessageDialog(null, us.getNombre() + " " + us.getPrimer_Apellido() + " ya no es encargado");
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }
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
        tbEmpleados = new javax.swing.JTable();
        north = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbDepartamentos = new javax.swing.JComboBox<>();
        btnSerch = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        south = new javax.swing.JPanel();
        east = new javax.swing.JPanel();
        weast = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(700, 600));
        setMinimumSize(new java.awt.Dimension(700, 600));
        setPreferredSize(new java.awt.Dimension(700, 500));
        setLayout(new java.awt.BorderLayout());

        center.setBackground(new java.awt.Color(255, 255, 255));
        center.setMaximumSize(new java.awt.Dimension(600, 500));
        center.setMinimumSize(new java.awt.Dimension(600, 500));
        center.setPreferredSize(new java.awt.Dimension(600, 500));
        center.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "Empleados del departamento"));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));

        tbEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EMPLEADO", "PUESTO", "ENCARGADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbEmpleados.setFillsViewportHeight(true);
        tbEmpleados.setIntercellSpacing(new java.awt.Dimension(5, 5));
        tbEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbEmpleados);
        if (tbEmpleados.getColumnModel().getColumnCount() > 0) {
            tbEmpleados.getColumnModel().getColumn(0).setResizable(false);
            tbEmpleados.getColumnModel().getColumn(1).setResizable(false);
            tbEmpleados.getColumnModel().getColumn(2).setMinWidth(90);
            tbEmpleados.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        center.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(center, java.awt.BorderLayout.CENTER);

        north.setBackground(new java.awt.Color(255, 255, 255));
        north.setPreferredSize(new java.awt.Dimension(600, 100));
        north.setLayout(new java.awt.GridLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(400, 120));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("ESCOJA UN DEPARTAMENTO");

        btnSerch.setBackground(new java.awt.Color(204, 255, 255));
        btnSerch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/serch.png"))); // NOI18N
        btnSerch.setToolTipText("BUSCAR");
        btnSerch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSerch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmbDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSerch, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmbDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSerch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.add(jPanel3);

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);

        btnSave.setToolTipText("Guardar cambios");
        btnSave.setBackground(new java.awt.Color(204, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-save-30.png"))); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSave);

        jPanel1.add(jToolBar1);

        north.add(jPanel1);

        add(north, java.awt.BorderLayout.PAGE_START);

        south.setBackground(new java.awt.Color(255, 255, 255));
        south.setPreferredSize(new java.awt.Dimension(600, 20));

        javax.swing.GroupLayout southLayout = new javax.swing.GroupLayout(south);
        south.setLayout(southLayout);
        southLayout.setHorizontalGroup(
            southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        southLayout.setVerticalGroup(
            southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(south, java.awt.BorderLayout.PAGE_END);

        east.setBackground(new java.awt.Color(255, 255, 255));
        east.setPreferredSize(new java.awt.Dimension(20, 600));

        javax.swing.GroupLayout eastLayout = new javax.swing.GroupLayout(east);
        east.setLayout(eastLayout);
        eastLayout.setHorizontalGroup(
            eastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        eastLayout.setVerticalGroup(
            eastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        add(east, java.awt.BorderLayout.LINE_END);

        weast.setBackground(new java.awt.Color(255, 255, 255));
        weast.setPreferredSize(new java.awt.Dimension(20, 600));

        javax.swing.GroupLayout weastLayout = new javax.swing.GroupLayout(weast);
        weast.setLayout(weastLayout);
        weastLayout.setHorizontalGroup(
            weastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        weastLayout.setVerticalGroup(
            weastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        add(weast, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSave;
    public javax.swing.JButton btnSerch;
    private javax.swing.JPanel center;
    public javax.swing.JComboBox<String> cmbDepartamentos;
    private javax.swing.JPanel east;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel north;
    private javax.swing.JPanel south;
    public javax.swing.JTable tbEmpleados;
    private javax.swing.JPanel weast;
    // End of variables declaration//GEN-END:variables
}
