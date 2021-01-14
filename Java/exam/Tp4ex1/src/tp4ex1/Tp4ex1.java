/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4ex1;

import java.util.stream.Stream;

/**
 *
 * @author Alexa
 */
public class Tp4ex1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String [] tab = {"Purple", "Blue", "Red", "Yellow",
                "Green", "Yellow", "Purple", "Black"};
        var cols = Stream.of(tab);
        cols.distinct().filter(w -> w.length()>=5).forEach(System.out::println);
        System.out.println("********************");
        var cols2 = Stream.of(tab);
        cols2.distinct().sorted()
                .filter(w -> w.length()>=5)
                .forEach(System.out::println);
        System.out.println("********************");
        var cols3 = Stream.of(tab);
        System.out.println("nb de couleurs différentes : "+ cols3.distinct().count());
        System.out.println("********************");
        var cols4 = Stream.of(tab);
        cols4.distinct().sorted().map(w -> w.substring(0, 3)).forEach(System.out::println);
        System.out.println("********************");
        var cols5 = Stream.of(tab);
        System.out.println("Apparition de Yellow : "+ cols5.filter(w -> w.equals("Yellow")).count());

    }
    
}
