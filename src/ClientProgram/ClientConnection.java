package ClientProgram;

import ClientProgram.GUI.IngameController;
import ClientProgram.GUI.LoginWindowController;
import ClientProgram.GUI.WinnerLobbyController;
import Model.Question;
import ServerUtilities.ServerResponse;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable{

    Socket socket;
    ObjectOutputStream out;
    LoginWindowController loginWindowController;
    IngameController ingameController;
    WinnerLobbyController winnerLobbyController;

    public ClientConnection (String hostName, int portNr, IngameController ingameController){
        this.ingameController = ingameController;

        System.out.println("ClientConnection created");
        try {
            this.socket = new Socket(hostName, portNr);
            out = new ObjectOutputStream(socket.getOutputStream());
            constructControllers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this).start();

    }

    public void constructControllers() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrawArea.fxml"));
//        loader.load();
//        loginWindowController = loader.getController();
        //System.out.println(loginWindowController);
        //loginWindowController = new LoginWindowController();
        //ingameController = new IngameController();
        //winnerLobbyController = new WinnerLobbyController();
    }



    @Override
    public void run() {
        System.out.println("Running...");
        receiveObjectFromServer();
    }

    private void receiveObjectFromServer() {
        Thread thread = new Thread(() -> {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object objectFromServer;
                while ((objectFromServer = in.readObject()) != null){
                    System.out.println("Object received for sorting");
                    processObjectFromServer(objectFromServer);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void sendObjectToServer(Object objectToServer){
        try {
            out.writeObject(objectToServer);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processObjectFromServer(Object objectFromServer){

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

            //Denna lambda Platform.runLater gör att vi inte bråkar med GUI-thread så synkar det snyggt
            Platform.runLater(() -> {
                ingameController.questionArea.setText(question.getQuestion());
                String[] answers = question.getAnswers();
                ingameController.answer1.setText(answers[0]);
                ingameController.answer2.setText(answers[1]);
                ingameController.answer3.setText(answers[2]);
                ingameController.answer4.setText(answers[3]);
            });
        }

        //Det går jättebra att skicka olika typer av object, måste inte vara klassat som Object
    }
}
