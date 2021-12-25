import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private BufferedReader stdin;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public Client(String serverName, int portNumber) {
        try {
            this.stdin = new BufferedReader(new InputStreamReader(System.in));
            this.clientSocket = new Socket(serverName, portNumber);
            this.socketIn = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.socketOut = new PrintWriter((this.clientSocket.getOutputStream()), true);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void communicateWithServer() {
        try {
            while (true) {
                String line = null;

                while (true) {
                    line = this.socketIn.readLine();

                    if (line.contains("\0")) {
                        line = line.replace("\0", "");
                        System.out.println(line);
                        break;
                    }

                    if (line.equals("QUIT")) {
                        return;
                    }

                    System.out.println(line);
                }

                line = this.stdin.readLine();
                this.socketOut.println(line); // this.socketOut.flush();
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                this.stdin.close();
                this.socketIn.close();
                this.socketOut.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 9090);
        client.communicateWithServer();
    }
}
