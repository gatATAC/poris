/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.XML;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

/**
 *  @author txinto
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.CE810E3C-42BE-890B-775A-6062604B3CAA]
// </editor-fold> 
public class XMLTreeModel implements TreeModel {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B01BEDDE-22EC-3BAF-1F22-F83AEB084F25]
    // </editor-fold> 
    private Node node;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E2C46AE5-F948-B58F-50D7-10226EB42B23]
    // </editor-fold> 
    /**
     * 
     * @param node
     */
    public XMLTreeModel (Node node) {
        this.node = node;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D81E9647-686E-38C0-3806-0310A500BBD2]
    // </editor-fold> 
    public void addTreeModelListener (TreeModelListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E2A0979D-7E41-5776-275D-217D1313BC3D]
    // </editor-fold> 
    public Object getChild (Object parent, int index) {
        Node nodeParent = (Node)parent;
        NamedNodeMap attrs = nodeParent.getAttributes(); 
        int attrCount = attrs!=null ? attrs.getLength() : 0; 
        if(index<attrCount) 
            return attrs.item(index); 
        NodeList children = nodeParent.getChildNodes(); 
        return children.item(index - attrCount);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FFCB2C3F-850D-4A23-6D31-10179469080B]
    // </editor-fold> 
    public int getChildCount (Object parent) {
        Node nodeParent = (Node)parent; 
        NamedNodeMap attrs = nodeParent.getAttributes(); 
        int attrCount = attrs!=null ? attrs.getLength() : 0; 
        NodeList children = nodeParent.getChildNodes(); 
        int childCount = children!=null ? children.getLength() : 0; 
        return attrCount + childCount; 
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.67FE981C-70B6-D6E4-BFC7-CBCF4FEE9E5D]
    // </editor-fold> 
    public int getIndexOfChild (Object parent, Object child) {
        Node nodeParent = (Node)parent; 
        Node childNode = (Node)child; 
 
        NamedNodeMap attrs = nodeParent.getAttributes(); 
        int attrCount = attrs!=null ? attrs.getLength() : 0; 
        if(childNode.getNodeType()==Node.ATTRIBUTE_NODE){ 
            for(int i=0; i<attrCount; i++){ 
                if(attrs.item(i)==child) 
                    return i; 
            } 
        }else{ 
            NodeList children = nodeParent.getChildNodes(); 
            int childCount = children!=null ? children.getLength() : 0; 
            for(int i=0; i<childCount; i++){ 
                if(children.item(i)==child) 
                    return attrCount + i; 
            } 
        } 
        throw new RuntimeException("this should never happen!");
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.803B680F-6F9F-D947-9129-D3D5DC1B4117]
    // </editor-fold> 
    public Object getRoot () {
        return node;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D6C05385-F172-3903-2435-01BDE627332A]
    // </editor-fold> 
    public boolean isLeaf (Object node) {
        return getChildCount(this.node)==0; 
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7DD66361-FB1D-CB8A-C170-EF3473BE96EF]
    // </editor-fold> 
    public void removeTreeModelListener (TreeModelListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8197A60A-099D-F2BA-E83D-20AB3A884F08]
    // </editor-fold> 
    public void valueForPathChanged (TreePath path, Object newValue) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    

}
