/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import org.gatATAC.poris.formatters.ValueDoubleFormatter;

/**
 *
 * @author txinto
 */
public class DataDouble implements Data<Double> {

    private Double dataValue;
    private final ValueDoubleFormatter formatter;

    /**
     * 
     * @param formatter
     */
    public DataDouble(ValueDoubleFormatter formatter) {
        if (formatter == null) {
            this.formatter = new ValueDoubleFormatter("double", 0);
        } else {
            this.formatter = formatter;
        }
    }

    /**
     * 
     * @return
     */
    public Double getDataValue() {
        return this.dataValue;
    }

    /**
     * 
     * @param dataValue
     */
    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    /**
     * 
     * @param strValue
     */
    public void setDataValueFromString(String strValue) {
        this.dataValue = this.formatter.getValue(strValue);
    }

    @Override
    public String toString() {
        return this.formatter.getValue(dataValue);
    }

    /**
     * 
     * @return
     */
    public String toUnformattedString() {
        return this.dataValue.toString();
    }
}
