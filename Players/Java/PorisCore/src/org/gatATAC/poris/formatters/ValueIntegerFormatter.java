/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.formatters;

/**
 *
 * @author txinto
 */
public class ValueIntegerFormatter extends ValueDoubleFormatter {
    /**
     * 
     * @param name
     * @param label
     * @param id
     */
    public ValueIntegerFormatter(String name, String label, int id) {
        super(name, label, id);
    }

    /**
     * 
     * @param name
     * @param id
     */
    public ValueIntegerFormatter(String name, int id) {
        super(name, id);
    }

    /**
     * 
     * @param value
     * @return
     */
    @Override
    public String getValue(double value) {
        return Integer.toString((int)value);
    }

    /**
     * 
     * @param value
     * @param noSpecificTreatment
     * @return
     */
    @Override
    public String getValue(double value, boolean noSpecificTreatment) {
        return Integer.toString((int)value);
    }

}
