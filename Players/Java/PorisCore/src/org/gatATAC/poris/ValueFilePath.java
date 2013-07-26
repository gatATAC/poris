/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatATAC.poris;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author txinto
 */
public class ValueFilePath extends ValueString implements ValueDataInterface<String>{

    private String fileExtension;
    private String fileDescription;

    public ValueFilePath(String name, String defaultValue, String fileExtension) {
        super(name, defaultValue);
        this.fileExtension = fileExtension;
    }
    /**
     * 
     * @param name
     */
    public ValueFilePath(String name) {
        super(name);
    }

    @Override
    public Object clone(String strValue) {
        return new ValueFilePath(this.getName(), strValue, this.fileExtension);
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        if (!fileExtension.equals(this.fileExtension)) {
            this.fileExtension = fileExtension;
            notifyObs();
        }
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        if (!fileDescription.equals(this.fileDescription)) {
            this.fileDescription = fileDescription;
            notifyObs();
        }
    }

    @Override
    public boolean loadFromXML(Node node) {
        boolean ret = super.loadFromXML(node);
        // Name
        String fileExt = getChildNodeWithName(node, "file-extension").getTextContent();
        this.setFileExtension(fileExt);
        String fileDesc = getChildNodeWithName(node, "file-description").getTextContent();
        this.setFileDescription(fileDesc);
        return ret;
    }

    @Override
    public Element toXML(Document doc, Class tagClass, boolean onlyIdent) {
        Element ret = super.toXML(doc, tagClass, onlyIdent);
        if (!onlyIdent) {
            Element fileExtNode = doc.createElement("file-extension");
            fileExtNode.setTextContent(this.fileExtension);
            ret.appendChild(fileExtNode);
            Element fileDescNode = doc.createElement("file-description");
            fileDescNode.setTextContent(this.fileDescription);
            ret.appendChild(fileDescNode);
        }
        return ret;

    }
}
