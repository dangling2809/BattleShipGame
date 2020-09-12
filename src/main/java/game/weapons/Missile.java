package game.weapons;

import game.board.Location;

/**
 * Currently we have only one type of weapon but easily create new weapons by implementing {@link Weapon}
 */
public class Missile implements Weapon{

    private Location target;

    public Missile(Location target) {
        this.target = target;
    }

    @Override
    public Location targetLocation() {
        return target;
    }
}
