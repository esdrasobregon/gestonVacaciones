/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.departamento;
import entidades.puesto;
import entidades.restriccionPermisos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class crudRestriccionPermisos {

    public static boolean addRestriccion(restriccionPermisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO `gestion_vacaciones_permisos`.`restriccion_permisos`"
                            + "("
                            + "`idtipo_restriccion_permisos`,"
                            + "`fecha_creacion`,"
                            + "`iddepartamento`,"
                            + "`idpuesto`,"
                            + "`fecha_inicio`,"
                            + "`fecha_fin`,"
                            + "`idEmpleado`,"
                            + "`id`,"
                            + "`vigente`,"
                            + "`cantidad_maxima`)"
                            + "VALUES"
                            + "("
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?);");

            ps.setInt(1, un.getIdtipo_restriccion_permisos());
            ps.setDate(2, new Date(un.getFecha_creacion().getTime()));
            ps.setInt(3, un.getIddepartamento());
            ps.setInt(4, un.getIdpuesto());
            ps.setDate(5, new Date(un.getFecha_inicio().getTime()));
            ps.setDate(6, new Date(un.getFecha_fin().getTime()));
            ps.setInt(7, un.getIdEmpleado());
            ps.setInt(8, un.getId());
            ps.setInt(9, un.getVigente());
            ps.setInt(10, un.getCantidad_maxima());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudRestriccionPermisos.addRestriccion()");
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizarVigencias(restriccionPermisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("update restriccion_permisos r set r.vigente = 0 "
                            + "where r.iddepartamento = ? "
                            + "and r.idpuesto = ? "
                            + "and r.idtipo_restriccion_permisos = ?;");

            ps.setInt(1, un.getIddepartamento());
            ps.setInt(2, un.getIdpuesto());
            ps.setInt(3, un.getIdtipo_restriccion_permisos());
            ps.execute();
            us = true;
            System.out.println("dep: " + un.getIddepartamento() + "puest: " + un.getIdpuesto() + "idrestrc: " + un.getIdtipo_restriccion_permisos());
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudRestriccionPermisos.actualizarVigencias()");
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<restriccionPermisos> getrestriccionPermisosPorDepPuesto(int dp, int p) {
        ArrayList<restriccionPermisos> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "rp.idrestriccion_permisos, "
                            + "rp.idtipo_restriccion_permisos, "
                            + "rp.fecha_creacion, "
                            + "rp.fecha_inicio, "
                            + "rp.fecha_fin, "
                            + "rp.idempleado, "
                            + "rp.id, "
                            + "rp.vigente, "
                            + "rp.cantidad_maxima, "
                            + "rp.iddepartamento, "
                            + "rp.idpuesto from restriccion_permisos rp "
                            + "where rp.iddepartamento = ?"
                            + " and rp.idpuesto = ? and rp.vigente = 1;"
                    );
            ps.setInt(1, dp);
            ps.setInt(2, p);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                restriccionPermisos d = new restriccionPermisos();
                d.setIdrestriccion_permisos(rs.getInt(1));
                d.setIdtipo_restriccion_permisos(rs.getInt(2));
                d.setFecha_creacion(rs.getDate(3));
                d.setFecha_inicio(rs.getDate(4));
                d.setFecha_fin(rs.getDate(5));
                d.setIdEmpleado(rs.getInt(6));
                d.setId(rs.getInt(7));
                d.setVigente(rs.getInt(8));
                d.setCantidad_maxima(rs.getInt(9));
                d.setIddepartamento(rs.getInt(10));
                d.setIdpuesto(rs.getInt(11));
                list.add(d);
            }
            con.close();
            return list;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<restriccionPermisos> getrestriccionPermisosVigentes() {
        ArrayList<restriccionPermisos> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "rp.idrestriccion_permisos, "
                            + "rp.idtipo_restriccion_permisos, "
                            + "rp.fecha_creacion, "
                            + "rp.fecha_inicio, "
                            + "rp.fecha_fin, "
                            + "rp.idempleado, "
                            + "rp.id, "
                            + "rp.vigente, "
                            + "rp.cantidad_maxima, "
                            + "rp.iddepartamento, "
                            + "rp.idpuesto from restriccion_permisos rp "
                            + "where rp.vigente = 1;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                restriccionPermisos d = new restriccionPermisos();
                d.setIdrestriccion_permisos(rs.getInt(1));
                d.setIdtipo_restriccion_permisos(rs.getInt(2));
                d.setFecha_creacion(rs.getDate(3));
                d.setFecha_inicio(rs.getDate(4));
                d.setFecha_fin(rs.getDate(5));
                d.setIdEmpleado(rs.getInt(6));
                d.setId(rs.getInt(7));
                d.setVigente(rs.getInt(8));
                d.setCantidad_maxima(rs.getInt(9));
                d.setIddepartamento(rs.getInt(10));
                d.setIdpuesto(rs.getInt(11));
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
