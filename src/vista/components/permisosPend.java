/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.components;

import entidades.Usuario;
import entidades.accionesPermiso;
import entidades.permisos;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author programador1
 */
public class permisosPend extends javax.swing.JPanel {

    /**
     * Creates new form permisosPend
     */
    ArrayList<accionesPermiso> listaAcciones;
    ArrayList<Usuario> empleConPermsPend;
    boolean refresh;
    ArrayList<permisos> listaPerPend;

    public permisosPend() {
        initComponents();
        this.listaAcciones = new ArrayList<>();
        this.listaPerPend = logica.permisosLogica.getPermisosPendientes();
        cargarListaEmpPermPend();
        addRowTablePosiblesUsuariosAdministradores(empleConPermsPend, listaPerPend);
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

    public void cargarListaEmpPermPend(ArrayList<Usuario> empleConPermsPend, ArrayList<permisos> listaPerPend) {
        empleConPermsPend = new ArrayList<>();
        listaAcciones = new ArrayList<>();
        if (empleConPermsPend != null) {
            listaPerPend.forEach(e -> {
                accionesPermiso ac = new accionesPermiso(e.getIdPermiso());
                listaAcciones.add(ac);

            });
        }
    }

    public void addRowTablePosiblesUsuariosAdministradores(ArrayList<Usuario> empleConPermsPend, ArrayList<permisos> listaPerPend) {
        try {
            DefaultTableModel model = (DefaultTableModel) this.tbPermisos.getModel();
            int index = 0;
            for (permisos p : listaPerPend) {
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
        } catch (Exception e) {
            System.out.println("vista.components.permisosPend.addRowTablePosiblesUsuariosAdministradores()");
            System.out.println("error: " + e.getMessage());
        }
    }

    public void tbEmpleadosEventChangeListener(ArrayList<permisos> listaPerPend) {
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
                        for (int i = 2; i < 6; i++) {
                            refresh = true;
                            if (colIndex != i) {
                                tbPermisos.setValueAt(!flag, rowIndex, i);
                            }

                        }
                    }

                    ac.visto = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 2).toString());
                    ac.aprobar = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 3).toString());
                    ac.denegar = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 4).toString());
                    ac.eliminar = Boolean.parseBoolean(tbPermisos.getValueAt(rowIndex, 5).toString());
                    refresh = false;
                }
            }
        });

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
        tbPermisos = new javax.swing.JTable();
        top = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        lbCount = new javax.swing.JLabel();
        down = new javax.swing.JPanel();
        rigth = new javax.swing.JPanel();
        left = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

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
            tbPermisos.getColumnModel().getColumn(0).setHeaderValue("ID");
            tbPermisos.getColumnModel().getColumn(1).setResizable(false);
            tbPermisos.getColumnModel().getColumn(1).setPreferredWidth(300);
            tbPermisos.getColumnModel().getColumn(1).setHeaderValue("NOMBRE");
            tbPermisos.getColumnModel().getColumn(2).setResizable(false);
            tbPermisos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(2).setHeaderValue("VISTO");
            tbPermisos.getColumnModel().getColumn(3).setResizable(false);
            tbPermisos.getColumnModel().getColumn(3).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(3).setHeaderValue("APROBAR");
            tbPermisos.getColumnModel().getColumn(4).setResizable(false);
            tbPermisos.getColumnModel().getColumn(4).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(4).setHeaderValue("DENEGAR");
            tbPermisos.getColumnModel().getColumn(5).setResizable(false);
            tbPermisos.getColumnModel().getColumn(5).setPreferredWidth(40);
            tbPermisos.getColumnModel().getColumn(5).setHeaderValue("ELIMINAR");
        }

        center.add(jScrollPane1);

        add(center, java.awt.BorderLayout.CENTER);

        top.setPreferredSize(new java.awt.Dimension(600, 50));

        jToolBar1.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-save-30.png"))); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSave);

        lbCount.setText("0");
        jToolBar1.add(lbCount);

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(top, java.awt.BorderLayout.PAGE_START);

        down.setPreferredSize(new java.awt.Dimension(600, 50));

        javax.swing.GroupLayout downLayout = new javax.swing.GroupLayout(down);
        down.setLayout(downLayout);
        downLayout.setHorizontalGroup(
            downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );
        downLayout.setVerticalGroup(
            downLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        add(down, java.awt.BorderLayout.PAGE_END);

        rigth.setPreferredSize(new java.awt.Dimension(30, 600));

        javax.swing.GroupLayout rigthLayout = new javax.swing.GroupLayout(rigth);
        rigth.setLayout(rigthLayout);
        rigthLayout.setHorizontalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        rigthLayout.setVerticalGroup(
            rigthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        add(rigth, java.awt.BorderLayout.LINE_END);

        left.setPreferredSize(new java.awt.Dimension(30, 600));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        add(left, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSave;
    private javax.swing.JPanel center;
    private javax.swing.JPanel down;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbCount;
    private javax.swing.JPanel left;
    private javax.swing.JPanel rigth;
    public javax.swing.JTable tbPermisos;
    private javax.swing.JPanel top;
    // End of variables declaration//GEN-END:variables
}
