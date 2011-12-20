/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris;

/**
 *
 * @param <T> 
 * @author txinto
 */
public interface Data<T> {
    /**
     * 
     * @param dataValue
     */
    public void setDataValue(T dataValue);
    /**
     * 
     * @return
     */
    public T getDataValue();
    /**
     * 
     * @param strValue
     */
    public void setDataValueFromString(String strValue);
    /**
     * 
     * @return
     */
    @Override
    public String toString();
    /**
     * 
     * @return
     */
    public String toUnformattedString();
}
