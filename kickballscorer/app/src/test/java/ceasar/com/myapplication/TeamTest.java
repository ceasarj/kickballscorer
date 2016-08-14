package ceasar.com.myapplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class TeamTest {

    @Test
    public void testNumOfPlayers(){
        Team team = new Team("Mad Kickers");
        team.generatePlayers(10);

        assertEquals(team.getNumOfPlayers(), 10);

        team.addPlayer("Player 11", 11);
        assertEquals(team.getNumOfPlayers(), 11);
    }

    @Test
    public void testNextKicker(){
        Team t = new Team("Test Team");
        t.generatePlayers(2);

        // first kicker
        assertEquals(t.getCurrKickPosition(), 0);

        //second kicker
        t.nextKicker(true);
        assertEquals(t.getCurrKickPosition(), 1);

        // back to the first kicker
        t.nextKicker(false);
        assertEquals(t.getCurrKickPosition(), 0);
    }

}
