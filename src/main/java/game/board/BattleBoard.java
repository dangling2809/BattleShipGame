package game.board;

import exception.InvalidInputException;
import game.player.Player;
import game.ships.BattleShip;
import game.ships.BattleShipPart;
import game.events.FireEventResultType;
import game.weapons.Weapon;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BattleBoard {

    private Player player;
    private final BattleShipPart[][] coordinates;
    private final int width;
    private final int height;

    public BattleBoard(int width,char height){
        this.width= width + 1 ;
        this.height= height - 'A' + 2;
        this.coordinates =new BattleShipPart[this.width][this.height];
    }

    /**
     * Initialization of BattleBord for given {@Link Player}
     * @throws InvalidInputException
     */
    public void initialize(Player player) throws InvalidInputException {
        List<BattleShip> battleShips= player.getBattleShips();
        placeBattleShipsOnBoard(battleShips);
        this.player=player;
    }

    /**
     * Places battleships on board for {@link Player}
     * @param battleShips
     * @throws InvalidInputException
     */
    private void placeBattleShipsOnBoard(List<BattleShip> battleShips) throws InvalidInputException{
        if(battleShips==null) return;
        for(BattleShip battleShip:battleShips)
        {
            Location locationToPlace = battleShip.getLocation();
            if (tryPlace(locationToPlace, battleShip)) {
                Logger.getAnonymousLogger().log(Level.FINER,"Placed BattleShip {0} at {1}",new Object[] {battleShip,locationToPlace});
            } else {
                throw new InvalidInputException(MessageFormat.format("Cannot place {0} on board at location {1}",
                        new Object[]{battleShip,locationToPlace}));
            }
        }
    }

    /**
     * CoOrdinates of {@link BattleBoard}
     * @return
     */
    public BattleShipPart[][] getCoordinates() {
        return coordinates;
    }

    /**
     * Tries to place
     * @param locationToPlace
     * @param battleShip
     * @return true if placed successFully
     * @throws InvalidInputException If {@link BattleShip} not placed successfully
     */
    private boolean tryPlace(Location locationToPlace,BattleShip battleShip) throws InvalidInputException{
        int xStart =locationToPlace.getxCoordinate();
        int xEnd= xStart + battleShip.getWidth() - 1 ;
        int yStart= locationToPlace.getyCoordinate();
        int yEnd= yStart + battleShip.getHeight() - 1 ;
        return withinBounds(xStart,xEnd,yStart,yEnd) && place(battleShip,xStart,xEnd,yStart,yEnd);

    }


    /**
     * PLace {@link BattleShip on Given CoOrdinate}
     * @param battleShip {@link BattleShip} to be placed
     * @param xStart Starting X-Coordinate
     * @param xEnd Ending X-Coordinate
     * @param yStart Starting Y-Coordinate
     * @param yEnd Ending Y-Coordinate
     * @return true if placed successFully
     * @throws InvalidInputException If the given CoOrdinate is already Occupied by anopther {@link BattleShip}
     */
    private boolean place(BattleShip battleShip, int xStart, int xEnd, int yStart, int yEnd) throws InvalidInputException {
        Queue<BattleShipPart> battleShipParts= new LinkedList<>(battleShip.getBattleShipParts().values());
        for(int y=yStart;y <= yEnd ;y++){
            for(int x=xStart;x <= xEnd ;x++){
                  if(coordinates[x][y]==null){
                      coordinates[x][y]=battleShipParts.poll();
                  }else{
                      throw new InvalidInputException(MessageFormat.format("Cannot place {0} on board at location {1},{2} - It is already occupied by {3}",
                              new Object[]{battleShip,x,y, coordinates[x][y]}));
                  }
            }
        }
        return true;
    }

    /**
     * Checks given Coordinates are within bounds for this board
     * @param xStart Starting X-Coordinate
     * @param xEnd  Ending X-Coordinate
     * @param yStart Starting Y-Coordinate
     * @param yEnd Ending Y-Coordinate
     * @return true if given coordinate are within bound
     * @throws InvalidInputException When CoOrdinates are out of bound
     */
    private boolean withinBounds(int xStart,int xEnd,int yStart,int yEnd) throws InvalidInputException {
        if(xStart >=1 && yStart >= 1 && xEnd < width && yEnd < height){
            return true;
        }
        throw new InvalidInputException(MessageFormat.format("Given coOrdinates are out of bound",
                new Object[]{xStart,xEnd,yStart,yEnd}));
    }

    /**
     * Check if Attacked {@Link Location} is inbound
     * @param attackLocation Location attacked
     * @return true if it is within battle area
     */
    private boolean inBounds(Location attackLocation){
        return attackLocation.getxCoordinate()>=0 &&
                attackLocation.getxCoordinate()<width
                && attackLocation.getyCoordinate()>=0
                && attackLocation.getyCoordinate() < height;
    }


    /**
     * Handles attack by given {@Link Weapon}
     * @param weapon weapon used to attack
     * @return
     */
    public FireEventResultType attackAndGetResult(Weapon weapon) {
        Location targetLocation=weapon.targetLocation();
        if(!inBounds(targetLocation)) return FireEventResultType.TARGET_MISS;
        int row= targetLocation.getxCoordinate();
        int column= targetLocation.getyCoordinate();
        BattleShipPart battleShipPart= coordinates[row][column];
        if(battleShipPart!=null) {
            BattleShip battleShip=battleShipPart.handleAttack(weapon);
            if(battleShipPart.getHitsLeft().intValue() <= 0){
                //remove from ship
                battleShip.getBattleShipParts().remove(battleShipPart.getPartId(),battleShipPart);
                //remove from board
                coordinates[row][column]=null;
            }
            return FireEventResultType.TARGET_HIT;
        }else{
            return FireEventResultType.TARGET_MISS;
        }
    }

    /**
     * Get Current Board state for debug
     */
    public String getBoardState(){
        StringBuilder builder=new StringBuilder();
        builder.append("Owner->"+this.player.getPlayerName()+"\n");
        builder.append("=================================="+"\n");
        for(int y=1;y < height ;y++){
            for(int x=1;x < width ;x++){
                if(coordinates[x][y]!=null)
                    builder.append(coordinates[x][y].getPartId()+"\t\t");
                else
                    builder.append("X"+"\t\t");
            }
            builder.append("\n");
        }
        builder.append("=================================="+"\n");
        return builder.toString();
    }
}
