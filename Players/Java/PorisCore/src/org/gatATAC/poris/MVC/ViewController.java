/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.MVC;

/**
 *
 * @author txinto
 */
public class ViewController extends View {

    /**
     * 
     * @param model
     */
    public ViewController(Model model) {
        super(model,null);
    }

    /**
     * 
     * @param model
     * @param controller
     */
    public ViewController(Model model, Controller controller) {
        this(model);
    }
    
    /**
     * 
     */
    public void handleEvent() {
        // To override
    }
    
}
