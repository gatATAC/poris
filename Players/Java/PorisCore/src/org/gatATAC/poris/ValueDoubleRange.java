/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import org.gatATAC.poris.app.PorisAppDelegate;
import org.gatATAC.poris.formatters.ValueDoubleFormatter;
import org.gatATAC.poris.formatters.ValueFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author txinto
 */
public class ValueDoubleRange extends Value implements ValueDataInterface<Double> {

    private Double min, max, defaultValue;

    /**
     * 
     * @param name
     * @param defaultValue
     * @param min
     * @param max
     */
    public ValueDoubleRange(String name, Double defaultValue, Double min, Double max) {
        super(name);
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;

    }

    /**
     * 
     * @return
     */
    @Override
    public ValueFormatter getFormatter() {
        ValueFormatter formatter = super.getFormatter();
        if (formatter == null) {
            formatter = PorisAppDelegate.DEFAULT_DOUBLE_FORMATTER;
        }
        return formatter;
    }

    /**
     * 
     * @param name
     */
    public ValueDoubleRange(String name) {
        super(name);
    }

    /**
     * 
     * @return
     */
    public Double getDefaultValue() {
        return defaultValue;
    }

    /**
     * 
     * @return
     */
    public Double getMax() {
        return max;
    }

    /**
     * 
     * @return
     */
    public Double getMin() {
        return min;
    }

    /**
     * 
     * @param defaultValue
     */
    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 
     * @param max
     */
    public void setMax(Double max) {
        this.max = max;
    }

    /**
     * 
     * @param min
     */
    public void setMin(Double min) {
        this.min = min;
    }

    @Override
    public Object clone(String strValue) {
        ValueDoubleRange ret2 = new ValueDoubleRange(this.getName(), this.defaultValue, this.min, this.max);
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
            return new ValueDoubleRange(name, this.defaultValue, this.min, this.max);
        }
        return null;
    }

    /**
     * 
     * @param value
     * @return
     */
    public boolean isValid(Double value) {
        return (value >= this.min)
                && (value <= this.max);
    }

    /**
     * 
     * @param strValue
     * @return
     */
    @Override
    public boolean isValidFromStr(String strValue) {
        return this.isValid(((ValueDoubleFormatter) this.getFormatter()).getValue(strValue));
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
        Element ret = super.toXML(doc, tagClass, onlyIdent);
        if (!onlyIdent) {
            Element defaultNode = doc.createElement("default-float");
            defaultNode.setTextContent(Double.toString(defaultValue));
            defaultNode.setAttribute("type", "float");
            ret.appendChild(defaultNode);
            Element minNode = doc.createElement("rangemin");
            minNode.setTextContent(Double.toString(min));
            minNode.setAttribute("type", "float");
            ret.appendChild(minNode);
            Element maxNode = doc.createElement("rangemax");
            maxNode.setTextContent(Double.toString(max));
            maxNode.setAttribute("type", "float");
            ret.appendChild(maxNode);
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
        //System.out.println("Ejecuto el codigo ValueDoubleRange.loadFromXML()");
        String defVal = getChildNodeWithName(node, "default-float").getTextContent();
        String maxVal = getChildNodeWithName(node, "rangemax").getTextContent();
        String minVal = getChildNodeWithName(node, "rangemin").getTextContent();
        //System.out.println("El valor por defecto de la instancia es " + defVal);
        this.setMax(Double.parseDouble(maxVal));
        this.setMin(Double.parseDouble(minVal));
        this.setDefaultValue(Double.parseDouble(defVal));
        return ret;
    }

    /**
     * 
     * @param toClone
     * @param min
     * @param max
     */
    public ValueDoubleRange(ValueDoubleRange toClone, Double min, Double max) {
        this(toClone.getName(), toClone.defaultValue, min, max);
    }

    @Override
    public String toString() {
        ValueDoubleFormatter formatter = (ValueDoubleFormatter) this.getFormatter();
        if (formatter != null) {
            return super.toString() + " [" + ((ValueDoubleFormatter) this.getFormatter()).getValue(this.min) + ".." + ((ValueDoubleFormatter) this.getFormatter()).getValue(this.defaultValue) + ".." + ((ValueDoubleFormatter) this.getFormatter()).getValue(this.max) + "]";
        } else {
            return super.toString() + " [" + Double.toString(this.min) + ".." + Double.toString(this.defaultValue) + ".." + Double.toString(this.max) + "]";
        }
    }

    /**
     * 
     * @param args
     */
    public static void main(String args[]) {
        ValueDoubleRange expTime = new ValueDoubleRange("expTime", 0.10, 0.001, 3600.0);
        System.out.println(expTime.toString());
    }
}
