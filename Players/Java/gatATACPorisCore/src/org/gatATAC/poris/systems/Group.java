/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems;

import org.w3c.dom.Node;

/**
 *
 * @author osiris
 */
public class Group extends SubSystem {
    public Group(String name) {
        super(name);
    }

    @Override
    public boolean loadFromXML(Node node) {
        boolean ret = super.loadFromXML(node);
        return ret;
    }

}
