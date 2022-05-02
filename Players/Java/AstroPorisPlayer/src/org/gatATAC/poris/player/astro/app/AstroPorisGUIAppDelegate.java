/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.player.astro.app;

import org.gatATAC.poris.player.formatters.ValueArcMinFormatter;
import org.gatATAC.poris.player.formatters.ValueArcSecFormatter;
import org.gatATAC.poris.player.formatters.ValueDMSFormatter;
import org.gatATAC.poris.player.formatters.ValueHMSFormatter;
import javax.swing.JFrame;
import org.gatATAC.poris.player.app.PorisGUIAppDelegate;

/**
 *
 * @author txinto
 */
public class AstroPorisGUIAppDelegate extends PorisGUIAppDelegate {

    /**
     *
     */
    public static ValueHMSFormatter DEFAULT_HMS_FORMATTER = new ValueHMSFormatter("HH:mm:ss.sss", "HH:mm:ss.sss", 2);
    /**
     *
     */
    public static ValueDMSFormatter DEFAULT_DMS_FORMATTER = new ValueDMSFormatter("DD:mm:ss.sss", "DD:mm:ss.sss", 3);
    /**
     *
     */
    public static ValueArcMinFormatter DEFAULT_ARCMIN_FORMATTER = new ValueArcMinFormatter("arcmin", "arcmin", 8);
    /**
     *
     */
    public static ValueArcSecFormatter DEFAULT_ARCSEC_FORMATTER = new ValueArcSecFormatter("arcsec", "arcsec", 9);

    /**
     *
     * @param instrumentFileName
     * @param showXMLButtons
     * @param showInvisible
     * @param showAbout
     */
    public AstroPorisGUIAppDelegate(String instrumentFileName, boolean showXMLButtons, boolean showInvisible, boolean showAbout) {
        super(instrumentFileName, showXMLButtons, showInvisible, showAbout);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/gatATAC/poris/player/astro/app/resources/AboutBox"); // NOI18N
        this.applicationDetails += "\n" + bundle.getString("Application.name") + " " + bundle.getString("Application.version");
    }

    @Override
    public boolean commitChanges() {
        return super.commitChanges();
        /* If you override this, be careful to execute the super.commitChanges() before doing anything else */
    }

    @Override
    public void discardChanges() {
        super.discardChanges();
    }

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        String instrumentFileName = null;
        String configFileName = null;
        if (args.length > 0) {
            instrumentFileName = args[0];
            if (args.length > 1) {
                configFileName = args[1];
            }
        } else {
            instrumentFileName = "instrument.xml";
            configFileName = "config.xml";
        }
        if (instrumentFileName != null) {
            AstroPorisGUIAppDelegate myInstrumentPanel = new AstroPorisGUIAppDelegate(instrumentFileName, true, true, true);
            myInstrumentPanel.showPanel();
            myInstrumentPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.out.println("Model " + myInstrumentPanel.toString() + "loaded from file " + instrumentFileName);
        }
        if (configFileName != null) {
            AstroPorisGUIAppDelegate myConfigPanel = new AstroPorisGUIAppDelegate(configFileName, true, true, false);
            myConfigPanel.showPanel();
            myConfigPanel.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            System.out.println("Model " + myConfigPanel.toString() + "loaded from file " + configFileName);
        }
    }
}
