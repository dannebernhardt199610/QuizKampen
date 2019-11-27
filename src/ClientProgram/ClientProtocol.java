package ClientProgram;

import ClientProgram.GUI.GUI_Control;
import ClientProgram.GUI.IngameController;
import Databas.Answer;
import Databas.Question;
import ServerUtilities.ServerResponse;
import javafx.application.Platform;

public class ClientProtocol {

    void processObjectFromServer(Object objectFromServer){
        IngameController ingameController = GUI_Control.getIngameController();

        if(objectFromServer instanceof ServerResponse){

            switch (((ServerResponse) objectFromServer).type){
                //Hantera Server Responses
                case MESSAGE_FROM_CLIENT:
                    ingameController.chatWindow.appendText(((ServerResponse) objectFromServer).message + "\n");
            }
        }
        else if (objectFromServer instanceof Question){
            System.out.println("Object received as Question");
            Question question = (Question) objectFromServer;

            System.out.println(question.getQuestion());

            //Denna lambda Platform.runLater gör att vi inte bråkar med GUI-thread så synkar det snyggt
            Platform.runLater(() -> {
                ingameController.questionArea.setText(question.getQuestion());
                //Visa kategori i rätt label
                ingameController.categoryLabel.setText("Kategori");
                Answer[] answers = question.getAnswers();
                ingameController.answer1.setText(answers[0].getClaim());
                ingameController.answer2.setText(answers[1].getClaim());
                ingameController.answer3.setText(answers[2].getClaim());
                ingameController.answer4.setText(answers[3].getClaim());
            });
        }

        //Det går jättebra att skicka olika typer av object, måste inte vara klassat som Object
    }
}
