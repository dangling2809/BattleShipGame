package game.ships;

/**
 * Type of allowed battleships in {@link BattleShip}
 */
public enum BattleShipType {
    P(1),
    Q(2);

    private int hitThreshold;

    BattleShipType(int hitThreshold) {
        this.hitThreshold=hitThreshold;
    }

    public int hitThreshold(){
        return hitThreshold;
    }
}
