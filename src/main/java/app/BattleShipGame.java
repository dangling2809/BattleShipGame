package app;

import exception.InvalidInputException;
import game.player.Player;
import parser.FileInputParser;
import parser.IParser;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleShipGame  {

    private Player player1;

    private Player player2;

    private File inputFile;


    public BattleShipGame(File inputFile) {
        this.inputFile=inputFile;
    }

    public void start() throws InvalidInputException {
        setup();
        //Staring it within main thread for this assignment , Can be easily extended to multithreaded approach if there are multiple players
        player1.fire(player2);
        Logger.getAnonymousLogger().log(Level.INFO,"Game Finished SuccessFully");
    }

    private void setup() throws InvalidInputException {
        IParser inputParser=new FileInputParser(inputFile,this);
        inputParser.parse();
        Logger.getAnonymousLogger().log(Level.INFO,"Game Initialized SuccessFully");
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public static void main(String[] args) throws Exception {
        if(args.length==0) {
            System.out.println("Please pass a input file to start the game");
            return;
        }
        BattleShipGame battleShipGame=new BattleShipGame(new File(args[0]));
        battleShipGame.start();
    }
}
