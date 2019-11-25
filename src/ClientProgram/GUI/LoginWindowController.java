package ClientProgram.GUI;

import ClientProgram.ClientConnection;
import ServerUtilities.ClientRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static ClientProgram.GUI.Main.clientConnection;

public class LoginWindowController {

    @FXML
    private VBox firstwindow;


    @FXML
    public TextField usernameField;

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }


    @FXML
    private Button button1;

    @FXML
    private Label label;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private Button b1;

    @FXML
    private Button howtoPlaybutton;


    @FXML
    private TextArea howtoPlayTextARea;

    @FXML
    void howtoPlay(ActionEvent event) {
        howtoPlayTextARea.setVisible(true);

    }

    @FXML
    void connectToServer(ActionEvent event) throws InterruptedException {
        initializeConnection(usernameField.getText());

        label.setVisible(true);
        progress.setVisible(true);
        label.setText("Connected as " + usernameField.getText());

        //Thread.sleep(2000);
        b1.setVisible(true);
    }

    @FXML
    void changeSceneToIngameScene(ActionEvent event) throws Exception {
        //This changes scene to the ingameScene
        GUI_Control.changeScene(GUI_Control.getIngameScene());
    }

    public void initializeConnection(String username){
        //Move connection to connect button!!
        String hostName = "127.0.0.1";
        int portNr = 13377;

        //Static import of Main.clientConnection so we can access it from anywhere
        clientConnection = new ClientConnection(hostName, portNr);
        System.out.println("Username = " + username);
        clientConnection.sendObjectToServer(new ClientRequest(ClientRequest.TYPE.SEND_USERNAME, username));
        System.out.println("Object sent to server");
    }
}
