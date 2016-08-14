package ceasar.com.myapplication;


import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by ceejay562 on 8/14/2016.
 */

public class PlayerTest {

    @Test
    public void testHit(){
        Player player = new Player("Ceasar");

        assertEquals(player.getNumOfHits(), 0);

        player.hit();
        assertEquals(player.getNumOfHits(), 1);

        player.out();
        assertEquals(player.getNumOfHits(), 1);

        player.hit();
        assertEquals(player.getNumOfHits(), 2);
    }

    @Test
    public void testAtBats(){
        Player player = new Player("Ceasar");

        assertEquals(player.getNumOfAtBats(), 0);

        player.hit();
        assertEquals(player.getNumOfAtBats(), 1);

        player.out();
        assertEquals(player.getNumOfAtBats(), 2);

        player.hit();
        assertEquals(player.getNumOfAtBats(), 3);
    }

    @Test
    public void testNameChange(){
        String name = "Ceasar";
        Player player = new Player(name);

        assertEquals(player.getName(), name);

        String newName = "Jay";
        player.changeName(newName);
        assertEquals(player.getName(), newName);
    }

}
