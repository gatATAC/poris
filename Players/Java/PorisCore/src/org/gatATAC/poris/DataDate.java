/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris;

import org.gatATAC.poris.formatters.ValueDateFormatter;
import java.util.Date;

/**
 *
 * @author txinto
 */
public class DataDate implements Data<Date> {

    private Date dataValue;
    private final ValueDateFormatter formatter;

    /**
     * 
     * @param formatter
     */
    public DataDate(ValueDateFormatter formatter) {
        this.dataValue=new Date();
        if (formatter==null){
            this.formatter = new ValueDateFormatter("date", 0);
        } else {
            this.formatter = formatter;
        }
    }

    /**
     * 
     * @return
     */
    public Date getDataValue() {
        return this.dataValue;
    }

    /**
     * 
     * @param dataValue
     */
    public void setDataValue(Date dataValue) {
        this.dataValue=dataValue;
    }

    /**
     * 
     * @param strValue
     */
    public void setDataValueFromString(String strValue) {
            this.dataValue=this.formatter.getValue(strValue);
    }

    @Override
    public String toString() {
        return this.toString(false);
    }
    /**
     * 
     * @param noSpecificTreatment
     * @return
     */
    public String toString(boolean noSpecificTreatment) {
        if (this.formatter!=null) {
            return this.formatter.getValue(dataValue,noSpecificTreatment);
        } else {
            if (dataValue==null) {
                return "null value";
            } else {
                return this.dataValue.toString();
            }
        }
    }

    /**
     * 
     * @return
     */
    public String toUnformattedString() {
        return this.toString(true);
    }
}
