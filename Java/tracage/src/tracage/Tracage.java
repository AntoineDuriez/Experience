/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tracage;

import java.util.logging.*;

/**
 *
 * @author Alexa
 */
public class Tracage {
    private static final Logger pckLogger = Logger.getLogger("tracage");
    private static final Logger log = Logger.getGlobal();
    
    //avec pckLogger.entering/.exiting
    String test1(){
        pckLogger.entering(this.getClass().getName(),"read");
        pckLogger.info("méthode test1");
        String t = "test 1";
        pckLogger.exiting(this.getClass().getName(),"read");
        return t;
    }
    //sans
    String test2(){
        log.info("méthode test2");
        String t = "test 2";
        return t;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tracage tr = new Tracage();
        pckLogger.setLevel(Level.INFO);
        log.setLevel(Level.INFO);
        //affichaage des 2 tests
        System.out.println(tr.test1());
        System.out.println(tr.test2());
        //création de hand pour afficher le traçage à l'écran
        ConsoleHandler hand = new ConsoleHandler();
        hand.setLevel(Level.INFO);
        //affichage à l'écran de pckLogger et log
        pckLogger.addHandler(hand);
        log.addHandler(hand);     
    }
    
}
