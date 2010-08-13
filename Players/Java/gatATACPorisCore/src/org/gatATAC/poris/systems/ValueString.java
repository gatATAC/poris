/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.systems;

import org.gatATAC.poris.systems.app.PorisAppDelegate;
import org.gatATAC.poris.systems.formatters.ValueFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author txinto
 */
public class ValueString extends Value implements ValueDataInterface<String> {

    private String defaultValue;

    public ValueString(String name, String defaultValue) {
        super(name);
        this.defaultValue = defaultValue;
    }

    public ValueString(String name) {
        super(name);
    }

    @Override
    public ValueFormatter getFormatter() {
        ValueFormatter formatter = super.getFormatter();
        if (formatter == null) {
            formatter = PorisAppDelegate.DEFAULT_STRING_FORMATTER;
        }
        return formatter;
    }
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        if (!defaultValue.equals(this.defaultValue)) {
            this.defaultValue = defaultValue;
            notifyObs();
        }
    }

    @Override
    public boolean isValid(Value value) {
        if (value.isDescendantOf(this.getClass())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidFromStr(String strValue) {
        return strValue.trim().length() > 0;
    }

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
