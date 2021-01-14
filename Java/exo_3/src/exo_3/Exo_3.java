/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exo_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Alexa
 */
public class Exo_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Stream<String>fstream1 = Stream.empty();
        Stream<String>fstream2 = Stream.empty();
        try{
            fstream1 = Files.lines(Paths.get("11-0.txt"));
            fstream2 = Files.lines(Paths.get("11-0.txt"));
        }catch(IOException ie){
            System.out.println("impossible d'accéder au document");
        }
        //lire le nb de ligne
        System.out.println("Nb de ligne : "+ fstream1.count());
        //taille de la plus grande ligne
        Stream<Integer>fstreamLength = fstream2.map(s->s.length());
        System.out.println(fstreamLength.max(Comparator.naturalOrder()).get());
        /*System.out.println("Taille plus grde ligne : "+
                fstreamLength.max(Comparator.naturalOrder()).get());*/
        //***********************************************************
        InputStream in = new FileInputStream("11-0.txt");
        Stream<String>cstream1 = new Scanner(in).tokens();
        System.out.println("Nb mot : "+ cstream1.count());
        InputStream on = new FileInputStream("11-0.txt");
        Stream<String>cstream2 = new Scanner(on).tokens();
        Stream<Integer>cstreamLength = cstream2.map(s->s.length());
        System.out.println("taille mot le plus long : "+
                cstreamLength.max(Comparator.naturalOrder()).get());
        //***les 20 mots les plus utilisés***
        InputStream an = new FileInputStream("11-0.txt");
        Stream<String> word = new Scanner(an).tokens();
        Map<String, Integer> Mword = word.collect(Collectors.toMap(s->s,
                i->1,
                (old, ne) -> old+ne));
        System.out.println(Mword);
        
                
                
    }
        
    
}
