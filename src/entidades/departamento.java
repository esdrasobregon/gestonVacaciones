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
public class departamento {

    boolean activar;
    String Descripcion;
    int Id_Departamento;
    String Observaciones;
    public ArrayList<puesto> puestos;

    public departamento() {
        this.puestos = new ArrayList<>();
    }

    public departamento(String Descripcion, int Id_Departamento, String Observaciones) {
        this.Descripcion = Descripcion;
        this.Id_Departamento = Id_Departamento;
        this.Observaciones = Observaciones;
    }

    public boolean isActivar() {
        return activar;
    }

    public void setActivar(boolean activar) {
        this.activar = activar;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
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

    public static void loadPuestos(ArrayList<puesto> p, ArrayList<departamento> d) {
        d.forEach(e -> {
            p.forEach(pe -> {
                if (pe.getId_Departamento() == e.getId_Departamento()) {

                    e.puestos.add(pe);
                }
            });
        });
    }

    public static boolean departamentoExist(ArrayList<departamento> departamentos, int d) {
        boolean found = false;
        int count = 0;
        while (count < departamentos.size() && !found) {
            if (departamentos.get(count).getId_Departamento() == d) {
                found = true;
            }
            count++;
        }
        return found;
    }

    public static departamento getDepartamento(ArrayList<departamento> departamentos, String d) {
        departamento dep = null;
        boolean found = false;
        int count = 0;
        while (count < departamentos.size() && !found) {
            if (departamentos.get(count).getDescripcion().equalsIgnoreCase(d)) {
                dep = departamentos.get(count);
                found = true;
            }
            count++;
        }
        return dep;
    }

    public static departamento getDepartamentoPorId(ArrayList<departamento> departamentos, int id) {
        departamento dep = null;
        boolean found = false;
        int count = 0;
        while (count < departamentos.size() && !found) {
            if (departamentos.get(count).getId_Departamento() == id) {
                dep = departamentos.get(count);
                found = true;
            }
            count++;
        }
        return dep;
    }

    /**
     * this function takes a departamento id and then look for it in the list
     * departamentos
     *
     * @param idDepartamento is the id of the departamento to look for
     * @param departamentos its a departamnento list
     * @return a string descripcion of the departamento
     */
    public static String getNombreDepartamento(ArrayList<departamento> departamentos, int idDepartamento) {
        String result = "";
        int count = 0;
        boolean stop = false;
        while (count < departamentos.size() && !stop) {
            if (departamentos.get(count).getId_Departamento() == idDepartamento) {
                result = departamentos.get(count).getDescripcion();
                stop = true;

            }
            count++;
        }
        return result;
    }

}
