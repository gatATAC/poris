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
    public static ValueHMSFormatter DEFAULT_HMS_FORMATTER=new ValueHMSFormatter("HH:mm:ss.sss","HH:mm:ss.sss",2);
    /**
     * 
     */
    public static ValueDMSFormatter DEFAULT_DMS_FORMATTER=new ValueDMSFormatter("DD:mm:ss.sss","DD:mm:ss.sss",3);
    /**
     * 
     */
    public static ValueArcMinFormatter DEFAULT_ARCMIN_FORMATTER=new ValueArcMinFormatter("arcmin","arcmin",8);
    /**
     * 
     */
    public static ValueArcSecFormatter DEFAULT_ARCSEC_FORMATTER=new ValueArcSecFormatter("arcsec","arcsec",9);

    /**
     * 
     * @param instrumentFileName
     * @param showXMLButtons
     * @param showInvisible
     */
    public AstroPorisGUIAppDelegate(String instrumentFileName, boolean showXMLButtons, boolean showInvisible, boolean showAbout) {
        super(instrumentFileName,showXMLButtons, showInvisible, showAbout);
        this.applicationDetails+="\nAstroPorisPlayer v0.31";
    }

    /**
     * 
     */
    public AstroPorisGUIAppDelegate() {
        this("instrument.xml",true,true,true);
    }

    @Override
    public boolean commitChanges() {
        return super.commitChanges();
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
        AstroPorisGUIAppDelegate loader=new AstroPorisGUIAppDelegate();
        loader.showPanel();
        loader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
