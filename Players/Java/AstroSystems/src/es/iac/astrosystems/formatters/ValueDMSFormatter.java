/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iac.astrosystems.formatters;

import jsky.coords.DMS;
import org.gatATAC.poris.systems.formatters.ValueDoubleFormatter;

/**
 *
 * @author txinto
 */
public class ValueDMSFormatter extends ValueDoubleFormatter {

    public ValueDMSFormatter(String name, int id) {
        super(name, id);
    }

    public ValueDMSFormatter(String name, String label, int id) {
        super(name, label, id);
    }
    @Override
    public double getValue(String strValue) {
        DMS ret = new DMS(strValue);
        if (ret != null) {
            return ret.getVal();
        } else {
            return super.getValue(strValue);
        }
    }

    @Override
    public double getValue(String strValue, boolean noSpecificTreatment) {
        if (noSpecificTreatment) {
            return super.getValue(strValue);
        } else {
            return this.getValue(strValue);
        }
    }

    @Override
    public String getValue(double value) {
        return (new DMS(value)).toString();
    }

    @Override
    public String getValue(double value, boolean noDMS) {
        if (noDMS) {
            return super.getValue(value);
        } else {
            return getValue(value);
        }
    }
}
