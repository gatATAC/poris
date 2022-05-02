/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris;

import org.gatATAC.XML.XMLTreeModel;
import org.gatATAC.poris.MVC.*;
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
    
    /**
     * 
     * @param model
     * @param controller
     */
    public SNodeXML(SNodeLib model, Controller controller) {
        super(model, controller);
        this.initComponents();
        System.err.println("Constructor 1");
        this.update();
    }
    
    /**
     * 
     * @param model
     */
    public SNodeXML(SNodeLib model) {
        super(model);
        this.initComponents();
        System.err.println("Constructor 2");
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
    /**
     * 
     */
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

    private static int contador=0;

    /**
     * 
     */
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
            contador++;
            System.err.println("Entro en SNodeXML.update por vez "+contador+" con el modelo "+this.getModel().getName());
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
                System.err.println("SNodeXML.update: 1");
                // This transforms the xml to string
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                System.err.println("SNodeXML.update: 2");                
                DOMSource source = new DOMSource(doc);
                StringWriter sw = new StringWriter();
                System.err.println("SNodeXML.update: 3");                
                //StreamResult result = new StreamResult("/home/txinto/result.xml");
                StreamResult result = new StreamResult(sw);
                transformer.transform(source, result);
                String staux = sw.toString();
                System.err.println("SNodeXML.update: 4");                

                xmlText.setText(staux);
                System.err.println("SNodeXML.update: 5");                
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

    /**
     * 
     * @return
     */
    public JTabbedPane getMainPanel() {
        if (mainPanel==null) {
            initComponents();
            System.err.println("getMainPanel");            
            update();
        }
        display();
        return mainPanel;
    }
    
    /**
     * 
     * @return
     */
    public String getXMLString() {
        if (mainPanel==null) {
            initComponents();
            System.err.println("getXMLString");
            update();
        }
        display();
        return this.xmlText.getText();
    }
}
