package HangmanFX;

import HangmanMessage.HangmanMessageObj;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HangmanController implements Initializable {

    private static String DEFAULT_STATUS = "Not Connected";
    private Connection connection;

    @FXML
    private Button sendGuessButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField guessField;
    @FXML
    private Label currentWord;
    @FXML
    private Label serverScore;
    @FXML
    private Label guessesLeft;
    @FXML
    private Label clientScore;
    @FXML
    private Button newGameButton;
    @FXML
    private Label connectionStatus;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button connectButton;
    @FXML
    private AnchorPane connectPane;
    @FXML
    private AnchorPane gamePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePane.setDisable(true);
        newGameButton.setDisable(true);
        disconnectButton.setDisable(true);
        connectionStatus.setText(DEFAULT_STATUS);
    }

    @FXML
    private void connectToServer(ActionEvent actionEvent) {
        Task connectToServer = new Task() {
            @Override
            protected Object call() throws Exception {
                connection = new Connection(ipField.getText(), Integer.parseInt(portField.getText()));
                return null;
            }

            @Override
            protected void succeeded() {
                connectionStatus.setText(connection.getConnectionMessage());
                if (connection.isConnected()) {
                    connectButton.setDisable(true);
                    disconnectButton.setDisable(false);
                    ipField.setDisable(true);
                    portField.setDisable(true);
                    newGameButton.setDisable(false);
                }
            }
        };
        new Thread(connectToServer).start();
    }

    public void closeConnection(ActionEvent actionEvent) {
        connection.closeConnection();
        connectButton.setDisable(false);
        disconnectButton.setDisable(true);
        ipField.setDisable(false);
        portField.setDisable(false);
        gamePane.setDisable(true);
        newGameButton.setDisable(true);
    }

    @FXML
    public void newGame(ActionEvent actionEvent) {
        Task newGame = new Task() {
            @Override
            protected Object call() throws Exception {
                connection.newGame();
                return null;
            }

            @Override
            protected void succeeded() {
                gamePane.setDisable(false);
                updateGamePane();
            }
        };
        new Thread(newGame).start();
    }

    private void updateGamePane() {
        HangmanMessageObj msgObj = connection.getMessageObj();
        serverScore.setText(String.valueOf(msgObj.getServerScore()));
        clientScore.setText(String.valueOf(msgObj.getClientScore()));
        guessesLeft.setText(String.valueOf(msgObj.getGuessesLeft()));
        currentWord.setText(msgObj.getHiddenWord());
        messageLabel.setText("");
        sendGuessButton.setText("Send");
        guessField.setDisable(false);
        guessField.clear();
        guessField.requestFocus();
    }

    @FXML
    public void sendGuess(ActionEvent actionEvent) {
        if (guessField.getText() != null) {
            Task sendGuess = new Task() {
                @Override
                protected Object call() throws Exception {
                    connection.getMessageObj().setGuess(guessField.getText().trim());
                    connection.sendGuess();
                    return null;
                }

                @Override
                protected void succeeded() {
                    updateGamePane();
                    if (connection.getMessageObj().getMessageToPlayer() != null) {
                        messageLabel.setText(connection.getMessageObj().getMessageToPlayer());
                        guessField.setDisable(true);
                        sendGuessButton.setText("New Round");
                        connection.getMessageObj().setNewRound(true);
                    }
                }
            };
            new Thread(sendGuess).start();
        }
    }

    public void exitApp(ActionEvent actionEvent) {
        if (connection.isConnected())
            connection.closeConnection();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}

