/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems;

/**
 *
 * @author txinto
 */
public interface Data<T> {
    public void setDataValue(T dataValue);
    public T getDataValue();
    public void setDataValueFromString(String strValue);
    @Override
    public String toString();
    public String toUnformattedString();
}
