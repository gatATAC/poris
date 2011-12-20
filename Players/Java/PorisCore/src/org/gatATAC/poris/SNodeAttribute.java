/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris;

/**
 *
 * @author txinto
 */
public class SNodeAttribute {
    private String name;
    private String content;
    private boolean visible;

    /**
     * 
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * 
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param name
     * @param content
     * @param isVisible
     */
    public SNodeAttribute(String name, String content, boolean isVisible) {
        this.name = name;
        this.content = content;
        this.visible = isVisible;
    }

    @Override
    public String toString() {
        return "["+this.name+":"+this.content+"]";
    }
}
