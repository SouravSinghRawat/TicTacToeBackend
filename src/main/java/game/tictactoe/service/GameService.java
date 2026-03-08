package game.tictactoe.service;

import game.tictactoe.model.Game;
import game.tictactoe.model.GameStatus;
import game.tictactoe.model.TicTacToeSymbol;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, Game> games = new ConcurrentHashMap<>();
    private final int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
    };

    public Game createGame(String player1Id) {
        String gameId = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        Game game = new Game(gameId);
        game.setPlayer1(player1Id);
        games.put(gameId, game);
        return game;
    }

    public Game joinGame(String gameId, String player2Id) throws Exception {
        if (!games.containsKey(gameId)) throw new Exception("Room not found");
        Game game = games.get(gameId);
        if (game.getPlayer2() != null) throw new Exception("Room is full");

        game.setPlayer2(player2Id);
        game.setCurrentStatus(GameStatus.IN_PROGRESS); //
        return game;
    }

    public Game makeMove(String gameId, int cellId, String player) throws Exception {
        Game game = games.get(gameId);
        if (game.getCurrentStatus() == GameStatus.FINISHED) throw new Exception("Game over");
        if (game.getBoard()[cellId] != TicTacToeSymbol.EMPTY) throw new Exception("Cell taken");

        game.getBoard()[cellId] = game.getCurrentTurn();

        if (checkWinner(game.getBoard(), game.getCurrentTurn())) {
            game.setWinner(player);
            game.setCurrentStatus(GameStatus.FINISHED);
        } else if (isBoardFull(game.getBoard())) {
            game.setWinner("DRAW");
            game.setCurrentStatus(GameStatus.FINISHED);
        } else {
            game.setCurrentTurn(game.getCurrentTurn() == TicTacToeSymbol.X ? TicTacToeSymbol.O : TicTacToeSymbol.X);
        }
        return game;
    }

    private boolean checkWinner(TicTacToeSymbol[] board, TicTacToeSymbol symbol) {
        for (int[] c : winConditions) {
            if (board[c[0]] == symbol && board[c[1]] == symbol && board[c[2]] == symbol) return true;
        }
        return false;
    }

    private boolean isBoardFull(TicTacToeSymbol[] board) {
        for (TicTacToeSymbol cell : board) { if (cell == TicTacToeSymbol.EMPTY) return false; }
        return true;
    }
}