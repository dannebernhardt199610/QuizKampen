package ClientProgram.GUI;

import ClientProgram.ClientConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

   public static ClientConnection clientConnection;
    public static String name = "Player";
    public static int rounds = 0;
    public static int questionsPerRound = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/loginWindow.fxml"));
            Pane root = loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            primaryStage.show();

            //This means that closing the window closes the program
            primaryStage.setOnCloseRequest(t -> {
                Platform.exit();
                System.exit(0);
            });

            //GUI_Control gets a static copy of the primaryStage so we can easily switch scenes from anywhere
            GUI_Control.setCurrentStage(primaryStage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
