/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import com.github.sarxos.webcam.Webcam;
import com.toedter.calendar.JDateChooser;
import entidades.permisos;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JTextField;
import java.io.File;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.time.ZoneId;
import javax.swing.JOptionPane;

/**
 *
 * @author programador1
 */
public class funcionesComunes {

    public static void justNumbers(JTextField txt, int maxLength) {
        txt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // if it's not a number, ignore the event
                }
                if (txt.getText().length() >= maxLength) // limit to 3 characters
                {
                    e.consume();
                }
            }
        });

    }

    public static void setTxtLength(JTextField txt, int maxLength) {
        txt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (txt.getText().length() >= maxLength) // limit to 3 characters
                {
                    e.consume();
                }
            }
        });

    }

    /**
     * this function deletes the files in the directory where is the parameter
     * path says
     *
     * @return a true if the proccess is successfully, false if is not
     * @param path is the direction given
     */
    public static boolean clearDirectry(String path) {

        boolean res = false;
        try {
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            res = true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        return res;
    }

    /**
     * this function creates a default directory
     *
     * @return a true if the proccess is successfull, a false if it is not
     * @param route is the path for the directory to be created
     */
    public static boolean createDirectory(String route) {
        boolean res = false;

        Path path = Paths.get(route);

        try {
            if (!Files.exists(path)) {

                Files.createDirectory(path);
                System.out.println("Directory" + route
                        + " created");
                res = true;
            } else {

                System.out.println("Directory already exists");
            }
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            res = false;
        }
        return res;
    }

    /**
     * this function checks wether a directory exists
     *
     * @return a true if exists, false if it is not
     * @param fileName is the direction for the file or directory to be look for
     */
    public static boolean checkDirectory(String fileName) {
        boolean res = false;
        try {

            Path path = Paths.get(fileName);

            if (Files.exists(path)) {
                res = true;
            }

        } catch (Exception e) {
            System.out.println("logica.funcionesComunes.checkComprobantesDirectory()");
            System.out.println("error: " + e.getMessage());
        }
        return res;
    }

    /**
     * this function deletes a file which route is pass as a parameter
     *
     * @param filePath is the url route to the file to delete
     * @return a boolean true if the file is deleted successfilly and a false if
     * is not deleted
     */
    public static boolean deleteFile(String filePath) {
        boolean result = false;
        try {

            File myObj = new File(filePath);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
                result = true;
            } else {
                System.out.println("Failed to delete the file.");
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * this function deletes the files in a directory, but not the directories
     * in it
     *
     * @param dirPath is the direction for the directory to look for
     * @return a true if the proccess is successfull, false if it is not
     */
    public static boolean deleteFiles(File dirPath) {
        boolean res = false;
        try {
            File filesList[] = dirPath.listFiles();
            for (File file : filesList) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteFiles(file);
                }
            }
            res = true;
        } catch (Exception e) {
            System.out.println("error deleting the files " + e.getMessage());
        }
        return res;
    }

    /**
     * this function uses the computer web cam to take a photo
     *
     * @return BufferedImage object or null if the image is not saved
     */
    public static BufferedImage takeAPhoto() {
        // get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        // get imagenComprobante
        BufferedImage imagenComprobante = webcam.getImage();
        webcam.close();

        // save imagenComprobante to PNG file
        try {
            ImageIO.write(imagenComprobante, "jpeg", new File("C:\\imagenes_gestion_vacaciones/test.jpeg"));

            return imagenComprobante;
        } catch (Exception e) {
            System.out.println("logica.funcionesComunes.takeAPhoto()");
            System.out.println(e.toString());
            return null;
        }

    }

    /**
     * this function uses the computer web cam to take a photo
     *
     * @param webcam is an instance of the webcam
     * @return BufferedImage object or null if the image is not saved
     */
    public static BufferedImage takeAPhoto(Webcam webcam) {
        // get default webcam and open it

        // get imagenComprobante
        BufferedImage imagenComprobante = webcam.getImage();

        // save imagenComprobante to PNG file
        try {
            ImageIO.write(imagenComprobante, "jpeg", new File("C:\\imagenes_gestion_vacaciones/test.jpeg"));

            return imagenComprobante;
        } catch (Exception e) {
            System.out.println("logica.funcionesComunes.takeAPhoto()");
            System.out.println(e.toString());
            return null;
        }

    }

// <editor-fold defaultstate="collapsed" desc="Datefunctios">
    /**
     * this function compares an start and dates
     *
     * @return -1 if the start date is before end, 1 if the end after, and 0 if
     * they are equals
     */
    public static int compareIniFinDates(Date ini, Date fin) {
        int result = 0;
        if (ini.before(fin)) {
            result = -1;
        } else if (ini.after(fin)) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * this function compares an start and dates
     *
     * @return -1 if the start date is before end 1 if the end before and 0 if
     * they are equals
     */
    public static int getDifferenceIniFinDates(Date ini, Date fin) {
        int result = 0;
        long lo = ChronoUnit.DAYS.between(ini.toInstant(), fin.toInstant());
        System.out.println("lapso: " + lo);
        result = (int) lo;
        return result;
    }

    /**
     * this function compares an start and dates
     *
     * @return -1 if the start date is before end 1 if the end before and 0 if
     * they are equals
     */
    public static String getCommunDates(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String result = format.format(date);
        return result;
    }

    public static long dayDifInDates(Date ini, Date fin) {
        long result = 0;
        long dateBeforeInMs = ini.getTime();
        long dateAfterInMs = fin.getTime();

        long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);

        result = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        return result;
    }

    public static Date getFechasFormatyyyyMMdd(Date d) {
        String pattern = "yyyy-MM-dd";
        Format f = new SimpleDateFormat(pattern);
        String strDate = f.format(d);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(strDate);
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean fechasRepetidas(Date d, ArrayList<permisos> listaPer) {
        System.out.println("fecha: " + d.toString());
        boolean result = false;
        try {

            Date date = getFechasFormatyyyyMMdd(d);

            int count = 0;
            boolean found = false;
            while (!found && count < listaPer.size()) {

                permisos p = listaPer.get(count);
                if (date.equals(p.getFecha_inicio()) || date.equals(p.getFecha_fin())) {
                    found = true;
                    result = true;
                } else {
                    if (date.after(p.getFecha_inicio()) && date.before(p.getFecha_fin())) {
                        found = true;
                        result = true;
                    }
                }
                count++;
            }

        } catch (Exception e) {
            System.out.print("error: " + e.getMessage());
        }
        return result;
    }

    public static Date getFechaFromDtChooser(JDateChooser dtFechaInicio) {
        Date d = null;
        try {
            d = new Date(dtFechaInicio.getDate().getYear(), dtFechaInicio.getDate().getMonth(), dtFechaInicio.getDate().getDate());
        } catch (Exception e) {
            System.out.println("logica.funcionesComunes.getFechaFromDtChooser()");
            System.out.println("error "+e.getMessage());
        }
        return d;
    }

    public static Date getDateFromLocalDate(LocalDate dt) {
        return Date.from(dt.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate calcNextTuesday() {
        LocalDate dt = LocalDate.now();
        return dt.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
    }

    /**
     * this function rerurns the next date when the day given
     *
     * @param day is the string day name, the days names have to be in spanish
     * @return the next date with the day name given
     */
    public static Date calcNextNameDayDate(String day) {
        LocalDate dt = LocalDate.now();
        LocalDate result = null;
        switch (day) {
            case "lunes":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                break;
            case "martes":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                break;
            case "miercoles":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                break;
            case "jueves":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                break;
            case "viernes":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                break;
            case "sabado":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                break;
            case "domingo":
                result = dt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                break;
        }

        //System.out.println("\nNext tuesday: " + dt.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)));
        return Date.from(result.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

//</editor-fold>
}
