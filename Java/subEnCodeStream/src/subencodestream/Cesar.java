
package subencodestream;

/**
 *Reprise du TP1 avec l'utilisation des Streams et des expressions lambda : Cesar
 * @author Marouby Alexandra
 */
public class Cesar {
    /**
     *Applique la méthode de Cesar
     * @param decalage      Int indiquant de combien de lettre l'on doti se décaler pour effectuer le chiffrage
     * @param lettre        caractère à décaler pour le chiffrage, si ce n'est pas une letre, ne subit pas de décalage
     * @return              retourne le char, décalé si c'est une lettre, identique sinon
     */
    public char methodeCesar(int decalage, char lettre){
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
}
