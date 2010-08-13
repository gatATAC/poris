/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.systems.formatters;

import org.gatATAC.poris.systems.ValueDateRange;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author txinto
 */
public class ValueDateFormatter extends ValueFormatter {

    public String dateFormatString = "dd.MM.yyyy HH:mm:ss z";
    public static String defaultDateFormatString = "dd.MM.yyyy HH:mm:ss z";
    public static String noTreatmentFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public SimpleDateFormat normalFormat = new SimpleDateFormat(dateFormatString);
    public static SimpleDateFormat defaultNormalFormat = new SimpleDateFormat(defaultDateFormatString);
    public static SimpleDateFormat noTreatmentFormat = new SimpleDateFormat(noTreatmentFormatString);
    //public static SimpleDateFormat noTreatmentFormat = new SimpleDateFormat(defaultDateFormatString);

    public ValueDateFormatter(String name, String label, int id) {
        super(name, label, id);
        this.dateFormatString=label;
        this.normalFormat=new SimpleDateFormat (this.dateFormatString);
    }

    public ValueDateFormatter(String name, int id) {
        super(name, defaultDateFormatString ,id);
        this.dateFormatString=defaultDateFormatString;
        this.normalFormat=defaultNormalFormat;
    }

    public Date getValueForFormat(String strValue, SimpleDateFormat format) {
        // precess the WC to the equinox of the observation
        Date date = null;
        try {
            date = (Date) format.parse(strValue);
        } catch (ParseException nfe) {
            // quietly ignore parse errors...
            // the alternative would be to return the unparsed source string
            // but that would break any checks for null on the returned Object
            System.err.println("ValueDateFormatter.getValueForFormat ["+nfe.getLocalizedMessage()+"]");
        }
        return date;
    }

    public String getValueForFormat(Date value, SimpleDateFormat format) {
        return format.format(value);
    }

    public Date getValue(String strValue) {
        return this.getValueForFormat(strValue, normalFormat);
    }

    public String getValue(Date value) {
        return this.getValueForFormat(value, normalFormat);
    }

    public Date getValue(String strValue, boolean noSpecificTreatment) {
        if (noSpecificTreatment) {
            return this.getValueForFormat(strValue, noTreatmentFormat);
        } else {
            return getValue(strValue);
        }
    }

    public String getValue(Date value, boolean noSpecificTreatment) {
        if (noSpecificTreatment) {
            return this.getValueForFormat(value, noTreatmentFormat);
        } else {
            return getValue(value);
        }
    }

        public static void main(String args[]) {
        ValueDateRange expTime = new ValueDateRange("dateObs", new Date(),new Date(),new Date());
        System.out.println(expTime.toString());
    }
}
