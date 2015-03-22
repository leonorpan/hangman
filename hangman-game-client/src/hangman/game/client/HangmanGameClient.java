/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.game.client;

import data.GameData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nora
 */
public class HangmanGameClient implements Runnable {

    /**
     * @param args the command line arguments
     */
    private final String host;
    private final int port;
    private final HangmanClientGUI2 gui;

    private final LinkedBlockingQueue<String> strings
            = new LinkedBlockingQueue<>();
    private ObjectInputStream in;
    private PrintWriter out;
    private static Socket clientSocket = null;
    private static PrintStream os = null;
    public boolean open = true;
    List<String> letters = new ArrayList<String>();
    
    
    public HangmanGameClient(HangmanClientGUI2 gui, String host, int port) {
        this.host = host;
        this.port = port;
        this.gui = gui;
    }

    @Override
    public void run() {
        connect();
        callServer();
    }
    
    public void close() throws IOException {
        if (clientSocket!=null) {
            clientSocket.close();
        }
    }

    public void connect() {
        try {
            clientSocket = new Socket(host, port);
            os = new PrintStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("connected");
        }  catch (UnknownHostException e)
        {
            gui.warn("Don't know about host: " + host + ".");
            System.exit(1);
        } catch (IOException e)
        {
            gui.warn("Couldn't get I/O for the connection to: "
                    + host + ".");
            System.exit(1);
        }
    }

    public void answer(String text) {
        strings.add(text);
        if (text.length()==1 && !letters.contains(text)){
            letters.add(text);
            gui.appendLetter(text);
        }

    }

    public void callServer() {
        Object response;
        GameData game;
        try {
            do {
                System.out.println("server called...");
                out.println(strings.take());
                response =  in.readObject();
                if (response instanceof GameData) {
                    game = (GameData) response;
                    System.out.println("Response from server: " + game.getMessage() + " " + game.getWord() + " " + game.getCounter());
                    printGui(game);
                    
                }
            } while (open);
        } catch (UnknownHostException unHst) {
            System.out.println("/n Host didn 't found /n");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exiting the game...");
            System.exit(1);
        }

    }
    
    private void printGui(GameData data) {
        if ((data.getMessage().contains("Client")&&data.getMessage().contains("Server"))){
            gui.printScores(data.getMessage());
        }else {
            gui.showResult(data);
        }
    } 



}
