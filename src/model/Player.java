package model;

import java.util.Scanner;

public class Player {
    private long id;
    private String name;
    private Symbol symbol;
    private PlayerType playerType;
    private final Scanner scn;

    public Player(long id, String name, Symbol symbol, PlayerType playerType) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.scn = new Scanner(System.in);
    }

    public Move makeMove(Board board){
        int row = scn.nextInt();
        int col = scn.nextInt();

        Cell cell = new Cell(row, col);
        cell.setPlayer(this);

        return new Move(cell, this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
