package strategy.winningStrategies;

import model.Board;
import model.Move;
import model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{

    private final Map<Symbol, Integer> leftDiagonalMap = new HashMap<>();
    private final Map<Symbol, Integer> rightDiagonalMap = new HashMap<>();

    @Override
    public boolean checkWin(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        if(row == col){
            leftDiagonalMap.put(symbol, leftDiagonalMap.getOrDefault(symbol, 0) + 1);
        }

        if(row + col == board.getSize() - 1){
            rightDiagonalMap.put(symbol, rightDiagonalMap.getOrDefault(symbol, 0) + 1);
        }

        return ((row == col && leftDiagonalMap.get(symbol).equals(board.getSize()))
                || (row + col == board.getSize() - 1 && rightDiagonalMap.get(symbol).equals(board.getSize())));

    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        if(row == col){
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol) - 1);
        }

        if(row + col == board.getSize() - 1){
            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol) - 1);
        }

    }
}
