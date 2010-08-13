/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems;

/**
 *
 * @author txinto
 */
public class SNodeAttribute {
    private String name;
    private String content;
    private boolean visible;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
