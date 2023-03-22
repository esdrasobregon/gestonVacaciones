/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.puesto;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class puestoLogica {

    public static ArrayList<puesto> getAllPuestos() {
        return datos.crudPuestos.getAllPuestos();
    }

    public static puesto getPuestoPorId(int idPuesto) {
        return datos.crudPuestos.getPuestoPorId(idPuesto);
    }

    public static ArrayList<puesto> getPuestoPorIdDep(int id_Departamento) {
        return datos.crudPuestos.getPuestoPorIdDep(id_Departamento);
    }
}
