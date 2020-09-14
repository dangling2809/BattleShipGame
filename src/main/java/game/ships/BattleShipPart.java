package game.ships;

import game.weapons.Weapon;

import java.util.concurrent.atomic.AtomicInteger;

public final class BattleShipPart {

    private final String partId;

    // If some body runs it in multithreaded environment, Updates to hitsLeft should be atomic operation
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

    public Integer getHitsLeft() {
        return hitsLeft.get();
    }

    public BattleShip getBelongsTo() {
        return belongsTo;
    }

    public BattleShip handleAttack(Weapon weapon) {
        hitsLeft.compareAndSet(hitsLeft.intValue(),hitsLeft.intValue() - weapon.getDamage());
        return belongsTo;
    }
}
