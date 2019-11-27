package ClientProgram;

import ClientProgram.GUI.GUI_Control;
import ClientProgram.GUI.Controllers.IngameController;
import Model.Question;
import Model.ScoreReport;
import ServerUtilities.ServerResponse;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientProtocol {

    void processObjectFromServer(Object objectFromServer){
        IngameController ingameController = GUI_Control.getIngameController();

        if(objectFromServer instanceof ServerResponse){

            switch (((ServerResponse) objectFromServer).type){
                //Hantera Server Responses
                case MESSAGE_FROM_CLIENT:
                    ingameController.chatwindowTextArea.appendText(((ServerResponse) objectFromServer).message + "\n");
                    break;
                case NOTIFY_GAME_OVER:
                    //Switch to game over window
                    break;
                case SCORE_REPORT:
                    System.out.println(((ServerResponse) objectFromServer).message);
                    Platform.runLater(()->{
                        GUI_Control.getScoreBoardController().scoreboardArea.setText("");
                        GUI_Control.getScoreBoardController().scoreboardArea.appendText(((ServerResponse) objectFromServer).message);
                        GUI_Control.changeScene(GUI_Control.getScoreBoardScene());
                    });
                    break;

            }
        }
        else if (objectFromServer instanceof Question){
            System.out.println("Object received as Question");
            Question question = (Question) objectFromServer;

            GUI_Control.getIngameController().currentQuestion = question;
            System.out.println(question.getQuestion());

            //Denna lambda Platform.runLater gör att vi inte bråkar med GUI-thread så synkar det snyggt
            Platform.runLater(() -> {
                GUI_Control.changeScene(GUI_Control.getIngameScene());
                ingameController.questionArea.setText(question.getQuestion());
                //Visa kategori i rätt label
                ingameController.categoryLabel.setText(question.getGenre());
                String[] answers = question.getAnswers();

                List<Button> buttonList = new ArrayList<>();
                buttonList.add(ingameController.answer1);
                buttonList.add(ingameController.answer2);
                buttonList.add(ingameController.answer3);
                buttonList.add(ingameController.answer4);

                for (int i = 0; i < buttonList.size(); i++) {
                    buttonList.get(i).setText(answers[i]);
                    buttonList.get(i).setDisable(false);
                    buttonList.get(i).setStyle("-fx-background-color:  #0074D9");
                }

            });
        }
        else if(objectFromServer instanceof List<?>){

            if ( ((List) objectFromServer).get(0) instanceof ScoreReport){
                //Hit kommer vi om objektet är en lista med ScoreReport-objekt

                List<ScoreReport> scoreReports = (List<ScoreReport>) objectFromServer;

                for (ScoreReport sr: scoreReports) {
                    System.out.println(sr);
                }

                Platform.runLater(()->{
                    GUI_Control.getScoreBoardController().scoreboardArea.setText("");
                    for (ScoreReport scoreReport : scoreReports) {
                        GUI_Control.getScoreBoardController().scoreboardArea.appendText(scoreReport.toString());
                    }

                    GUI_Control.changeScene(GUI_Control.getScoreBoardScene());
                });
            }
        }

        //Det går jättebra att skicka olika typer av object, måste inte vara klassat som Object
    }
}
