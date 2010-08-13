/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems.formatters;

/**
 *
 * @author txinto
 */
public class ValueDoubleFormatter extends ValueFormatter{

    public ValueDoubleFormatter(String name, int id) {
        super(name, id);
    }
    public ValueDoubleFormatter(String name, String label, int id) {
        super(name, label, id);
    }
    public double getValue(String strValue){
        return Double.parseDouble(strValue);
    }
    public String getValue(double value){
        return Double.toString(value);
    }
    public double getValue(String strValue, boolean noSpecificTreatment){
        return Double.parseDouble(strValue);
    }
    public String getValue(double value, boolean noSpecificTreatment){
        return Double.toString(value);
    }

}
