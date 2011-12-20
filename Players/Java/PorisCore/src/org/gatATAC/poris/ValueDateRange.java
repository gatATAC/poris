/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import org.gatATAC.poris.formatters.ValueDateFormatter;
import org.gatATAC.poris.formatters.ValueFormatter;
import java.util.Date;
import org.gatATAC.poris.app.PorisAppDelegate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author txinto
 */
public class ValueDateRange extends Value implements ValueDataInterface<Date> {

    private Date min, max, defaultValue;

    /**
     * 
     * @param name
     * @param defaultValue
     * @param min
     * @param max
     */
    public ValueDateRange(String name, Date defaultValue, Date min, Date max) {
        super(name);
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;

    }

    /**
     * 
     * @param name
     */
    public ValueDateRange(String name) {
        super(name);
    }

    /**
     * 
     * @return
     */
    public Date getDefaultValue() {
        return defaultValue;
    }

    /**
     * 
     * @return
     */
    public Date getMax() {
        return max;
    }

    /**
     * 
     * @return
     */
    @Override
    public ValueFormatter getFormatter() {
        ValueFormatter formatter = super.getFormatter();
        if (formatter == null) {
            formatter = PorisAppDelegate.DEFAULT_DATE_FORMATTER;
            this.setFormatter(formatter);
        }
        return formatter;
    }

    /**
     * 
     * @return
     */
    public Date getMin() {
        return min;
    }

    /**
     * 
     * @param defaultValue
     */
    public void setDefaultValue(Date defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 
     * @param max
     */
    public void setMax(Date max) {
        this.max = max;
    }

    /**
     * 
     * @param min
     */
    public void setMin(Date min) {
        this.min = min;
    }

    @Override
    public Object clone(String strValue) {
        ValueDateRange ret2 = new ValueDateRange(this.getName(), this.defaultValue, this.min, this.max);
        return ret2;
    }

    /**
     * 
     * @param name
     * @return
     */
    @Override
    public Value getValueForString(String name) {
        if (this.isValidFromStr(name)) {
            return new ValueDateRange(name, this.defaultValue, this.min, this.max);
        }
        return null;
    }

    /**
     * 
     * @param value
     * @return
     */
    public boolean isValid(Date value) {
        return ( value.after(this.min) && value.before(this.max)) || value.equals(min) || value.equals(max);
    }

    /**
     * 
     * @param strValue
     * @return
     */
    @Override
    public boolean isValidFromStr(String strValue) {
        return this.isValid(((ValueDateFormatter) this.getFormatter()).getValue(strValue));
    }

    /**
     * 
     * @param doc
     * @param tagClass
     * @param onlyIdent
     * @return
     */
    @Override
    public Element toXML(Document doc, Class tagClass, boolean onlyIdent) {
        Element ret = null;
        try {
            ret = super.toXML(doc, tagClass, onlyIdent);
            if (!onlyIdent) {
                Element defaultNode = doc.createElement("default-date");
                defaultNode.setTextContent(((ValueDateFormatter) this.getFormatter()).getValue(defaultValue,true));
                defaultNode.setAttribute("type", "datetime");
                ret.appendChild(defaultNode);
                Element minNode = doc.createElement("date-min");
                minNode.setTextContent(((ValueDateFormatter) this.getFormatter()).getValue(min, true));
                minNode.setAttribute("type", "datetime");
                ret.appendChild(minNode);
                Element maxNode = doc.createElement("date-max");
                maxNode.setTextContent(((ValueDateFormatter) this.getFormatter()).getValue(max, true));
                maxNode.setAttribute("type", "datetime");
                ret.appendChild(maxNode);
            }
        } catch (Exception exc) {
            System.err.println("Excepción en ValueDateRange.toXML: "+exc.getLocalizedMessage());
        }
        return ret;
    }

    /**
     * 
     * @param node
     * @return
     */
    @Override
    public boolean loadFromXML(Node node) {
        boolean ret = super.loadFromXML(node);
        // Name
        //System.out.println("Ejecuto el codigo ValueDateRange.loadFromXML()");
        String defVal = getChildNodeWithName(node, "default-date").getTextContent();
        String maxVal = getChildNodeWithName(node, "date-max").getTextContent();
        String minVal = getChildNodeWithName(node, "date-min").getTextContent();
        //System.out.println("El valor por defecto de la instancia es " + defVal);
        this.setMax(((ValueDateFormatter) this.getFormatter()).getValue(maxVal, true));
        this.setMin(((ValueDateFormatter) this.getFormatter()).getValue(minVal, true));
        this.setDefaultValue(((ValueDateFormatter) this.getFormatter()).getValue(defVal, true));
        return ret;
    }

    /**
     * 
     * @param toClone
     * @param min
     * @param max
     */
    public ValueDateRange(ValueDateRange toClone, Date min, Date max) {
        this(toClone.getName(), toClone.defaultValue, min, max);
    }

    @Override
    public String toString() {
        return super.toString() + " [" + ((ValueDateFormatter) this.getFormatter()).getValue(this.min) + ".." + ((ValueDateFormatter) this.getFormatter()).getValue(this.defaultValue) + ".." + ((ValueDateFormatter) this.getFormatter()).getValue(this.max) + "]";
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
