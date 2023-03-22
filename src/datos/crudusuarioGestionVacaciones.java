/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.Usuario;
import entidades.usuarioGestionVacaciones;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class crudusuarioGestionVacaciones {

    public static boolean addUsuarioGestionVacaciones(usuarioGestionVacaciones un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO `gestion_vacaciones_permisos`.`usuariogestionvacaciones`"
                            + "(`idEmpleado`,"
                            + "`id`,"
                            + "`fecha_creacion`,"
                            + "`fecha_modificacion`,"
                            + "`estado`,"
                            + "`iddepartamento`,"
                            + "`idtipo_usuario`)"
                            + "VALUES"
                            + "(?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?);");
            ps.setInt(1, un.getUs().getIdEmpleado());
            ps.setInt(2, un.getUs().getId());
            ps.setDate(3, new Date(un.getFecha_creacion().getTime()));
            ps.setDate(4, new Date(un.getFecha_modificacion().getTime()));
            ps.setInt(5, un.getEstado());
            ps.setInt(6, un.getUs().getDepartamento());
            ps.setInt(7, un.getIdtipo_usuario());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudusuarioGestionVacaciones.addPermiso()");
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<usuarioGestionVacaciones> getAllUsuarioVacaciones() {
        ArrayList<usuarioGestionVacaciones> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("SELECT "
                            + "g.idusuarioGestionVacaciones,"
                            + "g.idEmpleado,"
                            + "g.id,"
                            + "g.fecha_creacion,"
                            + "g.fecha_modificacion,"
                            + "g.estado,"
                            + "g.iddepartamento,"
                            + "g.idtipo_usuario"
                            + " FROM usuariogestionvacaciones g where g.estado =1;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario us = new Usuario();
                us.setIdEmpleado(rs.getInt(2));
                us.setId(rs.getInt(3));

                usuarioGestionVacaciones d = new usuarioGestionVacaciones();
                d.setUs(us);
                d.setIdusuarioGestionVacaciones(rs.getInt(1));
                d.setFecha_creacion(rs.getDate(4));
                d.setFecha_modificacion(rs.getDate(5));
                d.setEstado(rs.getInt(6));
                d.setIdDepartamento(rs.getInt(7));
                d.setIdtipo_usuario(rs.getInt(8));

                list.add(d);
            }
            con.close();
            return list;
        } catch (Exception e) {
            System.out.println("datos.crudusuarioGestionVacaciones.getAllUsuarioVacaciones()");
            System.out.println("error " + e.getMessage());
            return list;
        }
    }

    public static boolean deleteUsuarioGestionVacaciones(int idEmpleado) {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("delete from usuariogestionvacaciones "
                            + "WHERE `idEmpleado` = ?;");
            ps.setInt(1, idEmpleado);
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudusuarioGestionVacaciones.deleteUsuarioGestionVacaciones()");
            System.out.println("error " + e.getMessage());
            return us;
        }
    }

}
