package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class IngameController extends Controller implements Initializable {
    int counter = 0;
    String input = "";



    @FXML
    private AnchorPane question;


    @FXML
    private Button answer1;

    @FXML
    private Button answer2;

    @FXML
    private Button answer3;

    @FXML
    private Button answer4;

    @FXML
    private Label pointslabel;

    @FXML
    private Label labelpoints;

    @FXML
    private TextArea textareaa;

    @FXML
    private ImageView imageView;

    @FXML
    private Button continueButton;


    @FXML
    private Button chatwindowButton;

    @FXML
    private Pane chatpane;

    @FXML
    private TextArea chatWindow;

    @FXML
    private Button sendmsgButton;

    @FXML
    private TextField msgTextField;


    @FXML
    void continuetoWinninglobby(ActionEvent event) throws Exception{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("WinnerLobby.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
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
        input=input+ msgTextField.getText();

        chatWindow.setText("username:" + " " + input);

    }
    @FXML
    void openchatWindow(ActionEvent event) {
        chatpane.setVisible(true);
        chatWindow.setVisible(true);
        sendmsgButton.setVisible(true);
        msgTextField.setVisible(true);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


