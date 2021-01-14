/*
 * Copyright (C) 2019 EIDD 2A SIE
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package rdvmanager;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Manager of an appointment list.
 *
 * @version 0.1
 * @author M. Sighireanu
 * @author YOU
 */
public class RdvList {

    private LinkedList<Rdv> list;
    private int quota;

    /**
     * Default constructor
     */
    public RdvList() {
        this.list = new LinkedList<Rdv>();
        this.quota = 1000; // TODO push symbolic constant
    }

    /**
     * Get the quota
     *
     * @return the current quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Set a new quota if bigger than the size of the list
     *
     * TODO: EXO 2.3
     * @precondition    this.list.size() < aQuota
     * @param aQuota new value for quota
     * @return true iff the setting is done
     */
    public boolean setQuota(int aQuota) {
        // TODO: EXO 2.3
        assert this.list.size() < aQuota : "Quota smaller than list size !";
        // TODO: for Java 11 use the line below
        if (aQuota < Objects.requireNonNullElse(this.list.size(), this.quota)){
            return false;
        }
        this.quota = aQuota;
        return true;
    }

    /**
     * Add an appointment in the list
     *
     * TODO: EXO 2.4
     * @precondition this.list.size() < this.quota
     * @param aRdv new appointment
     * @return true iff add done
     */
    public boolean add(Rdv aRdv) {
        // TODO: EXO 2.4
        assert this.list.size() < this.quota : "Quota is smaller than list size !";
        if (this.quota > Objects.requireNonNull(this.list.size())) {
            this.list.add(aRdv);
            return true;
        }
        return false;
    }

    // TODO: EXO 1.6
    /**
     * Print rendezvous from lists after date @param d
     *
     * @param output output stream
     * @param d starting date
     */
    public void printFrom(PrintStream output, LocalDateTime d) {
        if (list == null) {
            output.println("Empty list! (quota = " + this.quota + ")");
            return;
        }
        boolean isEmpty = true;
        for (Rdv r : this.list) {
            if (r.getStart().isAfter(null)) {
                output.println(r);
                isEmpty = false;
            }
        }
        if (isEmpty) {
            output.println("No rendez-vous after " + d);
        }
    }

    // TODO: EXO 1.7 printFromTo
    /*énoncé : on comprend le terme "moment" comme une
    variable de type LocalDateTime, c'est à dire comme un couple date + heure*/
    public void printFromTo(PrintStream output, LocalDateTime debut, LocalDateTime fin){
        if (list == null) {
            output.println("Empty list! (quota = " + this.quota + ")");
            return;
        }
        boolean isEmpty = true;
        /*la spécification précise que l'un des 2 moments peut être nul, mais pas les 2 en même temps*/
        //le moment de fin est null
        if(debut.equals(null) && !fin.equals(null)){
            for(Rdv r : this.list){
                if(r.getStart().isBefore(fin)){
                    output.println(r);
                    isEmpty = false;
                }
            }
            if(isEmpty){
            output.println("No rendez-vous before "+ fin);
            }
            //le moment de début est null
        }else if(fin.equals(null) && !debut.equals(null)){
           printFrom(output, debut);    //appel de la fonction printFrom
           //les 2 moments sont nuls, on n'affiche aucunes dates et le programme est protégé des erreurs
        }else if(fin.equals(null) && debut.equals(null)){
            output.println("no date !");
        }else{
            //les 2 moments ne sont pas nuls
            for(Rdv r : this.list){
                if(r.getStart().isBefore(fin) && r.getStart().isAfter(debut)){
                    output.println(r);
                    isEmpty = false;
                }
            }
            if(isEmpty){
                output.println("No rendez-vous between "+ debut + " and "+ fin);
            }
        }
    }
    
    boolean isOkToRemove(){
        String rep = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter y or n");
        while(!rep.equals("y") || !rep.equals("n")){
            rep = sc.nextLine();
            if(rep.equals("y")){
                return true;
            }if(rep.equals("n")){
                return false;
            }
            System.out.println("Wrong enter, please restart : y or n");
        }
        return false;
    }
    // TODO: EXO 1.8 removeFromTo
    public void removeFromTo(PrintStream output, LocalDateTime debut, LocalDateTime fin){
        int i;
        if (list == null) {
            output.println("Empty list! (quota = " + this.quota + ")");
            return;
        }
        boolean isEmpty = true;
        /*la spécification précise que l'un des 2 moments peut être nul, mais pas les 2 en même temps*/
        //le moment de fin est null
        if(debut.equals(null) && !fin.equals(null)){
            for(i = 0 ; i < this.list.size() ; i++){
                if(this.list.get(i).getStart().isBefore(fin)){
                    output.println(this.list.get(i) + "will be removed from the list, are you agree ?\n");
                    if(isOkToRemove()){
                        output.println(this.list.get(i) + "is removed from the list\n");
                        this.list.remove(i);
                    }
                    isEmpty = false;
                }
            }
            if(isEmpty){
            output.println("No rendez-vous before "+ fin);
            }
            //le moment de début est null
        }else if(fin.equals(null) && !debut.equals(null)){
            for(i = 0 ; i < this.list.size() ; i++){
                if(this.list.get(i).getStart().isAfter(debut)){
                    output.println(this.list.get(i) + "will be removed from the list, are you agree ?\n");
                    if(isOkToRemove()){
                        output.println(this.list.get(i) + "is removed from the list\n");
                        this.list.remove(i);
                    }
                    isEmpty = false;
                }
            }
            if(isEmpty){
            output.println("No rendez-vous after "+ debut);
            }
           //les 2 moments sont nuls, on n'affiche aucunes dates et le programme est protégé des erreurs
        }else if(fin.equals(null) && debut.equals(null)){
            output.println("no date !");
        }else{
            //les 2 moments ne sont pas nuls
            for(i = 0 ; i < this.list.size(); i++){
                if(this.list.get(i).getStart().isBefore(fin) && this.list.get(i).getStart().isAfter(debut)){
                    output.println(this.list.get(i) + "will be removed from the list, are you agree ?\n");
                    if(isOkToRemove()){
                        output.println(this.list.get(i) + "is removed from the list\n");
                        this.list.remove(i);
                    }
                    isEmpty = false;
                }
            }
            if(isEmpty){
                output.println("No rendez-vous between "+ debut + " and "+ fin);
            }
        }
    }
    
    /**
     * Save the list in CSV format
     *
     * @param output output stream
     */
    public void saveCSV(PrintStream output) {
        output.println(";" + this.quota);
        for (Rdv rdv : this.list) {
            output.println(rdv.toCSV());
        }
    }
}
