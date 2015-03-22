/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman.game.server;

import data.GameData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nora
 */
public class HangmanController extends Thread {

    private GameData data;
    private WordHandler handler;
    private HangmanConnection connection;
    private String received;
    private int clientScore=0;
    private int serverScore=0;

    public HangmanController(Socket client) throws IOException {
        connection = new HangmanConnection(client);
        handler = new WordHandler();

    }

    @Override
    public void run() {
        try {
            initGame();
        } catch (IOException ex) {
            Logger.getLogger(HangmanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void writeScore(String s) {
        switch (s) {
            case "client":
                clientScore++;
                System.out.println("client score update"+clientScore);
                break;
            case "server":
                serverScore++;
                System.out.println("server score update"+serverScore);
                break;
        }
    }

    private void initGame() throws IOException {
        received = connection.readMessage();
        data = new GameData(received, handler.dashed);
        System.out.println("Message received: " + received);
        connection.sendMessage(data);
        while ((!received.equals("QUIT"))) {
            received = connection.readMessage();
            if (received.equals("scores")) {
                data.setscoresMessage(clientScore,serverScore);
            }
            else if (data.getCounter() == 0) {
                data.setLooseMessage();
                writeScore("server");
            } else if ((received.length() > 1) && (handler.compareTotal(received))) {
                data.setCongratsMessage();
                data.setWord(handler.word);
                 writeScore("client");
            } else if (handler.ifFoundListIncludes(received.toCharArray())) {
                data.setWord(handler.generateDashes());
                data.setInvalidTryMessage(received);
            } else if ((handler.getWord().contains(received)) && (received.length() == 1)) {
                handler.addToFound(received.toCharArray()[0]);
                if (handler.checkCompleteness()) {
                    data.setCongratsMessage();
                    data.setWord(handler.word);
                    writeScore("client");
                } else {
                    data.setFoundMessage();
                    data.setWord(handler.generateDashes());
                }
            } else {
                handler.addToFound(received.toCharArray()[0]);
                data.disincrementCounter();
                data.setRetryMessage(received);
                data.setWord(handler.generateDashes());
            }
            connection.sendMessage(data);
        }

        connection.close();

    }

  

    private int addInt(String s) {
        int i = Integer.parseInt(s);
        return i++;

    }
}
