/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.MVC;

/**
 *
 * @author txinto
 */
public class View extends Observer {
    private final Model model;
    private final Controller controller;

    /**
     * 
     * @param model
     */
    public View(Model model) {
        this.model = model;
        this.controller = new Controller(model,this);
        this.model.attach(this);
    }
    
    /**
     * 
     * @param model
     * @param controller
     */
    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.model.attach(this);
    }
    
    /**
     * 
     */
    public void activate() {
        
    }
    
    /**
     * 
     */
    public void display() {
        
    }
    
    /**
     * 
     * @return
     */
    public Model getModel() {
        return model;
    }
    
}
