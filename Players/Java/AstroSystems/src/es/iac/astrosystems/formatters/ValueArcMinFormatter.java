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
public class ValueArcMinFormatter extends ValueDoubleFormatter{

    public ValueArcMinFormatter(String name, String label, int id) {
        super(name, label, id);
    }

    public ValueArcMinFormatter(String name, int id) {
        super(name, id);
    }

}
