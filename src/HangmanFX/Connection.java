package HangmanFX;

import HangmanMessage.HangmanMessageObj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Connection object for Hangman client.
 */
public class Connection {

    private boolean isConnected;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private String connectionMessage;
    private Socket socket;
    private HangmanMessageObj hangmanMessage;

    public Connection(String address, int port) {

        isConnected = false;
        try {
            socket = new Socket(address, port);
            fromServer = new ObjectInputStream(socket.getInputStream());
            toServer = new ObjectOutputStream(socket.getOutputStream());
            connectionMessage = "Connected to: " + socket.getInetAddress();
            isConnected = true;
        } catch (ConnectException e) {
            System.out.println(e);
            connectionMessage = e.getMessage();
        } catch (UnknownHostException e) {
            System.out.println(e);
            connectionMessage = "Unknown host";
        } catch (IOException e) {
            e.printStackTrace();
            connectionMessage = "Bad input data";
        }
    }

    public void closeConnection() {
        try {
            hangmanMessage = new HangmanMessageObj();
            hangmanMessage.setCloseConnection(true);
            toServer.writeObject(hangmanMessage);
            fromServer.close();
            toServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConnectionMessage() {
        return connectionMessage;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void sendGuess() {
        try {
            toServer.writeObject(hangmanMessage);
            toServer.flush();
            hangmanMessage = (HangmanMessageObj) fromServer.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newGame() {
        hangmanMessage = new HangmanMessageObj();
        try {
            toServer.writeObject(hangmanMessage);
            toServer.flush();
            hangmanMessage = (HangmanMessageObj) fromServer.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HangmanMessageObj getMessageObj() {
        return hangmanMessage;
    }
}
