package ServerProgram;

import Databas.Question;
import Databas.QuestionDatabase;
import Model.Player;
import ServerUtilities.ClientRequest;
import Model.ScoreReport;
import ServerUtilities.ServerResponse;

import java.util.ArrayList;
import java.util.List;

public class Game {

    Properties gameConfigProperty = new Properties();
    try{
        gameConfigProperty.load(new FileInputStream("/Users/johanozbek/Desktop/untitled3/src/gameConfig.properties"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    int questionsPerRound = Integer.parseInt(gameConfigProperty.getProperty("questionsPerRound"));
    int nrOfRounds = Integer.parseInt(gameConfigProperty.getProperty("nrOfRounds"));
    int nrOfPlayers = Integer.parseInt(gameConfigProperty.getProperty("nrOfPlayers"));

    int currentRoundindex = 0;
    int currentQuestionindex = 0;

    QuestionDatabase questionDatabase = new QuestionDatabase();
    Question currentQuestion;

    List<PlayerServer> playerServers = new ArrayList<>();
    List<Question> questions = questionDatabase.getGameQuestions();

    //Initialize questions
    public Game(){
        System.out.println(getCurrentQuestion().toString());
    }

    public Question getCurrentQuestion(){
        //Currently only gaming questions
        return questionDatabase.getGameQuestions().get(currentQuestionindex);
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
                //Check answer!!!
//                if(objectFromClient.message.equals(questions.get(currentQuestionindex).getAnswers().)){
//                    player.getScoreReport().addPointsToCurrentRound(currentRoundindex);
//                }

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
