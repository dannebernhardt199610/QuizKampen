package ClientProgram.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        label.setVisible(true);
        progress.setVisible(true);
        label.setText("Connected as " + usernameField.getText());

        Thread.sleep(2000);
        b1.setVisible(true);
    }

    @FXML
    void changeSceneToIngameScene(ActionEvent event) throws Exception {
        //Vi måste kanske se till att man inte kan ändra användarnamn efter man uppkopplat, och att uppkopplingen faktiskt sker vid "connectToServer"

        //Ska vi byta några variabel-namn kanske som tableViewParent till ingameParent exempelvis?
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("ingameeea.fxml"));
//        Parent tableViewParent = loader.load();


        Scene ingameScene = new Scene(GUI_Control.getIngameParent());

        //Access the controller and initialize username in that controller
//        IngameController ingameController = loader.getController();
//        ingameController.initializeConnection(usernameField.getText());
        GUI_Control.getIngameController().initializeConnection(usernameField.getText());


        //This gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ingameScene);
        window.show();

    }
}
