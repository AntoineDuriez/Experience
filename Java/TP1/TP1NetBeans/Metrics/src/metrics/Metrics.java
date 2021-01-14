/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Un logiciel de traitement de données pour l'analyse de texte littéraire, qui compte le nombre de mot, les occurences de lettres et les 20 mots les plus utilisés.
 *@author Alexandra Marouby / Adrien Narayanane
 */
public class Metrics {
    //Instanciation des champs
    private long nombreMot; 
    private long [] listeLettre;
    private Map <String, Integer> indexMotIteration;
    private List <String> listeMot;
    /*On utilise une liste sans doublons pour compter les mots plutôt qu'une TreeSet car 
    on a besoin de la fonction .get() par la suite, qui n'existe pas pour les TreeSet*/
    private int [] listeValeurIndex;
    private String [] listeCleIndex;
    //reconstitution de mots
    private String lecteur;
    //constructeur
    /**
     *Initialise les champs de Metrics en vu de l'analyse du texte.
     */
    public Metrics() {
        this.nombreMot = 0;
        this.listeLettre = new long[26];
        for (int i = 0; i < 26; i++) {
            this.listeLettre[i] = 0;
        }
        this.indexMotIteration = new HashMap<String, Integer>();
        this.listeMot = new ArrayList <String>();
        this.lecteur = "";
    }
    //Méthodes
    /**
     *Remplis l'index avec un mot et compte le nombre de fois où il apparaît dans le texte.
     * @param mot       mot extrait du texte
     */
    public void Dictionnaire(String mot){
        int i;
        //le mot est déjà contenu
        if (this.indexMotIteration.containsKey(mot)){
            i = this.indexMotIteration.get(mot);
            //incrémentation de l'apparition du mot
            this.indexMotIteration.put(mot, i + 1);
        }else{
            //ajout du nouveau mot dans la Map
            this.indexMotIteration.put(mot, 1);
        }
    }
 
    /**
     *Trie la Map indexMotIteration dans 2 tableaux : un pour les mots (clés) et un pour le nombre d'occurence des mots (valeurs).
     */
    public void trieurDeDico(){
        //Initialisation des champs listeCleIndex et listeValeurIndex
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
     * Compte le nombre de lettre et le nombre de mot présent dans le texte.
     * @param caractere    le caractere actuellement lu par le buffer
     * @param preced    le caractere précédemment lu
     */
    public void compter(char caractere, char preced){
        int i;
        char analyseCaractere;
        //toutes les lettres sont analysées comme des minuscules
        if(caractere >= 'A' && caractere <= 'Z'){
            analyseCaractere = Character.toLowerCase(caractere);
        }else{
            analyseCaractere = caractere;
        }
        if((analyseCaractere >= 'a' && analyseCaractere <= 'z')){
            //incrémentation de la liste de lettre
            i = (int) analyseCaractere - 'a';
            this.listeLettre[i]++;
            //incrémentation de la lettre actuelle pour former un mot
            this.lecteur = this.lecteur + analyseCaractere;
            
            //le caractere n'est pas une lettre et suit une lettre
        }else if(!(analyseCaractere >= 'a' && analyseCaractere <= 'z') && ((preced >= 'a' && preced <= 'z') || (preced >= 'A' && preced <= 'Z'))){
            //un nouveau mot vient d'être crée
            this.nombreMot++;
            //ajout à la liste de mot s'il n'est pas déjà présent
            if(!(listeMot.contains(lecteur))){  
                this.listeMot.add(lecteur);
            }
            this.Dictionnaire(lecteur);
            //le lecteur est prêt à la réception d'un nouveau mot
            this.lecteur = "";
        }
    }
    /**
     *Lit le texte caractère par caractère
     * @param texte le texte a analyser, passé en argument
     * @throws FileNotFoundException    erreur de fichier non présent
     * @throws IOException              erreur de lecture buffer
     */
    public void Lecture(String texte) throws FileNotFoundException, IOException{
        InputStream flux = new FileInputStream(texte);
        InputStreamReader lecture = new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(lecture);
        int caractere;
        char caractereCible;
        char preced = ' ';
        //lecture du texte
        while((caractere = buff.read()) != -1){
            caractereCible = (char) caractere;
            //appel à la méthode pour compter les lettres et les mots
            this.compter(caractereCible, preced);
            preced = caractereCible;    
        }
        //dernière vérification d'incrémentation après la sortie de boucle
        if((preced >= 'a' && preced <= 'z') || ((preced >= 'A' && preced <= 'Z'))){
            this.nombreMot++;   
            this.Dictionnaire(this.lecteur);    
            this.listeMot.add(this.lecteur);
        }
        buff.close();
    }
    /**
     *Vérifie que le nom de fichier passé en paramètre est correct. 
     * @param fic   nom du fichier à analyser.
     * @return      retourne vrai si le fichier existe, faux s'il est inexistant.
     */
     public boolean estFichierCorrect(String fic){
        File f = new File(fic);
        if(f.isFile()){
            return true;
        }else{
            System.out.println("Le fichier texte n'est pas valide");
            return false;
        }
    }
     /**
      *Vérifie la syntaxe des arguments en ligne de commande.
      * @param info tableau contenant le texte à analyser
      * @return     retourne vrai si le tableau est de taille 1 et que estFichierCorrect() est vrai, faux sinon.
      */
    public boolean estAnalysable(String [] info){
        if(info.length == 1){
            if(this.estFichierCorrect(info[0])){
                return true;
            }
        }
        System.out.println("Arguments d'entrées invalide");
        return false;
    }
    /**
     *Affiche le nombre de fois où chaque lettres de l'alphabet apparaît dans le texte.
     */
    public void AffichageOccurenceLettre(){
        char lettre = 'a';
        System.out.println("Occurence des lettres : ");
        for(int i = 0 ; i < 26 ; i++){
            //Affichage de la lettre puis de l'occurence d'apparition
            System.out.println(lettre +" : "+ this.listeLettre[i]);
            lettre++;
        }
    }
    /**
     *Affiche les 20 mots les plus lus, et le nombre de fois où ils ont été lus.
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
            for (int i = 0; i < compteur; i++) {
                System.out.println(this.listeCleIndex[i] + " = " + this.listeValeurIndex[i]);
            }
    }
    /**
     *Affiche les 3 résultat demandés dans cet ordre : le nombre de mots, les occurences de lettres, les 20 mots les plus lu. 
     */
    public void AfficherResultat(){
        System.out.println("Nombre total de mots : "+ this.nombreMot);
        this.AffichageOccurenceLettre();
        this.AffichageOccurence20Mots();
    }
    
    /**
     * Réceptionne le texte à analyser en argument, vérifie sa validité et lance la procédure d'analyse.
     * @param args the command line arguments   le texte a analyser
     */
    public static void main(String[] args){
        // TODO code application logic here
        Instant debut = Instant.now();
        try{
            Metrics test = new Metrics();
            if(test.estAnalysable(args)){  
                test.Lecture(args[0]);   /*lancement de la méthode de lecture du texte*/
                Instant fin = Instant.now();
                test.trieurDeDico();    /*tri des mots les plus fréquents*/
                test.AfficherResultat();
                Duration tempsExe = Duration.between(debut, fin);
                System.out.println("temps d'exe : "+ tempsExe.toMillis()+ " ms");
            }else{
                System.out.println("erreur");
            }
        }catch(IOException e){
            System.out.println("erreur dans les arguments d'entrées");
        }
    }
}