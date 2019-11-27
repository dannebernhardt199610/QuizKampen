package ClientProgram.GUI;

import ClientProgram.GUI.Controllers.IngameController;
import ClientProgram.GUI.Controllers.ScoreBoardController;
import ClientProgram.GUI.Controllers.WinnerLobbyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI_Control {

    private static Stage currentStage; //Kopierar primary stage i Main

    private static FXMLLoader ingameLoader = new FXMLLoader(GUI_Control.class.getResource("FXML/InGame.fxml"));
    private static Parent ingameParent;
    static { try { ingameParent = ingameLoader.load(); } catch (IOException e) { e.printStackTrace(); }}
    private static Scene ingameScene = new Scene(ingameParent);
    private static IngameController ingameController = ingameLoader.getController();

    private static FXMLLoader winnerLobbyLoader = new FXMLLoader(GUI_Control.class.getResource("FXML/WinnerLobby.fxml"));
    private static Parent winnerLobbyParent;
    static { try { winnerLobbyParent = winnerLobbyLoader.load(); } catch (IOException e){ e.printStackTrace(); }}
    private static Scene winnerLobbyScene = new Scene(winnerLobbyParent);
    private static WinnerLobbyController winnerLobbyController = winnerLobbyLoader.getController();

    private static FXMLLoader scoreBoardLoader = new FXMLLoader(GUI_Control.class.getResource("FXML/ScoreBoard.fxml"));
    private static Parent scoreBoardParent;
    static { try { scoreBoardParent = scoreBoardLoader.load(); } catch (IOException e){ e.printStackTrace(); }}
    private static Scene scoreBoardScene = new Scene(scoreBoardParent);
    private static ScoreBoardController scoreBoardController = scoreBoardLoader.getController();


    //Getters for controllers and scenes
    public static IngameController getIngameController() {
        return ingameController;
    }

    public static WinnerLobbyController getWinnerLobbyController() {
        return winnerLobbyController;
    }

    public static ScoreBoardController getScoreBoardController() {
        return scoreBoardController;
    }

    public static Scene getIngameScene() {
        return ingameScene;
    }

    public static Scene getWinnerLobbyScene() {
        return winnerLobbyScene;
    }

    public static Scene getScoreBoardScene() {
        return scoreBoardScene;
    }



    //This sets the currentStage static to a copy of the primary stage so we easily can access the current stage
    public static void setCurrentStage(Stage primaryStage) {
        currentStage = primaryStage;
    }

    //This allows for easily switching scenes
    public static void changeScene(Scene scene){
        currentStage.setScene(scene);
        currentStage.show();
    }
}
