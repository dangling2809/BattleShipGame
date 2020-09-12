import app.BattleShipGame;
import exception.InvalidInputException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BattleShipGameIntegrationTest {

    @Test
    public void test_Player2ShouldWin() throws InvalidInputException {
        BattleShipGame game=new BattleShipGame(getFile("Player2Win.txt"));
        game.start();
        Assert.assertTrue(game.getPlayer2().isWinner());
    }

    @Test
    public void test_Player1ShouldWin() throws InvalidInputException {
        BattleShipGame game=new BattleShipGame(getFile("Player1Win.txt"));
        game.start();
        Assert.assertTrue(game.getPlayer1().isWinner());
    }

    @Test
    public void test_GameShouldDraw() throws InvalidInputException {
        BattleShipGame game=new BattleShipGame(getFile("Draw.txt"));
        game.start();
        Assert.assertTrue(!game.getPlayer1().isWinner() && !game.getPlayer2().isWinner());
    }

    @Test(expected = InvalidInputException.class)
    public void test_ShouldFailWithException() throws InvalidInputException {
        BattleShipGame game=new BattleShipGame(getFile("InvalidInput.txt"));
        game.start();
    }


    public File getFile(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file;
    }
}
