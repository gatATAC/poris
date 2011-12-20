/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import org.gatATAC.poris.MVC.Model;
import org.gatATAC.poris.MVC.Observer;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author txinto
 */
public class SNodeLib extends Model {

    private final ArrayList<SNode> nodeList;

    /**
     * 
     * @param name
     */
    public SNodeLib(String name) {
        super(name);
        nodeList = new ArrayList();
    }

    /**
     * 
     */
    public void clear() {
        nodeList.clear();
        notifyObs();
    }

    /**
     * 
     * @param obs
     */
    @Override
    public void attach(Observer obs) {
        super.attach(obs);
        for (int i = 0; i < this.nodeList.size(); i++) {
            this.nodeList.get(i).attach(obs);
        }
    }

    /**
     * 
     * @return
     */
    public int size() {
        return nodeList.size();
    }

    /**
     * 
     * @return
     */
    public SNode last() {
        return this.nodeList.get(nodeList.size() - 1);
    }

    /**
     * 
     * @param items
     */
    public void addSNodes(ArrayList<SNode> items) {
        for (int i = 0; i < items.size(); i++) {
            this.nodeList.add(items.get(i));
        }
        notifyObs();
    }

    /**
     * 
     * @param node
     * @return
     */
    public ArrayList<SNode> fromXML(Node node) {
        nodeList.clear();
        NodeList instanceNodes = node.getChildNodes();

        for (int i = 0; i < instanceNodes.getLength(); i++) {
            String instanceNodeName = instanceNodes.item(i).getNodeName();
            //System.out.println("Compruebo el child " + instanceNodeName);
            Node instanceNode = instanceNodes.item(i);
            Node instanceTypeNode = SNode.getChildNodeWithName(instanceNode, "type");
            if (instanceTypeNode != null) {
                //System.out.println("Tiene el atributo type!!!" + instanceTypeNode);
                String instanceClassName = instanceTypeNode.getTextContent();
                //System.out.println("Tiene atributos!!! La clase es " + instanceClassName);
                try {
                    /* Parche, intentar que no haya que poner el path a mano */
                    Class nodeClass = Class.forName("org.gatATAC.poris." + instanceClassName);
                    if (nodeClass != null) {
                        SNode instance = SNode.fromXML(nodeClass, instanceNodes.item(i));
                        if (instance != null) {
                            nodeList.add(instance);
                            //System.out.println("Cargué perfectamente " + instanceNodeName);
                            //System.out.println("Generando la instancia " + instance);
                        } else {
                            //System.out.println("Falló la carga de " + instanceNodeName);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    //System.err.println("Me salto el nodo de clase " + instanceClassName);
                }
            } else {
                //System.out.println("Me salto el nodo " + instanceNodeName);
            }
        }
        notifyObs();
        return nodeList;
    }

    /**
     * 
     * @param doc
     * @return
     */
    public Element toXML(Document doc) {
        Element ret = doc.createElement("sub-systems-v4");
        ret.setAttribute("id", "systems");
        ret.setNodeValue("systems");
        for (int i = 0; i < nodeList.size(); i++) {
            try {
                Element childNode = nodeList.get(i).toXML(doc, false);
                if (childNode != null) {
                    ret.appendChild(childNode);
                }
            } catch (Exception e) {
                System.err.println("Ocurrió una excepción "+e.getLocalizedMessage()+" en el nodo "+nodeList.get(i));
            }
        }
        return ret;
    }
}
