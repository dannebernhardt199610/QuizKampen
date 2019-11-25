package ClientProgram.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI_Control {

    private static Stage currentStage; //Kopierar primary stage i Main

    private static FXMLLoader ingameLoader = new FXMLLoader(GUI_Control.class.getResource("ingameeea.fxml"));
    private static Parent ingameParent;
    static { try { ingameParent = ingameLoader.load(); } catch (IOException e) { e.printStackTrace(); }}
    private static Scene ingameScene = new Scene(ingameParent);
    private static IngameController ingameController = ingameLoader.getController();

    private static FXMLLoader winnerLobbyLoader = new FXMLLoader(GUI_Control.class.getResource("WinnerLobby.fxml"));
    private static Parent winnerLobbyParent;
    static { try { winnerLobbyParent = winnerLobbyLoader.load(); } catch (IOException e){ e.printStackTrace(); }}
    private static Scene winnerLobbyScene = new Scene(winnerLobbyParent);
    private static WinnerLobbyController winnerLobbyController = winnerLobbyLoader.getController();


    //Getters for controllers and scenes
    public static IngameController getIngameController() {
        return ingameController;
    }

    public static WinnerLobbyController getWinnerLobbyController() {
        return winnerLobbyController;
    }

    public static Scene getIngameScene() {
        return ingameScene;
    }

    public static Scene getWinnerLobbyScene() {
        return winnerLobbyScene;
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
