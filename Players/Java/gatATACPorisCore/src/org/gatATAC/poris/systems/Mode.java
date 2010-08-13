/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.systems;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author osiris
 */
public class Mode extends SNode {

    private Value defaultValue;
    private Mode defaultSubMode;

    public Mode(String name) {
        super(name);
    }

    @Override
    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.BB277CA2-6794-D485-5D21-166CDDA9B7F6]
    // </editor-fold>
    public Element toXML(Document doc, Class tagClass, boolean onlyIdent) {
        Element ret = super.toXML(doc, tagClass, onlyIdent);
        if (!onlyIdent) {
        }
        return ret;
    }

    public void addSubMode(Mode sm) {
        this.addDestination(sm);
    }

    public void addValue(Value v) {
        this.addDestination(v);
    }

    public Mode getDefaultSubMode() {
        return defaultSubMode;
    }

    public void setDefaultSubMode(Mode defaultSubMode) {
        if (this.defaultSubMode != defaultSubMode) {
            this.defaultSubMode = defaultSubMode;
            notifyObs();
        }
    }

    public Value getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Value defaultValue) {
        if (this.defaultValue != defaultValue) {
            this.defaultValue = defaultValue;
            notifyObs();
        }
    }

    public ArrayList<SNode> getSubModes() {
        return getFromListByClass(this.destinations, Mode.class);
    }

    public ArrayList<SNode> getSuperModes() {
        return getFromListByClass(this.sources, Mode.class);
    }

    public ArrayList<SNode> getSystems() {
        return getFromListByClass(this.sources, SubSystem.class);
    }

    public ArrayList<SNode> getValues() {
        return getFromListByClass(this.destinations, Value.class);
    }

    public Mode getSubModeFromName(String name) {
        for (int i = 0; i < this.getSubModes().size(); i++) {
            if (((Mode) this.getSubModes().get(i)).getName().equals(name)) {
                return ((Mode) this.getSubModes().get(i));
            }
        }
        return null;
    }

    public Value getValueFromName(String name) {
        for (int i = 0; i < this.getValues().size(); i++) {
            if (this.getValues().get(i).getName().equals(name)) {
                return (Value) this.getValues().get(i);
            }
        }
        return null;
    }

    @Override
    public void addDestination(SNode child) {
        if (child.isDescendantOf(Mode.class) &&
                this.getDefaultSubMode() == null) {
            this.defaultSubMode = (Mode) child;
        } else {
            if (child.isDescendantOf(Value.class) &&
                    this.getDefaultValue() == null) {
                this.defaultValue = (Value) child;
            }
        }
        super.addDestination(child);
    }

    @Override
    public boolean loadFromXML(Node node) {
        boolean ret = super.loadFromXML(node);
        // Name
        //System.out.println("Ejecuto el codigo ValueDoubleRange.loadFromXML()");
        String defVal = getChildNodeWithName(node, "default-value-id").getTextContent();
        if (defVal != null) {
            Value newVal = (Value) xmlLoaderHashMap.get(defVal);
            if (newVal != null) {
                this.setDefaultValue(newVal);
            }
        }
        String defMod = getChildNodeWithName(node, "default-mode-id").getTextContent();
        if (defMod != null) {
            Mode newMod = (Mode) xmlLoaderHashMap.get(defMod);
            if (newMod != null) {
                //System.out.println("En el modo "+this+" he añadido el modo por defecto " + newMod);
                this.setDefaultSubMode(newMod);
            }
        }
        return ret;
    }
}
