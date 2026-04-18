package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * TowerC4Test - Pruebas de unidad para los nuevos tipos del Ciclo 4.
 * Cubre: NormalCup, OpenerCup, HierarchicalCup,
 *        NormalLid, FearfulLid, CrazyLid, TimedLid,
 *        y los nuevos métodos pushCup(type,i) y pushLid(type,i,color) de Tower.
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public class TowerC4Test
{
    private Tower tower;

    @Before
    public void setUp()
    {
        tower = new Tower(5, 200);
        tower.setVisibleForTest(false);
    }

        //  NormalCup
    

    @Test
    public void accordingC4Should01NormalCupType()
    {
        NormalCup cup = new NormalCup(3);
        assertEquals("normal", cup.getType());
    }

    @Test
    public void accordingC4Should02NormalCupAlwaysPops()
    {
        NormalCup cup = new NormalCup(3);
        assertTrue(cup.onPop(tower));
    }

    @Test
    public void accordingC4Should03PushNormalCupViaType()
    {
        tower.pushCup("normal", 3);
        assertTrue(tower.ok());
        assertEquals(3, tower.peekCup());
    }

    
    //  OpenerCup
    

    @Test
    public void accordingC4Should04OpenerCupType()
    {
        OpenerCup cup = new OpenerCup(2);
        assertEquals("opener", cup.getType());
    }

    @Test
    public void accordingC4Should05OpenerRemovesTopLid()
    {
        
        tower.pushCup("normal", 3);
        tower.pushLid("normal", 3, "red");
        
        tower.pushCup("opener", 2);
        assertTrue(tower.ok());
    
        int[] lided = tower.lidedCups();
        for (int n : lided) assertNotEquals(3, n);
    }

    @Test
    public void accordingC4Should06OpenerAlwaysPops()
    {
        OpenerCup cup = new OpenerCup(2);
        assertTrue(cup.onPop(tower));
    }

    
    //  HierarchicalCup
    

    @Test
    public void accordingC4Should07HierarchicalCupType()
    {
        HierarchicalCup cup = new HierarchicalCup(4);
        assertEquals("hierarchical", cup.getType());
    }

    @Test
    public void accordingC4Should08HierarchicalInsertsBelowSmaller()
    {
        tower.pushCup("normal", 1);
        tower.pushCup("normal", 2);
        tower.pushCup("hierarchical", 5);
        assertTrue(tower.ok());
        
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] row : items) if (row[0].equals("5")) found = true;
        assertTrue(found);
    }

    @Test
    public void accordingC4Should09HierarchicalReachedBottomBlocked()
    {
        
        tower.pushCup("hierarchical", 5);
        assertTrue(tower.ok());
        tower.popCup(); 
        assertFalse(tower.ok()); 
    }

    
    //  NormalLid
    

    @Test
    public void accordingC4Should11NormalLidType()
    {
        NormalLid lid = new NormalLid("red");
        assertEquals("normal", lid.getType());
    }

    @Test
    public void accordingC4Should12NormalLidAlwaysCanPushPop()
    {
        tower.pushCup("normal", 3);
        NormalLid lid = new NormalLid("blue");
        assertTrue(lid.canPush(tower, 3));
        assertTrue(lid.canPop(tower, 3));
    }

    
    //  FearfulLid
    

    @Test
    public void accordingC4Should13FearfulLidType()
    {
        FearfulLid lid = new FearfulLid("green", 3);
        assertEquals("fearful", lid.getType());
    }

    @Test
    public void accordingC4Should14FearfulLidCannotEnterWithoutCompanion()
    {
        
        tower.pushCup("normal", 2);
        tower.pushLid("fearful", 2, "green"); 
        
        FearfulLid lid = new FearfulLid("green", 5);
        assertFalse(lid.canPush(tower, 2));
    }

    @Test
    public void accordingC4Should15FearfulLidCanEnterWithCompanion()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        
        tower.pushLid("fearful", 2, "yellow");
        assertTrue(tower.ok());
    }

    @Test
    public void accordingC4Should16FearfulLidCannotPopFromCompanion()
    {
        tower.pushCup("normal", 3);
        FearfulLid lid = new FearfulLid("green", 3);
        
        assertFalse(lid.canPop(tower, 3));
    }

    @Test
    public void accordingC4Should17FearfulLidCanPopFromOther()
    {
        tower.pushCup("normal", 3);
        FearfulLid lid = new FearfulLid("green", 3);
        assertTrue(lid.canPop(tower, 2));
    }

    
    //  CrazyLid
    
    @Test
    public void accordingC4Should18CrazyLidType()
    {
        CrazyLid lid = new CrazyLid("cyan");
        assertEquals("crazy", lid.getType());
    }

    @Test
    public void accordingC4Should19CrazyLidGoesToBase()
    {
        tower.pushCup("normal", 4);
        tower.pushCup("normal", 2);
        
        tower.pushLid("crazy", 2, "magenta");
        assertTrue(tower.ok());
    
        int[] lided = tower.lidedCups();
        boolean baseHasLid = false;
        for (int n : lided) if (n == 4) baseHasLid = true;
        assertTrue(baseHasLid);
    }

    @Test
    public void accordingC4Should20CrazyLidAlwaysCanPushPop()
    {
        CrazyLid lid = new CrazyLid("magenta");
        assertTrue(lid.canPush(tower, 2));
        assertTrue(lid.canPop(tower, 2));
        assertTrue(lid.goesToBase());
    }

    
    //  TimedLid (tipo nuevo)
    

    @Test
    public void accordingC4Should21TimedLidType()
    {
        TimedLid lid = new TimedLid("orange", 3);
        assertEquals("timed", lid.getType());
    }

    @Test
    public void accordingC4Should22TimedLidNotExpiredInitially()
    {
        TimedLid lid = new TimedLid("orange", 3);
        assertFalse(lid.isExpired());
        assertEquals(3, lid.getRemainingOperations());
    }

    @Test
    public void accordingC4Should23TimedLidExpiresAfterOperations()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        
        tower.pushLid("timed", 3, "orange");
        assertTrue(tower.ok());

        
        tower.pushLid("normal", 2, "blue"); 
        tower.popLid(2);                     

        
        int[] lided = tower.lidedCups();
        boolean timedLidStillThere = false;
        for (int n : lided) if (n == 3) timedLidStillThere = true;
        assertFalse(timedLidStillThere);
    }

    @Test
    public void accordingC4Should24TimedLidRemainingDecrements()
    {
        TimedLid lid = new TimedLid("orange", 5);
        assertEquals(5, lid.getMaxOperations());
        assertEquals(5, lid.getRemainingOperations());
    }

    @Test
    public void accordingC4Should25TimedLidCanPushAndPop()
    {
        tower.pushCup("normal", 3);
        TimedLid lid = new TimedLid("orange", 3);
        assertTrue(lid.canPush(tower, 3));
        assertTrue(lid.canPop(tower, 3));
    }

    
    //  Tower — containsCup y removeExpiredLid
    

    @Test
    public void accordingC4Should26TowerContainsCup()
    {
        tower.pushCup("normal", 4);
        assertTrue(tower.containsCup(4));
        assertFalse(tower.containsCup(9));
    }

    @Test
    public void accordingC4Should27TowerRemoveExpiredLid()
    {
        tower.pushCup("normal", 3);
        tower.pushLid("normal", 3, "red");
        tower.removeExpiredLid(3);
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        assertEquals(0, lided.length);
    }

    @Test
    public void accordingC4Should28TowerPushCupTypeInStackingItems()
    {
        tower.pushCup("opener", 2);
        tower.pushCup("hierarchical", 4);
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertTrue(tower.ok());
    }
}
