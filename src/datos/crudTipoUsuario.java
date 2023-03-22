/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.tipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class crudTipoUsuario {

    public static ArrayList<tipoUsuario> getAllTipoUsuarios() {

        ArrayList<tipoUsuario> list = null;

        try {
            ArrayList<tipoUsuario> puestos = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            PreparedStatement ps
                    = con.prepareStatement("SELECT "
                            + "`tipo_usuario`.`idtipo_usuario`,"
                            + "`tipo_usuario`.`descripcion` "
                            + "FROM `gestion_vacaciones_permisos`.`tipo_usuario`;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipoUsuario d = new tipoUsuario();
                d.setIdTipoUsuario(rs.getInt(1));
                d.setDescripcion(rs.getString(2));
                puestos.add(d);
            }
            con.close();
            if (puestos.size() > 0) {
                list = puestos;
            }
            return list;
        } catch (Exception e) {
            System.out.println("error gettin tipo usuario " + e.getMessage());
            return null;
        }
    }

}
