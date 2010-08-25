/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems;

/**
 *
 * @author txinto
 */
public class DataString implements Data<String> {

    private String dataValue;
    public String getDataValue() {
        return this.dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue=dataValue;
    }

    public void setDataValueFromString(String strValue) {
        this.setDataValue(strValue);
    }

    @Override
    public String toString() {
        return this.dataValue.toString();
    }

    public String toUnformattedString() {
        return this.dataValue.toString();
    }
}
