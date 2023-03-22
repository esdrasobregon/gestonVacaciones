/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.components;

import entidades.departamento;
import java.util.ArrayList;
import javax.swing.JScrollBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author programador1
 */
public class departamentosTree extends JScrollBar {

    JTree departamentos;
    DefaultMutableTreeNode dep;

    public departamentosTree() {
        DefaultMutableTreeNode style = new DefaultMutableTreeNode("Style");
        DefaultMutableTreeNode color = new DefaultMutableTreeNode("color");
        DefaultMutableTreeNode font = new DefaultMutableTreeNode("font");
        style.add(color);
        style.add(font);
        DefaultMutableTreeNode red = new DefaultMutableTreeNode("red");
        DefaultMutableTreeNode blue = new DefaultMutableTreeNode("blue");
        DefaultMutableTreeNode black = new DefaultMutableTreeNode("black");
        DefaultMutableTreeNode green = new DefaultMutableTreeNode("green");
        color.add(red);
        color.add(blue);
        color.add(black);
        color.add(green);
        JTree jt = new JTree(style);
        this.add(jt);

    }

    public void setDepartamentos(ArrayList<departamento> listdepartamentos) {
        listdepartamentos.forEach(e -> {
            this.dep.add(new DefaultMutableTreeNode(e.getDescripcion()));
            System.out.println("departamaneto: " + e.getDescripcion());

        });
        this.departamentos = new JTree(dep);
    }
}
