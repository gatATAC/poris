/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.MVC;

import java.util.ArrayList;

/**
 *
 * @author txinto
 */
public class Model extends BaseClass {

    private String name;
    private ArrayList<Observer> observers;
    private boolean notifyFlag=true;

    /**
     * 
     * @param name
     */
    public Model(String name) {
        this.name = name;
        observers = new ArrayList();
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        this.notifyObs();
    }

    /**
     * 
     * @return
     */
    public boolean isNotifyFlag() {
        return notifyFlag;
    }

    /**
     * 
     * @param notifyFlag
     */
    public void setNotifyFlag(boolean notifyFlag) {
        this.notifyFlag = notifyFlag;
    }
    
    /**
     * 
     * @param obs
     */
    public void attach(Observer obs) {
        observers.add(obs);
    }
    
    /**
     * 
     * @param obs
     */
    public void detach(Observer obs) {
        observers.remove(obs);
    }
    
    /**
     * 
     */
    public void notifyObs() {
        if (isNotifyFlag()) {
            for (int i=0; i<observers.size();i++) {
                observers.get(i).update();
            }
        }
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ret=super.equals(obj);
        if (!ret) {
            if (obj.getClass()==String.class) {
                ret=this.toString()==obj;
            }
        }
        return ret;
    }

    @Override
    protected Object clone() {
        return this.clone(this.getName());
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected Object clone(String name) {
        //return super.clone();
        Model ret = null;
        ret=new Model(name);
        return (Object)ret;
    }
}
