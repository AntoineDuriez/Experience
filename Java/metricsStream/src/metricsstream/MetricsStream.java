
package metricsstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *Reprise du TP1 avec utilisation de Streams et expressions lambda : Exercice Metrics
 * @author Marouby Alexandra
 */
public class MetricsStream {
    //Champs
    private Map<String, Integer> indexMotIteration;
    private Map<Character, Integer> indexLettre;
    private int []listeValeurIndex;
    private String []listeCleIndex;
    private List<String>listeMot;
    private Duration tempsExe;
    
    //Constructeurs
    /**
     * Initilise les champs
     */
    public MetricsStream(){
        this.indexMotIteration = new HashMap<String,Integer>();
        this.listeMot = new ArrayList<String>();
    }
    //Méthodes
    /**
     *Vérifie que le fichier est valide
     * @param fic   le fichier à vérifier
     * @return      retourne true si le fichier est valide, false sinon
     */
    public static boolean estFichierCorrect(String fic){
        File f = new File(fic);
        if(f.isFile()){
            return true;
        }else{
            System.out.println("Le fichier texte n'est pas valide");
            return false;
        }
    }
    /**
    *Vérifie que l'on peut effectuer les opérations d'analyse sur le fichier texte 
    * @param info     tableau de string contenant les paramètres entrés à l'execution
    * @return         retourne true si l'analyse est possible, false sinon
    * @throws java.io.FileNotFoundException 
    */
    public static boolean estAnalysable(String [] info) throws FileNotFoundException{
        //vérification de la taille du tableau
        if(info.length == 1){
            //vérification de l'existance du fichier
            if(estFichierCorrect(info[0])){
                return true;
            }
        }
        System.out.println("Arguments d'entrées invalide");
        return false;
    }
    /**
    *Tri un dictionnaire par ordre de valeur décroissante en créant 2 tableaux contenant respectivement les clés et les valeurs
    */
    public void trieurDeDico(){
        //Initialisation des champs devant contenir les clés et les valeurs triées
        this.listeCleIndex = new String [this.listeMot.size()];
        this.listeValeurIndex = new int [this.listeMot.size()];
        String cleActuelle;
        String cleMax = "";
        int valeurActuelle;
        int valeurMax = 0;
        //compteur pour placement dans les tableaux
        int compteurPlacement = 0;
        //tri tant que la liste de mots n'est pas vide : méthode du tri par sélection
        while(!(this.listeMot.isEmpty())){
            for(int i = 0; i < this.listeMot.size(); i++) {
                cleActuelle = this.listeMot.get(i);
                valeurActuelle = this.indexMotIteration.get(cleActuelle);
                if(valeurActuelle >= valeurMax){
                    valeurMax = valeurActuelle;
                    cleMax = cleActuelle;
                }   
            }    
            listeCleIndex[compteurPlacement] = cleMax;
            listeValeurIndex[compteurPlacement] = valeurMax;
            compteurPlacement++;
            this.listeMot.remove(cleMax); 
            valeurMax = 0;
        }
    }

    /**
    *Affiche les mots les plus vus avec leurs occurences pour un maximum de 20 mots
    */
    public void AffichageOccurence20Mots(){
        int compteur = 0; 
        if(this.listeCleIndex.length < 20){
            //le texte comprend moins de 20 mots : affichage de la liste entière
            compteur = this.listeCleIndex.length;
        }else{
            //le texte comprend plus de 20 mots : affichage des 20 premiers
            compteur = 20;
        }
        System.out.println("Les 20 mots les plus vus : ");
        for (int i = 0; i < compteur; i++){
            System.out.println(this.listeCleIndex[i] + " = " + this.listeValeurIndex[i]);
        }
    }
    /**
    *Transforme les lettres majuscules en lettres minuscules
    * @param c     le charactère à analyser
    * @return      retourne le lettre minuscule si c est compris entre A et Z, retourne c sans modifications sinon
    */    
    public static char toLowerChar(char c){
        char analyseur;
        if(c >= 'A' && c <= 'Z'){
            analyseur = Character.toLowerCase(c);
        }else{
            analyseur = c;
        }
        return analyseur;
    }
    /**
    *Compte le nombre de mots contenu dans le texte du fichier
    * @param file      fichier à analyser
    * @throws FileNotFoundException 
    */
    public static void compterMot(String file) throws FileNotFoundException{
        /*un mot est distingué car : le caractère qui suit la dernière lettre n'est pas alpha-numérique
        et le caractère qui suit celui-ci est une lettre
        */
        Stream<String>nbMot = new Scanner(new FileInputStream(file)).useDelimiter("\\w*\\W\\w").tokens();
        System.out.println("Nombre de mots du texte : "+ nbMot.count());
    }
    /**
     *Remplit une liste de tous les mots contenu dans le fichier, sans doublons
     * @param file      fichier d'où l'on extrait les mots
     * @throws FileNotFoundException 
     */
    public void remplisseurListe(String file) throws FileNotFoundException{
        //Tout ce qui n'est pas un caractère alpha-numérique agit comme un séparateur entres les mots
        Stream<String>motDistinct = new Scanner(new FileInputStream(file)).useDelimiter("\\W").tokens();
        //on ne garde pas d'ensemble vide dans la liste ("")
        this.listeMot = motDistinct.distinct().filter(s->!(s.equals(""))).collect(Collectors.toList());
    }
    /**
     *Compte le nombre de lettre contenue dans le texte du fichier
     * @param file      fichier à analyser
     * @throws FileNotFoundException 
     */
    public void compterLettre(String file) throws FileNotFoundException{
        Stream<String>interStream = new Scanner(new FileInputStream(file)).tokens();
        Stream<Character>nbLettre;
        List<String>interList = new ArrayList<String>();
        interList = interStream.collect(Collectors.toList());
        nbLettre = interList.toString().chars()
                .mapToObj(c->(char)c)
                .map(c ->toLowerChar(c));//toutes les lettre sont traitées en étant en minuscule
        //création d'une map comptant les occurences de chaque lettres
        this.indexLettre = nbLettre.filter(c->(c>='a' && c<='z'))
                .collect(Collectors.toMap(c->c,
                j -> 1,
               (old1, ne) -> old1+ne));
        System.out.println("Occurence de lettre : "+ this.indexLettre);
    }
    /**
     * Compte les 20 mots les plus vus du texte
     * @param file      fichier à analyser
     * @throws FileNotFoundException 
     */
    public void compter20MotsPlusVu(String file) throws FileNotFoundException{
        Instant debut = Instant.now();
        Stream<String>motPlusVu = new Scanner(new FileInputStream(file)).useDelimiter("\\W").tokens();
            //création d'une map comptant les occurences de mots
            this.indexMotIteration = motPlusVu.filter(s->!(s.equals(""))).collect(Collectors.toMap(s-> s,
                    i -> 1,
                    (old, ne) -> old+ne));
            //rempli la liste des mots du texte, sans doublons
            this.remplisseurListe(file);
            Instant fin = Instant.now();
            this.tempsExe = Duration.between(debut, fin);
            this.trieurDeDico();
            this.AffichageOccurence20Mots();
    }
    /**
     * @param args      arguments en ligne de commande
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        MetricsStream chiffrage = new MetricsStream();
        //le fichier texte existe et est analysable
        if(estAnalysable(args)){
            compterMot(args[0]);
            chiffrage.compterLettre(args[0]);
            chiffrage.compter20MotsPlusVu(args[0]);
            System.out.println("temps d'exe : "+ chiffrage.tempsExe.toMillis() +"ms");
        }
    }
}
