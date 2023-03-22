/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.departamento;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class departamentoLog {

    public static ArrayList<departamento> getDepartamentos() {
        return datos.crudDepartamento.getDepartamentos();
    }
     public static departamento getDepartamentosPorId(int id) {
        return datos.crudDepartamento.getDepartamentoId(id);
    }
}
