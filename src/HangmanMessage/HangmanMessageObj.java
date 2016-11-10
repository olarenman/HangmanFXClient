package HangmanMessage;

import java.io.Serializable;

/**
 * Object for server communication.
 */
public class HangmanMessageObj implements Serializable {

    private static final long serialVersionUID = 3001270435217399311L;
    private static final int NO_OF_GUESSES_EACH_ROUND = 10;
    private boolean newGame;
    private boolean closeConnection;
    private boolean newRound;
    private String hiddenWord;
    private String Guess;
    private int guessesLeft;
    private int serverScore;
    private int clientScore;
    private String messageToPlayer;

    public HangmanMessageObj() {
        newGame = true;
        newRound = false;
        closeConnection = false;
        guessesLeft = NO_OF_GUESSES_EACH_ROUND;
        serverScore = 0;
        clientScore = 0;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNotNewGame() {
        newGame = false;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    public void decrementGuesses() {
        guessesLeft--;
    }

    public void resetGuesses() {
        guessesLeft = NO_OF_GUESSES_EACH_ROUND;
    }

    public void incrementServerScore() {
        serverScore++;
    }

    public void incrementClientScore() {
        clientScore++;
    }

    public int getGuessesLeft() {
        return guessesLeft;
    }

    public int getServerScore() {
        return serverScore;
    }

    public int getClientScore() {
        return clientScore;
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }

    public String getGuess() {
        return Guess;
    }

    public void setGuess(String guess) {
        Guess = guess;
    }

    public String getMessageToPlayer() {
        return messageToPlayer;
    }

    public void setMessageToPlayer(String messageToPlayer) {
        this.messageToPlayer = messageToPlayer;
    }

    public boolean isNewRound() {
        return newRound;
    }

    public void setNewRound(boolean newRound) {
        this.newRound = newRound;
    }
}