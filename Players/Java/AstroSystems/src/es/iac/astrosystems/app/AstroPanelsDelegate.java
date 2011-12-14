/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.iac.astrosystems.app;


import es.iac.astrosystems.formatters.ValueArcMinFormatter;
import es.iac.astrosystems.formatters.ValueArcSecFormatter;
import es.iac.astrosystems.formatters.ValueDMSFormatter;
import es.iac.astrosystems.formatters.ValueHMSFormatter;
import javax.swing.JFrame;
import org.gatATAC.poris.systems.app.PorisGUIAppDelegate;


/**
 *
 * @author txinto
 */
public class AstroPanelsDelegate extends PorisGUIAppDelegate{

    public static ValueHMSFormatter DEFAULT_HMS_FORMATTER=new ValueHMSFormatter("HH:mm:ss.sss","HH:mm:ss.sss",2);
    public static ValueDMSFormatter DEFAULT_DMS_FORMATTER=new ValueDMSFormatter("DD:mm:ss.sss","DD:mm:ss.sss",3);
    public static ValueArcMinFormatter DEFAULT_ARCMIN_FORMATTER=new ValueArcMinFormatter("arcmin","arcmin",8);
    public static ValueArcSecFormatter DEFAULT_ARCSEC_FORMATTER=new ValueArcSecFormatter("arcsec","arcsec",9);

    public AstroPanelsDelegate(String instrumentFileName, boolean showXMLButtons, boolean showInvisible) {
        super(instrumentFileName,showXMLButtons, showInvisible);
    }

    public AstroPanelsDelegate() {
        this("instrument.xml",true,true);
    }

    @Override
    public boolean commitChanges() {
        return super.commitChanges();
    }

    @Override
    public void discardChanges() {
        super.discardChanges();
    }

    public static void main(String args[]) {
        AstroPanelsDelegate loader=new AstroPanelsDelegate();
        loader.showPanel();
        loader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
