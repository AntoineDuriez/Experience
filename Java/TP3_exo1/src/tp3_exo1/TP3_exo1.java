/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3_exo1;

/**
 *
 * @author Alexa
 */
public abstract class TP3_exo1 implements Computable {
    public static void printer(Computable c){
        System.out.println(c.compute(2, 3));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //avec expresion lambda
        Computable comp1 = (f, s) -> f+s;
        Computable comp2 = new Computable() {
            @Override
            public double compute(double f, double s) {
                return f*s;
            }
        };
        printer(comp1);
        printer(comp2);
    }
}
