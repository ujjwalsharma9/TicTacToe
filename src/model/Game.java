package model;

import exception.BotCountExceededException;
import exception.PlayerCountMismatchException;
import exception.SymbolRepeatedException;
import strategy.winningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;
    private List<Move> moves;

    private Game(int size, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(size);
        this.players = players;
        this.gameState = GameState.IN_PROG;
        this.nextPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
        this.moves = new ArrayList<>();
    }

    private boolean isValid(int row, int col){
        if(row >= board.getSize() || col >= board.getSize()){
            return false;
        }

        Cell c = board.getCells().get(row).get(col);
        return c.getCellState() == CellState.EMPTY;
    }

    public void makeMove(){
        Player player = players.get(nextPlayerIndex);

        System.out.println(player.getName() + "'s turn, Enter row and col");
        Move move = player.makeMove(board);

        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();


        if(isValid(row, col)){
            Cell c = board.getCells().get(row).get(col);
            c.setPlayer(player);
            c.setCellState(CellState.FILLED);
            moves.add(new Move(c, player));
            nextPlayerIndex++;
            nextPlayerIndex %= players.size();

            if(checkWinner(move)){
                winner = player;
                gameState = GameState.WIN;
            }

            else if(moves.size() == board.getSize() * board.getSize()){
                gameState = GameState.DRAW;
            }
        }
        else{
            System.out.println("Invalid row and col");
        }
    }

    private boolean checkWinner(Move move){
        for(WinningStrategy winningStrategy: winningStrategies){
            if(winningStrategy.checkWin(board, move)){
                return true;
            }
        }

        return false;
    }

    public void undo(){
        if(moves.size() == 0){
            System.out.println("No moves to undo");
            return;
        }
        Move lastMove = moves.get(moves.size() - 1);
        moves.remove(lastMove);

        for(WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(board, lastMove);
        }

        lastMove.getCell().setCellState(CellState.EMPTY);

        nextPlayerIndex--;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();
    }

    public void display(){
        board.display();
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder{
        private int size;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        private Builder(){
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }


        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder addPlayer(Player player){
            this.players.add(player);
            return this;
        }

        public Builder addWinningStrategy(WinningStrategy winningStrategy){
            this.winningStrategies.add(winningStrategy);
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private void validateBotCount() throws BotCountExceededException {
            int count = 0;
            for(Player player: players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    count++;
                }
            }

            if(count > 1){
                throw new BotCountExceededException();
            }
        }

        private void validateSymbols() throws SymbolRepeatedException {
            HashMap<Character, Integer> symbolMap = new HashMap<>();
            for(Player player: players){
                symbolMap.put(player.getSymbol().getaChar(),
                        1 + symbolMap.getOrDefault(player.getSymbol().getaChar(), 0));

                if(symbolMap.get(player.getSymbol().getaChar()) > 1){
                    throw new SymbolRepeatedException();
                }
            }

        }

        private void validatePlayerCount() throws PlayerCountMismatchException {
            if(players.size() != size - 1){
                throw new PlayerCountMismatchException();
            }
        }

        public Game build() throws BotCountExceededException, PlayerCountMismatchException, SymbolRepeatedException {
            validateBotCount();
            validatePlayerCount();
            validateSymbols();
            return new Game(size, players, winningStrategies);
        }
    }
    public Board getBoard() {
        return board;
    }


    public List<Player> getPlayers() {
        return players;
    }


    public Player getWinner() {
        return winner;
    }


    public GameState getGameState() {
        return gameState;
    }


    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }


    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public List<Move> getMoves() {
        return moves;
    }

}
