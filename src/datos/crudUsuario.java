/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class crudUsuario {

    public static Usuario getUsuario(int id) {
        Usuario usuario = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.IdEmpleado, "
                            + "c.nombre, "
                            + "c.primer_apellido, "
                            + "c.segundo_apellido, "
                            + "c.Departamento, "
                            + "c.puesto, "
                            + "c.activo, "
                            + "c.IdTarjeta, "
                            + "c.VacacionesPen,"
                            + "c.VacacionesPeriodoActual, "
                            + "c.VacacionesAcumuladas, "
                            + "FechaIngreso, "
                            + "c.id "
                            + "from empleado c"
                            + " where c.IdEmpleado = ?"
                            + " and c.activo =1;"
                    );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setIdEmpleado(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido1(rs.getString(3));
                usuario.setApellido2(rs.getString(4));
                usuario.setDepartamento(rs.getInt(5));
                usuario.setIdPuesto(rs.getInt(6));
                usuario.setActivo(rs.getInt(7));
                usuario.setIdTarjeta(rs.getString(8));
                usuario.setVacacionesPen(rs.getInt(9));
                usuario.setVacacionesPeriodoActual(rs.getInt(10));
                usuario.setVacacionesAcumuladas(rs.getInt(11));
                usuario.setFechaIngreso(rs.getDate(12));
                usuario.setId(rs.getInt(13));

                System.out.println("nombre " + usuario.getNombre());

            }
            con.close();
            return usuario;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "query error " + e.getMessage());
            return null;
        }
    }

    public static Usuario getUsuario(String name) {
        Usuario usuario = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.IdEmpleado, "
                            + "c.nombre, "
                            + "c.primer_apellido, "
                            + "c.segundo_apellido, "
                            + "c.Departamento, "
                            + "c.puesto, "
                            + "c.activo, "
                            + "c.IdTarjeta, "
                            + "c.VacacionesPen,"
                            + "c.VacacionesPeriodoActual, "
                            + "c.VacacionesAcumuladas, "
                            + "FechaIngreso, "
                            + "c.id "
                            + "from empleado c"
                            + " where concat(c.nombre, c.Primer_Apellido, c.segundo_Apellido)=?"
                            + " and c.activo =1;"
                    );
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setIdEmpleado(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido1(rs.getString(3));
                usuario.setApellido2(rs.getString(4));
                usuario.setDepartamento(rs.getInt(5));
                usuario.setIdPuesto(rs.getInt(6));
                usuario.setActivo(rs.getInt(7));
                usuario.setIdTarjeta(rs.getString(8));
                usuario.setVacacionesPen(rs.getInt(9));
                usuario.setVacacionesPeriodoActual(rs.getInt(10));
                usuario.setVacacionesAcumuladas(rs.getInt(11));
                usuario.setFechaIngreso(rs.getDate(12));
                usuario.setId(rs.getInt(13));

                System.out.println("nombre " + usuario.getNombre());

            }
            con.close();
            return usuario;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "query error " + e.getMessage());
            return null;
        }

    }

    public static ArrayList<Usuario> getAllUsuariosActivosPorDepartamento(int idDepartamento) {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.IdEmpleado, "
                            + "c.nombre, "
                            + "c.primer_apellido, "
                            + "c.segundo_apellido, "
                            + "c.Departamento, "
                            + "c.puesto, "
                            + "c.activo, "
                            + "c.IdTarjeta, "
                            + "c.VacacionesPen,"
                            + "c.VacacionesPeriodoActual, "
                            + "c.VacacionesAcumuladas, "
                            + "c.FechaIngreso, "
                            + "c.id "
                            + "from empleado c"
                            + " where c.Departamento =? and "
                            + " c.activo =1 "
                            + "order by c.puesto asc;"
                    );
            ps.setInt(1, idDepartamento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario us = new Usuario();
                us.setIdEmpleado(rs.getInt(1));
                us.setNombre(rs.getString(2));
                us.setApellido1(rs.getString(3));
                us.setApellido2(rs.getString(4));
                us.setDepartamento(rs.getInt(5));
                us.setIdPuesto(rs.getInt(6));
                us.setActivo(rs.getInt(7));
                us.setIdTarjeta(rs.getString(8));
                us.setVacacionesPen(rs.getInt(9));
                us.setVacacionesPeriodoActual(rs.getInt(10));
                us.setVacacionesAcumuladas(rs.getInt(11));
                us.setFechaIngreso(rs.getDate(12));
                us.setId(rs.getInt(13));

                usuarios.add(us);

            }
            con.close();
            return usuarios;
        } catch (Exception e) {
            System.out.println("error in the query result " + e.getMessage());
            return null;
        }

    }

}
