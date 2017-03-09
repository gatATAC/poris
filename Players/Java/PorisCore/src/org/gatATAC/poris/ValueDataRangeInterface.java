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
public interface ValueDataRangeInterface<T> extends ValueDataInterface<T> {
    /**
     * 
     * @return
     */
    public T getMax();
    /**
     * 
     * @return
     */
    public T getMin();
    /**
     * 
     * @param max
     */
    public void setMax(T max);
    /**
     * 
     * @param min
     */
    public void setMin(T min);

}
