package game.weapons;

import game.board.Location;

public interface Weapon {

    //currently a weapon can do only 1 unit of damage implementers can provide their own damage strength
    int DEFAULT_DAMAGE=1;

    default int getDamage(){
        return DEFAULT_DAMAGE;
    }

    Location targetLocation();
}
