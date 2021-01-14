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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexandra Marouby / Adrien Narayanane
 */
public class Polybe {
    //Champs d'instance
    private final Map <Character, Integer> carrePolybe = new HashMap<Character, Integer>();
    //constructeur
    /**
     *Construit le carré de Polybe.
     */
    public Polybe(){
        int i;
        int j;
        char lettre = 'a';
        for(i = 0 ; i < 5 ; i++){
            for(j = 0 ; j < 5 ; j++){
                if(lettre == 'i'){
                    lettre++;
                }
                //construction du carré
                this.carrePolybe.put(lettre, (((i+1) * 10) + j + 1 ));
                lettre++;
            }
        }
    }
    //Méthodes
    /**
     *Applique la méthode de Polybe sur le texte.
     * @param texte texte sur lequel est appliqué le chiffrage
     * @return      renvoie le texte une fois crypté si le try réussi, renvoie un message d'erreur sinon
     * @throws FileNotFoundException    erreur dans le fichier
     * @throws IOException              erreur dans le buffer
     */
    public String methodePolybe(String texte) throws FileNotFoundException, IOException{
        InputStream flux = new FileInputStream(texte);
        InputStreamReader lecteur = new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(lecteur);
        int carac;
        char valeur;
        String cryptage = "";
        while((carac = buff.read()) != -1){
            valeur = (char) carac;
            if(valeur >= 'A' && valeur <= 'Z'){
                valeur = Character.toLowerCase(valeur);
            }
            if(valeur == 'i'){
                valeur = 'j';
            }
            if((this.carrePolybe.containsKey(valeur))){
                cryptage += this.carrePolybe.get(valeur);
            }else{
                cryptage += valeur;
            }
        }
        return cryptage;
    }
}
