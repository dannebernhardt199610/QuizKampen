package ServerProgram;

import Model.Player;
import ServerUtilities.ClientRequest;
import Model.Question;
import Model.ScoreReport;
import ServerUtilities.ServerResponse;

import java.util.ArrayList;
import java.util.List;

public class Game {

    int nrOfPlayers = 2; //LOAD FROM PROPERTIES!!
    int nrOfRounds = 3; //LOAD FROM PROPERTIES!!
    int questionsPerRound = 2; //LOAD FROM PROPERTIES!!

    int currentRoundindex = 0;
    int currentQuestionindex = 0;

    List<PlayerServer> playerServers = new ArrayList<>();
    List<Question> questions = new ArrayList<>();


    public Game(){
        questions.add(new Question("Vad är 1+1?", new String[]{"2", "4", "8", "123"}));
    }


    public void addPlayerToList(PlayerServer playerServer){
        playerServers.add(playerServer);
    }



    //Här hanterar vi vad som händer med de objekt vi tar emot, de object vi vill skicka till klienter osv
    public synchronized void processObject(PlayerServer player, Object objectFromClient){

        //Vi matar in vilken spelare som tagit emot objektet, samt objektet som tagits emot
        //Hantera objekt som kommer in från PlayerServer

        if(objectFromClient instanceof ClientRequest){
            processRequest(player, (ClientRequest) objectFromClient);
        }



        //Exempel på att skicka ett objekt
        //playerServer.sendObjectToClient(object);

    }

    private void processRequest(PlayerServer playerServer, ClientRequest objectFromClient) {

        Player player = playerServer.getPlayer();

        switch (objectFromClient.type) {
            case SET_USERNAME:
                player.setName(objectFromClient.message);
                player.getScoreReport().setPlayerName(player.getName());
                break;
            case REQUEST_SCORE:
                //This sends a list with all the players scores to the playerServer that requested it
                List<ScoreReport> playerScoreReports = new ArrayList<>();
                for (PlayerServer pServer: playerServers) {
                    playerScoreReports.add(pServer.player.getScoreReport());
                }
                playerServer.sendObjectToClient(playerScoreReports);
                break;
            case SUBMIT_ANSWER:
                player.setHasAnswered(true);
                if(objectFromClient.message.equals(questions.get(currentQuestionindex).getCorrectAnswer())){
                    player.getScoreReport().addPointsToCurrentRound(currentRoundindex);
                }


                for (PlayerServer pServer : playerServers) {
                    if(!pServer.player.isHasAnswered()){
                        return;
                    }
                }
                //Hit kommer vi bara om alla har svarat
                for (PlayerServer pServer : playerServers) {
                    pServer.player.setHasAnswered(false);
                }
                currentQuestionindex++;
                if(currentQuestionindex < questionsPerRound){
                    sendToAllPlayers(questions.get(currentQuestionindex));
                }
                else {
                    currentRoundindex++;
                    currentQuestionindex = 0;
                    if(currentRoundindex < nrOfRounds){
                        //Skicka notifiering att en av spelarna ska välja kategori
                    }
                    else {
                        //Notify game over
                    }
                }


                //Check the submitted answer and update score if correct
                //Check if all players have answered (create boolean)
                //If all players have answered, move to the next question and send that question to all players
                break;
            case MESSAGE_TO_ALL:
                System.out.println("Message to all received");
                System.out.println("Message: " + objectFromClient.message);
                //Send chat message to all players
                ServerResponse serverResponse = new ServerResponse(ServerResponse.TYPE.MESSAGE_FROM_CLIENT,
                                                "[" + player.getName() + "] " + objectFromClient.message);
                for (PlayerServer pServer: playerServers) {
                    if(pServer != playerServer){
                        pServer.sendObjectToClient(serverResponse);
                    }
                }

                break;
            case NOTIFY_READY:
                player.setReady(true);
                if (playerServers.size() == nrOfPlayers) {
                    for (PlayerServer pServer : playerServers) {
                        if(!pServer.player.isReady())
                            return;
                    }
                    //Hit kommer vi bara om alla spelare isReady == true
                    sendToAllPlayers(questions.get(currentQuestionindex));
                }
                break;
        }
    }

    public void sendToAllPlayers(Object objectToClient){
        for (PlayerServer playerServer: playerServers) {
            playerServer.sendObjectToClient(objectToClient);
        }
        System.out.println("Object has now been sent to all clients");
    }


}
