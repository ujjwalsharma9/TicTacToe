package strategy.winningStrategies;

import model.Board;
import model.Move;

public interface WinningStrategy {
    boolean checkWin(Board board, Move move);

    void handleUndo(Board board, Move move);
}
