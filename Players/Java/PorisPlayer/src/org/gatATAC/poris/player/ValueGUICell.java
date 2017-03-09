/*
 * ValueGUICell.java
 *
 * Created on 14 de noviembre de 2008, 11:01
 */
package org.gatATAC.poris.player;

import org.gatATAC.poris.SNode;
import org.gatATAC.poris.Value;
import org.gatATAC.poris.ValueDataInterface;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import org.gatATAC.poris.ValueFilePath;

/**
 *
 * @author  txinto
 */
public class ValueGUICell extends javax.swing.JPanel {
    
    private final CfgGUI gui;
    private Value value;
    private Component valueCell;
    private javax.swing.JButton attrButton;
    private javax.swing.JButton fileButton;
    private SNodeAttributesPanel attrPanel;
    private final boolean showInvisible;

    /** Creates new form ValueGUICell */
    public ValueGUICell(CfgGUI gui, boolean showInvisible) {
        this.gui = gui;
        this.showInvisible = showInvisible;
        initComponents();
    }
    
    public CfgGUI getGUI() {
        return this.gui;
    }
    
    public void setValue(Value value) {
        if (this.value != value) {
            this.value = value;
            if (valueCell == null && value != null) {
                addCell();
            }
        }
        this.update();
    }
    
    public Value getValue() {
        return this.value;
    }
    
    private void cellComboActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.value != (Value) ((JComboBox) valueCell).getSelectedItem()) {
            this.value = (Value) ((JComboBox) valueCell).getSelectedItem();
            this.gui.handleEvent();
        }
    }
    private String prevCellTextContent = null;
    
    private void processCellTextChange() {
        String strToProcess = ((JTextField) valueCell).getText();
        if ((prevCellTextContent == null) || (!prevCellTextContent.equals(strToProcess))) {
            // The new value must match the previous one
            if (this.value.isValidFromStr(strToProcess)) {
                // Valid value
                this.gui.getCfg().setDataValueFromStr(strToProcess);
                this.gui.handleEvent();
                prevCellTextContent = strToProcess;
            } else {
                // Not valid value
                ((JTextField) valueCell).setText(this.gui.getCfg().getData().toString());
                this.updateUI();
            }
        }
    }
    
    private void cellTextFocusLost(java.awt.event.FocusEvent evt) {
        processCellTextChange();
    }
    
    private void cellTextActionPerformed(java.awt.event.ActionEvent evt) {
        processCellTextChange();
    }
    
    private void addCell() {
        try {
            java.awt.GridBagConstraints gridBagConstraints;
            
            if (valueCell != null) {
                this.remove(valueCell);
            }
            if (this.value != null) {
                if (value.implementsInterface(ValueDataInterface.class)) {
                    valueCell = new javax.swing.JTextField();
                    ((JTextField) valueCell).setText(this.gui.getCfg().getData().toString());
                    ((JTextField) valueCell).addActionListener(new java.awt.event.ActionListener() {
                        
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            cellTextActionPerformed(evt);
                        }
                    });
                    ((JTextField) valueCell).addFocusListener(new java.awt.event.FocusAdapter() {
                        
                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            cellTextFocusLost(evt);
                        }
                    });

                    /* TODO: Cambiar este Parche para que no se nos "chafe" la celda de texto */
                    Dimension dim = new Dimension();
                    dim = valueCell.getSize();
                    dim.height = 22;
                    dim.width = 7 * Math.min(this.value.toString().length(), 32);
                    valueCell.setMinimumSize(dim);
                } else {
                    valueCell = new javax.swing.JComboBox();
                    ((JComboBox) valueCell).addActionListener(new java.awt.event.ActionListener() {
                        
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            cellComboActionPerformed(evt);
                        }
                    });
                }
            }
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
            gridBagConstraints.weightx = 100.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.gridwidth=2;
            gridBagConstraints.gridx=0;
            add(valueCell, gridBagConstraints);
            if (this.value != null) {
                if (this.value.getAttributes().size() > 0) {
                    attrButton = new javax.swing.JButton();
                    attrButton.setText("i"); // NOI18N
                    attrButton.setName("attrButton"); // NOI18N
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
                    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
                    gridBagConstraints.gridx=10;
                    attrButton.addActionListener(new java.awt.event.ActionListener() {
                        
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            attrButtonActionPerformed(evt);
                        }
                    });
                    add(attrButton, gridBagConstraints);
                }
                if (value.getClass() == ValueFilePath.class) {
                    fileButton = new javax.swing.JButton();
                    fileButton.setText("f"); // NOI18N
                    fileButton.setName("fileButton"); // NOI18N
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
                    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
                    gridBagConstraints.gridx=9;
                    fileButton.addActionListener(new java.awt.event.ActionListener() {
                        
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            fileButtonActionPerformed(evt);
                        }
                    });
                    add(fileButton, gridBagConstraints);
                }
            }
        } catch (Exception e) {
            System.err.println("ValueGUICell-->AddCell: " + this.gui.getCfg().getModel().getName() + " : " + e.getLocalizedMessage());
        }
    }
    
    private void attrButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.attrPanel == null) {
            this.attrPanel = new SNodeAttributesPanel(this.value,
                    this.showInvisible);
        }
        this.attrPanel.setVisible(true);
    }
    
    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose the file with "+((ValueFilePath)this.value).getFileExtension()+" extension");
        chooser.setCurrentDirectory(new File("."));

        FileExtensionFilter filter = new FileExtensionFilter(((ValueFilePath)this.value).getFileExtension(), ((ValueFilePath)this.value).getFileExtension()+" file.");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String theFile = chooser.getSelectedFile().toString();
            File inputFile = new File(theFile);
            if (inputFile.exists()) {
               ((JTextField) valueCell).setText(theFile);
               this.processCellTextChange();
            }
        }
    }
    
    public void setChoices(ArrayList<SNode> choices) {
        if (choices.size() > 0) {
            if (this.valueCell == null) {
                if (value == null) {
                    value = (Value) choices.get(0);
                }
                addCell();
            }
            if (this.valueCell.getClass() == JComboBox.class) {
                ((JComboBox) this.valueCell).setModel(new DefaultComboBoxModel(choices.toArray()));
                this.valueCell.setEnabled(choices.size() > 1);
            }
        } else {
            this.value = null;
            if (this.valueCell != null) {
                this.valueCell.setVisible(false);
            }
            this.valueCell = null;
        }
    }
    
    private void update() {
        if (this.value != null) {
            if (this.value.implementsInterface(ValueDataInterface.class)) {
                if (this.valueCell.getClass() != JTextField.class) {
                    addCell();
                }
                ((JTextField) this.valueCell).setText(this.gui.getCfg().getData().toString());
            } else {
                if (this.valueCell.getClass() != JComboBox.class) {
                    addCell();
                    ((JComboBox) valueCell).setModel(new DefaultComboBoxModel(this.gui.getCfg().getMode().getValues().toArray()));
                    this.valueCell.setEnabled(this.gui.getCfg().getMode().getValues().size() > 1);
                }
                this.attrPanel = null;
                ((JComboBox) this.valueCell).setSelectedItem(this.value);
            }
            this.updateUI();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        setName("Form"); // NOI18N
        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
