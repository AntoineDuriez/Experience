/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *Reprise du TP d'analyse de texte avec Thread et protocole TCP client-serveur : partie client
 * @author Marouby Alexandra Narayanane Adrien
 */
public class Client {

    /**
     *Crée un client et le fait se connecter au serveur pour lui envoyer son texte
     * @param args      Liste de String des arguments en ligne de commande
     */
    public static void main(String[] args) {
        try{
            Socket s = new Socket();    //création de la socket client
            s.connect(new InetSocketAddress(9102)); //connexion a serveur
            InputStream inStream = s.getInputStream();      //Valeur entrante sur le client
                    OutputStream outStream = s.getOutputStream();   //Valeur sortante sur le client
                    Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true);
            Scanner texte = new Scanner(new FileInputStream(args[0]));  //obtention du texte à analyser
            while(texte.hasNextLine()){
                out.println(texte.nextLine());  //envoi du texte ligne par ligne
            }
            out.println("BYE"); //signalement de la fin du texte
            while(in.hasNextLine()){
                System.out.println(in.nextLine());  //Réception du travail du serveur
            }
        }catch(IOException ioe){
            System.out.println("Le nom du fichier n'est pas valide");
        }
    }
    
}
