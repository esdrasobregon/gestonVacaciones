/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.puesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class crudPuestos {

    public static ArrayList<puesto> getAllPuestos() {

        ArrayList<puesto> list = null;

        try {
            ArrayList<puesto> puestos = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.Id_Departamento, "
                            + "c.Id_Puesto, "
                            + "c.Descripcion "
                            + "from puestos c;"
                    );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                puesto d = new puesto();
                d.setId_Departamento(rs.getInt(1));
                d.setId_Puesto(rs.getInt(2));
                d.setDescripcion(rs.getString(3));
                d.setActivar(false);
                puestos.add(d);
            }
            con.close();
            if (puestos.size() > 0) {
                list = puestos;
            }
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
            return null;
        }
    }

    public static puesto getPuestoPorId(int idPuesto) {
        puesto d = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.Id_Departamento, "
                            + "c.Id_Puesto, "
                            + "c.Descripcion "
                            + "from puestos c "
                            + "where c.Id_Puesto = ?;"
                    );
            ps.setInt(1, idPuesto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d = new puesto();
                d.setId_Departamento(rs.getInt(1));
                d.setId_Puesto(rs.getInt(2));
                d.setDescripcion(rs.getString(3));
                d.setActivar(false);
            }
            con.close();

            return d;

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<puesto> getPuestoPorIdDep(int id_Departamento) {
        ArrayList<puesto> list = null;

        try {
            ArrayList<puesto> puestos = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getSolConnector();
            PreparedStatement ps
                    = con.prepareStatement("select "
                            + "c.Id_Departamento, "
                            + "c.Id_Puesto, "
                            + "c.Descripcion "
                            + "from puestos c"
                            + " where c.Id_Departamento = ? ;"
                    );
            ps.setInt(1, id_Departamento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                puesto d = new puesto();
                d.setId_Departamento(rs.getInt(1));
                d.setId_Puesto(rs.getInt(2));
                d.setDescripcion(rs.getString(3));
                d.setActivar(false);
                puestos.add(d);
            }
            con.close();
            if (puestos.size() > 0) {
                list = puestos;
            }
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
            return null;
        }
    }

}
