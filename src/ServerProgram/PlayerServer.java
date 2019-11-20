package ServerProgram;

import Model.Player;
import Model.Score;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerServer implements Runnable{

    Player player;
    private final Socket socket;
    Game game;
    Score score;

    public PlayerServer(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
        game.addPlayerToList(this);
        new Thread(this).start();

        int numberOfRounds = 4; //CHANGE TO READ FROM PROPERTIES INSTEAD!!
        this.score = new Score(numberOfRounds);
        player = new Player();
    }

    @Override
    public void run() {
        receiveFromClient();
    }

    private void receiveFromClient() {
        Thread thread = new Thread(() -> {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object objectFromClient;
                while ((objectFromClient = in.readObject()) != null){
                    game.processObject(this, objectFromClient);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void sendObjectToClient(Object objectToClient){
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(objectToClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
