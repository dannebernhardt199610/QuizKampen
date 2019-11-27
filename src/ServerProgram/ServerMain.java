package ServerProgram;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class ServerMain implements Runnable {

    private Properties gameConfigProperty = new Properties();
    private int playersPerGame;
    private int portNr = 13377;


    public ServerMain(){
        new Thread(this).start();
    }




    public static void main(String[] args) {
        new ServerMain();
    }

    @Override
    public void run() {
        try {
            gameConfigProperty.load(new FileInputStream("src/ServerProgram/Resources/gameConfig.properties"));
            playersPerGame = Integer.parseInt(gameConfigProperty.getProperty("nrOfPlayers"));
            portNr = Integer.parseInt(gameConfigProperty.getProperty("portNr"));

            ServerSocket serverSocket = new ServerSocket(portNr);


            System.out.println("Server running");
            boolean running = true;
            while (running){
                Game game = new Game();

                for (int i = 0; i < playersPerGame; i++) {
                    Socket socket = serverSocket.accept();
                    new PlayerServer(socket, game);
                    System.out.println("Player " + i + " Connected");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
