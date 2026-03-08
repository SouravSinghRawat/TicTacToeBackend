package game.tictactoe.controller;

import game.tictactoe.model.Game;
import game.tictactoe.model.GameMessage;
import game.tictactoe.service.GameService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    public GameController(GameService gameService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/game.create")
    public void create(@Payload String playerName) {
        Game game = gameService.createGame(playerName);
        // Send to the room topic
        messagingTemplate.convertAndSend("/topic/game/" + game.getGameId(), game);
        // Send to the player-specific discovery topic
        messagingTemplate.convertAndSend("/topic/game/create/" + playerName, game);
    }

    @MessageMapping("/game.join")
    public void join(@Payload GameMessage msg) throws Exception {
        Game game = gameService.joinGame(msg.getGameId(), msg.getSender());
        messagingTemplate.convertAndSend("/topic/game/" + game.getGameId(), game);
    }

    @MessageMapping("/game.move")
    public void move(@Payload GameMessage msg) throws Exception {
        Game game = gameService.makeMove(msg.getGameId(), msg.getCellId(), msg.getSender());
        messagingTemplate.convertAndSend("/topic/game/" + game.getGameId(), game);
    }
}