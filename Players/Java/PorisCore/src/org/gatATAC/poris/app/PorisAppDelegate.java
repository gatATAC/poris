package org.gatATAC.poris.app;

import org.gatATAC.poris.Cfg;
import org.gatATAC.poris.SNode;
import org.gatATAC.poris.SNodeLib;
import org.gatATAC.poris.SubSystem;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.gatATAC.poris.formatters.ValueDateFormatter;
import org.gatATAC.poris.formatters.ValueDoubleFormatter;
import org.gatATAC.poris.formatters.ValueFormatter;
import org.gatATAC.poris.formatters.ValueIntegerFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author txinto
 */
public class PorisAppDelegate {

    private final SNodeLib systemLib = new SNodeLib("System Library");
    private final SNodeLib configLib = new SNodeLib("Config Library");
    private SubSystem system;
    private Cfg config;
    private final String instrumentFileName;
    private boolean modelLoaded;    
    protected String applicationDetails;

    /**
     * 
     */
    public static ValueDoubleFormatter DEFAULT_DOUBLE_FORMATTER = new ValueDoubleFormatter("real", "real", 1);
    /**
     * 
     */
    public static ValueDoubleFormatter ANGLE_FORMATTER = new ValueDoubleFormatter("angle", "angle", 4);
    /**
     * 
     */
    public static ValueDoubleFormatter S_FORMATTER = new ValueDoubleFormatter("s", "s", 5);
    /**
     * 
     */
    public static ValueDateFormatter DEFAULT_DATE_FORMATTER = new ValueDateFormatter("Date", "dd.MM.yyyy HH:mm:ss z", 6);
    /**
     * 
     */
    public static ValueIntegerFormatter DEFAULT_INTEGER_FORMATTER = new ValueIntegerFormatter("integer", "#", 7);
    /**
     * 
     */
    public static ValueFormatter DEFAULT_STRING_FORMATTER = null;

    /**
     * 
     * @param instrumentFileName
     */
    public PorisAppDelegate(String instrumentFileName) {
        this.instrumentFileName = instrumentFileName;
        this.modelLoaded = false;
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/gatATAC/poris/app/resources/AboutBox"); // NOI18N
        this.applicationDetails=bundle.getString("Application.name")+" "+bundle.getString("Application.version");
        this.packageInit();
        this.loadModel();
    }


    /**
     * Get the value of applicationDetails
     *
     * @return the value of applicationDetails
     */
    public String getApplicationDetails() {
        return applicationDetails;
    }

    /**
     * Set the value of applicationDetails
     *
     * @param applicationDetails new value of applicationDetails
     */
    public void setApplicationDetails(String applicationDetails) {
        this.applicationDetails = applicationDetails;
    }
    
    
    /**
     * 
     */
    protected void packageInit() {
    }

    /**
     * 
     * @return
     */
    public Cfg getConfig() {
        return config;
    }

    /**
     * 
     * @return
     */
    public SNodeLib getConfigLib() {
        return configLib;
    }

    /**
     * 
     * @return
     */
    public boolean isModelLoaded() {
        return modelLoaded;
    }

    /**
     * 
     * @return
     */
    public SubSystem getSystem() {
        return system;
    }

    /**
     * 
     * @return
     */
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
            System.err.println("PorisAppDeletage.initialization [" + exc.getLocalizedMessage() + "]");
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

    /**
     * 
     */
    public void discardChanges() {
        // Put your code here
    }

    /**
     * 
     * @return
     */
    public boolean commitChanges() {
        // Issue PORIS-1 #1
        int ret = JOptionPane.showConfirmDialog(null, "¿Are you sure to commit the changes?", "Committing changes", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String instrumentFileName=null;
        String configFileName=null;
        if (args.length>0){
            instrumentFileName=args[0];
            if (args.length>1){
                configFileName= args[1];
            }
        } else {
            instrumentFileName= "instrument.xml";
            configFileName= "config.xml";
        }
        if (instrumentFileName!=null){
            PorisAppDelegate myInstrumentPanel = new PorisAppDelegate(instrumentFileName);
            System.out.println("Cargado del fichero "+instrumentFileName+" el modelo "+myInstrumentPanel.toString());
        }
        if (configFileName!=null){
            PorisAppDelegate myConfigPanel = new PorisAppDelegate(configFileName);
            System.out.println("Cargado del fichero "+configFileName+" el modelo "+myConfigPanel.toString());
        }
        //System.exit(0);
    }
}
