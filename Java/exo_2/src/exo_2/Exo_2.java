/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exo_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Alexa
 */
public class Exo_2 {

    /**
     * @param args the command line arguments
     */
    
    char decaleur(int decalage, char lettre){
        int incrementCryptage = 0;
        char valeur = lettre; 
        if(valeur >= 'A' && valeur <= 'Z'){
                valeur = Character.toLowerCase(valeur);
            }
            if(valeur >= 'a' && valeur <= 'z'){ 
                while(incrementCryptage < decalage){   
                    if(valeur == 'z'){  
                        valeur = 'a';   
                    }else{
                        valeur++;   
                    }
                    incrementCryptage++;   
                }
        }
           return valeur;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Exo_2 test = new Exo_2();
        String S = "abcdefg 45634 fre";
        InputStream in = new FileInputStream("test.txt");
        /* crée un stream de char et le rempli avec S
        mapToObj : parce que S.chars() renvoie un flux d'entier -> conversion
        */
        Stream<String> l = new Scanner(in)
                .tokens();  //le stream est construit  
        l.forEach(System.out::println);
        Stream<Character> streamer = S.chars().mapToObj(c->(char)c)
                .map(c->test.decaleur(2, c));
        streamer.forEach(c->System.out.print(c));   
        
        
        
    }
    
}
