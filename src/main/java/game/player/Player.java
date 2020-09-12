package game.player;

import game.*;
import game.board.BattleBoard;
import game.events.FireEvent;
import game.events.FireEventResultType;
import game.ships.BattleShip;
import game.weapons.Weapon;
import printer.ConsolePrinter;
import printer.Printer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Player {

    private final String playerName;

    private final List<BattleShip> battleShips;

    private final Queue<Weapon> weapons;

    private final BattleBoard battleBoard;

    private final GameController controller;

    private Printer printer= ConsolePrinter.INSTANCE;

    private boolean winner;

    public Player(String playerName,BattleBoard battleBoard) {
        this.playerName = playerName;
        this.battleShips = new ArrayList<>();
        this.weapons = new LinkedList<>();
        this.battleBoard = battleBoard;
        this.controller=GameController.getInstance();
    }

    /**
     * To attack enemy
     * @param enemy
     */
    public void fire(Player enemy)
    {
        if(weapons.isEmpty()){
            printer.print(this.getPlayerName() + " has no more missiles left to launch");
            this.controller.enemyTurn(this,enemy);
            return;
        }
        this.controller.process(new FireEvent(this,enemy,weapons.poll()));
    }

    /**
     * To Handle Attack
     * @param weapon
     * @return
     */
    public FireEventResultType handleFire(Weapon weapon){
        BattleBoard board=this.getBattleBoard();
        //handle attack on board and capture result
        FireEventResultType result = board.attackAndGetResult(weapon);
        return result;
    }

    public int getTotalLivesLeft() {
        return this.getBattleShips().
                stream().collect(Collectors.summingInt(ship->ship.getTotalLives()));
    }

    public Queue<Weapon> getWeapons() {
        return weapons;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<BattleShip> getBattleShips() {
        return battleShips;
    }

    public BattleBoard getBattleBoard() {
        return battleBoard;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
