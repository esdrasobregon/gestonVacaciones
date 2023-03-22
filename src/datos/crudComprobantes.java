/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.comprobante;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Icon;
import logica.repostory;

/**
 *
 * @author programador1
 */
public class crudComprobantes {

    public static boolean addComprobante(String comprobanteName, String fileRoute) {
        boolean result = false;
        try {
            // directory from where the program was launched
            // e.g /home/mkyong/projects/core-java/java-io

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO comprobante_solicitud (titulo, imagen) VALUES(?,?)");
            pstmt.setString(1, comprobanteName);
            //Inserting Blob type
            InputStream in = new FileInputStream(fileRoute);
            pstmt.setBlob(2, in);
            //Executing the statement
            pstmt.execute();
            System.out.println("Record inserted......");
            result = true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return result;
        }
        return result;
    }

    public static comprobante getComprobante(String comprobanteName) {
        comprobante result = null;
        try {
            // directory from where the program was launched
            // e.g /home/mkyong/projects/core-java/java-io

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            PreparedStatement pstmt = con.prepareStatement("select c.idComprobante "
                    + "from comprobante_solicitud c"
                    + " where titulo = ?");
            pstmt.setString(1, comprobanteName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result = new comprobante();
                result.setIdComprobante(rs.getInt(1));
                result.setTitulo(comprobanteName);
            }
            con.close();
            return result;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return result;
        }

    }

    public static Icon getImagenComprobante(int idComprobante) {
        Icon result = null;
        try {
            // directory from where the program was launched
            // e.g /home/mkyong/projects/core-java/java-io
            String ruta = repostory.getComprobantesPath() + idComprobante + ".jpeg";
            File file = new File(ruta);
            FileOutputStream fos = new FileOutputStream(file);
            byte b[];
            Blob blob;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            String query = "select imagen from comprobante_solicitud where idComprobante = ?";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setLong(1, idComprobante);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                blob = rs.getBlob("imagen");
                b = blob.getBytes(1, (int) blob.length());
                fos.write(b);
            }
            fos.close();
            con.close();
            return result;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return result;
        }

    }

    public static comprobante getComprobante(int idComprobante) {
        comprobante result = new comprobante();
        try {
            // directory from where the program was launched
            // e.g /home/mkyong/projects/core-java/java-io

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            String query = "select s.titulo, s.imagen from comprobante_solicitud s where idComprobante = ?";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setLong(1, idComprobante);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                result.setIdComprobante(idComprobante);
                result.setTitulo(rs.getString(1));
                File file = new File(repostory.getImagesPath() + "test.jpeg");
                FileOutputStream fos = new FileOutputStream(file);
                byte b[];
                Blob blob;
                blob = rs.getBlob("imagen");
                b = blob.getBytes(2, (int) blob.length());
                fos.write(b);
                fos.close();
            }

            con.close();
            return result;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return result;
        }

    }

}
