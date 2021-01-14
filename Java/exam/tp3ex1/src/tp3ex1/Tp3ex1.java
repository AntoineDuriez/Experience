/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3ex1;

/**
 *
 * @author Alexa
 */
public class Tp3ex1 implements Computable{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Computable comp1 = (x,y)-> x+y;
        Tp3ex1 test = new Tp3ex1();
        double t = test.compute(3, 2);
        System.out.println(comp1.compute(3, 2));
        System.out.println(t);
        
    }    

    @Override
    public double compute(double arg1, double arg2) {
        return(arg1*arg2);
    }
}
