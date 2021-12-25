public class Referee {
    private Player xPlayer;
    private Player oPlayer;
    private Board board;

    public Referee() {
    }

    public void runTheGame() {
        this.xPlayer.alertPlayers("\n[REFEREE] You are now connected.");
        this.oPlayer.alertPlayers("\n[REFEREE] You are now connected.");

        // ask players for their name
        this.xPlayer.setPlayerName("\n[REFEREE] Please enter your name.");
        this.oPlayer.setPlayerName("\n[REFEREE] Please enter your name.");

        // set each other as opponents to one another
        this.xPlayer.setOpponent(this.oPlayer);
        this.oPlayer.setOpponent(this.xPlayer);

        // tell the name of their opponent
        this.xPlayer.alertPlayers("\n[REFREE] Hello " + this.xPlayer.getPlayerName() + "! Your opponent is "
                + this.oPlayer.getPlayerName() + ".");
        this.oPlayer.alertPlayers("\n[REFREE] Hello " + this.oPlayer.getPlayerName() + "! Your opponent is "
                + this.xPlayer.getPlayerName() + ".");

        this.xPlayer.alertPlayers("\n[REFREE] Game started.");
        this.oPlayer.alertPlayers("\n[REFREE] Game started.");

        this.xPlayer.alertPlayers(this.board.display());
        this.oPlayer.alertPlayers(this.board.display());

        this.xPlayer.play();

        this.xPlayer.alertPlayers("\n[REFEREE] Game ended.");
        this.oPlayer.alertPlayers("\n[REFEREE] Game ended.");
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setoPlayer(Player oPlayer) {
        this.oPlayer = oPlayer;
    }

    public void setxPlayer(Player xPlayer) {
        this.xPlayer = xPlayer;
    }

    public Player getxPlayer() {
        return this.xPlayer;
    }

    public Player getoPlayer() {
        return this.oPlayer;
    }
}
