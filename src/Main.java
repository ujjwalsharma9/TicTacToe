import controller.GameController;
import exception.BotCountExceededException;
import exception.PlayerCountMismatchException;
import exception.SymbolRepeatedException;
import model.*;
import strategy.winningStrategies.ColWinningStrategy;
import strategy.winningStrategies.DiagonalWinningStrategy;
import strategy.winningStrategies.RowWinningStrategy;
import strategy.winningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SymbolRepeatedException, PlayerCountMismatchException, BotCountExceededException {

        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController();
        //instantiate a game
        //play till winner is decided.

        int size = 3;

        List<Player> players = new ArrayList<>();

        players.add(new Player(1L, "Ujjwal", new Symbol('X'), PlayerType.HUMAN));
        players.add(new Bot(2L, "GPT", new Symbol('0'), BotDifficultyLevel.EASY));

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

        Game game = gameController.createGame(size, players, winningStrategies);

        while(gameController.checkState(game).equals(GameState.IN_PROG)){
            gameController.displayGame(game);
            System.out.println("Does anyone want to undo?");
            String undo = scanner.next();
            if(undo.equalsIgnoreCase("y")){
                game.undo();
                continue;
            }
            gameController.makeMove(game);
        }

        if(gameController.checkState(game).equals(GameState.WIN)){
            System.out.println("The winner is " + gameController.getWinner(game).getName());
        }
        else{
            System.out.println("Its a draw!");
        }
    }
}