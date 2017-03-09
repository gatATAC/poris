/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris.player;

import org.gatATAC.poris.formatters.ValueFormatter;
import org.gatATAC.poris.*;
import org.gatATAC.poris.MVC.ViewController;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author txinto
 */
public class CfgGUI extends ViewController {

    private final CfgGUIPanel panel;
    private final ArrayList<CfgGUI> childrenCfgGUIs;
    private final ArrayList<Cfg> childrenCfgs;
    private final boolean isGroup;
    private final boolean showInvisible;

    public CfgGUI(Cfg cfg, boolean showLabel, boolean showFrame, boolean showInvisible) {
        super(cfg);
        this.showInvisible=showInvisible;
        boolean isGroupAux;
        isGroupAux = this.getCfg().getModel().isDescendantOf(Group.class);
        isGroup = isGroupAux;

        if (showFrame) {
            if (this.getCfg().getModel().toString().equals("-")) {
                showFrame = false;
            }
        }
        boolean mayHaveFrame = (this.getCfg().getDestinations().size() > 1);
        if (!mayHaveFrame) {
            if (this.getCfg().getDestinations().size() > 0) {
                if (((Cfg) this.getCfg().getDestinations().get(0)).getModel().isDescendantOf(SubSystem.class)) {
                    mayHaveFrame = true;
                }
            }
        }
        if (this.getCfg().getMode() != null) {
            mayHaveFrame = mayHaveFrame && !isGroup;
        }
        boolean mayHaveLabel = showLabel || isGroup;
        this.panel = new CfgGUIPanel(this, mayHaveFrame && showFrame, this.getCfg().isHasValue(), mayHaveLabel, isGroup,this.showInvisible);
        String aux = "";
        Value v = this.getCfg().getValue();
        if (v != null) {
            ValueFormatter vf = v.getFormatter();
            if (vf != null) {
                aux += "("+vf.getLabel()+")";
            }
        }
        this.panel.setLabel(this.getCfg().getLabel()+aux);
        this.panel.setModeChoices(this.getCfg().getPossibleModes());
        this.panel.setMode(this.getCfg().getMode());
        //System.out.println("En CfgGUI de "+this.getCfg()+ " he puesto el modo"+this.getCfg().getMode()+" en el panel");
        if (this.getCfg().isHasValue()) {
            this.panel.setValue(this.getCfg().getValue());
        }
        childrenCfgGUIs = new ArrayList();
        childrenCfgs = new ArrayList();
        //System.out.println("***Me encuentro en la CfgGUI "+this+" con cfg "+this.getCfg());
        for (int i = 0; i < this.getCfg().getDestinations().size(); i++) {
            Cfg thisCfg = (Cfg) this.getCfg().getDestinations().get(i);
            //System.out.println("Voy a tratar la cfg "+thisCfg+" que tiene modo "+thisCfg.getMode());
            CfgGUI childCfgGUI;
            if (this.getCfg().getMode() != null) {
                //System.out.println("El modo que he encontrado en la cfg padre es "+this.getCfg().getMode());
                boolean isSelectedInGroup = false;
                //System.out.println("En el cfg de "+this.getCfg()+" con modo "+this.getCfg().getMode()+" y mirando el hijo "+thisCfg);
                if (isGroup) {
                    isSelectedInGroup = (thisCfg.getMode() == this.getCfg().getSubMode());
                    //System.out.println("En el cfg de "+this.getCfg()+" con modo "+this.getCfg().getMode()+" y mirando el hijo "+thisCfg+" concluimos que está seleccionado? "+isSelectedInGroup);
                }
                childCfgGUI = new CfgGUI(thisCfg, !isGroup, mayHaveFrame,this.showInvisible);
                childrenCfgGUIs.add(childCfgGUI);

                boolean isWrapper = true;

                isWrapper = isWrapper && !this.getCfg().isHasValue();
                isWrapper = isWrapper && !(this.getCfg().getModel().getSubSystems().size() > 1);
                if (isWrapper) {
                    isWrapper = ((SubSystem) this.getCfg().getModel().getSubSystems().get(0)).getSubSystems().size() <= 1;
                }
                //System.out.println("El modelo "+this.getCfg().getModel()+" tiene "+this.getCfg().getModel().getSubSystems().size()+" hijos");


                //panel.addPanel(childCfgGUI.getPanel(), isGroup || isWrapper);
                panel.addPanel(childCfgGUI.getPanel(), isGroup, isSelectedInGroup);
            } else {
                childCfgGUI = new CfgGUI(thisCfg, true, mayHaveFrame,this.showInvisible);
                childrenCfgGUIs.add(childCfgGUI);
                panel.addPanel(childCfgGUI.getPanel(), false, false);
            }
            childrenCfgs.add(thisCfg);
            //System.out.println("Anyado la cfg "+thisCfg+" con modo "+thisCfg.getMode());
            thisCfg.notifyObs();
        }
        this.update();
    }

    public Cfg getCfg() {
        return (Cfg) this.getModel();
    }

    @Override
    public void handleEvent() {
        if (this.getCfg().isHasValue()) {
            Value valueToSet = (Value) this.panel.getValue();
            if (valueToSet != null && valueToSet.isConsistent()) {
                this.getCfg().setValue(valueToSet);
                super.handleEvent();
            } else {
                JOptionPane.showMessageDialog(this.panel, "The value '" + this.panel.getValue() + "' is not valid for " + this.getCfg() + " parameter.  ValueToSet=" + valueToSet);
            }
            //this.update();
        }
    }

    public void handleEventMode() {
        this.getCfg().setMode(this.panel.getSelectedMode());
        //System.out.println("En handleEventMode de "+this.getCfg()+"pongo el modo "+this.panel.getSelectedMode());
        //this.update();
    }

    @Override
    public void update() {
        super.update();
        this.panel.setModeChoices(this.getCfg().getPossibleModes());
        this.panel.setMode(this.getCfg().getMode());

        String formatStr="";
        Value v = this.getCfg().getValue();
        if (v != null) {
            ValueFormatter vf = v.getFormatter();
            if (vf != null) {
                formatStr += "("+vf.getLabel()+")";
            }
        }
        this.panel.setLabel(this.getCfg().getLabel()+formatStr);

        //System.out.println("En update de "+this.getCfg()+" pongo el modo "+this.getCfg().getMode());
        if (this.getCfg().isHasValue()) {
            this.panel.setValue(this.getCfg().getValue());
        }
    }

    public CfgGUIPanel getPanel() {
        return panel;
    }
}
