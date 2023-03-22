/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.Auditoria_permiso;
import entidades.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class crudAuditoria_permiso {

    public static boolean addAuditoria(Auditoria_permiso un) {

        boolean us = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO auditoria_permiso "
                            + "(`idPermiso`,"
                            + "`idEmpleado_responsable`,"
                            + "`id_responsable`,"
                            + "`anterior_estado_permiso`,"
                            + "nuevo_estado_permiso,"
                            + "fecha_creacion_permiso,"
                            + "id_tipo_permiso,"
                            + "fecha_modificacion,"
                            + "idEmpleado_solicitante,"
                            + "id_solicitante) "
                            + "VALUES"
                            + "(?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?);");

            ps.setInt(1, un.getIdPermiso());
            ps.setInt(2, un.getIdEmpleadoResponsable());
            ps.setInt(3, un.getIdResponsable());
            ps.setInt(4, un.getAnterior_estado_permiso());
            ps.setInt(5, un.getNuevo_estado_permiso());
            ps.setDate(6, new Date(un.getFecha_creacion().getTime()));
            ps.setInt(7, un.getId_tipo_permiso());
            ps.setDate(8, new Date(un.getFecha_modificacion().getTime()));
            ps.setInt(9, un.getIdEmpleadoSolicitante());
            ps.setInt(10, un.getIdSolicitante());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public static ArrayList<Auditoria_permiso> getHistorialAuditoria(int idpermiso) {
        ArrayList<Auditoria_permiso> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "a.idauditoria_permiso,"
                            + "a.idPermiso, "
                            + "a.fecha_creacion_permiso, "
                            + "a.idEmpleado_responsable, "
                            + "a.id_responsable, "
                            + "a.anterior_estado_permiso, "
                            + "a.nuevo_estado_permiso, "
                            + "e.nombre, "
                            + "e.Primer_Apellido, "
                            + "e.Segundo_Apellido, "
                            + "a.fecha_modificacion,"
                            + "a.id_tipo_permiso "
                            + "from auditoria_permiso a"
                            + " join pruebasol.empleado e"
                            + " on a.idEmpleado_responsable = e.idEmpleado and a.idPermiso = ?"
                            + " order by a.idauditoria_permiso;"
                    );
            ps.setInt(1, idpermiso);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Auditoria_permiso d = new Auditoria_permiso();
                d.setIdauditoria_permiso(rs.getInt(1));
                d.setIdPermiso(rs.getInt(2));
                d.setFecha_creacion(rs.getDate(3));
                Usuario us = new Usuario();

                us.setIdEmpleado(rs.getInt(4));
                us.setId(rs.getInt(5));
                d.setAnterior_estado_permiso(rs.getInt(6));
                d.setNuevo_estado_permiso(rs.getInt(7));
                us.setNombre(rs.getString(8));
                us.setPrimer_Apellido(rs.getString(9));
                us.setSegundo_Apellido(rs.getString(10));
                d.setUs(us);
                list.add(d);
            }
            con.close();
            return list;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

}
