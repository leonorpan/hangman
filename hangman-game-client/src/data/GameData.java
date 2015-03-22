/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nora
 */
public class GameData implements java.io.Serializable{
public Level level;
public Integer counter;
public String word;
public String message;
public static final long serialVersionUID = 5L;
    
public GameData(String level,String word)  {
    System.out.println("Level is:"+level);
    switch(level) {
        case "Easy":
            this.level=Level.EASY;
            this.counter = 20;
            break;
        case "Medium":
            this.level=Level.MEDIUM;
            this.counter = 15;
            break;
        case "Hard":
            this.level=Level.HARD;
            this.counter = 10;
            break; 
        case "Veteran":
            this.level=Level.VETERAN;
            this.counter=5;
            break;
        default:
            this.level=Level.MEDIUM;
            this.counter=15;
            break;
    }
    this.word=word;
    this.message="Welcome to the Hangman game! Guess a letter or the word";
}



public void setWord(String str){
    this.word=str;
}

public void setCongratsMessage()
{
    this.message="Congratulations! You won the server!";
}

public void setRetryMessage(String reply){
    this.message="Letter "+reply+" is not contained. Sorry";
}

public void setFoundMessage() {
    this.message="Good job! You found a letter!";
}

public void setLooseMessage() {
    this.message="Sorry, you lost the game!";
}

public String getMessage(){
    return this.message;
}

public void setInvalidTryMessage(String l) {
    this.message="You have already tried letter "+l+" . Try another letter of the alphabet";
}

public void setCounter(Integer attempts) {
    this.counter=attempts;
}

public Integer getCounter() {
    return this.counter;
}

public void disincrementCounter() {
    this.counter--;
}

public String getWord() {
        return this.word;
}

    public void setscoresMessage(int c,int s) {
this.message="Client is: "+c+"\n Server: "+s;
    }

}
