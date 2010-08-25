/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems.formatters;

/**
 *
 * @author txinto
 */
public class ValueStringFormatter extends ValueFormatter{

    public ValueStringFormatter(String name, int id) {
        super(name, id);
    }
    public ValueStringFormatter(String name, String label, int id) {
        super(name, label, id);
    }
    public String getValue(String value){
        return value;
    }
    public String getValue(String strValue, boolean noSpecificTreatment){
        return strValue;
    }
}
