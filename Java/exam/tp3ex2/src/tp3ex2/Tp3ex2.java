/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3ex2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Scanner;
import java.util.function.IntPredicate;

/**
 *
 * @author Alexa
 */
public class Tp3ex2 {
    private HashSet<String> words;
    
    public Tp3ex2(){
        this.words = new HashSet<>();
    }
    
    public void setWords(InputStream in){
        Scanner data = new Scanner(in);
        while(data.hasNext()){
            var e = data.next();
            this.words.add(e);
        }
    }
    
    public void printWords(OutputStream out){
        Writer w = new OutputStreamWriter(out);
        this.words.forEach(System.out::println);
    }
    
    public void filterLength(IntPredicate p){
        this.words.removeIf(w->!p.test(w.length()));
    }
    
    public HashSet<String> getLength(IntPredicate p){
        Tp3ex2 w = new Tp3ex2();
        w.words = (HashSet<String>)this.words.clone();
        w.filterLength(p);
        return w.words;
    }
    
    public static void main(String [] args) throws FileNotFoundException{
        InputStream in = new FileInputStream("test.txt");
        OutputStream out = System.out;
        IntPredicate p = new IntPredicate() {
            @Override
            public boolean test(int value) {
                if(value <= 3){
                    return true;
                }else{
                    return false;
                }
            }
        };
        Tp3ex2 test = new Tp3ex2();
        test.setWords(in);
        System.out.println("clone : "+ test.getLength(p));
        System.out.println("original : ");
        test.printWords(out);
    }
}
