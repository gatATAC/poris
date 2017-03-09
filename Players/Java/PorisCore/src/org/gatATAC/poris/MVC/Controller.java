/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.MVC;

/**
 *
 * @author txinto
 */
public class Controller extends Observer {
    private final Model model;
    private final View view;

    /**
     * 
     * @param model
     * @param view
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.model.attach(this);
    }
    
    /**
     * 
     */
    public void handleEvent() {
        // To override
    }
    
    /**
     * 
     */
    @Override
    public void update() {
        
    }
}
