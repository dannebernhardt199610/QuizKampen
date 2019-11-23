package ClientProgram.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class GUI_Control {

    private static FXMLLoader ingameLoader = new FXMLLoader(GUI_Control.class.getResource("ingameeea.fxml"));
    private static Parent ingameParent;
    static { try { ingameParent = ingameLoader.load(); } catch (IOException e) { e.printStackTrace(); }}
    private static IngameController ingameController = ingameLoader.getController();

    private static FXMLLoader winnerLobbyLoader = new FXMLLoader(GUI_Control.class.getResource("WinnerLobby.fxml"));
    private static Parent winnerLobbyParent;
    static { try { winnerLobbyParent = winnerLobbyLoader.load(); } catch (IOException e){ e.printStackTrace(); }}
    private static WinnerLobbyController winnerLobbyController = winnerLobbyLoader.getController();



    public static FXMLLoader getIngameLoader() {
        return ingameLoader;
    }

    public static void setIngameLoader(FXMLLoader ingameLoader) {
        GUI_Control.ingameLoader = ingameLoader;
    }

    public static Parent getIngameParent() {
        return ingameParent;
    }

    public static void setIngameParent(Parent ingameParent) {
        GUI_Control.ingameParent = ingameParent;
    }

    public static IngameController getIngameController() {
        return ingameController;
    }

    public static void setIngameController(IngameController ingameController) {
        GUI_Control.ingameController = ingameController;
    }


    public static FXMLLoader getWinnerLobbyLoader() {
        return winnerLobbyLoader;
    }

    public static void setWinnerLobbyLoader(FXMLLoader winnerLobbyLoader) {
        GUI_Control.winnerLobbyLoader = winnerLobbyLoader;
    }

    public static Parent getWinnerLobbyParent() {
        return winnerLobbyParent;
    }

    public static void setWinnerLobbyParent(Parent winnerLobbyParent) {
        GUI_Control.winnerLobbyParent = winnerLobbyParent;
    }

    public static WinnerLobbyController getWinnerLobbyController() {
        return winnerLobbyController;
    }

    public static void setWinnerLobbyController(WinnerLobbyController winnerLobbyController) {
        GUI_Control.winnerLobbyController = winnerLobbyController;
    }
}
