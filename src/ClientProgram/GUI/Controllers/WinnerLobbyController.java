package ClientProgram.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class WinnerLobbyController {


    @FXML
    private Button EXITbutton;

    @FXML
    private ProgressBar winnerbar1;

    @FXML
    private ProgressBar winnerbar5;

    @FXML
    private ProgressBar winnerbar6;

    @FXML
    private ProgressBar winnerbar2;

    @FXML
    private ProgressBar winnerbar3;

    @FXML
    private ProgressBar winnerbar7;

    @FXML
    private ProgressBar winnerbar4;

    @FXML
    private ProgressBar winnerbar8;

    @FXML
    void disconnect(ActionEvent event) throws Exception {
     System.exit(0);
    }

}

