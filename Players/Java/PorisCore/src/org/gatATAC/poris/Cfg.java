package org.gatATAC.poris;

import org.gatATAC.poris.formatters.ValueDateFormatter;
import org.gatATAC.poris.formatters.ValueDoubleFormatter;
import org.gatATAC.poris.formatters.ValueFormatter;
import org.gatATAC.poris.formatters.ValueStringFormatter;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class Cfg
 * @author txinto
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.5E50B261-E2BF-A3F3-E358-904A97E7F209]
// </editor-fold> 
public class Cfg extends SNode {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5DE5B290-3135-1B97-632B-36674BAAE0EB]
    // </editor-fold> 
    private SubSystem model;
    private Value value;
    private boolean hasValue;
    private Data data;
    private static int idCounter = 1;
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4C1E880E-1DBB-9BE6-02B3-3199ADFA0075]
    // </editor-fold> 
    private Mode mode;
    private ArrayList<SNode> possibleModes;
    private Mode subMode;

    /**
     * 
     * @param name
     */
    public Cfg(String name) {
        super(name);
        this.setId(0);
    }

    /**
     * 
     * @return
     */
    public Mode getSubMode() {
        return subMode;
    }

    /**
     * 
     * @param subMode
     */
    public void setSubMode(Mode subMode) {
        this.subMode = subMode;
        notifyObs();
    }

    /**
     * 
     * @return
     */
    public Data getData() {
        if (this.data == null) {
            if (this.value != null){
                if (this.value.implementsInterface(ValueDataInterface.class)) {
                    if (this.value.isDescendantOf(ValueDoubleRange.class)) {
                        this.data = new DataDouble((ValueDoubleFormatter) this.value.getFormatter());
                    } else {
                        if (this.value.isDescendantOf(ValueDateRange.class)) {
                            this.data = new DataDate((ValueDateFormatter) this.value.getFormatter());
                        } else {
                            this.data = new DataString();
                        }
                    }
                    this.data.setDataValue(((ValueDataInterface) this.value).getDefaultValue());
                }
            } else {
                /* Value is not active, probably because the mode is not activating the param */
                /* null will be returned, nothing to do here */
            }
        } else {
            if (this.value != null){
                if (this.value.implementsInterface(ValueDataInterface.class)) {
                    if (!this.value.isValidFromStr(this.data.toString())) {
                        if (this.value.isDescendantOf(ValueDoubleRange.class)) {
                            this.data = new DataDouble((ValueDoubleFormatter) this.value.getFormatter());
                        } else {
                            if (this.value.isDescendantOf(ValueDateRange.class)) {
                                this.data = new DataDate((ValueDateFormatter) this.value.getFormatter());
                            } else {
                                this.data = new DataString();
                            }
                        }
                        this.data.setDataValue(((ValueDataInterface) this.value).getDefaultValue());
                    }
                }
            } else {
                /* Value is null, probably because a mode is not activating the param */
                /* null will be returned, nothing to do here */
            }
        }
        return this.data;
    }

    /**
     * 
     * @param strValue
     */
    public void setDataValueFromStr(String strValue) {
        ValueFormatter thisFormatter = this.value.getFormatter();
        if (thisFormatter != null) {
            if (thisFormatter instanceof ValueDoubleFormatter) {
                ValueDoubleFormatter thisDoubleFormatter = (ValueDoubleFormatter) thisFormatter;
                this.getData().setDataValue(thisDoubleFormatter.getValue(strValue));
            } else {
                if (thisFormatter instanceof ValueDateFormatter) {
                    ValueDateFormatter thisDateFormatter = (ValueDateFormatter) thisFormatter;
                    this.getData().setDataValue(thisDateFormatter.getValue(strValue));
                } else {
                    if (thisFormatter instanceof ValueStringFormatter) {
                        ValueStringFormatter thisStringFormatter = (ValueStringFormatter) thisFormatter;
                        this.getData().setDataValue(thisStringFormatter.getValue(strValue));
                    } else {
                        /// Error, no es ni string, ni date, ni double
                    }
                }
            }
        } else {
            this.getData().setDataValueFromString(strValue);
        }
        notifyObs();
    }

    /**
     * 
     * @param value
     */
    public void setDataValue(Object value) {
        this.getData().setDataValue(value);
        notifyObs();
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F5376FB2-6E9D-2FDA-8175-12CBDA11A32A]
    // </editor-fold> 
    /**
     * 
     * @param model
     */
    public Cfg(SubSystem model) {
        super(model.getName());
        //System.out.println("******* Inicio Creo el Cfg "+this);
        this.setId(0);
        this.setModel(model);
        // FIXME: Eliminar este parche que hemos puesto aquí para que se muestre bien la interfaz
        this.setMode(null);
        //System.out.println("Estoy creando el Cfg de "+this+" con el modo por defecto "+this.getModel().getDefaultMode());
        if (this.getModel().isDescendantOf(Group.class)) {
            this.subMode = this.getModel().getDefaultMode();
        } else {
            this.subMode = null;
        }
        this.setMode(this.getModel().getDefaultMode());
        //System.out.println("... el modo por defecto de la cfg " + this + " queda... " + this.getMode());
        // Fin del FIXME:
    }

    /**
     * 
     * @param model
     */
    public void setModel(SubSystem model) {
        if (this.getId() == 0) {
            this.setId(idCounter++);
        }
        this.model = model;
        this.possibleModes = model.getModes();
        this.mode = model.getDefaultMode();
        //System.out.println("------- Creo el cfg " + this);
        //System.out.println("Los valores son "+this.model.getValues());
        //System.out.println("Los valores del modo son "+this.mode.getValues());
        if (this.model.getValues().size() > 0) {
            this.hasValue = true;
            this.value = this.mode.getDefaultValue();
            //System.out.println("Pongo el valor "+this.value);
        } else {
            this.hasValue = false;
        }
        for (int i = 0; i < this.model.getSubSystems().size(); i++) {
            ArrayList<SNode> modesForChild = this.getModesForChild((SubSystem) this.model.getSubSystems().get(i));

            Cfg newCfg = new Cfg((SubSystem) this.model.getSubSystems().get(i));
            newCfg.setPossibleModes(modesForChild);
            if (modesForChild.size() < 1) {
                //System.out.println("Voy a pasar la cfg " + this + " a modo nulo");
                newCfg.setMode(null);
            }
            this.addDestination(newCfg);
        }
        this.setLabel(model.getLabel());
        //System.out.println("Finalmente el nodo es "+this.getMode()+" y el valor de "+this+" es "+this.getValue());
    }

    /**
     * 
     * @return
     */
    public ArrayList<SNode> getPossibleModes() {
        return possibleModes;
    }

    private boolean checkPossibleModesEqual(ArrayList<SNode> possibleModes, ArrayList<SNode> thisPossibleModes) {
        boolean ret = false;

        if (possibleModes.size() == thisPossibleModes.size()) {
            ret = true;
            for (int i = 0; i < possibleModes.size() && ret; i++) {
                ret = thisPossibleModes.contains(possibleModes.get(i));
            }
        }
        return ret;
    }

    /**
     * 
     * @param possibleModes
     */
    public void setPossibleModes(ArrayList<SNode> possibleModes) {
        if (!checkPossibleModesEqual(possibleModes, this.possibleModes)) {
            this.possibleModes = possibleModes;
            notifyObs();
        }
    }

    /**
     * 
     * @return
     */
    public boolean isHasValue() {
        return hasValue;
    }

    private ArrayList<SNode> getModesForChild(SubSystem child) {
        //System.out.println("En "+this+" y modo "+this.mode+" busco los modos para "+child);
        ArrayList<SNode> ret = new ArrayList();
        for (int i = 0; i < this.mode.getSubModes().size(); i++) {
            Mode thisMode = (Mode) this.mode.getSubModes().get(i);
            //System.out.println("En el modo "+thisMode+" Miro el nodo "+child+" entre los sistemas "+thisMode.getSystems());
            if (thisMode.getSystems().contains(child)) {
                //System.out.println("Añado el nodo "+thisMode);
                ret.add((Mode) thisMode);
            }
        }

        return ret;
    }

    /**
     * 
     * @return
     */
    @Override
    public boolean isConsistent() {
        return super.isConsistent(); //&& this.mode!=null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.585EF7AA-D79E-7B4E-0D80-5EE26E7C763D]
    // </editor-fold> 
    /**
     * 
     * @return
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * 
     * @param model
     * @return
     */
    public Cfg getCfgForModel(SubSystem model) {
        for (int i = 0; i < this.getDestinations().size(); i++) {
            if (((Cfg) this.getDestinations().get(i)).model == model) {
                return (Cfg) this.getDestinations().get(i);
            }
        }
        return null;
    }

    /**
     * 
     * @param model
     * @return
     */
    public Cfg getDescendantForModel(SubSystem model) {
        for (int i = 0; i < this.getDestinations().size(); i++) {
            if (((Cfg) this.getDestinations().get(i)).model == model) {
                return (Cfg) this.getDestinations().get(i);
            } else {
                Cfg ret = ((Cfg) this.getDestinations().get(i)).getDescendantForModel(model);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9FDF5084-1008-4FB3-2340-AB64FAB257E2]
    // </editor-fold> 
    /**
     * 
     * @param mode
     */
    public void setMode(Mode mode) {
        if (this.mode != mode) {
            //System.out.println("En " + this + " intento poner el modo " + mode);
            this.mode = mode;
            if (this.mode != null) {
                if (this.hasValue) {
                    ArrayList<SNode> possibleValues = this.mode.getValues();
                    //System.out.println("En el modo "+mode+" los posibles valores son "+possibleValues);
                    if (!possibleValues.contains(this.value)) {
                        this.value = this.mode.getDefaultValue();
                        //System.out.println("Pongo el valor "+this.value);
                    }
                }
                ArrayList<Cfg> childsForLoop = (ArrayList<Cfg>) this.getDestinations().clone();
                for (int i = 0; i < this.mode.getSubModes().size(); i++) {
                    Mode thisChildMode = (Mode) this.mode.getSubModes().get(i);
                    //System.out.println("++Trato el modo " + thisChildMode);
                    for (int i2 = 0; i2 < this.getDestinations().size(); i2++) {
                        if (thisChildMode.getSystems().contains(((Cfg) this.getDestinations().get(i2)).getModel())) {
                            Cfg childCfg = (Cfg) this.getDestinations().get(i2);
                            childCfg.setPossibleModes(this.getModesForChild(childCfg.getModel()));
                            if (childCfg.getMode() != null) {
                                if (!childCfg.getPossibleModes().contains(childCfg.getMode())) {
                                    childCfg.setMode(thisChildMode);
                                }
                            } else {
                                childCfg.setMode(thisChildMode);
                            }
                            childsForLoop.remove(childCfg);
                        }
                    }
                }
                if (!this.mode.getSubModes().contains(this.subMode)) {
                    if (this.mode.getSubModes().size() == 0) {
                        this.subMode = null;
                    } else {
                        if (this.mode.getSubModes().contains(this.mode.getDefaultSubMode())) {
                            this.subMode = (this.mode.getDefaultSubMode());
                        } else {
                            this.subMode = ((Mode) this.mode.getSubModes().get(0));
                        }
                    }
                }
                // Este segundo pase por los submodos es para conseguir que al cargar la aplicación
                // muestre el subModo correcto.
                for (int i = 0; i < this.mode.getSubModes().size(); i++) {
                    Mode thisChildMode = (Mode) this.mode.getSubModes().get(i);
                    for (int i2 = 0; i2 < this.getDestinations().size(); i2++) {
                        if (thisChildMode.getSystems().contains(((Cfg) this.getDestinations().get(i2)).getModel())) {
                            Cfg childCfg = (Cfg) this.getDestinations().get(i2);
                            /* TODO: Hay que tener especial cuidado con los submodos por defecto
                             * parece que ahora funciona bien, pero quizá no esté bien con otro juego de
                             * pruebas.  Posiblemente el problema esté en cuál es el submodo por defecto
                             * que toma cuando éste no existe, por ejemplo en un grupo.
                             */
                            if (!childCfg.getPossibleModes().contains(childCfg.getMode())) {
                                if (childCfg.getPossibleModes().contains(this.subMode)) {
                                    childCfg.setMode(this.subMode);
                                }
                            }
                        }
                    }
                }
                while (childsForLoop.size() > 0) {
                    //System.out.println("En cfg pongo el primer hijo " + childsForLoop.get(0) + " a nulo para borrarlo");
                    ((Cfg) childsForLoop.get(0)).setMode(null);
                    childsForLoop.remove(0);
                }


            } else {
                this.value = null;
            }

            //System.out.println("Tras el cambio el valor "+this+" es "+this.getValue());
            //System.out.println("y el modo "+this.getMode());
            //System.out.println("Intentaron que fuera "+mode);
            notifyObs();
            //System.out.println("En "+this+" he intentado poner el modo "+mode);
        }
    }

    /**
     * 
     * @param value
     * @return
     */
    public boolean isValidValue(Value value) {
        if (this.hasValue) {
            if (this.getMode()!= null){
                ArrayList<SNode> possibleValues = this.getMode().getValues();
                for (int i = 0; i < possibleValues.size(); i++) {
                    if (((Value) possibleValues.get(i)).isValid(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FA596DB4-5954-42B2-F5AE-14B3E13D658D]
    // </editor-fold> 
    /**
     * 
     * @return
     */
    public SubSystem getSubSystem() {
        return model;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E7ADB82A-B80A-4A80-2A3F-79B8EC8C2D3D]
    // </editor-fold> 
    /**
     * 
     * @return
     */
    public Value getValue() {
        if (this.hasValue) {
            return value;
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.49A32127-4AFB-A085-0B75-016EBBD0E488]
    // </editor-fold> 
    /**
     * 
     * @param value
     */
    public void setValue(Value value) {
        if (this.hasValue) {
            if (this.isValidValue(value)) {
                if (this.value == null) {
                    this.value = value;
                    notifyObs();
                } else {
                    if (!this.value.equals(value)) {
                        this.value = value;
                        notifyObs();
                    }
                }
            } else {
                System.err.println("The value " + value + " is not valid for the model " + this);
                /*if (!this.isValidValue(this.value)) {
                this.value=this.getMode().getDefaultValue();
                }*/
            }
        } else {
            System.err.println("The cfg " + this + " has no values");
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
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9EE6FF2A-28DE-9313-9888-DDFE4BBA8BEB]
    // </editor-fold> 
    public Element toXML(Document doc, Class tagClass, boolean onlyIdent) {
        if (this.getMode() != null) {
            Element ret = super.toXML(doc, tagClass, onlyIdent);
            if (ret != null) {

                // Add model
                ret.setAttribute("model", this.getSubSystem().getName());
                // Add mode
                ret.setAttribute("mode", this.getMode().getName());
                // Add value
                if (this.hasValue && this.value != null) {
                    ret.setAttribute("value", this.getValue().getName());
                    if (this.value.implementsInterface(ValueDataInterface.class)) {
                        ret.setAttribute("dataValue", this.getData().toUnformattedString());
                    }
                }
            }
            return ret;
        } else {
            return null;
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public boolean isXMLExportable() {
        boolean ret = super.isXMLExportable();

        ret = ret && (this.mode != null);

        return ret;
    }

    /**
     * 
     * @return
     */
    public SubSystem getModel() {
        return model;
    }

    /**
     * 
     * @param node
     * @return
     */
    @Override
    public boolean loadFromXML(Node node) {
        boolean ret = super.loadFromXML(node);
        //System.out.println("Ejecuto el codigo Cfg.loadFromXML()");
        return ret;
    }
}

