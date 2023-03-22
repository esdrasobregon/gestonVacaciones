/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Usuario;
import javax.swing.JLabel;

/**
 *
 * @author programador1
 */
public class connection implements Runnable {

    private int idEmpleado;
    private JLabel lbServer;
    private boolean continueProcess;

    public connection(int idEmpleado, JLabel lbServer) {
        this.idEmpleado = idEmpleado;
        this.lbServer = lbServer;
        this.continueProcess = true;
    }

    public void terminateProcess() {
        System.err.println("logica.connection.terminateProcess()");
        this.continueProcess = false;
    }

    @Override
    public void run() {
        while (continueProcess) {
            try {
                //your code
                Thread.sleep(10 * 1000);//this is poll interval
                Usuario us = logica.UsuarioLogica.getUsuario(idEmpleado);
                if (us != null) {
                    System.err.println("usser " + us.getNombre() + " " + us.getApellido1() + " connected");
                    lbServer.setToolTipText("Servidor conectado");

                }
            } catch (InterruptedException e) {
                System.err.println(".run()" + e.getMessage());
                System.err.println("usser not connected");
                lbServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-server-shutdown-20.png"))); // NOI18N
                lbServer.setToolTipText("Servidor desconectado");
            }
        }
    }

}
