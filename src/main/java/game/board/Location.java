package game.board;

import game.ships.BattleShipPart;

import java.util.Objects;

/**
 *  Models a Location in game, This can used as both TargetLocation and {@link BattleShipPart} Location on  {@link BattleBoard}
 */
public class Location {

    private final int xCoordinate;

    private final int yCoordinate;

    public Location(char yCoordinate, int xCoordinate) {
        this.yCoordinate = yCoordinate - 'A' + 1 ;
        this.xCoordinate = xCoordinate  ;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public char yCoordinateCharValue() {
        return (char) (yCoordinate + 'A' - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return xCoordinate == location.xCoordinate &&
                yCoordinate == location.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

    public String toString() {
        return "" + (char)yCoordinateCharValue() + "" + xCoordinate;
    }
}
