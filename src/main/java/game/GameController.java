package game;

import game.events.FireEvent;
import game.events.FireEventResultType;
import game.player.Player;
import game.weapons.Weapon;
import printer.ConsolePrinter;
import printer.Printer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton GameController
 */
public class GameController {

    private static GameController INSTANCE=new GameController();

    //this can be injected via some config using frameworks i.e Spring
    private Printer printer=ConsolePrinter.INSTANCE; //setting it to default console for now

    private GameController(){

    }

    public static GameController getInstance() {
        return INSTANCE;
    }

    /**
     * Process any Fire Event raised in system
     * @param fireEvent
     */
    public void process(FireEvent fireEvent){
        Weapon weapon=fireEvent.getWeapon();
        FireEventResultType fireEventResultType = fireEvent.getTarget().handleFire(weapon);
        processFireEventResult(fireEvent, fireEventResultType);
    }

    /**
     * Give turn to enemy
     * @param source
     * @param target
     */
    public void enemyTurn(Player source,Player target){
        if(!isGameFinished(source,target))
            target.fire(source);
    }


    /**
     * Process Result of last {@link FireEvent}
     * @param fireEvent
     * @param fireEventResultType
     */
    private void processFireEventResult(FireEvent fireEvent, FireEventResultType fireEventResultType) {
        Player source=fireEvent.getSource();
        Player target=fireEvent.getTarget();
        printer.print(fireEvent.getEventLog() + " which got " +fireEventResultType.getValue());
        if(FireEventResultType.TARGET_HIT.equals(fireEventResultType)){
            if (!isGameFinished(source, target))
                source.fire(target);
        }else{
            target.fire(source);
        }
    }

    /**
     * To check if Game is finished or not
     * @param source
     * @param target
     * @return
     */
    private boolean isGameFinished(Player source, Player target) {
        if(target.getTotalLivesLeft() <= 0) {
            printer.print(source.getPlayerName() + " has won ");
            Logger.getAnonymousLogger().log(Level.FINER,source.getBattleBoard().getBoardState()+target.getBattleBoard().getBoardState());
            source.setWinner(true);
            return true;
        }else if(source.getWeapons().isEmpty() && target.getWeapons().isEmpty()){
            printer.print("Both players weapons finished - Game is draw");
            Logger.getAnonymousLogger().log(Level.FINER,source.getBattleBoard().getBoardState()+target.getBattleBoard().getBoardState());
            return true;
        }
        return false;
    }

}
