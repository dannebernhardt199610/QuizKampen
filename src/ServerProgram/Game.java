package ServerProgram;

import Model.Player;
import ServerUtilities.ClientRequest;
import Model.Question;
import Model.Score;

import java.util.ArrayList;
import java.util.List;

public class Game {

    int nrOfPlayers = 2; //LOAD FROM PROPERTIES!!

    List<PlayerServer> playerServers = new ArrayList<>();
    Question currentQuestion = new Question("Vad är 1+1?", new String[]{"2", "4", "8", "123"});

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

        switch (objectFromClient.type) {

            case SEND_USERNAME:
                System.out.println("SEND_USERNAME received");
                System.out.println("Number or players:" + playerServers.size());
                Player player = playerServer.getPlayer();
                player.setName(objectFromClient.message);

                if(playerServers.size() == nrOfPlayers){
                    System.out.println("All players connected, ready to send");
                    //JUST A TEST! GET FROM DATABASE

                    sendToAllPlayers(currentQuestion);
                }
                break;
            case REQUEST_SCORE:
                //This sends a list with all the players scores to the playerServer that requested it
                List<Score> playerScores = new ArrayList<>();
                for (PlayerServer server: playerServers) {
                    playerScores.add(server.score);
                }
                playerServer.sendObjectToClient(playerScores);
                break;
            case SUBMIT_ANSWER:
                //Check the submitted answer and update score if correct
                //Check if all players have answered (create boolean)
                //If all players have answered, move to the next question and send that question to all players
                break;
            case MESSAGE_TO_ALL:
                //Send chat message to all players
                sendToAllPlayers(objectFromClient.message);
                break;
            case VERIFY_USERNAME:
                //Check if username already exists. Return ServerResponse object
                break;
        }
    }

    public void sendToAllPlayers(Object objectToClient){
        for (PlayerServer playerServer: playerServers) {
            playerServer.sendObjectToClient(objectToClient);
        }
    }


}
