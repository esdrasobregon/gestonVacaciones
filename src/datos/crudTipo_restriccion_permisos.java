/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.tipo_restriccion_permisos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class crudTipo_restriccion_permisos {

    public static ArrayList<tipo_restriccion_permisos> getAllTipos() {
        ArrayList<tipo_restriccion_permisos> list = null;

        try {
            ArrayList<tipo_restriccion_permisos> puestos = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.idtipo_restriccion_permisos, "
                            + "c.decripcion "
                            + "from tipo_restriccion_permisos c;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipo_restriccion_permisos d = new tipo_restriccion_permisos();
                d.setIdtipo_restriccion_permisos(rs.getInt(1));
                d.setDecripcion(rs.getString(2));
                puestos.add(d);
            }
            con.close();
            if (puestos.size() > 0) {
                list = puestos;
            }
            return list;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "error " + e.getMessage());
            System.out.println("datos.crudTipo_restriccion_permisos.getAllTipos()");
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    
}
