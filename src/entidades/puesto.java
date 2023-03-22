/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;

/**
 *
 * @author programador1
 */
public class puesto {

    public static boolean puestoExists(ArrayList<puesto> puestos, int idPuesto) {
        boolean found = false;
        int count = 0;
        while (count < puestos.size() && !found) {
            if (puestos.get(count).getId_Puesto() == idPuesto) {
                found = true;
            }
            count++;
        }
        return found;

    }

    boolean activar;

    public boolean isActivar() {
        return activar;
    }

    public void setActivar(boolean activar) {
        this.activar = activar;
    }
    int Id_Puesto;
    String Descripcion;
    int Id_Departamento;

    public puesto() {
    }

    public puesto(int Id_Puesto, String Descripcion, int Id_Departamento) {
        this.Id_Puesto = Id_Puesto;
        this.Descripcion = Descripcion;
        this.Id_Departamento = Id_Departamento;
    }

    public int getId_Puesto() {
        return Id_Puesto;
    }

    public void setId_Puesto(int Id_Puesto) {
        this.Id_Puesto = Id_Puesto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getId_Departamento() {
        return Id_Departamento;
    }

    public void setId_Departamento(int Id_Departamento) {
        this.Id_Departamento = Id_Departamento;
    }

    public static puesto getPuesto(ArrayList<puesto> puestos, String d) {
        puesto p = null;
        boolean found = false;
        int count = 0;
        while (count < puestos.size() && !found) {
            if (puestos.get(count).getDescripcion().equalsIgnoreCase(d)) {
                p = puestos.get(count);
                found = true;
            }
            count++;
        }
        return p;
    }

    /**
     * this function takes a puesto id and then look for it in the list puestos
     *
     * @param puestos is the list
     * @return a string nombre of the puesto
     * @param idpuesto is the puesto id
     */
    public static String getNombrePuesto(ArrayList<puesto> puestos, int idpuesto) {
        String result = "";
        int count = 0;
        boolean stop = false;
        while (count < puestos.size() && !stop) {
            if (puestos.get(count).getId_Puesto() == idpuesto) {
                result = puestos.get(count).getDescripcion();
                stop = true;
            }
            count++;
        }
        return result;
    }

}
