package game.ships;

import game.weapons.Weapon;

import java.util.concurrent.atomic.AtomicInteger;

public final class BattleShipPart {

    private final String partId;

    private final AtomicInteger hitsLeft;

    private final BattleShip belongsTo;

    public BattleShipPart(String partId, int hitsLeft ,BattleShip belongsTo) {
        this.partId = partId;
        this.hitsLeft = new AtomicInteger(hitsLeft);
        this.belongsTo=belongsTo;
    }

    public String getPartId() {
        return partId;
    }

    public AtomicInteger getHitsLeft() {
        return hitsLeft;
    }

    public BattleShip getBelongsTo() {
        return belongsTo;
    }

    public BattleShip handleAttack(Weapon weapon) {
        hitsLeft.compareAndSet(hitsLeft.intValue(),hitsLeft.intValue() - weapon.getDamage());
        return belongsTo;
    }
}
