package ServerProgram;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain implements Runnable {

    private int playersPerGame = 2;
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
