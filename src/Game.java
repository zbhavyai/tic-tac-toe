import java.io.*;

public class Game implements Runnable {
    private Board theBoard;
    private Referee theRef;

    public Game() {
        this.theBoard = new Board();
    }

    public void appointReferee(Referee r) throws IOException {
        this.theRef = r;
        this.theRef.setBoard(this.theBoard);
        this.theRef.getoPlayer().setBoard(this.theBoard);
        this.theRef.getxPlayer().setBoard(this.theBoard);
    }

    @Override
    public void run() {
        this.theRef.runTheGame();
    }
}
