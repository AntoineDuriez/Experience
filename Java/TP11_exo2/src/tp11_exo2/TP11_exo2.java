/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp11_exo2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *Reprise du TP d'analyse de texte avec Thread et protocole TCP client-serveur : partie serveur
 * @author Marouby Alexandra Narayanane Adrien
 */
public class TP11_exo2 {
    //Champs
    private int iClients = 1;
    
    //Méthodes
    /**
     *Création du serveur et dialogue avec ses clients
     * @param args      Liste de String des arguments en ligne de commande
     */
    public static void main(String[] args) {
        try(ServerSocket serveur = new ServerSocket(9102)){     //création de la socket serveur
            TP11_exo2 tp = new TP11_exo2();
            while(true){
                //s'il y a moins de 10 clients connectés, le serveur en accepte un nouveau
                if(tp.iClients <= 10){
                    Socket clientele = serveur.accept();       //le client est accepté sur le serveur
                    System.out.println("Il y a actuellement "+ tp.iClients +" clients");
                    Runnable tache = new MotsPlusVu(clientele);     //tâche à effectuer par le serveur pour le client
                    Thread threadeur = new Thread(tache);
                    threadeur.start();
                    tp.iClients++;
                    try{
                       threadeur.join();
                       System.out.println("Le client numéro : "+ (tp.iClients - 1) +" est parti");
                       tp.iClients--;
                    }catch(InterruptedException ie){
                        System.out.println("interruption");
                    }
                }else{
                    //trop de monde en ligne
                    System.out.println("Le nombre maximum de client est atteint");
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }        
    }
}
    /**
     *Cette classe récupère l'entrée d'un client et effectue le traitement
     * @author Marouby Alexandra Narayanane Adrien
     */
    class MotsPlusVu implements Runnable {
        //Champs
        private Socket clientActuel;
        
        //Constructeur
        public MotsPlusVu (Socket clientAVenir){
            this.clientActuel = clientAVenir;
        }
        
        /**
         *Calcule le nombre de mots à afficher
         * @param tab   tableau contenant les occurences des mots, on se sert de sa taille pour définir le nombre de mot à afficher
         * @return      Renvoie un int indiquant le nombre de mots à afficher au client
         */
        public int compteur(int[] tab){
            //On affiche au maximum les 20 premiers mots
            if(tab.length < 20){
                return tab.length;
            }else{
                return 20;
            }
        }
        /**
         *Tache du serveur : Renvoyer les 20 mots les plus vus dans le texte
         */
        @Override
        public void run(){
            try(InputStream inStream = clientActuel.getInputStream();       //Valeur entrante sur le serveur
                    OutputStream outStream = clientActuel.getOutputStream();        //Valeur sortante sur le serveur
                    Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);     
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true))
            {
                out.println("Salutations ! Entrez 'BYE' une fois fini, il ne sera pas pris en compte dans le traitement");    
                boolean fin = false; 
                List<String>addeur = new ArrayList<String>();
                while(!fin && in.hasNextLine()){
                    String ligne = in.nextLine();       //ajout du texte dans le string ligne par ligne
                    if(ligne.trim().equals("BYE")){     //la fin du texte est atteinte
                        int compte;
                        String [] cle;
                        int [] val;
                        travailServeur ts = new travailServeur();
                        ts.travail(addeur);     //appel la méthode travail dans travailServeur
                        cle = ts.getListeCleIndex();    //récupération de la liste de clé
                        val = ts.getListeValeurIndex(); //récupération de la liste de valeurs
                        compte = this.compteur(ts.getListeValeurIndex());   //récupération du nombre de mots à afficher
                        for(int i = 0 ; i < compte ; i++){      //affichage des mots
                            out.println(cle[i] +" : "+ val[i]);
                        }
                        fin = true;
                        out.println("Traitement effectué, au revoir !"); 
                        clientActuel.close();       //le client est expulsé une fois le travail du serveur terminé
                    }else{
                        addeur.add(ligne);  //ajout de la ligne créée dans le string dans la liste globale
                    }
                }
            }catch(IOException ioe){
                System.out.println("erreur");
            }
        }
    }
