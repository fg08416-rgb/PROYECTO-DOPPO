package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas adicionales para aumentar la cobertura de Tower.java
 * Cubre: orderTower, reverseTower, makeInvisible, toString,
 *        pushCup(int), popCup, peekCup, removeCup, removeLid
 * Proyecto: stackingItems - Ciclo Final
 */
public class TowerExtraTest
{
    private Tower t;

    @Before
    public void setUp()
    {
        t = new Tower(4);
        t.setVisibleForTest(false);
    }


    @Test
    public void accordingExtraShould03OrderTowerKeepsLidsWithCups()
    {
        t.pushLid(4, "red");
        t.swap(new String[]{"cup","4"}, new String[]{"cup","1"});
        t.orderTower();
        String[][] items = t.stackingItems();
        String lid4 = "";
        for (String[] item : items) if (item[0].equals("4")) lid4 = item[1];
        assertEquals("red", lid4);
        assertTrue(t.ok());
    }


    @Test
    public void accordingExtraShould04ReverseTowerInvertsOrder()
    {
        t.reverseTower();
        String[][] items = t.stackingItems();
        assertEquals("1", items[0][0]);
        assertEquals("4", items[3][0]);
        assertTrue(t.ok());
    }

    @Test
    public void accordingExtraShould05ReverseTowerTwiceRestoresOrder()
    {
        t.reverseTower();
        t.reverseTower();
        String[][] items = t.stackingItems();
        assertEquals("4", items[0][0]);
        assertEquals("1", items[3][0]);
        assertTrue(t.ok());
    }

    @Test
    public void accordingExtraShould06ReverseTowerKeepsLidsWithCups()
    {
        t.pushLid(1, "blue");
        t.reverseTower();
        String[][] items = t.stackingItems();
        String lid1 = "";
        for (String[] item : items) if (item[0].equals("1")) lid1 = item[1];
        assertEquals("blue", lid1);
        assertTrue(t.ok());
    }

    

    @Test
    public void accordingExtraShould07PushCupIntAddsNormalCup()
    {
        Tower t2 = new Tower(5, 200);
        t2.setVisibleForTest(false);
        t2.pushCup(3);
        assertTrue(t2.ok());
        assertEquals(3, t2.peekCup());
    }

    @Test
    public void accordingExtraShould08PopCupReturnsTopCup()
    {
        int popped = t.popCup();
        assertEquals(1, popped);
        assertTrue(t.ok());
    }

    @Test
    public void accordingExtraShould09PopCupEmptyTowerFails()
    {
        Tower t2 = new Tower(5, 200);
        t2.setVisibleForTest(false);
        t2.popCup();
        assertFalse(t2.ok());
    }

    @Test
    public void accordingExtraShould10PopCupReducesSize()
    {
        int before = t.stackingItems().length;
        t.popCup();
        int after = t.stackingItems().length;
        assertEquals(before - 1, after);
        assertTrue(t.ok());
    }

    
    @Test
    public void accordingExtraShould11PeekCupReturnsTopWithoutRemoving()
    {
        int peek = t.peekCup();
        assertEquals(1, peek);
        assertEquals(4, t.stackingItems().length);
        assertTrue(t.ok());
    }

    @Test
    public void accordingExtraShould12PeekCupEmptyTowerFails()
    {
        Tower t2 = new Tower(5, 200);
        t2.setVisibleForTest(false);
        int result = t2.peekCup();
        assertEquals(-1, result);
        assertFalse(t2.ok());
    }

    

    @Test
    public void accordingExtraShould13RemoveCupFromMiddle()
    {
        t.removeCup(2);
        assertTrue(t.ok());
        assertEquals(3, t.stackingItems().length);
    }

    @Test
    public void accordingExtraShould14RemoveCupInvalidFails()
    {
        t.removeCup(99);
        assertFalse(t.ok());
    }

    

    @Test
    public void accordingExtraShould15RemoveLidWorks()
    {
        t.pushLid(3, "green");
        t.removeLid(3);
        assertTrue(t.ok());
        int[] lided = t.lidedCups();
        for (int n : lided) assertNotEquals(3, n);
    }

    @Test
    public void accordingExtraShould16RemoveLidNoLidFails()
    {
        t.removeLid(3); // no tiene tapa
        assertFalse(t.ok());
    }

    @Test
    public void accordingExtraShould17RemoveLidInvalidCupFails()
    {
        t.removeLid(99);
        assertFalse(t.ok());
    }

   

    @Test
    public void accordingExtraShould18ToStringNotNull()
    {
        String s = t.toString();
        assertNotNull(s);
        assertTrue(s.contains("Tower"));
        assertTrue(t.ok());
    }

    @Test
    public void accordingExtraShould19ToStringContainsCupInfo()
    {
        t.pushLid(4, "red");
        String s = t.toString();
        assertTrue(s.contains("red"));
    }

    

    @Test
    public void accordingExtraShould20MakeInvisibleSetsOkTrue()
    {
        t.makeInvisible();
        assertTrue(t.ok());
    }


    @Test
    public void accordingExtraShould21HeightWithLidOnTop()
    {
        int hSin = t.height();
        t.pushLid(1, "red"); // taza 1 está en el tope
        int hCon = t.height();
        assertEquals(hSin + 1, hCon);
        assertTrue(t.ok());
    }

    @Test
    public void accordingExtraShould22HeightEmptyTowerIsZero()
    {
        Tower t2 = new Tower(5, 200);
        t2.setVisibleForTest(false);
        assertEquals(0, t2.height());
    }
}
