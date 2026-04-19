package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * TowerCC4test - Pruebas de casos comunes para el Ciclo 4.
 * Cubre escenarios de integración entre los distintos tipos de
 * tazas y tapas, incluyendo interacciones entre ellos.
 *
 * Creación colectiva - Proyecto: stackingItems - Ciclo 4
 */
public class TowerCC4Test
{
    private Tower tower;

    @Before
    public void setUp()
    {
        tower = new Tower(10, 500);
        tower.setVisibleForTest(false);
    }


    @Test
    public void accordingCC4Should01OpenerEntersEmptyTowerOk()
    {
        tower.pushCup("opener", 3);
        assertTrue(tower.ok());
        assertEquals(3, tower.peekCup());
    }

    @Test
    public void accordingCC4Should02OpenerRemovesLidBeforeEntering()
    {
        tower.pushCup("normal", 4);
        tower.pushLid("normal", 4, "red");
        tower.pushCup("opener", 2);
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        for (int n : lided) assertNotEquals(4, n);
    }

    @Test
    public void accordingCC4Should03OpenerWithNoLidOnTopOk()
    {
        
        tower.pushCup("normal", 4);
        tower.pushCup("opener", 2);
        assertTrue(tower.ok());
        assertEquals(2, tower.peekCup());
    }

    @Test
    public void accordingCC4Should04TwoOpenersInSequence()
    {
        tower.pushCup("normal", 5);
        tower.pushLid("normal", 5, "blue");
        tower.pushCup("opener", 3);
        tower.pushLid("normal", 3, "green");
        tower.pushCup("opener", 1);
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        for (int n : lided) assertNotEquals(3, n);
    }


    @Test
    public void accordingCC4Should05HierarchicalInEmptyTowerReachesBottom()
    {
        tower.pushCup("hierarchical", 4);
        assertTrue(tower.ok());
        tower.popCup();
        assertFalse(tower.ok());
    }

    @Test
    public void accordingCC4Should06HierarchicalAboveAllStaysOnTop()
    {
        tower.pushCup("normal", 1);
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 3);
        tower.pushCup("hierarchical", 5);
        assertTrue(tower.ok());
        tower.popCup(); 
        assertTrue(tower.ok());
        tower.removeCup(5);
        assertFalse(tower.ok());
    }

    @Test
    public void accordingCC4Should08TowerSizeAfterHierarchical()
    {
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 3);
        tower.pushCup("hierarchical", 5);
        String[][] items = tower.stackingItems();
        assertEquals(3, items.length);
        assertTrue(tower.ok());
    }



    @Test
    public void accordingCC4Should10FearfulEntersWhenCompanionPresent()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 5);
        FearfulLid fl = new FearfulLid("green", 5);
        assertTrue(fl.canPush(tower, 3));
    }

    @Test
    public void accordingCC4Should11FearfulStaysIfTappingCompanion()
    {
        tower.pushCup("normal", 3);
        FearfulLid fl = new FearfulLid("green", 3);
        assertFalse(fl.canPop(tower, 3));
    }

    @Test
    public void accordingCC4Should12FearfulLeavesIfNotTappingCompanion()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        FearfulLid fl = new FearfulLid("green", 3);
        assertTrue(fl.canPop(tower, 2));
    }


    @Test
    public void accordingCC4Should13CrazyGoesToBaseNotTarget()
    {
        tower.pushCup("normal", 5);
        tower.pushCup("normal", 2);
        tower.pushLid("crazy", 2, "magenta");
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        boolean baseHasLid = false;
        for (int n : lided) if (n == 5) baseHasLid = true;
        assertTrue(baseHasLid);
    }

    @Test
    public void accordingCC4Should14CrazyOverwritesBaseLid()
    {
        tower.pushCup("normal", 4);
        tower.pushCup("normal", 2);
        tower.pushLid("normal", 4, "red"); 
        tower.pushLid("crazy", 2, "magenta"); 
        assertTrue(tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals("magenta", items[0][1]);
    }

    @Test
    public void accordingCC4Should15TimedLidPresentAfterFewOps()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        tower.pushLid("timed", 3, "orange"); 
        assertTrue(tower.ok());
        tower.pushLid("normal", 2, "blue");
        int[] lided = tower.lidedCups();
        boolean timedPresent = false;
        for (int n : lided) if (n == 3) timedPresent = true;
        assertTrue(timedPresent);
    }

    @Test
    public void accordingCC4Should16TimedLidDisappearsAfterMaxOps()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        tower.pushLid("timed", 3, "orange"); 
        tower.pushLid("normal", 2, "blue"); 
        tower.popLid(2);                      
        tower.pushLid("normal", 2, "red");   
        int[] lided = tower.lidedCups();
        boolean timedGone = true;
        for (int n : lided) if (n == 3) timedGone = false;
        assertTrue(timedGone);
    }

    @Test
    public void accordingCC4Should17TimedLidRemainingCountCorrect()
    {
        TimedLid tl = new TimedLid("orange", 4);
        assertEquals(4, tl.getRemainingOperations());
        assertFalse(tl.isExpired());
    }

    @Test
    public void accordingCC4Should18MixedCupsInTower()
    {
        tower.pushCup("normal", 5);
        tower.pushCup("opener", 3);
        tower.pushCup("normal", 1);
        assertTrue(tower.ok());
        assertEquals(3, tower.stackingItems().length);
    }

    @Test
    public void accordingCC4Should19MixedLidsInTower()
    {
        tower.pushCup("normal", 5);
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 1);
        tower.pushLid("normal", 1, "red");
        tower.pushLid("timed", 3, "orange");
        tower.pushLid("crazy", 1, "magenta"); 
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        assertTrue(lided.length >= 2);
    }

    @Test
    public void accordingCC4Should20ContainsCupReflectsRealState()
    {
        tower.pushCup("normal", 3);
        assertTrue(tower.containsCup(3));
        tower.removeCup(3);
        assertTrue(tower.ok());
        assertFalse(tower.containsCup(3));
    }
}
