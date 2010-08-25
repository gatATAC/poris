/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.iac.astrosystems.formatters;

import jsky.coords.HMS;
import org.gatATAC.poris.systems.formatters.ValueDoubleFormatter;

/**
 *
 * @author txinto
 */
public class ValueHMSFormatter extends ValueDoubleFormatter{

    public ValueHMSFormatter(String name, int id) {
        super(name, id);
    }

    public ValueHMSFormatter(String name, String label, int id) {
        super(name, label, id);
    }

    @Override
    public double getValue(String strValue) {
        HMS ret=new HMS(strValue);
        if (ret!=null){
            return ret.getVal()*15.0;
        } else {
            return super.getValue(strValue)*15.0;
        }
    }
    @Override
    public double getValue(String strValue, boolean noSpecificTreatment) {
        if (noSpecificTreatment){
            return super.getValue(strValue)*15.0;
        } else {
            return this.getValue(strValue);
        }
    }
    @Override
    public String getValue(double value) {
        return (new HMS(value/15.0)).toString();
    }

    @Override
    public String getValue(double value, boolean noHMS){
        if (noHMS){
            return super.getValue(value/15.0);
        } else {
            return this.getValue(value);
        }
    }
}
