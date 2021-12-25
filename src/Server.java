import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Constants {
    private ServerSocket serverSocket;
    private ExecutorService pool;

    Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.pool = Executors.newCachedThreadPool();
            System.out.printf("%n[SERVER] The server is now running!");
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void communicateWithClient() {
        try {
            while (true) {
                System.out.printf("%n%n[SERVER] Waiting for player(s) to launch a new game.");

                Socket playerXsocket = this.serverSocket.accept();
                Player xPlayer = new Player(playerXsocket, LETTER_X);
                System.out.printf("%n%n[SERVER] A new player joined. Player assigned \'" + LETTER_X + "\'");

                Socket playerOsocket = this.serverSocket.accept();
                Player oPlayer = new Player(playerOsocket, LETTER_O);
                System.out.printf("%n%n[SERVER] A new player joined. Player assigned \'" + LETTER_O + "\'");

                Referee theRef = new Referee();
                theRef.setxPlayer(xPlayer);
                theRef.setoPlayer(oPlayer);

                Game theGame = new Game();
                System.out.printf("%n%n[SERVER] New game created");

                theGame.appointReferee(theRef);
                System.out.printf("%n%n[SERVER] Referee assigned to the game");
                this.pool.execute(theGame);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            this.pool.shutdown();
            System.out.printf("%n[SERVER] The server is shutting down!");
        }
    }

    public static void main(String[] args) {
        Server s = new Server(9090);
        s.communicateWithClient();
    }
}
