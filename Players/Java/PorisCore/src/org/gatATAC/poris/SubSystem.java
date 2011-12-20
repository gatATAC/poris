/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author osiris
 */
public class SubSystem extends SNode {

    private Mode defaultMode;

    /**
     * 
     * @param name
     */
    public SubSystem(String name) {
        super(name);
    }

    /**
     * 
     * @param defaultMode
     */
    public void setDefaultMode(Mode defaultMode) {
        if (defaultMode!=this.defaultMode) {
            this.defaultMode = defaultMode;
            //System.out.println("2 En "+this+" pongo como defaultMode "+defaultMode);
            notifyObs();
        }
    }

    /**
     * 
     * @return
     */
    public Mode getDefaultMode() {
        return defaultMode;
    }

    /**
     * 
     * @param sm
     */
    public void addMode(Mode sm) {
        this.addDestination(sm);
    }

    /**
     * 
     * @param child
     */
    @Override
    public void addDestination(SNode child) {
        if (child.isDescendantOf(Mode.class) &&
                this.getDefaultMode()==null)
        {
            this.defaultMode=(Mode)child;
            //System.out.println("En "+this+" pongo como defaultMode "+child);
        }
        super.addDestination(child);
    }

    /**
     * 
     * @param s
     */
    public void addSubSystem (SubSystem s) {
        this.addDestination(s);
    }

    /**
     * 
     * @param v
     */
    public void addValue(Value v) {
        this.addDestination(v);
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<SNode> getModes() {
        return this.getFromListByClass(this.destinations, Mode.class);
    }

    /**
     * 
     * @return
     */
    public ArrayList<SNode> getSubSystems() {
        return this.getFromListByClass(this.destinations, SubSystem.class);
    }

    /**
     * 
     * @return
     */
    public ArrayList<SNode> getSuperSystems() {
        return this.getFromListByClass(this.sources, SubSystem.class);
    }

    /**
     * 
     * @return
     */
    public ArrayList<SNode> getValues() {
        return this.getFromListByClass(this.destinations, Value.class);
    }

    /**
     * 
     * @param name
     * @return
     */
    public Mode getModeFromName(String name) {
        for (int i=0;i<this.getModes().size();i++) {
            if (this.getModes().get(i).getName().equals(name)) {
                return (Mode)this.getModes().get(i);
            }
        }
        return null;
    }

    /**
     * 
     * @param name
     * @return
     */
    public Value getValueFromName(String name) {
        for (int i=0;i<this.getValues().size();i++) {
            if (this.getValues().get(i).getName().equals(name)) {
                return (Value)this.getValues().get(i);
            }
        }
        return null;
    }


    /**
     * 
     * @param name
     * @return
     */
    public SubSystem getSubSystemFromName(String name) {
        for (int i=0;i<this.getSubSystems().size();i++) {
            if (((SubSystem)this.getSubSystems().get(i)).getName().equals(name)) {
                return (SubSystem)this.getSubSystems().get(i);
            }
        }
        return null;
    }

    /**
     * 
     * @param name
     * @return
     */
    public SubSystem getDescendantFromName(String name) {
        for (int i=0;i<this.getSubSystems().size();i++) {
            if (((SubSystem)this.getSubSystems().get(i)).getName().equals(name)) {
                return (SubSystem)this.getSubSystems().get(i);
            } else {
                SubSystem ret=((SubSystem)this.getSubSystems().get(i)).getDescendantFromName(name);
                if (ret!=null){
                    return ret;
                }
            }
        }
        return null;
    }
    

    /**
     * 
     * @param doc
     * @param tagClass
     * @param onlyIdent
     * @return
     */
    @Override
    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.BB277CA2-6794-D485-5D21-166CDDA9B7F6]
    // </editor-fold>
    public Element toXML (Document doc, Class tagClass, boolean onlyIdent) {
        Element ret=super.toXML(doc, tagClass, onlyIdent);
        if (!onlyIdent) {

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
        String defMod = getChildNodeWithName(node, "default-mode-id").getTextContent();
        if (defMod != null) {
            Mode newMod = (Mode) xmlLoaderHashMap.get(defMod);
            if (newMod != null) {
                this.setDefaultMode(newMod);
            }
        }
        return ret;
    }

}
