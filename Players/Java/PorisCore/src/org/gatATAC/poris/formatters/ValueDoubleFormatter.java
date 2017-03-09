/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.formatters;

/**
 *
 * @author txinto
 */
public class ValueDoubleFormatter extends ValueFormatter{

    /**
     * 
     * @param name
     * @param id
     */
    public ValueDoubleFormatter(String name, int id) {
        super(name, id);
    }
    /**
     * 
     * @param name
     * @param label
     * @param id
     */
    public ValueDoubleFormatter(String name, String label, int id) {
        super(name, label, id);
    }
    /**
     * 
     * @param strValue
     * @return
     */
    public double getValue(String strValue){
        return Double.parseDouble(strValue);
    }
    /**
     * 
     * @param value
     * @return
     */
    public String getValue(double value){
        return Double.toString(value);
    }
    /**
     * 
     * @param strValue
     * @param noSpecificTreatment
     * @return
     */
    public double getValue(String strValue, boolean noSpecificTreatment){
        return Double.parseDouble(strValue);
    }
    /**
     * 
     * @param value
     * @param noSpecificTreatment
     * @return
     */
    public String getValue(double value, boolean noSpecificTreatment){
        return Double.toString(value);
    }

}
