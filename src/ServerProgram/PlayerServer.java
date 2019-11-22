package ServerProgram;

import Model.Player;
import Model.ScoreReport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerServer implements Runnable{

    Player player;
    private final Socket socket;
    ObjectOutputStream out;
    Game game;
    ScoreReport scoreReport;

    public PlayerServer(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
        game.addPlayerToList(this);

        try { out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) { e.printStackTrace(); }

        new Thread(this).start();

        int numberOfRounds = 4; //CHANGE TO READ FROM PROPERTIES INSTEAD!!
        this.scoreReport = new ScoreReport(numberOfRounds);
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
            out.writeObject(objectToClient);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
