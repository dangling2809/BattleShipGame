package parser;

import game.ships.BattleShip;
import app.BattleShipGame;
import game.ships.BattleShipType;
import game.board.Location;
import game.board.BattleBoard;
import exception.InvalidInputException;
import game.weapons.Missile;
import game.player.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileInputParser implements IParser {

    private final String DELEMETER=" ";

    private File file;

    private BattleShipGame battleShipGame;

    public FileInputParser(File file, BattleShipGame battleShipGame){
        this.file=file;
        this.battleShipGame=battleShipGame;
    }

    public void parse() throws InvalidInputException {
        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
                List<String> input=stream.collect(Collectors.toList());
                String[] widthAndHeightOfBattleBoard=input.get(0).split(DELEMETER);
                int numberOfBattleShips= Integer.valueOf(input.get(1));
                String[] battleShip1Config= input.get(2).split(DELEMETER);
                String[] battleShip2Config= input.get(3).split(DELEMETER);
                String[] player1Missiles=input.get(4).split(DELEMETER);
                String[] player2Missiles= input.get(5).split(DELEMETER);

                //create boards for both players
                BattleBoard board1=getBattleBoard(widthAndHeightOfBattleBoard);
                BattleBoard board2=getBattleBoard(widthAndHeightOfBattleBoard);

                //create players
                Player player1=new Player("Player-1",board1);
                Player player2=new Player("Player-2",board2);

                //assign battleships  to players
                assignBattleShips(battleShip1Config,player1,player2);
                assignBattleShips(battleShip2Config,player1,player2);

                //initialize boards with battleships
                board1.initialize(player1);
                board2.initialize(player2);

                //assign missiles to player 1
                assignMissiles(player1Missiles,player1);
                //assign missiles to player 2
                assignMissiles(player2Missiles,player2);

                //uncomment below to view  game setup
               // player1.getBattleBoard().display();
              //  player2.getBattleBoard().display();

                this.battleShipGame.setPlayer1(player1);
                this.battleShipGame.setPlayer2(player2);
        } catch (InvalidInputException | IOException e){
            Logger.getAnonymousLogger().log(Level.SEVERE,e.getMessage(),e);
            throw new InvalidInputException("Non parsable input ->" + e.getMessage(),e);
        }

    }

    /**
     * Assigns missile
     * @param missilesConfigurations
     * @param player
     */
    private void assignMissiles(String[] missilesConfigurations, Player player) throws InvalidInputException {
        try {
            for (String missileConfig : missilesConfigurations) {
                Location missileTarget = new Location(missileConfig.charAt(0), Integer.parseInt("" + missileConfig.charAt(1)));
                Missile missile = new Missile(missileTarget);
                player.getWeapons().offer(missile);
            }
        }catch(ArrayIndexOutOfBoundsException e){
            throwInvalidInputException(Arrays.toString(missilesConfigurations),e);
        }
    }

    private void assignBattleShips(String[] battleShipConfig, Player player1, Player player2) throws InvalidInputException {
        try {
        final BattleShipType battleShipType=BattleShipType.valueOf(battleShipConfig[0]);
        final int width=Integer.parseInt(battleShipConfig[1]);
        final int height=Integer.parseInt(battleShipConfig[2]);
        Spliterator<String> spliterator = Arrays.spliterator(battleShipConfig, 3, battleShipConfig.length);
        //assign battle ships alternatively
        AtomicBoolean isPLayer1BattleShip= new AtomicBoolean(true);
        spliterator.forEachRemaining(s -> {
            Location location=new Location(s.charAt(0),Integer.parseInt(""+s.charAt(1)));
            BattleShip battleShip=new BattleShip(battleShipType,width,height,location);
            if(isPLayer1BattleShip.get()){
                player1.getBattleShips().add(battleShip);
                isPLayer1BattleShip.set(false);
            }else{
                player2.getBattleShips().add(battleShip);
                isPLayer1BattleShip.set(true);
            }
        });
        }catch(ArrayIndexOutOfBoundsException e){
            throwInvalidInputException(Arrays.toString(battleShipConfig),e);
        }
    }

    private BattleBoard getBattleBoard(String[] widthAndHeightOfBattleBoard) throws InvalidInputException {
        try {
            int width=Integer.valueOf(widthAndHeightOfBattleBoard[0]);
            char height=widthAndHeightOfBattleBoard[1].charAt(0);
            return new BattleBoard(width,height);
        }catch(ArrayIndexOutOfBoundsException e){
            throwInvalidInputException(Arrays.toString(widthAndHeightOfBattleBoard),e);
         }
        return null;
    }


    public void throwInvalidInputException(String line,Exception e) throws InvalidInputException {
        if(line==null || line.isEmpty()) throw new InvalidInputException("Empty line" ,e);
        throw new InvalidInputException("Line " + line + " is invalid" ,e);
    }

}
