/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Usuario;
import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class UsuarioLogica {

    public static Usuario getUsuario(int id) {

        return datos.crudUsuario.getUsuario(id);
    }

    public static Usuario getUsuario(String name) {
        return datos.crudUsuario.getUsuario(name);
    }

    public static ArrayList<Usuario> getAllUsuariosActivosPorDepartamento(int idDepartamento) {
        return datos.crudUsuario.getAllUsuariosActivosPorDepartamento(idDepartamento);
    }

}
