/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class dbSettings {

    public static boolean createTbAuditoria_permiso() {
        return datos.dbStructure.createTbAuditoria_permiso();
    }

    public static boolean checkbGestionVacaciones(String dbname) {
        return datos.dbStructure.checkbGestionVacaciones(dbname);
    }

    public static boolean createDbGestionVacaciones() {
        return datos.dbStructure.createDbGestionVacaciones();
    }

    public static boolean createDbstructure() {
        boolean result = false;
        System.out.println("logica.dbSettings.createDbstructure()");
        System.out.println("creating table id tipo permisos");
        datos.dbStructure.createTbTipo_permisos();
        System.out.println("inserting tipos permiso");
        datos.dbStructure.addDefaultTipo_permisos();
        System.out.println("creating table permisos");
        datos.dbStructure.createTbPermisos();
        System.out.println("creating table comprobante soilicitudes");
        datos.dbStructure.createTbComprobanteSolicitud();
        System.out.println("creating table auditoria de permisos");
        datos.dbStructure.createTbAuditoria_permiso();
        System.out.println("creating tipo de restricciones de permisos");
        datos.dbStructure.createIdtipo_restriccion_permisos();
        System.out.println("adding default values for tipo restricciones");
        datos.dbStructure.addDefaultIdRestriccionPermisos();
        System.out.println("creating table restriccion de permisos");
        datos.dbStructure.createTbrestriccion_permisos();
        System.out.println("database gestion permisos ready");
        System.out.println("creating tipo usuario table and defoult values");
        datos.dbStructure.createTbTipo_usuario();
        datos.dbStructure.addDefaultTipo_usuario();
        System.out.println("tipo usuario table created");
        System.out.println("creating gestionvacaciones db table");
        datos.dbStructure.createTbUsuariogestionvacaciones();
        System.out.println("gestionvacaciones table created");
        result = true;
        return result;
    }
}
