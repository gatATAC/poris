/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.formatters;

import org.gatATAC.poris.ValueDateRange;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author txinto
 */
public class ValueDateFormatter extends ValueFormatter {

    /**
     * 
     */
    public String dateFormatString = "dd.MM.yyyy HH:mm:ss z";
    /**
     * 
     */
    public static String defaultDateFormatString = "dd.MM.yyyy HH:mm:ss z";
    /**
     * 
     */
    public static String oldNoTreatmentFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * 
     */
    public static String newNoTreatmentFormatString = "yyyy-MM-dd HH:mm:ss z";
    /**
     * 
     */
    public SimpleDateFormat normalFormat = new SimpleDateFormat(dateFormatString);
    /**
     * 
     */
    public static SimpleDateFormat defaultNormalFormat = new SimpleDateFormat(defaultDateFormatString);
    /**
     * 
     */
    public static SimpleDateFormat newNoTreatmentFormat = new SimpleDateFormat(newNoTreatmentFormatString);
    /**
     * 
     */
    public static SimpleDateFormat oldNoTreatmentFormat = new SimpleDateFormat(oldNoTreatmentFormatString);
    //public static SimpleDateFormat newNoTreatmentFormat = new SimpleDateFormat(defaultDateFormatString);

    /**
     * 
     * @param name
     * @param label
     * @param id
     */
    public ValueDateFormatter(String name, String label, int id) {
        super(name, label, id);
        this.dateFormatString = label;
        this.normalFormat = new SimpleDateFormat(this.dateFormatString);
        this.normalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * 
     * @param name
     * @param id
     */
    public ValueDateFormatter(String name, int id) {
        super(name, defaultDateFormatString, id);
        this.dateFormatString = defaultDateFormatString;
        this.normalFormat = defaultNormalFormat;
        this.normalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * 
     * @param strValue
     * @param format
     * @param alternativeFormat
     * @return
     */
    public Date getValueForFormat(String strValue, SimpleDateFormat format, SimpleDateFormat alternativeFormat) {
        try {
            return (Date) format.parse(strValue);
        } catch (Exception e) {
            try {
                return (Date) alternativeFormat.parse(strValue);
            }catch (Exception e2){
                System.err.println("getValueForFormat: "+e2.getLocalizedMessage()+". Assuming now as time");
                return new Date();
            }
        }
    }

    /**
     * 
     * @param value
     * @param format
     * @return
     */
    public String getValueForFormat(Date value, SimpleDateFormat format) {
        return format.format(value);
    }

    /**
     * 
     * @param strValue
     * @return
     */
    public Date getValue(String strValue) {
        this.normalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return this.getValueForFormat(strValue, normalFormat,normalFormat);
    }

    /**
     * 
     * @param value
     * @return
     */
    public String getValue(Date value) {
        normalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return this.getValueForFormat(value, normalFormat);
    }

    /**
     * 
     * @param strValue
     * @param noSpecificTreatment
     * @return
     */
    public Date getValue(String strValue, boolean noSpecificTreatment) {
        if (noSpecificTreatment) {
            return this.getValueForFormat(strValue, newNoTreatmentFormat, oldNoTreatmentFormat);
        } else {
            return getValue(strValue);
        }
    }

    /**
     * 
     * @param value
     * @param noSpecificTreatment
     * @return
     */
    public String getValue(Date value, boolean noSpecificTreatment) {
        if (noSpecificTreatment) {
            return this.getValueForFormat(value, newNoTreatmentFormat);
        } else {
            return getValue(value);
        }
    }

    /**
     * 
     * @param args
     */
    public static void main(String args[]) {
        ValueDateRange expTime = new ValueDateRange("dateObs", new Date(), new Date(), new Date());
        System.out.println(expTime.toString());
    }
}
