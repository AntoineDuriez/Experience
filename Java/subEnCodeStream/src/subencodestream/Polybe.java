
package subencodestream;

import java.util.HashMap;
import java.util.Map;

/**
 *Reprise du TP1 avec l'utilisation des Streams et des expressions lambda : Polybe
 * @author Marouby Alexandra
 */
public class Polybe {
    //Champs
    private final Map <Character, Integer> carrePolybe = new HashMap<Character, Integer>();
    //Constructeur
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
    /**
     *Applique la méthode de Polybe
     * @param c     caractère à modifier pour le chiffrage
     * @return      retourne le caractère chiffré sous forme de string (par ex : 'a' devient "11")
     */
    public String methodePolybe(char c){
        String cryptage = "";
        char lettre = c;
        if(lettre >= 'A' && lettre <= 'Z'){
            lettre = Character.toLowerCase(lettre);
        }
        if(lettre == 'i'){
            lettre = 'j';
          }
       //si le caractère est une clé du carré de Polybe, il est chiffrable
        if((this.carrePolybe.containsKey(lettre))){
            cryptage += this.carrePolybe.get(lettre);
        }else{
            cryptage += lettre;
        }
        return cryptage;
    }
}
    

