/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems.MVC;

import java.util.ArrayList;

/**
 *
 * @author txinto
 */
public class BaseClass {
    
    public boolean isDescendantOf(Class ancestorClass) {
        Class thisClass=this.getClass();
        return isDescendantOf(thisClass,ancestorClass);
    }

    public boolean implementsInterface(Class interfaceClass) {
        boolean ret=false;
        Class[] interfaces=this.getClass().getInterfaces();
        for (int i=0;i<interfaces.length && !ret;i++) {
            ret=interfaces[i].equals(interfaceClass);
        }
        return ret;
    }

    public static boolean isDescendantOf(Class clase, Class ancestorClass) {
        Class thisClass=clase;
        while (thisClass!=null) {
            if (thisClass==ancestorClass) { 
                return true;
            }
            thisClass=thisClass.getSuperclass();
        }
        
        return false;
    }
    
    public static boolean isDescendantOf(String strClass, Class ancestorClass) {
        try {
            Class thisClass=Class.forName(strClass);
            return isDescendantOf(thisClass,ancestorClass);
        }
        catch (ClassNotFoundException e) {
            //System.err.println("BaseClass.isDescendantOf: ClassNotFoundException: "+e.getLocalizedMessage());
            return false;
        }
    }
}
