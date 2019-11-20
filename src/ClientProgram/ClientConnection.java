package ClientProgram;

import ClientProgram.GUI.IngameController;
import ClientProgram.GUI.LoginWindowController;
import ClientProgram.GUI.WinnerLobbyController;
import Model.Question;
import ServerUtilities.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable{

    Socket socket;
    //LoginWindowController loginWindowController;
    //IngameController ingameController;
    //WinnerLobbyController winnerLobbyController;

    public ClientConnection (String hostName, int portNr){
        System.out.println("ClientConnection created");
        constructControllers();

        try {
            this.socket = new Socket(hostName, portNr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread().start();

    }

    public void constructControllers(){
        //loginWindowController = new LoginWindowController();
        //ingameController = new IngameController();
        //winnerLobbyController = new WinnerLobbyController();
    }



    @Override
    public void run() {
        receiveObjectFromServer();
    }

    private void receiveObjectFromServer() {
        Thread thread = new Thread(() -> {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object objectFromServer;
                while ((objectFromServer = in.readObject()) != null){
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
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(objectToServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processObjectFromServer(Object objectFromServer){

        if(objectFromServer instanceof ServerResponse){

            switch (((ServerResponse) objectFromServer).type){
                //Hantera Server Responses
            }
        }
        else if (objectFromServer instanceof Question){
            Question question = (Question) objectFromServer;
            //ingameController.questionArea.setText(question.getQuestion());

            String[] answers = question.getAnswers();
//            ingameController.answer1.setText(answers[0]);
//            ingameController.answer2.setText(answers[1]);
//            ingameController.answer3.setText(answers[2]);
//            ingameController.answer4.setText(answers[3]);


        }

        //Här gör vi saker med objektet vi tagit emot


        //exempel på att skicka objekt till servern
        //sendObjectToServer(object);

        //Det går jättebra att skicka olika typer av object, måste inte vara klassat som Object
    }
}
