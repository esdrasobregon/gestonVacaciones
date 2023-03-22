/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author programador1
 */
public class credenciales {

    public static boolean logIn(String nombreUsuario, String contrasenya) {
        boolean result = false;
        String url = "jdbc:mysql://localhost:3306/";
        try {
            Connection con = DriverManager.getConnection(
                    url + "gestion_vacaciones_permisos",
                    nombreUsuario,
                    contrasenya);
            result = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    /**
     * use this function when calling a gestion_vacaciones_permisos table
     */
    public static Connection getConnector() {
        String usuario = "root";
        String contrasenya = "admin";
        String url = "jdbc:mysql://localhost:3306/";
        String db = "gestion_vacaciones_permisos";
        try {
            Connection con = DriverManager.getConnection(
                    url + db,
                    usuario,
                    contrasenya);
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * use this function when calling a sol table
     */
    public static Connection getSolConnector() {
        String usuario = "root";
        String contrasenya = "admin";
        String url = "jdbc:mysql://localhost:3306/";
        String db = "pruebasol";
        try {
            Connection con = DriverManager.getConnection(
                    url + db,
                    usuario,
                    contrasenya);
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
