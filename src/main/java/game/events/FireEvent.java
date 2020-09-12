package game.events;

import game.board.Location;
import game.player.Player;
import game.weapons.Weapon;

/**
 * Raised when PLayer attacks an enemy
 */
public class FireEvent implements GameEvent {

    private Player source;

    private Player target;

    private Weapon weapon;

    private Location targetLocation;

    public FireEvent(Player source, Player target,Weapon weapon) {
        this.source = source;
        this.target = target;
        this.weapon=weapon;
        this.targetLocation=weapon.targetLocation();
    }


    public Player getSource() {
        return source;
    }

    public void setSource(Player source) {
        this.source = source;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public String getEventLog() {
        return source.getPlayerName() + " fires a missile with target "+ targetLocation.yCoordinateCharValue()+targetLocation.getxCoordinate();
    }
}
