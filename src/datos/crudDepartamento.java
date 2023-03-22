/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.departamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class crudDepartamento {

    public static ArrayList<departamento> getDepartamentos() {
        ArrayList<departamento> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.Id_Departamento, "
                            + "c.Observaciones, "
                            + "c.Descripcion "
                            + "from departamentos c;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departamento d = new departamento();
                d.setId_Departamento(rs.getInt(1));
                d.setObservaciones(rs.getString(2));
                d.setDescripcion(rs.getString(3));
                d.setActivar(false);
                list.add(d);
            }
            con.close();
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
            return null;
        }
    }

    public static departamento getDepartamentoId(int id) {
        departamento d = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.Id_Departamento, "
                            + "c.Observaciones, "
                            + "c.Descripcion "
                            + "from departamentos c"
                            + " where c.Id_Departamento =?;"
                    );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d = new departamento();
                d.setId_Departamento(rs.getInt(1));
                d.setObservaciones(rs.getString(2));
                d.setDescripcion(rs.getString(3));
                d.setActivar(false);
            }
            con.close();
            return d;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
            return null;
        }
    }

}
