/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class informationMessages {

    public final static String ADMINPERMISOSGUIDE = "Ventana Información de permisos" + "\n"
            + " Para procesar una solicitud:\n"
            + "     1. Seleccione el departamento\n"
            + "     2. Seleccione el tipo de permiso\n"
            + "     3. De las opciones de estado seleccione pendientes o vistos\n"
            + "     4. Seleccione un nombre de la lista de permisos\n"
            + "     5. Seleccione Editar, Aprobar, Eliminar, etc, según la necesidad";

    public final static String VENTSOLICITUDESGUIDE = "Ventana RESTRICCIONES" + "\n"
            + " Para guardar una restricción:\n"
            + "     1. Seleccione el departamento\n"
            + "     2. Seleccione el puesto\n"
            + "     3. Seleccione un tipo de restricción\n"
            + "     4. Ingrese las fechas o la cantidad\n"
            + "     5. Seleccione guardar";
    public final static String VENTHISTORIALGUIDE = "Ventana Historial de permisos" + "\n"
            + " Para ver un historia de solicitudes de un empleado:\n"
            + "     1. Ingrese el código de empleado o el nombre completo sin errores\n"
            + "     2. Enter o click al botón Buscar\n"
            + "     3. Seleccione uno de los registros de la tabla\n";
    public final static String VENTUSUARIOSGUIDE = "Ventana Usuarios" + "\n"
            + " Para agregar o eliminar un encargado de departamento:\n"
            + "     1. Seleccione un departamento y espere a que se muestre la información\n"
            + "     2. Dele check o elimine el check de la lista, según la necesidad\n"
            + "     3. Guarde los cambios\n";
    public final static String VENTFORMULARIOGUIDE = "Ventana Formulario de solicitudes" + "\n"
            + " Para realizar una solicitud:\n"
            + "     1. Completar correctamente el formulario\n"
            + "     2. Presionar botón enviar\n"
            + " Para editar una solicitud:\n"
            + "     1. Realizar los cambios necesarios en el formulario\n"
            + "     2. Click al botón Aprobar, Denegar o Visto";

    public final static String VENTADMINPERMISOSINFO = "Ventana Información de permisos" + "\n"
            + " En esta ventana puede administrar las solicitudes pendientes y vistas";
    public final static String VENTRESTRICCIONESINFO = "Ventana RESTRICCIONES" + "\n"
            + " En esta ventana puede agregar una restricción a las solicitudes de un determinado puesto de un departamento\n";
    public final static String VENTHISTORIALINFO = "Ventana Historial de permisos" + "\n"
            + " En esta ventana puede visualizar el historial de solicitudes de un empleado\n";
    public final static String VENTUSUARIOS = "Ventana Usuarios" + "\n"
            + " En esta ventana puede agregar o eliminar un encargado de departamento\n";
    public final static String FORMSOLICITUDESINFO = "Ventana Formulario de solicitudes" + "\n"
            + " En esta ventana puede realizar solicitudes de permiso, o administrar solicitudes de otros usuarios\n";

    public static void getVentInfoMessage(int index, boolean encargado, boolean miPermiso) {
        switch (index) {
            case 0:
                if (miPermiso) {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.FORMSOLICITUDESINFO);
                } else {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.VENTADMINPERMISOSINFO);
                }
                break;
            case 1:
                if (encargado) {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.FORMSOLICITUDESINFO);
                } else {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.VENTRESTRICCIONESINFO);
                }
                break;
            case 2:
                JOptionPane.showMessageDialog(null, logica.informationMessages.VENTHISTORIALINFO);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, logica.informationMessages.VENTUSUARIOS);
                break;
            case 4:
                JOptionPane.showMessageDialog(null, logica.informationMessages.FORMSOLICITUDESINFO);
                break;
            default:
                break;
        }
    }

    public static void getVentGuideMessage(int index, boolean encargado, boolean miPermiso) {
        switch (index) {
            case 0:
                if (miPermiso) {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.VENTFORMULARIOGUIDE);
                } else {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.ADMINPERMISOSGUIDE);
                }
                break;
            case 1:
                if (encargado) {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.VENTFORMULARIOGUIDE);
                } else {
                    JOptionPane.showMessageDialog(null, logica.informationMessages.VENTSOLICITUDESGUIDE);
                }
                break;
            case 2:
                JOptionPane.showMessageDialog(null, logica.informationMessages.VENTHISTORIALGUIDE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, logica.informationMessages.VENTUSUARIOSGUIDE);
                break;
            case 4:
                JOptionPane.showMessageDialog(null, logica.informationMessages.VENTFORMULARIOGUIDE);

                break;
            default:
                break;
        }
    }

}
