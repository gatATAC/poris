/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.systems;

import org.gatATAC.poris.systems.formatters.ValueDoubleFormatter;

/**
 *
 * @author txinto
 */
public class DataDouble implements Data<Double> {

    private Double dataValue;
    private final ValueDoubleFormatter formatter;

    public DataDouble(ValueDoubleFormatter formatter) {
        if (formatter == null) {
            this.formatter = new ValueDoubleFormatter("double", 0);
        } else {
            this.formatter = formatter;
        }
    }

    public Double getDataValue() {
        return this.dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    public void setDataValueFromString(String strValue) {
        this.dataValue = this.formatter.getValue(strValue);
    }

    @Override
    public String toString() {
        return this.formatter.getValue(dataValue);
    }

    public String toUnformattedString() {
        return this.dataValue.toString();
    }
}
