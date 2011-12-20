/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.formatters;

import java.util.ArrayList;

/**
 *
 * @author txinto
 */
public abstract class ValueFormatter {
    private final String name;
    private String label;
    private final int id;
    private static ArrayList<ValueFormatter> instanceList=new ArrayList<ValueFormatter>();

    /**
     * 
     * @param name
     * @return
     */
    public static ValueFormatter getFormatterForName(String name) {
        ValueFormatter ret = null;

        for (int i = 0; i < instanceList.size(); i++) {
            if (instanceList.get(i).getName().equals(name)) {
                ret = instanceList.get(i);
            }
        }

        return ret;
    }

    /**
     * 
     * @param id
     * @return
     */
    public static ValueFormatter getFormatterForId(int id) {
        ValueFormatter ret = null;

        for (int i = 0; i < instanceList.size(); i++) {
            if (instanceList.get(i).getId()==id) {
                ret = instanceList.get(i);
            }
        }

        return ret;
    }


    /**
     * 
     * @param name
     * @param id
     */
    public ValueFormatter(String name, int id) {
        this.name = name;
        this.id = id;
        instanceList.add(this);
    }

    /**
     * 
     * @param name
     * @param label
     * @param id
     */
    public ValueFormatter(String name, String label, int id) {
        this(name,id);
        this.label = label;
        instanceList.add(this);
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
     * @return
     */
    public int getId() {
        return id;
    }
}
