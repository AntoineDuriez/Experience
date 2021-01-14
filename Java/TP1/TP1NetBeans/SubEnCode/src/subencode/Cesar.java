/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subencode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Alexandra Marouby / Adrien Narayanane
 */
public class Cesar {
    //Méthode  

    /**
     *Applique la méthode de Cesar sur le texte
     * @param texte     texte a chiffrer
     * @param decalage  nombre de lettre à décaler pour chiffrer
     * @return          retourne le texte crypté si le try réussi, sinon retourne un message d'erreur
     * @throws FileNotFoundException    erreur dans le fichier
     * @throws IOException              erreur dans le buffer
     */
    public String methodeCesar(String texte, int decalage) throws FileNotFoundException, IOException{
        InputStream flux = new FileInputStream(texte);
        InputStreamReader lecteur = new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(lecteur);
        int carac = 0;
        char valeur;
        String cryptage = "";
        int incrementCryptage = 0;
        while((carac = buff.read()) != -1){
            valeur = (char) carac;  /*auto cast pour obtenir le caractere ciblée*/
            if(valeur >= 'A' && valeur <= 'Z'){
                valeur = Character.toLowerCase(valeur);
            }
            if(valeur >= 'a' && valeur <= 'z'){ /*le caractere est une lettre*/
                while(incrementCryptage < decalage){   /*rouage > decalage*/
                    if(valeur == 'z'){  /*valeur == 'a'*/   /*si le caractere vaut 'z', incrementation manuelle vers 'a'*/
                        valeur = 'a';   /*valeur = 'z'*/
                    }else{
                        valeur++;   /*valeur--*/
                    }
                    incrementCryptage++;   /*rouage--*/
                }
                incrementCryptage = 0; /*remise à 0 de l'incrémenteur de décalage*/
            }
            cryptage += valeur; /*construction du texte crypté*/ 
        }
        return cryptage;
    }
}
