package game.tictactoe.model;

//import lombok.Data;
//
//@Data
public class Game {
    private String gameId;
    private TicTacToeSymbol[] board;
    private String player1;
    private String player2;
    private TicTacToeSymbol currentTurn;
    private String winner;
    private GameStatus currentStatus;

    public Game(String gameId){
        this.gameId=gameId;
        this.board=new TicTacToeSymbol[9];
        for(int i=0;i<9;i++){
            board[i]=TicTacToeSymbol.EMPTY;
        }
        this.currentStatus=GameStatus.NEW;
        this.currentTurn=TicTacToeSymbol.X;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public TicTacToeSymbol[] getBoard() {
        return board;
    }

    public void setBoard(TicTacToeSymbol[] board) {
        this.board = board;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public TicTacToeSymbol getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(TicTacToeSymbol currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(GameStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
