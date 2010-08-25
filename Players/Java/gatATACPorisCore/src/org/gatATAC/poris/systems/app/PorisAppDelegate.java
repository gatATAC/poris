/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.systems.app;

import org.gatATAC.poris.systems.Cfg;
import org.gatATAC.poris.systems.SNode;
import org.gatATAC.poris.systems.SNodeLib;
import org.gatATAC.poris.systems.SubSystem;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.gatATAC.poris.systems.formatters.ValueDateFormatter;
import org.gatATAC.poris.systems.formatters.ValueDoubleFormatter;
import org.gatATAC.poris.systems.formatters.ValueFormatter;
import org.gatATAC.poris.systems.formatters.ValueIntegerFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author txinto
 */
public class PorisAppDelegate {

    private SNodeLib systemLib = new SNodeLib("System Library");
    private SNodeLib configLib = new SNodeLib("Config Library");
    private SubSystem system;
    private Cfg config;
    private final String instrumentFileName;
    private boolean modelLoaded;
    public static ValueDoubleFormatter DEFAULT_DOUBLE_FORMATTER = new ValueDoubleFormatter("real", "real", 1);
    public static ValueDoubleFormatter ANGLE_FORMATTER = new ValueDoubleFormatter("angle", "angle", 4);
    public static ValueDoubleFormatter S_FORMATTER = new ValueDoubleFormatter("s", "s", 5);
    public static ValueDateFormatter DEFAULT_DATE_FORMATTER = new ValueDateFormatter("Date", "dd.MM.yyyy HH:mm:ss z", 6);
    public static ValueIntegerFormatter DEFAULT_INTEGER_FORMATTER = new ValueIntegerFormatter("integer", "#", 7);
    public static ValueFormatter DEFAULT_STRING_FORMATTER = null;

    public PorisAppDelegate(String instrumentFileName) {
        this.instrumentFileName = instrumentFileName;
        this.modelLoaded = false;
        this.packageInit();
        this.loadModel();
    }

    protected void packageInit() {
    }

    public Cfg getConfig() {
        return config;
    }

    public SNodeLib getConfigLib() {
        return configLib;
    }

    public boolean isModelLoaded() {
        return modelLoaded;
    }

    public SubSystem getSystem() {
        return system;
    }

    public SNodeLib getSystemLib() {
        return systemLib;
    }

    private boolean initialization(String fileName) {
        // Add XML document element to modelToLoad
        try {
            systemLib.clear();
            configLib.clear();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            Element root = doc.getDocumentElement();
            systemLib.fromXML(root);
            //System.out.println("Total de " + sLib.size() + " instancias cargadas");
            system = (SubSystem) systemLib.last();
            //System.out.println("El objeto modelo es " + s);
            //System.out.print(" y");
            if (!system.isConsistent()) {
                //System.out.print(" No");
            }
            //System.out.println(" es consistente");
            //System.out.println("Sus valores son " + ((SubSystem) s).getValues());
            //System.out.println("Sus hijos son " + ((SubSystem) s).getSubSystems());
            //System.out.println("Sus modos son " + ((SubSystem) s).getModes());
            config = new Cfg(system);
            ArrayList<SNode> aux = new ArrayList();
            if (config.subTree(aux)) {
                configLib.addSNodes(aux);
            }
        } catch (Exception exc) {
            System.err.println("MainOPMSApp2.initialization [" + exc.getLocalizedMessage() + "]");
        }
        return true;
    }

    private boolean loadModel() {
        if (!this.isModelLoaded()) {
            boolean exists = (new File(instrumentFileName)).exists();
            if (exists) {
                // Model file exists
                if (this.initialization(instrumentFileName)) {
                    this.modelLoaded = true;
                    return true;
                } else {
                    return false;
                }
            } else {
                // Model file does not exist
                JOptionPane.showMessageDialog(null, "There is not a " + instrumentFileName + " file in the directory");
                return false;
            }
        } else {
            return true;
        }
    }

    public void discardChanges() {
        // Put your code here
    }

    public boolean commitChanges() {
        int ret = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere cambiar los parámetros de la observación?", "Cambio de observación", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String instrumentFileName= "instrument.xml";
        String configFileName= "config.xml";
        PorisAppDelegate myInstrumentPanel = new PorisAppDelegate(instrumentFileName);
        PorisAppDelegate myConfigPanel = new PorisAppDelegate(configFileName);
        System.out.println("Cargado del fichero "+instrumentFileName+" el modelo "+myInstrumentPanel.toString());
        System.out.println("Cargado del fichero "+configFileName+" el modelo "+myConfigPanel.toString());
        myConfigPanel.toString();
        //System.exit(0);
    }
}
