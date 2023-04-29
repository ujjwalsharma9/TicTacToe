package controller;

import exception.BotCountExceededException;
import exception.PlayerCountMismatchException;
import exception.SymbolRepeatedException;
import model.Game;
import model.GameState;
import model.Player;
import strategy.winningStrategies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game createGame(int size, List<Player> players, List<WinningStrategy> winningStrategies) throws SymbolRepeatedException, PlayerCountMismatchException, BotCountExceededException {
        return Game.getBuilder().setSize(size).setPlayers(players)
                .setWinningStrategies(winningStrategies).build();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public void displayGame(Game game) {
        game.display();
    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public Player getWinner(Game game){
        return game.getWinner();
    }

    public void undo(Game game){
        game.undo();
    }
}
