/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems;

import org.gatATAC.XML.XMLTreeModel;
import org.gatATAC.poris.systems.MVC.*;
import java.io.StringWriter;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author txinto
 */
public class SNodeXML extends View {

    private JTabbedPane mainPanel;
    private JTextArea xmlText;
    private JScrollPane xmlTextPanel;
    private JTree xmlTree;
    private JScrollPane xmlTreePanel;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    
    public SNodeXML(SNodeLib model, Controller controller) {
        super(model, controller);
        this.initComponents();
        this.update();
    }
    
    public SNodeXML(SNodeLib model) {
        super(model);
        this.initComponents();
        this.update();
   }
    
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new JTabbedPane();
        xmlText = new JTextArea();
        xmlTextPanel = new JScrollPane();
        xmlTree = new JTree();
        //this.xmlTree.setCellRenderer(new XMLTreeCellRenderer());
        xmlTreePanel = new JScrollPane();
        
        mainPanel.setName(this.getModel().getName()); // NOI18N
        mainPanel.setLayout(new java.awt.GridBagLayout());
        mainPanel.setName("xmlPanel"); // NOI18N
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(this.getModel().getName()+" XML")); // NOI18N

        xmlTextPanel.setName("xmlTextPanel"); // NOI18N

        xmlText.setColumns(20);
        xmlText.setRows(5);
        xmlText.setName("xmlText"); // NOI18N
        xmlTextPanel.setViewportView(xmlText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 3.0;
        mainPanel.add(xmlTextPanel, gridBagConstraints);

        xmlTreePanel.setName("xmlTreePanel"); // NOI18N

        xmlTree.setName("xmlTree"); // NOI18N
        xmlTreePanel.setViewportView(xmlTree);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 3.0;
        mainPanel.add(xmlTreePanel, gridBagConstraints);
    }
    @Override
    public void display() {
        this.mainPanel.setVisible(true);
        this.mainPanel.updateUI();
        mainPanel.updateUI();
        xmlText.updateUI();
        xmlTextPanel.updateUI();
        xmlTree.updateUI();
        xmlTreePanel.updateUI();
    }

    //private static int contador=0;

    @Override
    public void update() {
        if (dbf==null) {
            dbf = DocumentBuilderFactory.newInstance();
            try {
                db = dbf.newDocumentBuilder();
                doc = db.newDocument();
            } 
            catch (Exception exc)
            {
                System.err.println("Excepción en newDocumentBuilder ["+exc.getLocalizedMessage()+"] al hacer SNodeXML.update");
            }
        }
        try
        {
            //contador++;
            //System.out.println("Entro en SNodeXML.update por vez "+contador);
            doc = db.newDocument();
            Node osiXML = null;
            osiXML = ((SNodeLib)this.getModel()).toXML(doc);
            if (osiXML!=null) {
                try {
                doc.appendChild(osiXML);
                } catch (Exception e) {
                    System.err.println("SNodeXML.update: No sabemos por qué ocurre esta excepción: "+e.getLocalizedMessage()+" en el nodo "+osiXML);
                }
                XMLTreeModel treeModel = new XMLTreeModel(osiXML);
                this.xmlTree.setModel(treeModel);
                this.xmlTree.updateUI();

                // This transforms the xml to string
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StringWriter sw = new StringWriter();
                //StreamResult result = new StreamResult("/home/txinto/result.xml");
                StreamResult result = new StreamResult(sw);
                transformer.transform(source, result);
                String staux = sw.toString();

                xmlText.setText(staux);
            }
        }
        catch (Exception exc)
        {
            System.err.println("SNodeXML.update ["+exc.getLocalizedMessage()+"]");
        }
        mainPanel.updateUI();
        xmlText.updateUI();
        xmlTextPanel.updateUI();
        xmlTree.updateUI();
        xmlTreePanel.updateUI();
    }

    public JTabbedPane getMainPanel() {
        if (mainPanel==null) {
            initComponents();
            update();
        }
        display();
        return mainPanel;
    }
    
    public String getXMLString() {
        if (mainPanel==null) {
            initComponents();
            update();
        }
        display();
        return this.xmlText.getText();
    }
}
