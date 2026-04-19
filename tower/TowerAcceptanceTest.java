package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de Aceptación - StackingItems Ciclo Final
 * Verifica que el sistema cumple los requisitos funcionales
 * desde el punto de vista del usuario.
 *
 * Requisitos cubiertos:
 * - create tower
 * - manage cup (push, pop, remove)
 * - manage lid (push, pop, remove)
 * - reorganize tower (order, reverse, swap, cover)
 * - consult information (height, lided cups, stacking items, swap to reduce)
 * - solve y simulate (TowerContest)
 *
 * Autores: Francisco Javier Gomez Rubiano - Oscar Daniel Lopez Cruz
 */
public class TowerAcceptanceTest
{
    private Tower tower;
    private TowerContest contest;

    @Before
    public void setUp()
    {
        tower = new Tower(5);
        tower.setVisibleForTest(false);
        contest = new TowerContest();
    }

    @Test
    public void acceptanceShould01CreateTowerWithNCups()
    {
        Tower t = new Tower(5);
        t.setVisibleForTest(false);
        assertEquals(5, t.stackingItems().length);
        assertTrue(t.ok());
    }

    @Test
    public void acceptanceShould02CreateTowerWithCapacity()
    {
        Tower t = new Tower(10, 500);
        t.setVisibleForTest(false);
        assertEquals(0, t.stackingItems().length);
        assertTrue(t.ok());
    }

    @Test
    public void acceptanceShould03CreateTowerLargestAtBase()
    {
        String[][] items = tower.stackingItems();
        assertEquals("5", items[0][0]);
        assertEquals("1", items[4][0]);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould04PushCupToTower()
    {
        Tower t = new Tower(10, 500);
        t.setVisibleForTest(false);
        t.pushCup(3);
        assertEquals(3, t.peekCup());
        assertTrue(t.ok());
    }

    @Test
    public void acceptanceShould05PopCupFromTower()
    {
        int before = tower.stackingItems().length;
        tower.popCup();
        assertEquals(before - 1, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould06RemoveCupFromMiddle()
    {
        tower.removeCup(3);
        assertTrue(tower.ok());
        assertEquals(4, tower.stackingItems().length);
        assertFalse(tower.containsCup(3));
    }

    @Test
    public void acceptanceShould07PushCupByType()
    {
        Tower t = new Tower(10, 500);
        t.setVisibleForTest(false);
        t.pushCup("normal", 2);
        t.pushCup("opener", 1);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }
    @Test
    public void acceptanceShould08PushLidOnCup()
    {
        // El usuario coloca una tapa sobre una taza
        tower.pushLid(5, "red");
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(5, lided[0]);
    }

    @Test
    public void acceptanceShould09PopLidFromCup()
    {
        tower.pushLid(5, "red");
        String color = tower.popLid(5);
        assertEquals("red", color);
        assertTrue(tower.ok());
        assertEquals(0, tower.lidedCups().length);
    }

    @Test
    public void acceptanceShould10RemoveLidFromCup()
    {
        tower.pushLid(3, "blue");
        tower.removeLid(3);
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        for (int n : lided) assertNotEquals(3, n);
    }

    @Test
    public void acceptanceShould11PushLidByType()
    {
       
        tower.pushLid("normal", 5, "red");
        tower.pushLid("timed", 3, "orange");
        assertTrue(tower.ok());
        assertEquals(2, tower.lidedCups().length);
    }


    @Test
    public void acceptanceShould13ReverseTower()
    {
        
        tower.reverseTower();
        String[][] items = tower.stackingItems();
        assertEquals("1", items[0][0]);
        assertEquals("5", items[4][0]);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould14SwapCups()
    {
        
        tower.swap(new String[]{"cup","5"}, new String[]{"cup","1"});
        String[][] items = tower.stackingItems();
        assertEquals("1", items[0][0]);
        assertEquals("5", items[4][0]);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould15SwapLids()
    {
        
        tower.pushLid(5, "red");
        tower.pushLid(3, "blue");
        tower.swap(new String[]{"lid","5"}, new String[]{"lid","3"});
        String[][] items = tower.stackingItems();
        String lid5 = "", lid3 = "";
        for (String[] item : items) {
            if (item[0].equals("5")) lid5 = item[1];
            if (item[0].equals("3")) lid3 = item[1];
        }
        assertEquals("blue", lid5);
        assertEquals("red", lid3);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould16CoverAllCups()
    {
        
        tower.cover();
        assertEquals(tower.stackingItems().length, tower.lidedCups().length);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould17GetHeight()
    {
        int h = tower.height();
        assertTrue(h > 0);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould18GetLidedCups()
    {
        tower.pushLid(5, "red");
        tower.pushLid(3, "blue");
        int[] lided = tower.lidedCups();
        assertEquals(2, lided.length);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould19GetStackingItems()
    {
        String[][] items = tower.stackingItems();
        assertEquals(5, items.length);
        assertNotNull(items[0][0]);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould20SwapToReduce()
    {
        tower.pushLid(1, "red");
        String[][] result = tower.swapToReduce();
        assertTrue(result.length > 0);
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould21SolveValidCase()
    {
        String result = contest.solve(4, 9);
        assertNotEquals("impossible", result);
        assertTrue(contest.ok());
    }

    @Test
    public void acceptanceShould22SolveImpossibleCase()
    {
        String result = contest.solve(4, 6); // 
        assertEquals("impossible", result);
        assertFalse(contest.ok());
    }

    @Test
    public void acceptanceShould23SimulateValidCase()
    {
        contest.simulate(4, 9);
        assertTrue(contest.ok());
    }

    @Test
    public void acceptanceShould24SimulateImpossibleCase()
    {
        contest.simulate(4, 6);
        assertFalse(contest.ok());
    }

    @Test
    public void acceptanceShould25MakeInvisible()
    {
        tower.makeInvisible();
        assertTrue(tower.ok());
    }

    @Test
    public void acceptanceShould26SetVisibleForTest()
    {
        tower.setVisibleForTest(false);
        assertTrue(tower.ok());
    }
}
