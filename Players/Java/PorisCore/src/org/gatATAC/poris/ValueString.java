/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import org.gatATAC.poris.app.PorisAppDelegate;
import org.gatATAC.poris.formatters.ValueFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author txinto
 */
public class ValueString extends Value implements ValueDataInterface<String> {

    private String defaultValue;

    /**
     * 
     * @param name
     * @param defaultValue
     */
    public ValueString(String name, String defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
    }

    /**
     * 
     * @param name
     */
    public ValueString(String name) {
        super(name);
    }

    /**
     * 
     * @return
     */
    @Override
    public ValueFormatter getFormatter() {
        ValueFormatter formatter = super.getFormatter();
        if (formatter == null) {
            formatter = PorisAppDelegate.DEFAULT_STRING_FORMATTER;
        }
        return formatter;
    }
    /**
     * 
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        if (!defaultValue.equals(this.defaultValue)) {
            this.defaultValue = defaultValue;
            notifyObs();
        }
    }

    /**
     * 
     * @param value
     * @return
     */
    @Override
    public boolean isValid(Value value) {
        if (value.isDescendantOf(this.getClass())) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param strValue
     * @return
     */
    @Override
    public boolean isValidFromStr(String strValue) {
        return strValue.trim().length() > 0;
    }

    /**
     * 
     * @param strValue
     * @return
     */
    @Override
    public Value getValueForString(String strValue) {
        if (this.isValidFromStr(strValue)) {
            return this;
        }
        return null;
    }

    @Override
    public Object clone(String strValue) {
        return new ValueString(this.getName(), strValue);
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
            Element defaultNode = doc.createElement("defaultString");
            defaultNode.setTextContent(this.defaultValue);
            defaultNode.setAttribute("type", "float");
            ret.appendChild(defaultNode);
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
        //System.out.println("Ejecuto el codigo ValueString.loadFromXML()");
        // Name
        String defVal = getChildNodeWithName(node, "default-string").getTextContent();
        //System.out.println("El valor por defecto de la instancia es " + defVal);
        this.setDefaultValue(defVal);
        return ret;
    }
}
