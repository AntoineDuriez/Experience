package subencodestream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *Reprise du TP1 avec l'utilisation des Streams et des expressions lambda : subEnCode
 * @author Marouby Alexandra
 */
public class SubEnCodeStream {
    //Champs
    private String type;
    private int decalage;
   //Constructeurs
    public SubEnCodeStream() {
        //par défaut le type est Cesar
        this.type = "cesar";
        this.decalage = 0;
    }
    //Methodes
    /**
     *Vérifie que le string passé en paramètre corespond à un entier
     * @param rep       le string à analyser
     * @return          retourne true si le paramètre correspond à un entier, false sinon
     */
    public static boolean estEntier(String rep){
        try{
            Integer.parseInt(rep);
        }catch(NumberFormatException nfe){
            return false;
        }
            return true;
    }
    /**
     *Vérifie que le fichier est valide
     * @param fic   le fichier à vérifier
     * @return      retourne true si le fichier est valide, false sinon
     */
    public static boolean estExistant(String fic){
        File f = new File(fic);
        if(f.isFile()){
            return true;
        }else{
            return false;
        }
    }
    /**
     *Appelle la méthode de César sur le mot en argument 
     * @param mot       String sur lequel appliquer le cryptage
     * @param decalage  int représentant le décalage de lettre à appliquer
     * @return          retourne le string chiffré
     */
    public static String fromStringToEncryptionCesar (String mot, int decalage){
        int i = 0;
        String retour = "";
        char c;
        Cesar ces = new Cesar();
        for(i = 0 ; i < mot.length() ; i++){
            c = ces.methodeCesar(decalage,mot.charAt(i));
            retour += c;
        }
        return retour;
    }
    /**
     *Appelle la méthode de Polybe sur le mot en argument 
     * @param mot       String sur lequel appliquer le cryptage
     * @return          retourne le string chiffré
     */
    public static String fromStringToEncryptionPolybe(String mot){
        int i = 0;
        String retour = "";
        String s;
        Polybe pol = new Polybe();
            for(i = 0 ; i < mot.length() ; i++){
                s = pol.methodePolybe(mot.charAt(i));
                retour += s;
            }
        return retour;
    }
    /**
     *Vérifie la possibilité d'effectuer un chiffrement de Cesar sur le fichier texte
     * @param info      tableau de string contenant les arguments entrés à l'execution
     * @return          retourne true si le chiffrement de Cesar est faisable, false sinon
     * @throws IOException 
     */
    public boolean estCesar(String [] info) throws IOException{
        try{
            //vérifie le taille du tableau pour voir si la norme d'écriture imposée est respectée
            if(info[0].equals("cesar") && info.length == 3){
                this.type = info[0];
                //vérification que le décalage est bien un chiffre
                if(estEntier(info[1])){
                    //conversion
                    this.decalage = Integer.parseInt(info[1]);
                    if(estExistant(info[2])){
                        return true;
                    }
                }
            }
        }catch(ArrayIndexOutOfBoundsException aiooe){
            return false;
        }
        return false;
    }
    /**
     *Vérifie la possibilité d'effectuer un chiffrement de Polybe sur le fichier texte
     * @param info      tableau de string contenant les arguments entrés à l'execution
     * @return          retourne true si le chiffrement de Cesar est faisable, false sinon
     * @throws IOException 
     */
    public boolean estPolybe(String [] info) throws IOException{
        try{
            //vérifie le taille du tableau pour voir si la norme d'écriture imposée est respectée
            if(info[0].equals("polybe") && info.length == 2){
                this.type = info[0];
                    if(estExistant(info[1])){
                        return true;
                    }
                }
        }catch(ArrayIndexOutOfBoundsException aiooe){
            return false;
        }
        return false;
    }
    /**
     *Vérifie qu'il est possible d'effectuer un des chiffrements sur le contenu du fichier texte
     * @param info      tableau de string contenant les arguments entrés à l'execution
     * @return          retourne true si l'on peut effectuer un chiffrement de Cesar ou de Polybe, false sinon
     * @throws IOException 
     */
    public boolean estPossible(String [] info) throws IOException{
        if(this.estCesar(info) || this.estPolybe(info)){
           return true;
        }else{
            System.out.println("erreur dans les arguments d'entrées");
            return false;
        }
    }
    /**
     * @param args      les arguments en ligne de commande
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SubEnCodeStream chiffrage = new SubEnCodeStream();
        //le chiffrage est possible
        if(chiffrage.estPossible(args)){
            if(chiffrage.type.equals("cesar")){
                Stream <String>chiffreurC = Files.lines(Paths.get(args[2]));
                chiffreurC.map(s->fromStringToEncryptionCesar(s, chiffrage.decalage)).forEach(System.out::println);
            }else{
                Stream<String>chiffreurP = Files.lines(Paths.get(args[1]));
                chiffreurP.map(s->fromStringToEncryptionPolybe(s)).forEach(System.out::println);
            }
        }
    }    
}
