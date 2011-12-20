/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris;

/**
 *
 * @author txinto
 */
public class DataString implements Data<String> {

    private String dataValue;
    /**
     * 
     * @return
     */
    public String getDataValue() {
        return this.dataValue;
    }

    /**
     * 
     * @param dataValue
     */
    public void setDataValue(String dataValue) {
        this.dataValue=dataValue;
    }

    /**
     * 
     * @param strValue
     */
    public void setDataValueFromString(String strValue) {
        this.setDataValue(strValue);
    }

    @Override
    public String toString() {
        return this.dataValue.toString();
    }

    /**
     * 
     * @return
     */
    public String toUnformattedString() {
        return this.dataValue.toString();
    }
}
