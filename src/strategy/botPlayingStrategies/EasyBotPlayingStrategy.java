package strategy.botPlayingStrategies;

import model.Board;
import model.Cell;
import model.CellState;
import model.Move;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board) {
        for(List<Cell> cells: board.getCells()){
            for(Cell cell: cells){
                if(cell.getCellState().equals(CellState.EMPTY)){
                    return new Move(cell, null);
                }
            }
        }

        return null;
    }
}
