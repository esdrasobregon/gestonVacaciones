/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author programador1
 */
public class dbStructure {

    public static boolean createDbGestionVacaciones() {
        // Open a connection
        boolean result = false;
        String usuario = "root";
        String contrasenya = "admin";
        String url = "jdbc:mysql://localhost:3306/";

        try ( Connection conn = DriverManager.getConnection(url, usuario, contrasenya);  Statement stmt = conn.createStatement();) {
            String sql = "CREATE  DATABASE if not exists `gestion_vacaciones_permisos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
            stmt.close();
            result = true;
            return result;
        } catch (SQLException e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean checkbGestionVacaciones(String dbname) {

        boolean result = false;
        String usuario = "root";
        String contrasenya = "admin";
        String url = "jdbc:mysql://localhost:3306/";
        String sql = "SELECT schema_name FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME =?";
        try ( Connection conn = DriverManager.getConnection(url, usuario, contrasenya);) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dbname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Database located successfully...");
                result = true;
            }
            rs.close();
            return result;
        } catch (SQLException e) {
            System.out.println("error " + e.getMessage());
            return false;
        }

    }

    public static boolean createDbGestionVacaciones01() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE  DATABASE if not exists `gestion_vacaciones_permisos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;");

            ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbTipo_permisos() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE if not exists `tipo_permisos` ("
                            + "  `idTipo_permiso` int NOT NULL AUTO_INCREMENT,"
                            + "  `descripcion` varchar(255) NOT NULL,"
                            + "  PRIMARY KEY (`idTipo_permiso`),"
                            + "  UNIQUE KEY `descUniq` (`descripcion`)"
                            + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean addDefaultTipo_permisos() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO `gestion_vacaciones_permisos`.`tipo_permisos`\n"
                            + "(idTipo_permiso,"
                            + "`descripcion`)"
                            + "VALUES"
                            + "(1,'VACACIONES'),"
                            + "(2,'PERMISO SIN GOCE DE SALARIO'),"
                            + "(3,'VACACIONES (medio día)'),"
                            + "(4,'CITA SIN GOCE DE SALARIO'),"
                            + "(5,'CITA MEDICA'),"
                            + "(6,'CITA MIGRACION'),"
                            + "(7,'CITA JUZGADO'),"
                            + "(8,'CITA LICENCIA'),"
                            + "(9,'CAMBIO DIA LIBRE');");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbPermisos() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE if not exists `permiso` ("
                            + "  `idPermiso` int NOT NULL AUTO_INCREMENT,"
                            + "  `fecha_creacion` date NOT NULL,"
                            + "  `fecha_inicio` date NOT NULL,"
                            + "  `fecha_fin` date NOT NULL,"
                            + "  `idEmpleado` int NOT NULL,"
                            + "  `id` int NOT NULL,"
                            + "  `estado` int NOT NULL,"
                            + "  `idComprobante` int DEFAULT NULL,"
                            + "  `idTipo_permiso` int DEFAULT NULL,"
                            + "  `trabajar` int DEFAULT '-1',"
                            + "  `dia_libre` varchar(15) NOT NULL,"
                            + "  `obserbaciones` varchar(50) DEFAULT NULL,"
                            + "  `idpuesto` int NOT NULL,"
                            + "   id_departamento int not null,"
                            + "  PRIMARY KEY (`idPermiso`),"
                            + "  KEY `idTipo_permiso` (`idTipo_permiso`),"
                            + "  CONSTRAINT `permiso_ibfk_1` FOREIGN KEY (`idTipo_permiso`) REFERENCES `tipo_permisos` (`idTipo_permiso`)"
                            + ") ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbAuditoria_permiso() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE if not exists `auditoria_permiso` ("
                            + "  `idauditoria_permiso` int NOT NULL AUTO_INCREMENT,"
                            + "  `idPermiso` int NOT NULL,"
                            + "  `id_tipo_permiso` int NOT NULL,"
                            + "  `fecha_modificacion` date NOT NULL,"
                            + "  `fecha_creacion_permiso` date NOT NULL,"
                            + "  `idEmpleado_responsable` int NOT NULL,"
                            + "  `id_responsable` int NOT NULL,"
                            + "  `idEmpleado_solicitante` int NOT NULL,"
                            + "   id_solicitante int NOT NULL,"
                            + "  `anterior_estado_permiso` int NOT NULL,"
                            + "  `nuevo_estado_permiso` int NOT NULL,"
                            + "  PRIMARY KEY (`idauditoria_permiso`)"
                            + ") ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbComprobanteSolicitud() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE if not exists `comprobante_solicitud` ("
                            + "  `idComprobante` int unsigned NOT NULL AUTO_INCREMENT,"
                            + "  `titulo` varchar(45) NOT NULL,"
                            + "  `imagen` longblob NOT NULL,"
                            + "  PRIMARY KEY (`idComprobante`),"
                            + "  UNIQUE KEY `tituloUnq` (`titulo`)"
                            + ") ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createIdtipo_restriccion_permisos() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE if not exists`tipo_restriccion_permisos` ("
                            + "  `idtipo_restriccion_permisos` int NOT NULL AUTO_INCREMENT,"
                            + "  `decripcion` varchar(15) DEFAULT NULL,"
                            + "  PRIMARY KEY (`idtipo_restriccion_permisos`)"
                            + ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean addDefaultIdRestriccionPermisos() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("insert into tipo_restriccion_permisos"
                            + "(idtipo_restriccion_permisos,decripcion)"
                            + "VALUES"
                            + "(1,'POR FECHAS'),"
                            + "(2,'POR CANTIDAD');");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbrestriccion_permisos() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE if not exists `restriccion_permisos` ("
                            + "  `idrestriccion_permisos` int NOT NULL AUTO_INCREMENT,"
                            + "  `idtipo_restriccion_permisos` int NOT NULL,"
                            + "  `fecha_creacion` date NOT NULL,"
                            + "  `iddepartamento` int DEFAULT NULL,"
                            + "  `idpuesto` int DEFAULT NULL,"
                            + "  `fecha_inicio` date DEFAULT NULL,"
                            + "  `fecha_fin` date DEFAULT NULL,"
                            + "  `idEmpleado` int NOT NULL,"
                            + "  `id` int NOT NULL,"
                            + "  `vigente` tinyint NOT NULL,"
                            + "  `cantidad_maxima` int DEFAULT NULL,"
                            + "  PRIMARY KEY (`idrestriccion_permisos`),"
                            + "  KEY `idtipo_restriccion_permisos` (`idtipo_restriccion_permisos`),"
                            + "  CONSTRAINT `restriccion_permisos_ibfk_1` FOREIGN KEY (`idtipo_restriccion_permisos`) REFERENCES `tipo_restriccion_permisos` (`idtipo_restriccion_permisos`)"
                            + ") ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbTipo_usuario() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE `tipo_usuario` ("
                            + "  `idtipo_usuario` int NOT NULL AUTO_INCREMENT,"
                            + "  `descripcion` varchar(20) NOT NULL,"
                            + "  PRIMARY KEY (`idtipo_usuario`),"
                            + "  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)"
                            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean addDefaultTipo_usuario() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("insert into tipo_usuario(idtipo_usuario, descripcion)"
                            + "VALUES"
                            + "(1,'Secretaria/o'),"
                            + "(2,'Jefes');");

            us = ps.execute();
            con.close();
            us = true;
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }

    public static boolean createTbUsuariogestionvacaciones() {

        boolean us = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = credenciales.getConnector();

            //here sonoo is database name, root is username and password 
            PreparedStatement ps
                    = con.prepareStatement("CREATE TABLE `usuariogestionvacaciones` ("
                            + "  `idusuarioGestionVacaciones` int NOT NULL AUTO_INCREMENT,"
                            + "  `idEmpleado` int NOT NULL,"
                            + "  `id` int NOT NULL,"
                            + "  `fecha_creacion` date NOT NULL,"
                            + "  `fecha_modificacion` date NOT NULL,"
                            + "  `estado` tinyint NOT NULL,"
                            + "  `iddepartamento` int NOT NULL,"
                            + "  `idtipo_usuario` int NOT NULL,"
                            + "  PRIMARY KEY (`idusuarioGestionVacaciones`),"
                            + "  UNIQUE KEY `idEmpleado_UNIQUE` (`idEmpleado`),"
                            + "  UNIQUE KEY `id_UNIQUE` (`id`),"
                            + "  KEY `idtipo_usuarioFK1_idx` (`idtipo_usuario`),"
                            + "  CONSTRAINT `idtipo_usuarioFK1` FOREIGN KEY (`idtipo_usuario`) REFERENCES `tipo_usuario` (`idtipo_usuario`)"
                            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            us = ps.execute();
            con.close();
            return us;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return false;
        }
    }
}
