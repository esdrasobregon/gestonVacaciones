/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.util;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author programador1
 */
public class commonPanel {

    public static void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setVisible(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setVisible(isEnabled);
        }
    }

    public static void handleResizePanel(JPanel panel, int ancho, int alto) {
        panel.setPreferredSize(new Dimension(ancho, alto));
        panel.invalidate();
        panel.validate();
        panel.repaint();
    }
}
