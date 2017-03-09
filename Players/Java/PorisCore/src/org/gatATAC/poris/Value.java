package org.gatATAC.poris;

import org.gatATAC.poris.formatters.ValueFormatter;
import org.gatATAC.XML.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// </editor-fold> 
/**
 * 
 * @author txinto
 */
public class Value extends SNode {

    private String description;
    private ValueFormatter formatter;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.606444B4-8DDE-882F-B8BF-09B6495B1AF8]
    // </editor-fold> 
    /**
     * 
     * @param name
     */
    public Value(String name) {
        super(name);
    }

    /**
     * Clones the current instance with a different name
     * @param name The name to be set for the instance
     * @return A clone of the current instance, but with given name
     */
    @Override
    public Object clone(String name) {
        return new Value(name);
    }

    /**
     * 
     * @param node
     * @return
     */
    @Override
    public boolean loadFromXML(Node node) {
        boolean ret = super.loadFromXML(node);
        // Value Formatter Id
        String instanceFormatterId = Utils.getTextContent((Element) getChildNodeWithName(node, "value-formatter-id"));
        if (instanceFormatterId != null) {
            this.formatter = ValueFormatter.getFormatterForId(Integer.parseInt(instanceFormatterId));
        }
        //System.out.println("Ejecuto el codigo Value.loadFromXML()");
        return ret;
    }

    /**
     * 
     * @return
     */
    public ValueFormatter getFormatter() {
        return formatter;
    }

    /**
     * 
     * @param formatter
     */
    public void setFormatter(ValueFormatter formatter) {
        this.formatter = formatter;
        notifyObs();
    }

    /**
     * 
     * @param value
     * @return
     */
    public boolean isValid(Value value) {
        return this.equals(value);
    }

    /**
     * 
     * @param name
     * @return
     */
    public Value getValueForString(String name) {
        if (this.getName().equals(name)) {
            return this;
        }
        return null;
    }

    /**
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        if (this.description != null) {
            return this.description;
        } else {
            return super.toString();
        }
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
            if (this.getFormatter() != null) {
                Element formatterIdNode = doc.createElement("value-formatter-id");
                Utils.setTextContent(doc, formatterIdNode, Integer.toString(this.getFormatter().getId()));
                ret.appendChild(formatterIdNode);
            }
        }
        return ret;
    }
}

