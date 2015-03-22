/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Nora
 */
public class HangmanGameServer {

    /**
     * @param args the command line arguments
     */
    private static ServerSocket serverSocket;
    private static final int PORT = 8777;
    private static boolean listening=true;
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Welcome to the hangman game!");
        } catch (IOException expIO) {
            System.out.println("\n Unable to set up port " + PORT);
            System.exit(1);
        }

        while(listening){
            Socket client = serverSocket.accept();
            HangmanController hc = new HangmanController(client);
            hc.start();
        } 
        
        serverSocket.close();
        
        
    }
    
}
