package ClientProgram.GUI.Controllers;

import ClientProgram.ClientConnection;
import ClientProgram.GUI.GUI_Control;
import ClientProgram.GUI.Main;
import Model.Question;
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
    public Label totalPointsLabel;
    public Label currentRoundPointsLabel;
    public Label totalPointsCountLabel;

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
    private Pane chatpane;

    @FXML
    private Button sendmsgButton;

    @FXML
    private Label showTotalScoreLabel;

    @FXML
    private Button chatWindowButton;

    @FXML
    public TextArea chatwindowTextArea;

    @FXML
    private TextField msgTextField;

    public Question currentQuestion;
    int currentRoundPoints = 0;
    int totalPoints = 0;


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

                chatwindowTextArea.appendText("[Du] " + messageToServer + "\n");
                ClientRequest messageRequest = new ClientRequest(ClientRequest.TYPE.MESSAGE_TO_ALL, messageToServer);
                Main.clientConnection.sendObjectToServer(messageRequest);
            }
        }

    public void answerButton(ActionEvent actionEvent) {
        List<Button> answerButtons = new ArrayList<>();
        answerButtons.add(answer1);
        answerButtons.add(answer2);
        answerButtons.add(answer3);
        answerButtons.add(answer4);

        for (Button button: answerButtons) {
            button.setDisable(true);
        }

        Button clickedButton = (Button) actionEvent.getSource();

        if(clickedButton.getText().equals(currentQuestion.getCorrectAnswer())){
            clickedButton.setStyle("-fx-background-color: GREEN");
            currentRoundPoints++;
            currentRoundPointsLabel.setText(Integer.toString(currentRoundPoints));
            totalPoints++;
            totalPointsCountLabel.setText(Integer.toString(totalPoints));
        }
        else {
            clickedButton.setStyle("-fx-background-color: RED");
        }

        String answer = clickedButton.getText();






        Main.clientConnection.sendObjectToServer(new ClientRequest(ClientRequest.TYPE.SUBMIT_ANSWER, answer));



        //disable buttons, preferably pretty :P
        //Set color for buttons
    }


    @FXML
    void show() {
        chatwindowTextArea.setVisible(true);
        sendmsgButton.setVisible(true);
        msgTextField.setVisible(true);
    }
    @FXML
    void hide(){
        chatwindowTextArea.setVisible(false);
        sendmsgButton.setVisible(false);
        msgTextField.setVisible(false);
    }
    @FXML
    void openchatWindow(ActionEvent event) {

        if (chatWindowButton.getText().equalsIgnoreCase("chatWindow")) {
            show();
            chatWindowButton.setText("Close");
        }else if (chatWindowButton.getText().equalsIgnoreCase("Close")){
            hide();
            chatWindowButton.setText("ChatWindow");
        }

    }
}


