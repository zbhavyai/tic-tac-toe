import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Constants {
    private String name;
    private char mark;
    private Board board;
    private Player opponent;

    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public Player(Socket s, char mark) {
        this.clientSocket = s;
        this.mark = mark;

        try {
            this.socketIn = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.socketOut = new PrintWriter(this.clientSocket.getOutputStream(), true);

            this.socketOut.println("\n[INFO] Hello.");
            this.socketOut.println("\n[INFO] You will be \'" + this.getMark() + "\' for this game.");
            this.socketOut.println("\n[INFO] Please wait while we connect you to an opponent.");
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void setBoard(Board theBoard) {
        this.board = theBoard;
    }

    public char getMark() {
        return this.mark;
    }

    public void alertPlayers(String alert) {
        this.socketOut.println(alert);
    }

    private void globalPrint(String message) {
        this.socketOut.println(message);
        this.opponent.socketOut.println(message);
    }

    public String getPlayerName() {
        return this.name;
    }

    public void setPlayerName(String prompt) {
        while (this.name == null) {
            try {
                this.socketOut.println(prompt + "\0");
                this.name = this.socketIn.readLine();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void play() {
        this.makeMove();
        this.globalPrint(this.board.display());

        if (this.board.xWins() == false) {
            if (this.board.oWins() == false) {
                if (this.board.isFull() == false) {
                    this.opponent.play();
                }

                else {
                    this.globalPrint("\n[RESULT] THE GAME IS OVER: Tie!\0");
                }
            }

            else {
                String winnerName = this.mark == 'O' ? this.name : this.opponent.name;
                this.globalPrint("\n[RESULT] THE GAME IS OVER: " + winnerName + " is the winner!\0");
            }
        }

        else {
            String winnerName = this.mark == 'X' ? this.name : this.opponent.name;
            this.globalPrint("\n[RESULT] THE GAME IS OVER: " + winnerName + " is the winner!\0");
        }
    }

    public void makeMove() {
        while (true) {
            int row = this.getRowOrColIndex(
                    String.format("[QUES] %s, what row should your next %c be placed in? ", this.name, this.mark));
            int col = this.getRowOrColIndex(
                    String.format("[QUES] %s, what column should your next %c be placed in? ", this.name, this.mark));

            if (this.board.getMark(row, col) == ' ') {
                this.board.addMark(row, col, this.mark);
                break;
            }

            else {
                this.socketOut.println(
                        String.format("[INFO] (%d, %d) is already marked. Please choose another (row, column)%n", row, col));
            }
        }

    }

    private int getRowOrColIndex(String prompt) {
        this.socketOut.println(prompt + "\0");
        int index;

        while (true) {
            try {
                index = Integer.parseInt(this.socketIn.readLine());

                if (index > 2 || index < 0) {
                    throw new IOException("Move not valid");
                }

                return index;
            }

            catch (NumberFormatException e) {
                this.socketOut.println("[INFO] Invalid number. Please try again\0");
            }

            catch (IOException e) {
                this.socketOut.println("[INFO] " + e.getMessage() + ". Please try again\0");
            }
        }
    }
}
