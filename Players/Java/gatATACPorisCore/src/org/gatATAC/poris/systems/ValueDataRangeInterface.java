/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems;

/**
 *
 * @author txinto
 */
public interface ValueDataRangeInterface<T> extends ValueDataInterface<T> {
    public T getMax();
    public T getMin();
    public void setMax(T max);
    public void setMin(T min);

}
