/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.tipoPermisos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class crudTipoPermisos {

    public static ArrayList<tipoPermisos> getTipoPermisos() {
        ArrayList<tipoPermisos> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "idTipo_permiso, "
                            + "descripcion from tipo_permisos"
                            + " order by idTipo_permiso;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipoPermisos d = new tipoPermisos();
                d.setIdTipo_permiso(rs.getInt(1));
                d.setDescripcion(rs.getString(2));
                list.add(d);
            }
            con.close();
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
            return null;
        }
    }

}
