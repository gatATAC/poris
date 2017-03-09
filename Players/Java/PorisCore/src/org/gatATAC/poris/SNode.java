/**
 *
 */
package org.gatATAC.poris;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.gatATAC.XML.Utils;
import org.gatATAC.poris.MVC.BaseClass;
import org.gatATAC.poris.MVC.Model;
import org.gatATAC.poris.MVC.Observer;

/**
 * @author txinto
 *
 */
public class SNode extends Model {

    /**
     * 
     */
    protected static ArrayList<SNode> instanceList = new ArrayList<SNode>();
    /**
     * 
     */
    protected ArrayList<SNode> destinations;
    /**
     * 
     */
    protected ArrayList<SNode> sources;
    private String label;
    private ArrayList<SNodeAttribute> attributes;
    private int id;
    private int nodeTypeId;
    private boolean modeVisibleFlag = false;

    /**
     * 
     * @param name
     */
    public SNode(String name) {
        super(name);
        destinations = new ArrayList<SNode>();
        sources = new ArrayList<SNode>();
        attributes = new ArrayList<SNodeAttribute>();
        instanceList.add(this);
        this.label = name;
    }

    /**
     * 
     * @return
     */
    public ArrayList<SNodeAttribute> getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attr
     * @return
     */
    public boolean addAttribute(SNodeAttribute attr) {
        boolean ret = false;
        if (!attributes.contains(attr)) {
            ret = attributes.add(attr);
            notifyObs();
        }
        return ret;
    }

    /**
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
        notifyObs();
    }

    /**
     * 
     * @return
     */
    public int getNodeTypeId() {
        return nodeTypeId;
    }

    /**
     * 
     * @param nodeTypeId
     */
    public void setNodeTypeId(int nodeTypeId) {
        this.nodeTypeId = nodeTypeId;
        notifyObs();
    }

    /**
     * 
     * @param tagClass
     * @return
     */
    public Class<?> getThisPackageClass(Class<?> tagClass) {
        Class<?> ret = tagClass;
        while (ret.getPackage() != SNode.class.getPackage()) {
            ret = ret.getSuperclass();
        }
        return ret;
    }

    /**
     * 
     * @return
     */
    public boolean isModeVisibleFlag() {
        return modeVisibleFlag;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }

    /**
     * 
     * @param obs
     */
    @Override
    public void attach(Observer obs) {
        super.attach(obs);
        for (int i = 0; i < this.destinations.size(); i++) {
            this.destinations.get(i).attach(obs);
        }
    }

    /**
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
        notifyObs();
    }

    /**
     * 
     * @param child
     */
    public void addDestination(SNode child) {
        if (!destinations.contains(child)) {
            destinations.add(child);
            if (!child.sources.contains(this)) {
                child.addSource(this);
            }
            notifyObs();
        }
    }

    /**
     * 
     * @param parent
     */
    public void addSource(SNode parent) {
        if (!sources.contains(parent)) {
            sources.add(parent);
            if (!parent.destinations.contains(this)) {
                parent.addDestination(this);
            }
            notifyObs();
        }
    }

    /**
     * 
     * @param name
     * @return
     */
    public boolean isValidFromStr(String name) {
        //System.out.println("Chequeo en "+this+" la validez de "+name+" con resultado "+this.getName().equals(name));
        //System.out.println("this.getName() es "+this.getName());
        return this.getName().equals(name);
    }

    /**
     * 
     * @return
     */
    public ArrayList<SNode> getDestinations() {
        return destinations;
    }

    SNode getDestinationsFromName(String name) {
        for (int i = 0; i < this.destinations.size(); i++) {
            if (this.destinations.get(i).isValidFromStr(name)) {
                return this.destinations.get(i);
            }
        }
        return null;
    }

    SNode getSourcesFromName(String name) {
        for (int i = 0; i < this.sources.size(); i++) {
            if (this.sources.get(i).isValidFromStr(name)) {
                return this.sources.get(i);
            }
        }
        return null;
    }

    /**
     * 
     * @return
     */
    public static ArrayList<SNode> getInstanceList() {
        return instanceList;
    }

    /**
     * 
     * @param name
     * @return
     */
    public static SNode getInstance(String name) {
        SNode ret = null;

        for (int i = 0; i < instanceList.size(); i++) {
            if (instanceList.get(i).getName().equals(name)) {
                ret = instanceList.get(i);
            }
        }

        return ret;
    }

    /**
     * 
     * @return
     */
    public boolean isConsistent() {
        return this.destinations != null && this.sources != null;
    }

    /**
     * 
     * @param doc
     * @param onlyIdent
     * @return
     */
    public Element toXML(Document doc, boolean onlyIdent) {
        try {
            return this.toXML(doc, this.getClass(), onlyIdent);
        } catch (Exception e){
            System.err.println("Pillé la excepción");
        }
        return this.toXML(doc, this.getClass(), onlyIdent);
    }

    /**
     * 
     * @param doc
     * @param tagClass
     * @param onlyIdent
     * @return
     */
    public Element toXML(Document doc, Class<?> tagClass, boolean onlyIdent) {
        if (this.isConsistent()) {
            Element ret = doc.createElement(Utils.decamelize(getThisPackageClass(tagClass).getSimpleName()));
            Element nameNode = doc.createElement("name");
            Utils.setTextContent(doc, nameNode, this.getName());
            ret.appendChild(nameNode);
            if (!onlyIdent) {
                Element idNode = doc.createElement("id");
                idNode.setAttribute("type", "integer");
                Utils.setTextContent(doc, idNode, Integer.toString(this.getId()));
                ret.appendChild(idNode);
                Element typeNode = doc.createElement("type");
                Utils.setTextContent(doc, typeNode, getThisPackageClass(tagClass).getSimpleName());
                ret.appendChild(typeNode);
                Element nodeTypeIdNode = doc.createElement("node-type-id");
                nodeTypeIdNode.setAttribute("type", "integer");
                Utils.setTextContent(doc, nodeTypeIdNode, Integer.toString(this.getNodeTypeId()));
                ret.appendChild(nodeTypeIdNode);

                Element labelsNode = doc.createElement("labels");
                labelsNode.setAttribute("type", "array");
                if (!this.getLabel().equals(this.getName())) {

                    Element labelNode = doc.createElement("label");
                    // Name
                    Element labelNameNode = doc.createElement("name");
                    Utils.setTextContent(doc, labelNameNode, this.getLabel());
                    labelNode.appendChild(labelNameNode);
                    // Scope
                    Element labelScopeNode = doc.createElement("scope-kind");
                    Element labelScopeOPMSNode = doc.createElement("name");
                    Utils.setTextContent(doc, labelScopeOPMSNode, "OPMS");
                    labelScopeNode.appendChild(labelScopeOPMSNode);
                    labelNode.appendChild(labelScopeNode);

                    labelsNode.appendChild(labelNode);
                }
                ret.appendChild(labelsNode);

                Element destsNode = doc.createElement("destinations");
                destsNode.setAttribute("type", "array");
                for (int i = 0; i < this.destinations.size(); i++) {
                    if (this.destinations.get(i).isXMLExportable()) {
                        Element destNode = doc.createElement("destination");
                        destNode.setAttribute("type", this.destinations.get(i).getClass().getSimpleName());
                        Element destIdNode = doc.createElement("id");
                        destIdNode.setAttribute("type", "integer");
                        Utils.setTextContent(doc, destIdNode, Integer.toString(this.destinations.get(i).getId()));
                        destNode.appendChild(destIdNode);
                        destsNode.appendChild(destNode);
                    }
                }
                ret.appendChild(destsNode);

                Element attrsNode = doc.createElement("node-attributes");
                attrsNode.setAttribute("type", "array");
                for (int i = 0; i < this.attributes.size(); i++) {
                    Element attrNode = doc.createElement("node-attribute");
                    Element attrNameNode = doc.createElement("name");
                    Utils.setTextContent(doc, attrNameNode, this.attributes.get(i).getName());
                    attrNode.appendChild(attrNameNode);
                    Element attrContentNode = doc.createElement("content");
                    Utils.setTextContent(doc, attrContentNode, this.attributes.get(i).getContent());
                    attrNode.appendChild(attrContentNode);
                    attrsNode.appendChild(attrNode);
                }
                ret.appendChild(attrsNode);
            }
            return ret;
        } else {
            System.err.println("Parece ser que el basemodel " + this + " no es consistente, veamos a ver...");
            System.err.flush();
            System.err.println("El SNode tiene los siguientes destinos: " + destinations);
            for (int i = 0; i < this.destinations.size(); i++) {
                System.err.print("El valor " + this.destinations.get(i) + "es consistente?: " + this.destinations.get(i).isConsistent());
            }
            return null;
        }
    }

    /**
     * 
     * @return
     */
    public boolean isXMLExportable() {
        return true;
    }

    /**
     * 
     * @param list
     * @return
     */
    public boolean subTree(ArrayList<SNode> list) {
        boolean ret = true;
        if (!list.contains(this)) {
            for (int i = 0; i < destinations.size() && ret; i++) {
                ret = ret && destinations.get(i).subTree(list);
            }
            if (ret) {
                ret = list.add(this);
            }
        }
        return ret;
    }

    /**
     * 
     * @param clase
     * @param instanceName
     * @return
     */
    public static SNode getSNodeInstance(Class<?> clase, String instanceName) {
        Class<?>[] intArgsClass = new Class[]{String.class};
        Object[] intArgs = new Object[]{instanceName};
        Constructor<?> intArgsConstructor;

        try {
            intArgsConstructor = clase.getConstructor(intArgsClass);

            try {
                return (SNode) intArgsConstructor.newInstance(intArgs);
            } catch (InstantiationException e) {
                System.err.println("SNode.getSNodeInstance"+e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                System.err.println("SNode.getSNodeInstance"+e.getLocalizedMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("SNode.getSNodeInstance"+e.getLocalizedMessage());
            } catch (InvocationTargetException e) {
                System.err.println("SNode.getSNodeInstance"+e.getLocalizedMessage());
            }
        } catch (NoSuchMethodException e) {
            System.err.println("SNode.getSNodeInstance"+e.getLocalizedMessage());
        }
        return null;
    }
    static HashMap<String, SNode> xmlLoaderHashMap = new HashMap<String, SNode>();

    /**
     * 
     * @param node
     * @param name
     * @return
     */
    public static Node getChildNodeWithName(Node node, String name) {
        boolean typeFound = false;
        NodeList childNodes = node.getChildNodes();
        Node ret = null;

        for (int i = 0; i < childNodes.getLength() && !typeFound; i++) {
            if (childNodes.item(i).getNodeName().equals(name)) {
                ret = childNodes.item(i);
                typeFound = true;
            }
        }
        return ret;
    }

    /**
     * 
     * @param list
     * @param clase
     * @return
     */
    public ArrayList<SNode> getFromListByClass(ArrayList<SNode> list, Class<?> clase) {
        ArrayList<SNode> ret = new ArrayList<SNode>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isDescendantOf(clase)) {
                ret.add(list.get(i));
            }
        }
        return ret;
    }

    /**
     * 
     * @param list
     * @param clase
     * @param name
     * @return
     */
    public SNode getFromListByClassAndName(ArrayList<SNode> list, Class<?> clase, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isDescendantOf(clase)) {
                if (list.get(i).getName().equals(name)) {
                    return list.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param node
     * @return
     */
    public boolean loadFromXML(Node node) {
        boolean ret = true;

        // Name
        String instanceName = Utils.getTextContent((Element) getChildNodeWithName(node, "name"));
        this.setName(instanceName);
        this.setLabel(instanceName);
        // Id
        String instanceId = Utils.getTextContent((Element) getChildNodeWithName(node, "id"));
        xmlLoaderHashMap.put(instanceId, this);
        this.setId(Integer.parseInt(instanceId));
        // Node Type Id
        String instanceNodeTypeId = Utils.getTextContent((Element) getChildNodeWithName(node, "node-type-id"));
        this.setNodeTypeId(Integer.parseInt(instanceNodeTypeId));
        // Label
        Node labelsNode = getChildNodeWithName(node, "labels");
        if (labelsNode != null) {
            NodeList childLabels = labelsNode.getChildNodes();
            for (int i = 0; i < childLabels.getLength(); i++) {
                Node labelNode = childLabels.item(i);
                if (labelNode.getNodeName().equals("label")) {
                    Node scopeNode = getChildNodeWithName(labelNode, "scope-kind");
                    if (scopeNode != null) {
                        Node scopeNameNode = getChildNodeWithName(scopeNode, "name");
                        String scopeName = Utils.getTextContent((Element) scopeNameNode);
                        if (scopeName.equals("OPMS")) {
                            this.setLabel(Utils.getTextContent((Element) getChildNodeWithName(labelNode, "name")));
                        }
                    }
                }
            }
        }
        // Attributes
        Node attrsNode = getChildNodeWithName(node, "node-attributes");
        if (attrsNode != null) {
            NodeList childLabels = attrsNode.getChildNodes();
            for (int i = 0; i < childLabels.getLength(); i++) {
                Node attrNode = childLabels.item(i);
                if (attrNode.getNodeName().equals("node-attribute")) {
                    String name, content;
                    Node attrNameNode = getChildNodeWithName(attrNode, "name");
                    if (attrNameNode != null) {
                        name = Utils.getTextContent((Element) attrNameNode);
                        Node attrContentNode = getChildNodeWithName(attrNode, "content");
                        if (attrContentNode != null) {
                            Node visibleNode = getChildNodeWithName(attrNode, "visibility");
                            boolean visible=false;
                            if (visibleNode!=null){
                                visible=Boolean.parseBoolean(Utils.getTextContent((Element)visibleNode));
                            }
                            content = Utils.getTextContent((Element) attrContentNode);
                            SNodeAttribute attr=new SNodeAttribute(name,content,visible);
                            this.addAttribute(attr);
                            //System.out.println("+++++ Hemos añadido a "+this+" el atributo "+attr);
                        }
                    }
                }
            }
        }
        // Destinations
        Node destinationsNode = getChildNodeWithName(node, "destinations");
        if (destinationsNode != null) {
            NodeList childDests = destinationsNode.getChildNodes();
            for (int i = 0; i < childDests.getLength(); i++) {
                Node destNode = childDests.item(i);
                if (destNode.getNodeName().equals("destination")) {
                    String destId = Utils.getTextContent((Element) getChildNodeWithName(destNode, "id"));
                    SNode newDest = (SNode) xmlLoaderHashMap.get(destId);
                    if (newDest != null) {
                        this.addDestination(newDest);
                    }
                }
            }
        }

        return ret;
    }

    /**
     * 
     * @param clase
     * @param node
     * @return
     */
    public static SNode fromXML(Class<?> clase, Node node) {
        SNode ret = getSNodeInstance(clase, "idle");
        if (ret.loadFromXML(node)) {
            return ret;
        } else {
            System.err.println("It was impossible to create the SNode from the given XML node");
            return null;
        }
    }

    /**
     * 
     * @param clase
     * @param name
     * @return
     */
    public static SNode createInstanceFromClass(
            Class<?> clase, String name) {
        Class<?>[] intArgsClass = new Class[]{clase};
        Constructor<?> intArgsConstructor;

        try {
            intArgsConstructor = clase.getConstructor(intArgsClass);
            Object[] intArgs = new Object[]{name};

            try {
                return (SNode) intArgsConstructor.newInstance(intArgs);
            } catch (InstantiationException e) {
                System.err.println(e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getLocalizedMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            } catch (InvocationTargetException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (NoSuchMethodException e) {
            System.err.println("BaseModel.createInstanceFromClass: " + e.getLocalizedMessage());
        }

        return new SNode(name);
    }

    /**
     * 
     * @param node
     * @param clase
     * @return
     */
    public static SNode getBaseModelFromNodeAndClassName(
            Node node, String clase) {
        SNode ret = null;

        try {
            Class<?> thisClass = Class.forName(clase);
            try {


                if (BaseClass.isDescendantOf(thisClass, SNode.class)) {
                    Method[] methods = thisClass.getMethods();
                    boolean found = false;
                    for (int i2 = 0;
                            i2 < methods.length && !found; i2++) {
                        if (methods[i2].getName().equals("fromXML")) {
                            Object[] invokeArgs = {node};
                            ret = (SNode) methods[i2].invoke(null, invokeArgs);
                            if (ret == null) {
                                System.err.println("Falló la funcion fromXML con clase " + thisClass.getSimpleName() + " y nodo " + node.getNodeName());
                            }
                            found = true;
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                System.err.println("BaseModel.getBaseModelFromNodeAndClass IllegalAccessException: " + e.getLocalizedMessage());
            } catch (InvocationTargetException e) {
                System.err.println("BaseModel.getBaseModelFromNodeAndClass InvocationTargetException: " + e.getLocalizedMessage());
                System.err.println("Node: " + node + " class: " + clase);
            } catch (Exception e) {
                System.err.println("BaseModel.getBaseModelFromNodeAndClass Exception: " + e.getLocalizedMessage());
            }

        } catch (ClassNotFoundException e) {
            //System.err.println("BaseModel.getBaseModelFromNodeAndClass ClassNotFoundException: "+e.getLocalizedMessage());
        }

        return ret;
    }
}
