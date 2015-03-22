/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.game.server;

import data.GameData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Nora
 */
public class HangmanConnection {
    
    private BufferedReader input;
    private ObjectOutputStream output;
    private  Socket socket;
    
    public HangmanConnection(Socket client) throws IOException{
        this.socket=client;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new ObjectOutputStream(client.getOutputStream());
    }
    
    public String readMessage() throws IOException {
    return input.readLine();    
    }
    
    public void sendMessage(GameData data) throws IOException {
        output.reset();
        output.writeObject(data);
        output.flush();
    }
    
    public void close() {
         try {
            if (socket != null) {
                System.out.println("closing the server....");
                socket.close();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
    
}
