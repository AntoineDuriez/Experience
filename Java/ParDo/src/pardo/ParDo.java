/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pardo;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexa
 */
public class ParDo {
    private int [] data = new int[10000];
    private int inext;
    private int nbThread;
    
    public ParDo(){
        int i = 0;
        this.inext = 0;
        this.nbThread = 0;
        for(i = 0 ; i < this.data.length ; i++){
            this.data[i] = 0;
        }
    }
    
    public /*synchronized*/ void incrementeur(){
        ReentrantLock lock = new ReentrantLock();
        while(this.inext < 10000){
            lock.lock();
            try {
                this.data[this.inext] += 2;
                this.inext++;
                System.out.println(Thread.currentThread() + "valeur inext : " + this.inext);
                System.out.println("-------------------------");
            } finally {
               lock.unlock();
            }
        }
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ParDo test = new ParDo();
        try{
            test.nbThread = Integer.parseInt(args[0]);
            //création du tableau de thread
            Thread [] threader = new Thread[test.nbThread];
            //tache unique d'increment
            Runnable task = ()-> test.incrementeur();
            for (int i = 0; i < threader.length; i++) {
                threader[i] = new Thread(task);  
            }
            for(Thread thread : threader){
                thread.start();
            }
            for (Thread threader1 : threader) {
                threader1.join();
            }
            for(int inc : test.data){
                System.out.println(inc);
            }  
            System.out.println("valeur finale inext : "+ test.inext);
        }catch(NumberFormatException nfe){
            System.out.println("Mauvais format de nombre");
            Logger.getLogger(ParDo.class.getName()).log(Level.SEVERE, null, nfe);
        }catch(IndexOutOfBoundsException ioob){
            System.out.println("Erreur argument");
            Logger.getLogger(ParDo.class.getName()).log(Level.SEVERE, null, ioob);
        } catch (InterruptedException ex) {
            System.out.println("Interruption du travail");
            Logger.getLogger(ParDo.class.getName()).log(Level.SEVERE, null, ex);
        }    
    } 
}
