/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp10_exo2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
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
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *Reprise de l'analyseur de texte avec des threads
 * @author Marouby Alexandra Narayanane Adrien
 */
public class TP10_exo2 {
    //Champs
    private final int QUEUE_SIZE = 100000;
    private int N;
    private BlockingQueue<String> tableau;
    private ConcurrentHashMap<String,Integer> nbMotsVus;
    private List<String>listeMot;
    private int []listeValeurIndex;
    private String []listeCleIndex;
    
    //Constructeurs
    /**
     *Initialise les champs
     */
    public TP10_exo2(){
        this.tableau = new ArrayBlockingQueue<String>(this.QUEUE_SIZE);
        this.nbMotsVus = new ConcurrentHashMap<>();
        this.listeMot = new ArrayList<String>();
    }
    
    //Méthodes
    /**
     *Lit le fichier dont le nom est passé en paramètre et l'ajoute ligne par ligne dans la BlockingQueue
     * @param nomTexte  String contenant le nom du texte a traiter 
     */
    public void lecture(String nomTexte){
        //lecture du texte dans un stream
        try(Stream<String> texte = Files.lines(Paths.get(nomTexte))){
            texte.forEach(s->this.tableau.add(s));
        }catch(IOException ioe){    //le nom du texte n'est pas valide
            System.out.println("Le fichier est inexistant");
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
                String traiteur = Objects.requireNonNullElse(this.tableau.poll(), "");  //permet d'éviter les exceptions de type noSuchElementException
                Scanner toList = new Scanner(traiteur).useDelimiter("\\W"); //chaque caractère non alphe-numérique est un délimiteur
                while(toList.hasNext()){
                    String colporteur = toList.next();
                    //classement des mots dans la map intere
                    if(!nbMotLocal.containsKey(colporteur)){
                        nbMotLocal.put(colporteur, 1);
                    }else{
                        nbMotLocal.put(colporteur, nbMotLocal.get(colporteur) + 1);
                    }
                }
            }
        //classement des mots dans la ConcurrentHashMap
        Set<String> setteur = nbMotLocal.keySet();
        setteur.forEach((key) -> {
            this.nbMotsVus.compute(key, (k,v)->(v==null)?nbMotLocal.get(k):v + nbMotLocal.get(k));
        });
    }
    
    /**
     *Lance les tâches du producteur et des consommateurs, et affiche le résultat de la ConcurrentHashMap
     * @param args      Liste de String des arguments en ligne de commande
     */
    public static void main(String[] args) {
        TP10_exo2 tr = new TP10_exo2();
        //nombre de thread consommateur
        tr.N = Integer.parseInt(args[1]);
            try{
                Instant debut = Instant.now();  //début du décompte du temps d'éxécution
                /*On choisit de mesurer le temps d'éxécution pris pour remplir la ConcurrentHashMap, 
                pas le temps d'analyse du texte*/
                Thread producteur;  
                Runnable tacheProd = () -> tr.lecture(args[0]);     //tâche du thread producteur
                producteur = new Thread(tacheProd);
                producteur.start();
                producteur.join();
                Thread [] consommateur = new Thread[tr.N];      //création des threads consommateurs
                Runnable tacheCons = () -> tr.compterMots();   //tâche des threads consommateurs
                for (int i = 0; i < consommateur.length; i++) {
                    consommateur[i] = new Thread(tacheCons);  
                }
                for(Thread thread : consommateur){
                    thread.start();
                }
                for (Thread threader1 : consommateur) {
                    threader1.join();
                }
                tr.nbMotsVus.remove("");        //la ConccurentHashMap ne doit pas contenir de vide ""
                Instant fin = Instant.now();    //fin du décompte du temps d'éxecution
                Duration tempsExe = Duration.between(debut, fin);   //calcul du temps écoulé entre le début et la fin du temps d'éxécution
                System.out.println("temps d'éxécution pour ConcurrentHashMap, avec "+ tr.N +" travailleurs : "+ tempsExe.toMillis() +" ms");
             }catch(InterruptedException ie){   //exception pour arret de travail
                System.out.println("Interruption du travail");
                Logger.getLogger(TP10_exo2.class.getName()).log(Level.SEVERE, null, ie);
            }
        }            
    }  

