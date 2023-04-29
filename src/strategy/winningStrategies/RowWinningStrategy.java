package strategy.winningStrategies;

import model.Board;
import model.Move;
import model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{

    private final Map<Integer, Map<Symbol, Integer>> rowMap = new HashMap<>();

    @Override
    public boolean checkWin(Board board, Move move) {
        int row = move.getCell().getRow();

        Symbol symbol = move.getPlayer().getSymbol();

        if(!rowMap.containsKey(row)){
            rowMap.put(row, new HashMap<>());
        }

        Map<Symbol, Integer> symbolMap = rowMap.get(row);

        symbolMap.put(symbol, symbolMap.getOrDefault(symbol, 0) + 1);

        return symbolMap.get(symbol).equals(board.getSize());
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();

        Symbol symbol = move.getPlayer().getSymbol();


        Map<Symbol, Integer> symbolMap = rowMap.get(row);

        symbolMap.put(symbol, symbolMap.get(symbol) - 1);

    }
}
