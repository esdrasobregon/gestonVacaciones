/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.Usuario;
import entidades.permisos;
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
public class crudPermisos {

    public static boolean addPermiso(permisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO permiso "
                            + "("
                            + "`fecha_inicio`,"
                            + "`fecha_fin`,"
                            + "`idEmpleado`,"
                            + "`id`,"
                            + "`estado`,"
                            + "`idTipo_permiso`,"
                            + "obserbaciones,"
                            + "trabajar, "
                            + "fecha_creacion,"
                            + " dia_libre,"
                            + "idpuesto,"
                            + "id_departamento) "
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
                            + "?,"
                            + "?,"
                            + "?);");

            ps.setDate(1, new Date(un.getFecha_inicio().getTime()));
            ps.setDate(2, new Date(un.getFecha_fin().getTime()));
            ps.setInt(3, un.getIdEmpleado());
            ps.setInt(4, un.getId());
            ps.setInt(5, un.getEstado());
            ps.setInt(6, un.getIdTipo_permiso());
            ps.setString(7, un.getObserbaciones());
            ps.setInt(8, un.getTrabajar());
            ps.setDate(9, new Date(un.getFecha_creacion().getTime()));
            ps.setString(10, un.getDia_libre());
            ps.setInt(11, un.getIdpuesto());
            ps.setInt(12, un.getIdDepartamento());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudPermisos.addPermiso()");
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean updatePermiso(permisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("UPDATE `gestion_vacaciones_permisos`.`permiso`\n"
                            + "SET "
                            + "`fecha_inicio` = ?, "
                            + "`fecha_fin` = ?, "
                            + "`estado` = ?, "
                            + " idTipo_permiso=?,"
                            + " trabajar =? "
                            + "WHERE `idPermiso` = ?;");

            ps.setDate(1, new Date(un.getFecha_inicio().getTime()));
            ps.setDate(2, new Date(un.getFecha_fin().getTime()));
            ps.setInt(3, un.getEstado());
            ps.setInt(4, un.getIdTipo_permiso());
            ps.setInt(5, un.getTrabajar());
            ps.setInt(6, un.getIdPermiso());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudPermisos.updatePermiso()");
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean addPermisoComprobante(permisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO permiso "
                            + "("
                            + "`fecha_inicio`,"
                            + "`fecha_fin`,"
                            + "`idEmpleado`,"
                            + "`id`,"
                            + "`estado`,"
                            + "`idTipo_permiso`,"
                            + " idComprobante,"
                            + "obserbaciones, "
                            + "fecha_creacion,"
                            + "dia_libre,"
                            + "idpuesto,"
                            + "id_departamento) "
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
                            + "?,"
                            + "?,"
                            + "?);");

            ps.setDate(1, new Date(un.getFecha_inicio().getTime()));
            ps.setDate(2, new Date(un.getFecha_fin().getTime()));
            ps.setInt(3, un.getIdEmpleado());
            ps.setInt(4, un.getId());
            ps.setInt(5, un.getEstado());
            ps.setInt(6, un.getIdTipo_permiso());
            ps.setInt(7, un.getIdComprobante());
            ps.setString(8, un.getObserbaciones());
            ps.setDate(9, new Date(un.getFecha_creacion().getTime()));
            ps.setString(10, un.getDia_libre());
            ps.setInt(11, un.getIdpuesto());
            ps.setInt(12, un.getIdDepartamento());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {

            System.out.println("error: " + e.getMessage());
            return false;
        }
    }

    public static boolean AgregarPermiso(permisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO permiso "
                            + "("
                            + "`fecha_inicio`,"
                            + "`fecha_fin`,"
                            + "`idEmpleado`,"
                            + "`id`,"
                            + "`estado`,"
                            + "`idTipo_permiso, "
                            + "fecha_creacion`) "
                            + "VALUES"
                            + "(?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?);");

            ps.setDate(1, new Date(un.getFecha_inicio().getTime()));
            ps.setDate(2, new Date(un.getFecha_fin().getTime()));
            ps.setInt(3, un.getIdEmpleado());
            ps.setInt(4, un.getId());
            ps.setInt(5, un.getEstado());
            ps.setInt(6, un.getIdTipo_permiso());
            ps.setInt(7, un.getIdComprobante());
            ps.setDate(8, new Date(un.getFecha_creacion().getTime()));
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("datos.crudPermisos.AgregarPermiso()");
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<permisos> getPermisos() {
        ArrayList<permisos> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "	p.idEmpleado, "
                            + "p.id, "
                            + "p.estado, "
                            + "p.idcomprobante, "
                            + "p.idtipo_permiso, "
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.fecha_creacion,"
                            + "dia_libre, "
                            + "idpuesto from permiso p;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(9));
                d.setDia_libre(rs.getString(10));
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

    public static ArrayList<permisos> getPermisosPorEmpleado(Usuario us) {
        ArrayList<permisos> list = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "	p.idEmpleado, "
                            + "p.id, "
                            + "p.estado, "
                            + "p.idcomprobante, "
                            + "p.idtipo_permiso, "
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.fecha_creacion,"
                            + "p.obserbaciones,"
                            + "p.trabajar,"
                            + "dia_libre,"
                            + "idpuesto from permiso p"
                            + " where p.idEmpleado = ?"
                            + " and p.estado < 3;"
                    );
            ps.setInt(1, us.getIdEmpleado());
            ResultSet rs = ps.executeQuery();
            ArrayList<permisos> listap = new ArrayList<>();
            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(9));
                d.setObserbaciones(rs.getString(10));
                d.setTrabajar(rs.getInt(11));
                d.setDia_libre(rs.getString(12));
                d.setIdpuesto(rs.getInt(13));
                listap.add(d);
            }
            con.close();
            if (!listap.isEmpty()) {
                list = listap;
            }
            return list;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<permisos> getAllPermisosPorEmpleado(Usuario us) {
        ArrayList<permisos> list = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "	p.idEmpleado, "
                            + "p.id, "
                            + "p.estado, "
                            + "p.idcomprobante, "
                            + "p.idtipo_permiso, "
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.fecha_creacion,"
                            + "p.obserbaciones,"
                            + "p.trabajar,"
                            + "dia_libre,"
                            + "idpuesto from permiso p"
                            + " where p.idEmpleado = ?;"
                    );
            ps.setInt(1, us.getIdEmpleado());
            ResultSet rs = ps.executeQuery();
            ArrayList<permisos> listap = new ArrayList<>();
            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(9));
                d.setObserbaciones(rs.getString(10));
                d.setTrabajar(rs.getInt(11));
                d.setDia_libre(rs.getString(12));
                d.setIdpuesto(rs.getInt(13));
                listap.add(d);
            }
            con.close();
            if (!listap.isEmpty()) {
                list = listap;
            }
            return list;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

    public static int getContadorPermisosPorTipoEstadoPuesto(int idpuesto, int idtipo, int estado) {
        int list = -1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            PreparedStatement ps
                    = con.prepareStatement("select count(*) from permiso p where p.idpuesto = ? and p.estado =? and p.idTipo_permiso = ?"
                    );
            ps.setInt(1, idpuesto);
            ps.setInt(2, estado);
            ps.setInt(3, idtipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = rs.getInt(1);
            }
            con.close();

            return list;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return -1;
        }
    }

    public static ArrayList<permisos> getPermisosPendientes() {
        ArrayList<permisos> list = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "	p.idEmpleado, "
                            + "p.id, "
                            + "p.estado, "
                            + "p.idcomprobante, "
                            + "p.idtipo_permiso, "
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.fecha_creacion, "
                            + "p.obserbaciones,"
                            + "p.trabajar,"
                            + "dia_libre,"
                            + "idpuesto,"
                            + "id_departamento from permiso p"
                            + " where p.estado < 2;"
                    );
            ResultSet rs = ps.executeQuery();
            ArrayList<permisos> listap = new ArrayList<>();
            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(9));
                d.setObserbaciones(rs.getString(10));
                d.setTrabajar(rs.getInt(11));
                d.setDia_libre(rs.getString(12));
                d.setIdpuesto(rs.getInt(13));
                d.setIdDepartamento(rs.getInt(14));
                listap.add(d);
            }
            con.close();
            if (!listap.isEmpty()) {
                list = listap;
            }
            return list;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<permisos> getParaVacacionesPorFecha(java.util.Date fechaini, java.util.Date fechaFin) {
        ArrayList<permisos> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "	p.idEmpleado, "
                            + "p.id, "
                            + "p.estado, "
                            + "p.idcomprobante, "
                            + "p.idtipo_permiso, "
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.fecha_creacion,"
                            + "p.dia_libre,"
                            + "id_departamento from permiso p"
                            + " where p.fecha_fin > ? "
                            + "and p.idtipo_permiso =1 "
                            + "order by p.fecha_inicio;"
                    );
            ps.setDate(1, new Date(fechaini.getTime()));
            //ps.setDate(2, new Date(fechaFin.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(8));
                d.setDia_libre(rs.getString(9));
                d.setIdDepartamento(rs.getInt(10));
                list.add(d);
            }
            con.close();
            if (list.size() < 1) {
                return null;
            } else {
                return list;
            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<permisos> getParaVacacionesPorFechas(Usuario us, java.util.Date fechaini, java.util.Date fechaFin) {
        ArrayList<permisos> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "p.idEmpleado,"
                            + "p.id,"
                            + "p.estado,"
                            + "p.idComprobante,"
                            + "p.idTipo_permiso,"
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.obserbaciones, "
                            + "p.fecha_creacion,"
                            + "p.dia_libre, "
                            + "p.idpuesto, "
                            + "p.id_departamento "
                            + "from pruebasol.empleado e "
                            + "join gestion_vacaciones_permisos.permiso p "
                            + "on (e.departamento =? and e.puesto =? )"
                            + "and (p.idTipo_permiso = 1 and p.fecha_fin > ?)"
                            + "and (e.idempleado = p.idempleado)"
                            + "order by p.fecha_inicio;"
                    );
            ps.setInt(1, us.getDepartamento());
            ps.setInt(2, us.getIdPuesto());
            ps.setDate(3, new Date(fechaini.getTime()));
            //ps.setDate(2, new Date(fechaFin.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(9));
                d.setDia_libre(rs.getString(10));
                d.setIdpuesto(11);
                d.setIdDepartamento(12);
                list.add(d);
            }
            con.close();
            if (list.size() < 1) {
                return null;
            } else {
                return list;
            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return null;
        }
    }

    public static boolean deltetePermiso(permisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("delete from permiso "
                            + "WHERE `idPermiso` = ?;");
            ps.setInt(1, un.getIdPermiso());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return us;
        }
    }

    public static boolean addPermisoEliminado(permisos un) {
        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO permiso "
                            + "("
                            + "`fecha_inicio`,"
                            + "`fecha_fin`,"
                            + "`idEmpleado`,"
                            + "`id`,"
                            + "`estado`,"
                            + "`idTipo_permiso`,"
                            + "obserbaciones,"
                            + "trabajar, "
                            + "fecha_creacion,"
                            + " dia_libre,"
                            + "idpuesto,"
                            + "idPermiso,"
                            + "id_departamento) "
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
                            + "?,"
                            + "?,"
                            + "?,"
                            + "?);");

            ps.setDate(1, new Date(un.getFecha_inicio().getTime()));
            ps.setDate(2, new Date(un.getFecha_fin().getTime()));
            ps.setInt(3, un.getIdEmpleado());
            ps.setInt(4, un.getId());
            ps.setInt(5, un.getEstado());
            ps.setInt(6, un.getIdTipo_permiso());
            ps.setString(7, un.getObserbaciones());
            ps.setInt(8, un.getTrabajar());
            ps.setDate(9, new Date(un.getFecha_creacion().getTime()));
            ps.setString(10, un.getDia_libre());
            ps.setInt(11, un.getIdpuesto());
            ps.setInt(12, un.getIdPermiso());
            ps.setInt(13, un.getIdDepartamento());
            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public static ArrayList<permisos> getPermisosPorDepartamento(int idDepartamento) {
        ArrayList<permisos> list = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "p.idPermiso,"
                            + "	p.idEmpleado, "
                            + "p.id, "
                            + "p.estado, "
                            + "p.idcomprobante, "
                            + "p.idtipo_permiso, "
                            + "p.fecha_inicio,"
                            + "p.fecha_fin,"
                            + "p.fecha_creacion, "
                            + "p.obserbaciones,"
                            + "p.trabajar,"
                            + "dia_libre,"
                            + "idpuesto, "
                            + "id_departamento from permiso p"
                            + " where p.estado = 0 and p.id_departamento =?;"
                    );
            ps.setInt(1, idDepartamento);
            ResultSet rs = ps.executeQuery();

            ArrayList<permisos> listap = new ArrayList<>();
            while (rs.next()) {
                permisos d = new permisos();
                d.setIdPermiso(rs.getInt(1));
                d.setIdEmpleado(rs.getInt(2));
                d.setId(rs.getInt(3));
                d.setEstado(rs.getInt(4));
                d.setIdComprobante(rs.getInt(5));
                d.setIdTipo_permiso(rs.getInt(6));
                d.setFecha_inicio(rs.getDate(7));
                d.setFecha_fin(rs.getDate(8));
                d.setFecha_creacion(rs.getDate(9));
                d.setObserbaciones(rs.getString(10));
                d.setTrabajar(rs.getInt(11));
                d.setDia_libre(rs.getString(12));
                d.setIdpuesto(rs.getInt(13));
                d.setIdDepartamento(rs.getInt(14));
                listap.add(d);
            }
            con.close();
            if (!listap.isEmpty()) {
                list = listap;
            }
            return list;
        } catch (Exception e) {
            System.out.println("datos.crudPermisos.getPermisosPorDepartamento()");
            System.err.println("error " + e.getMessage());
            return null;
        }
    }

}
