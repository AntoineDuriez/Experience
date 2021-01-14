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
    private int data [] = new int[10000];
    private int inext;
    
    public ParDo(){
        int i = 0;
        this.inext = 0;
        for(i = 0 ; i < this.data.length ;i++){
            this.data[i] = 0;
        }
        
    }
    
    public void increment(){
        ReentrantLock lock = new ReentrantLock();
        while(this.inext < 10000){
            lock.lock();
            try {
                this.data[this.inext] += 2;
                this.inext++;
                
            } finally {
                lock.unlock();
            }
        } 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        ParDo t = new ParDo();
        try {
        int N = Integer.parseInt(args[0]);
        Thread threader [] = new Thread[N];
        Runnable tache = () -> t.increment();
        int i = 0;
        for(i = 0 ; i < threader.length ; i++){
            threader[i] = new Thread(tache);
        }
        for(Thread th : threader){
            th.start();
        }
        for(Thread thr : threader){
            thr.join(); 
        }
        for(int j : t.data){
            System.out.println(j);
        }
        } catch (InterruptedException | IndexOutOfBoundsException ex) {
                Logger.getLogger(ParDo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
