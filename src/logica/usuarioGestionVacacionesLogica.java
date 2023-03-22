/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.usuarioGestionVacaciones;

/**
 *
 * @author programador1
 */
public class usuarioGestionVacacionesLogica {

    public static boolean addUsuarioGestionVacaciones(usuarioGestionVacaciones un) {
        return datos.crudusuarioGestionVacaciones.addUsuarioGestionVacaciones(un);
    }

    public static boolean deleteUsuarioGestionVacaciones(int idEmpleado) {
        return datos.crudusuarioGestionVacaciones.deleteUsuarioGestionVacaciones(idEmpleado);
    }

}
