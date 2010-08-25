/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.iac.astrosystems.formatters;

import org.gatATAC.poris.systems.formatters.ValueDoubleFormatter;

/**
 *
 * @author txinto
 */
public class ValueArcSecFormatter extends ValueDoubleFormatter{


    public static void init(){

    }
    public ValueArcSecFormatter(String name, String label, int id) {
        super(name, label, id);
    }

    public ValueArcSecFormatter(String name, int id) {
        super(name, id);
    }

}
