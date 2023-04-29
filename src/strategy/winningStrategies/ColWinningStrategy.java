package strategy.winningStrategies;

import model.Board;
import model.Move;
import model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{

    private final Map<Integer, Map<Symbol, Integer>> colMap = new HashMap<>();

    @Override
    public boolean checkWin(Board board, Move move) {
        int col = move.getCell().getColumn();

        Symbol symbol = move.getPlayer().getSymbol();

        if(!colMap.containsKey(col)){
            colMap.put(col, new HashMap<>());
        }

        Map<Symbol, Integer> symbolMap = colMap.get(col);

        symbolMap.put(symbol, symbolMap.getOrDefault(symbol, 0) + 1);

        return symbolMap.get(symbol).equals(board.getSize());
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getColumn();

        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> symbolMap = colMap.get(col);

        symbolMap.put(symbol, symbolMap.get(symbol) - 1);
    }
}
