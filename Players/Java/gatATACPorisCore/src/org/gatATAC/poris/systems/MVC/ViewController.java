/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.poris.systems.MVC;

/**
 *
 * @author txinto
 */
public class ViewController extends View {

    public ViewController(Model model) {
        super(model,null);
    }

    public ViewController(Model model, Controller controller) {
        this(model);
    }
    
    public void handleEvent() {
        // To override
    }
    
}
