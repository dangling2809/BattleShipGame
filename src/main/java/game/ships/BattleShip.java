package game.ships;

import game.board.Location;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Model for a BattleShip
 */
public class BattleShip {

    private BattleShipType battleShipType;

    private ConcurrentMap<String,BattleShipPart> battleShipParts;

    private int width;

    private int height;

    private Location location;

    public BattleShip(BattleShipType battleShipType, int width, int height , Location location) {
        this.battleShipType = battleShipType;
        this.width = width;
        this.height = height;
        this.battleShipParts = buildParts(this.width * this.height,battleShipType.hitThreshold());

        this.location=location;
    }

    private ConcurrentMap<String, BattleShipPart> buildParts(int totalParts, int hitsThreshold ) {
        return IntStream.range(1,totalParts+1).parallel().mapToObj(battleShipNumber ->
                new BattleShipPart( battleShipType.name() + battleShipNumber ,hitsThreshold , this) )
                .collect(Collectors.toConcurrentMap(part-> part.getPartId(),part->part));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BattleShipType getBattleShipType() {
        return battleShipType;
    }

    public Location getLocation() {
        return location;
    }

    public BattleShipPart removePart(String partId){
       return  battleShipParts.remove(partId);
    }

    public ConcurrentMap<String, BattleShipPart> getBattleShipParts() {
        return battleShipParts;
    }

    public int getTotalLives() {
        return this.battleShipParts.values().stream()
                .collect(Collectors.summingInt(part->part.getHitsLeft()));
    }

    @Override
    public String toString() {
        return "BattleShip{" +
                "battleShipType=" + battleShipType +
                ", battleShipParts=" + battleShipParts +
                ", width=" + width +
                ", height=" + height +
                ", location=" + location +
                '}';
    }
}
