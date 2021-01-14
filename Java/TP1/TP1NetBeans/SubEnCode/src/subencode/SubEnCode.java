/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subencode;

import java.io.File;
import java.io.IOException;

/**
 *Un logiciel pour chiffrer des textes, avec les méthodes de César ou de Polybe
 * @author Alexandra Marouby / Adrien Narayanane
 */
public class SubEnCode {
    //Champs d'instance
    private String texteCrypte;
    private String type;
    private int decalage;
    private String fichierTexte;
    //constructeur
    /**
     *Initialise les champs de la classe en vu du chiffrage : par défaut, chiffrage "cesar".
     */
    public SubEnCode() {
        this.texteCrypte = "";
        this.type = "cesar";
        this.decalage = 0;
        this.fichierTexte = "";
    }
    //Methodes
    /**
     *Vérifie si le paramètre est un entier : uniquement lorsque chiffrage "cesar"
     * @param rep   paramètre à analyser pour savoir s'il est un entier
     * @return      retourne vrai si le paramètre est un entier, faux sinon
     */
    public boolean estEntier(String rep){
        try{
            Integer.parseInt(rep);
        }catch(NumberFormatException e){
            return false;
        }
            return true;
    }
    /**
     *Vérifie que le paramètre est un fichier existant
     * @param fic   Un String contenant le nom d'un fichier texte
     * @return      retourne vrai si le fichier décrit existe, faux sinon
     */
    public boolean estExistant(String fic){
        File f = new File(fic);
        if(f.isFile()){
            return true;
        }else{
            return false;
        }
    }
    /**
     *Vérifie que le chiffrement demandé correspond à un chiffrement de cesar et vérifie les paramètres entrés
     * @param info      String contenant les paramètres à prendre en compte pour le chiffrement : type de méthode, décalage et texte à chiffrer
     * @return retourne vrai si le chiffrage de cesar est possible, 0 sinon     
     */
    public boolean estCesar(String [] info){
        try{
            if(info[0].equals("cesar") && info.length == 3){
                this.type = info[0];
                if(this.estEntier(info[1])){
                    this.decalage = Integer.parseInt(info[1]);
                    if(this.estExistant(info[2])){
                        this.fichierTexte = info[2];
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
     *Vérifie que le chiffrement demandé correspond à un chiffrement de polybe et vérifie les paramètres entrés.
     * @param info      String contenant les paramètres à prendre en compte pour le chiffrement : type de méthode et texte à chiffrer
     * @return retourne vrai si le chiffrage de polybe est possible, 0 sinon
     */
    public boolean estPolybe(String [] info){
        try{
            if(info[0].equals("polybe") && info.length == 2){
                this.type = info[0];
                    if(this.estExistant(info[1])){
                        this.fichierTexte = info[1];
                        return true;
                    }
                }
        }catch(ArrayIndexOutOfBoundsException aiooe){
            return false;
        }
        return false;
    }
    /**
     * Vérifie que le chiffrage du texte est possible.
     * @param info  String contenant les informations pour le chiffrage
     * @return      retourne vrai si le chiffrage est possible, faux sinon
     */
    public boolean estPossible(String [] info){
        if(this.estCesar(info) || this.estPolybe(info)){
           return true;
        }else{
            System.out.println("erreur dans les arguments d'entrées");
            return false;
        }
    }
    /**
     *Affiche le texte après son chiffrement
     * @param typage    indique le type de chiffrage choisi : cesar ou polybe
     * @throws IOException  erreur dans le fichier texte
     */
    public void Affichage(String typage) throws IOException{
        if(typage.equals("cesar")){
            Cesar chiffrageCesar = new Cesar();
            this.texteCrypte = chiffrageCesar.methodeCesar(this.fichierTexte, this.decalage);
        }else{
            Polybe chiffragePolybe = new Polybe();
            this.texteCrypte = chiffragePolybe.methodePolybe(this.fichierTexte);
        }
        System.out.println(this.texteCrypte);
    }
    /**
     *Réceptionne le texte et execute le chiffrage demandé.
     * @param args the command line arguments   si cesar : cesar decalage texte, si polybe : polybe texte
     */
    public static void main(String[] args){
        // TODO code application logic here
        try{
            SubEnCode chiffrage = new SubEnCode();
            if(chiffrage.estPossible(args)){
                chiffrage.Affichage(chiffrage.type);
            }
        }catch(IOException e){
            System.out.println("chiffrage impossible");
        }
    }
}
