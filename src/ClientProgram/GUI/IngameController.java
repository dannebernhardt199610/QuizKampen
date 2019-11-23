package ClientProgram.GUI;

import ClientProgram.ClientConnection;
import ServerUtilities.ClientRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IngameController{
    int counter = 0;
    String input = "";

    ClientConnection clientConnection;

    @FXML
    private AnchorPane question;


    @FXML
    public Button answer1;

    @FXML
    public Button answer2;

    @FXML
    public Button answer3;

    @FXML
    public Button answer4;

    @FXML
    private Label pointslabel;

    @FXML
    private Label labelpoints;

    @FXML
    public TextArea questionArea;

    @FXML
    private ImageView imageView;

    @FXML
    private Button continueButton;


    @FXML
    private Button chatwindowButton;

    @FXML
    private Pane chatpane;

    @FXML
    public TextArea chatWindow;

    @FXML
    public Button sendmsgButton;

    @FXML
    public TextField msgTextField;


    public void initializeConnection(String username){
        //Move connection to connect button!!
        String hostName = "127.0.0.1";
        int portNr = 13377;
        this.clientConnection = new ClientConnection(hostName, portNr);
        System.out.println("Username = " + username);
        clientConnection.sendObjectToServer(new ClientRequest(ClientRequest.TYPE.SEND_USERNAME, username));
        System.out.println("Object sent to server");
    }


    @FXML
    void continuetoWinninglobby(ActionEvent event) throws Exception{
        Scene winnerLobbyScene = new Scene(GUI_Control.getWinnerLobbyParent());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(winnerLobbyScene);
        window.show();


    }


    @FXML
    void answer1(ActionEvent event) {
        counter++;
        labelpoints.setVisible(true);
        labelpoints.setText(String.valueOf(counter));


    }

    @FXML
    void answer2(ActionEvent event) {

    }

    @FXML
    void answer3(ActionEvent event) {

    }

    @FXML
    void answer4(ActionEvent event) {

    }

    @FXML
    void sendMsg(ActionEvent event) {
            String messageToServer;
            if (!msgTextField.getText().isEmpty()) {
                messageToServer = msgTextField.getText();
                msgTextField.setText("");

                chatWindow.appendText("[Du] " + messageToServer + "\n");
                ClientRequest messageRequest = new ClientRequest(ClientRequest.TYPE.MESSAGE_TO_ALL, messageToServer);
                clientConnection.sendObjectToServer(messageRequest);
            }
        }

    @FXML
    void openchatWindow(ActionEvent event) {
        chatpane.setVisible(true);
        chatWindow.setVisible(true);
        sendmsgButton.setVisible(true);
        msgTextField.setVisible(true);

    }

    public void initialize() {
        //If we want to do stuff right after the scene is loaded
    }
}


