/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exo_1;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * @author Alexa
 */
public class Exo_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        var cols = Stream.of("Purple", "Blue", "Red", "Yellow", 
                "Green", "Yellow", "Purple", "Black");
        String [] tabCols = {"Purple", "Blue", "Red", "Yellow", 
                "Green", "Yellow", "Purple", "Black"};
        //Stream par tableau
        Stream<String> cols2 = Arrays.stream(tabCols);
        Stream<String> cols3 = Arrays.stream(tabCols);
        Stream<String> cols4 = Arrays.stream(tabCols);
        Stream<String> cols5 = Arrays.stream(tabCols);
        //affichage couleur nom < 5
        cols.distinct().filter(c->c.length()>=5).forEach(System.out::println);
        //affichage des  triés
        cols2.distinct().filter(c->c.length()>=5)
                .peek(System.out::println)
                .sorted()
                .forEach(System.out::println);
        //compter le nb de couleur
        System.out.println(cols3.distinct().count());
        //afficher les 3 premières lettres de la liste des couleurs
        cols4.distinct().sorted().map(c->c.substring(0, 3))
                .forEach(System.out::println);
        //aficher le nb d'occurence de Yellow
        System.out.println(cols5.filter(c->c.equals("Yellow"))
                .count());
    }
}
