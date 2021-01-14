/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author Alexa
 */
public class Test {
    
        char methodeCesar(int decalage, char lettre){
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
    
    public String decaleur(String mot){
        int i = 0;
        String retour = "";
        char c;
        for(i = 0 ; i < mot.length() ; i++){
            c = this.methodeCesar(1,mot.charAt(i));
            retour += c;
        }
        return retour;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Test testeur = new Test();
        Stream<String>t = new Scanner(new FileInputStream("test.txt")).tokens();
        System.out.println(t.map(s->s.split("'")).count());
    }
    
}
