/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems.formatters;

/**
 *
 * @author txinto
 */
public class ValueIntegerFormatter extends ValueDoubleFormatter {
    public ValueIntegerFormatter(String name, String label, int id) {
        super(name, label, id);
    }

    public ValueIntegerFormatter(String name, int id) {
        super(name, id);
    }

    @Override
    public String getValue(double value) {
        return Integer.toString((int)value);
    }

    @Override
    public String getValue(double value, boolean noSpecificTreatment) {
        return Integer.toString((int)value);
    }

}
