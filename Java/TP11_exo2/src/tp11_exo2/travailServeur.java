/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp11_exo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Classe pour l'analyse de mots, remplis la tâche du serveur
 * @author Marouby Alexandra Narayanane Adrien
 */
public class travailServeur {
    //Champs
    private final int QUEUE_SIZE = 100000;
    private int N;
    private BlockingQueue<String> tableau;
    private ConcurrentHashMap<String,Integer> nbMotsVus;
    //Champs pour le tri des 20 mots les plus vus
    private List<String>listeMot;
    private int []listeValeurIndex;
    private String []listeCleIndex;
    
    //Constructeur
    /**
     *Initialisation des champs
     */
    public travailServeur(){
        this.tableau = new ArrayBlockingQueue<String>(this.QUEUE_SIZE);
        this.listeMot = new ArrayList<String>();
        this.nbMotsVus = new ConcurrentHashMap<>();
        //le nombre de threads consommateurs est fixé par rapport au meilleur temps d'execution du TP précédent
        this.N = 5;
    }
    
    /**
     *Getteur de listeValeurIndex
     * @return      retourne listeValeurIndex
     */
    public int [] getListeValeurIndex(){
        return this.listeValeurIndex;
    }
    
    /**
     *Getteur de listeCleIndex
     * @return      retourne listeCleIndex
     */
    public String [] getListeCleIndex(){
        return this.listeCleIndex;
    }
    /**
     *Place les éléments d'une liste dans une BlockingQueue
     * @param texte     List des éléments à placer dans la BlockingQueue
     */
    public void lecture(List<String> texte){
        while(!texte.isEmpty()){
            int i = 0;
            this.tableau.add(texte.remove(i));  //le contenu de la liste est déplacé dans le BlockingQueue
            i++;
        }
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
                valeurActuelle = this.nbMotsVus.get(cleActuelle);
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
     *Compte les mots de la BlockingQueue pour les placer dans la ConcurrentHashMap avec leurs occurences
     */
    public void compterMots() {
        //map interne
        Map<String, Integer>nbMotLocal = new HashMap<>();
        //le travail s'effectue tant que la BlockingQueue n'est pas vide
        while(!this.tableau.isEmpty()){                
            String traiteur = Objects.requireNonNullElse(this.tableau.poll(), "");      //permet d'éviter les exceptions de type noSuchElementException
            Scanner toList = new Scanner(traiteur).useDelimiter("\\W");     //chaque caractère non alphe-numérique est un délimiteur
            while(toList.hasNext()){
                String colporteur = toList.next(); 
                //classement des mots dans la map interne
                if(!nbMotLocal.containsKey(colporteur)){
                    nbMotLocal.put(colporteur, 1);
                }else{
                    nbMotLocal.put(colporteur, nbMotLocal.get(colporteur) + 1);
                }
            }
        }
        Set<String> setteur = nbMotLocal.keySet();
        setteur.forEach((key) -> {
            this.nbMotsVus.compute(key, (k,v)->(v==null)?nbMotLocal.get(k):v + nbMotLocal.get(k));
        });
    }
    
    /**
     *Lance les tâches du producteur et des consommateurs, et affiche le résultat de la ConcurrentHashMap    
     * @param texte     Liste de String des arguments en ligne de commande
     */
    public void travail(List <String> texte) {
        try{
            Thread producteur;  
            Runnable tacheProd = () -> this.lecture(texte);     //tâche du thread producteur
            producteur = new Thread(tacheProd);
            producteur.start();
            producteur.join();
            Thread [] consommateur = new Thread[this.N];        //création des threads consommateurs
            Runnable tacheCons = () -> this.compterMots();      //tâche des threads consommateurs
            for (int i = 0; i < consommateur.length; i++) {
                    consommateur[i] = new Thread(tacheCons);  
            }
            for(Thread thread : consommateur){
                thread.start();
            }
            for (Thread threader1 : consommateur) {
                threader1.join();
            }
            this.nbMotsVus.remove("");      //la ConccurentHashMap ne doit pas contenir de vide ""
            Set<String> setteur = this.nbMotsVus.keySet();      
            setteur.forEach(s->this.listeMot.add(s));
            this.trieurDeDico();
        }catch(InterruptedException ie){        //exception pour arret de travail
            System.out.println("Interruption du travail");
            Logger.getLogger(travailServeur.class.getName()).log(Level.SEVERE, null, ie);
        }
    }
}
