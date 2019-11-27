package ServerProgram;

import Model.Question;
import ServerProgram.Databas.QuestionDatabase;
import Model.Player;
import ServerUtilities.ClientRequest;
import Model.ScoreReport;
import ServerUtilities.ServerResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Game {

    Properties gameConfigProperty = new Properties();

    int questionsPerRound;
    int nrOfRounds;
    int nrOfPlayers;

    private int currentRoundindex = 0;
    private int currentQuestionindex = 0;
    private int currentGenreIndex = 0;

    QuestionDatabase questionDatabase;

    List<PlayerServer> playerServers = new ArrayList<>();
    ArrayList[] genres;
    List<Question> questionList;

    //Initialize questions
    public Game() {

        try {
            gameConfigProperty.load(new FileInputStream("src/ServerProgram/Resources/gameConfig.properties"));
            questionsPerRound = Integer.parseInt(gameConfigProperty.getProperty("questionsPerRound"));
            nrOfRounds = Integer.parseInt(gameConfigProperty.getProperty("nrOfRounds"));
            nrOfPlayers = Integer.parseInt(gameConfigProperty.getProperty("nrOfPlayers"));

            //Frågor med mera är redan slumpmässiga då de slumpas i database-konstruktorn
            questionDatabase = new QuestionDatabase();
            genres = questionDatabase.getGenres();
            questionList = genres[currentGenreIndex];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Question getCurrentQuestion(){
        //Currently only gaming questions
        return questionDatabase.getGameQuestions().get(currentQuestionindex);
    }

    public void addPlayerToList(PlayerServer playerServer){
        playerServers.add(playerServer);
    }



    //Här hanterar vi vad som händer med de objekt vi tar emot, de object vi vill skicka till klienter osv
    public synchronized void processObject(PlayerServer playerServer, Object objectFromClient){

        //Vi matar in vilken spelare som tagit emot objektet, samt objektet som tagits emot
        //Hantera objekt som kommer in från PlayerServer

        if(objectFromClient instanceof ClientRequest){
            processRequest(playerServer, (ClientRequest) objectFromClient);
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
               if(objectFromClient.message.equals(questionList.get(currentQuestionindex).getCorrectAnswer())){
                    player.getScoreReport().addPointToCurrentRound();
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
                    sendToAllPlayers(questionList.get(currentQuestionindex));
                }
                else {
                    //Förbered för nästa runda
                    currentRoundindex++;

                    currentGenreIndex++;
                    currentQuestionindex = 0;

                    //Visa poängställning
                    List<ScoreReport> scoreReports = new ArrayList<>();
                    for (PlayerServer pServer: playerServers) {
                        pServer.getPlayer().getScoreReport().setCurrentRoundIndex(currentRoundindex);
                        scoreReports.add(pServer.getPlayer().getScoreReport());
                        System.out.println(pServer.getPlayer().getScoreReport());
                    }

                    String scoreReportsString = "";
                    for(ScoreReport sr:scoreReports) {
                        scoreReportsString += sr.toString();
                    }

                    for (PlayerServer pServer: playerServers) {
                        //pServer.sendListToClient(scoreReports);
                        pServer.sendObjectToClient(new ServerResponse(ServerResponse.TYPE.SCORE_REPORT, scoreReportsString));
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(currentGenreIndex >= genres.length){
                        currentGenreIndex = 0;
                        //Ny database innebär att allting shufflas
                        questionDatabase = new QuestionDatabase();
                        genres = questionDatabase.getGenres();
                    }

                    if(currentRoundindex < nrOfRounds){
                        //Vi byter kategori
                        questionList = genres[currentGenreIndex];
                        sendToAllPlayers(questionList.get(currentQuestionindex));
                    }
                    else {
                        //Notify game over
                        sendToAllPlayers(new ServerResponse(ServerResponse.TYPE.NOTIFY_GAME_OVER, null));
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


                    sendToAllPlayers(questionList.get(currentQuestionindex));
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
