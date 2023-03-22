/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import logica.funcionesComunes;

/**
 *
 * @author programador1
 */
public class videoFrame extends JFrame {
    
    BufferedImage imagenComprobante;
    boolean imageTaked;
    public Webcam webcam;
    public JButton b;
    private WindowAdapter windowAdapter;
    
    public videoFrame() {
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/images/icons8-traveler-30.png"));
        this.setTitle("Comprobante solicitud");
        setIconImage(icon.getImage());
        windowAdapter = new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                closeCamera();
            }
        };
        addWindowFocusListener(new WindowFocusListener() {
            int c = 0;
            
            @Override
            public void windowLostFocus(WindowEvent e) {
                closeCamera();
                dispose();
                
            }
            
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println(".windowGainedFocus()");
                
            }
        });
        JPanel pnlAcction = new JPanel();
        pnlAcction.setLayout(new FlowLayout());
        this.imageTaked = false;
        b = new JButton();
        b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-camera-30.png")));
        b.setBackground(new java.awt.Color(204, 255, 255));
        
        vista.util.botonComun.mouseHoverListener(b);
        
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        
        JPanel center = new JPanel();
        Border border = new LineBorder(new java.awt.Color(153, 153, 153), 2, true);
        center.setBorder(border);
        center.add(panel);
        center.setVisible(true);
        center.revalidate();
        center.repaint();
        
        pnlAcction.add(b);
        this.add(pnlAcction, BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.addWindowListener(windowAdapter);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
    }
    
    public void closeCamera() {
        this.webcam.close();
    }
    
    public BufferedImage getBufferedImage() {
        return this.imagenComprobante;
    }
    
    public boolean isImageTaked() {
        return this.imageTaked;
    }
    
    public boolean takeImage() {
        
        this.imagenComprobante = funcionesComunes.takeAPhoto(this.webcam);
        this.imageTaked = true;
        return imageTaked;
    }
    
}
