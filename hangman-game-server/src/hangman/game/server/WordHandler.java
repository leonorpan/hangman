/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.game.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Nora
 */
public class WordHandler {
    
    File file;
    String word;
    String dashed;
    List<Character> found;
    
    public WordHandler() throws FileNotFoundException {
        this.found = new ArrayList<Character>();
        this.file =getFile();
        this.word = selectWord();
        this.dashed = word.replaceAll(".", " _");
        System.out.println("Word selected: "+word);
        
    }
    
    public String getDashed() {
        return generateDashes();
    }
    
    public void addToFound(Character c) {
        this.found.add(c);
    }
    
    public boolean ifFoundListIncludes (char[]  s) {
        if (found.contains(s[0])){
            return true;
        }
        else {
            return false;
        }
    }
    
    private String selectWord() throws FileNotFoundException{
        String str="";
        do {
            str=chooseWord();
        }while((!str.matches((".*\\d+.*")))&&(str.length()<3));
        return str;
    }
    
    public String getWord() {
        return this.word;
    }
    
        public String generateDashes() {
        if (found.size()>0) {
        StringBuilder result = new StringBuilder(found.size());
        found.stream().forEach((c) -> {
            result.append(c);
        });
        String output = result.toString();
        String except = "[^" + output + "]";
        this.dashed = word.replaceAll(except, " _");
        }
        return this.dashed;

    }
        
        public boolean compareTotal(String guess) throws IOException {
        if (guess.equalsIgnoreCase(word)){
            return true;  
        }else {
            return false;
        }
    }
    
    
    private File getFile() {
        URL url = getClass().getResource("words.txt");
        file = new File(url.getPath());
        return file;
    }
    
    
     private String chooseWord() throws FileNotFoundException {
        String result = null;
        Random rand = new Random();
        int n = 0;
        for (Scanner sc = new Scanner(file); sc.hasNext();) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0) {
                result = line.toLowerCase();
            }
        }
        return result;
    }
     
      public boolean checkCompleteness() {
        if (this.dashed.contains("_")) {
            return false;
        } else {
            return true;
        }
    }
}
