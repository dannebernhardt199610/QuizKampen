package ClientProgram.GUI.Controllers;

import ClientProgram.ClientConnection;
import ClientProgram.GUI.GUI_Control;
import ClientProgram.GUI.Main;
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

import java.util.ArrayList;
import java.util.List;

public class IngameController{
    public Label categoryLabel;

    int counter = 0;
    String input = "";

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


    @FXML
    void continuetoWinninglobby(ActionEvent event) throws Exception{
        GUI_Control.changeScene(GUI_Control.getWinnerLobbyScene());


    }




    @FXML
    void sendMsg(ActionEvent event) {
            String messageToServer;
            if (!msgTextField.getText().isEmpty()) {
                messageToServer = msgTextField.getText();
                msgTextField.setText("");

                chatWindow.appendText("[Du] " + messageToServer + "\n");
                ClientRequest messageRequest = new ClientRequest(ClientRequest.TYPE.MESSAGE_TO_ALL, messageToServer);
                Main.clientConnection.sendObjectToServer(messageRequest);
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

    public void answerButton(ActionEvent actionEvent) {
        List<Button> answerButtons = new ArrayList<>();
        answerButtons.add(answer1);
        answerButtons.add(answer2);
        answerButtons.add(answer3);
        answerButtons.add(answer4);


        Button clickedButton = (Button) actionEvent.getSource();

        String answer = clickedButton.getText();
        Main.clientConnection.sendObjectToServer(new ClientRequest(ClientRequest.TYPE.SUBMIT_ANSWER, answer));

        //disable buttons, preferably pretty :P
        //Set color for buttons
    }
}


