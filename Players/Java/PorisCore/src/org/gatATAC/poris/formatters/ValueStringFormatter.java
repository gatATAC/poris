/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.formatters;

/**
 *
 * @author txinto
 */
public class ValueStringFormatter extends ValueFormatter{

    /**
     * 
     * @param name
     * @param id
     */
    public ValueStringFormatter(String name, int id) {
        super(name, id);
    }
    /**
     * 
     * @param name
     * @param label
     * @param id
     */
    public ValueStringFormatter(String name, String label, int id) {
        super(name, label, id);
    }
    /**
     * 
     * @param value
     * @return
     */
    public String getValue(String value){
        return value;
    }
    /**
     * 
     * @param strValue
     * @param noSpecificTreatment
     * @return
     */
    public String getValue(String strValue, boolean noSpecificTreatment){
        return strValue;
    }
}
