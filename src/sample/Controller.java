package sample;

import com.sun.glass.events.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox firstwindow;

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    @FXML
    public TextField username;

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
    void ConnecttoServer(ActionEvent event) throws InterruptedException {
        label.setVisible(true);
        progress.setVisible(true);
        label.setText("Connected as " + username.getText());

        Thread.sleep(2000);
        b1.setVisible(true);
    }

    @FXML
    void listen(ActionEvent event) throws Exception {

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("ingameeea.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();

        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
