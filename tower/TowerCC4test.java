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
public class TowerCC4test
{
    private Tower tower;

    @Before
    public void setUp()
    {
        tower = new Tower(10, 500);
        tower.setVisibleForTest(false);
    }

    // ═══════════════════════════════════════════════════════════════
    //  Interacción OpenerCup con tapas existentes
    // ═══════════════════════════════════════════════════════════════

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
        // Taza 4 ya no debe tener tapa
        int[] lided = tower.lidedCups();
        for (int n : lided) assertNotEquals(4, n);
    }

    @Test
    public void accordingCC4Should03OpenerWithNoLidOnTopOk()
    {
        // No hay tapa en el tope: opener entra sin problema
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
        // Taza 3 ya no debe tener tapa
        int[] lided = tower.lidedCups();
        for (int n : lided) assertNotEquals(3, n);
    }

    // ═══════════════════════════════════════════════════════════════
    //  Interacción HierarchicalCup con la torre
    // ═══════════════════════════════════════════════════════════════

    @Test
    public void accordingCC4Should05HierarchicalInEmptyTowerReachesBottom()
    {
        tower.pushCup("hierarchical", 4);
        assertTrue(tower.ok());
        // Llegó al fondo → no puede quitarse
        tower.popCup();
        assertFalse(tower.ok());
    }

    @Test
    public void accordingCC4Should06HierarchicalAboveAllStaysOnTop()
    {
        // Torre con tazas 1,2,3 → hierarchical 5 llega al fondo
        tower.pushCup("normal", 1);
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 3);
        tower.pushCup("hierarchical", 5);
        assertTrue(tower.ok());
        // Debe estar en posición base (fondo)
        tower.popCup(); // quitar taza 3 del tope
        assertTrue(tower.ok());
        // hierarchical 5 está bloqueada en el fondo, no se puede quitar
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

    // ═══════════════════════════════════════════════════════════════
    //  Interacción FearfulLid
    // ═══════════════════════════════════════════════════════════════

    @Test
    public void accordingCC4Should10FearfulEntersWhenCompanionPresent()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 5); // companion presente
        // FearfulLid sobre taza 3, companion=5
        FearfulLid fl = new FearfulLid("green", 5);
        assertTrue(fl.canPush(tower, 3));
    }

    @Test
    public void accordingCC4Should11FearfulStaysIfTappingCompanion()
    {
        tower.pushCup("normal", 3);
        FearfulLid fl = new FearfulLid("green", 3);
        // Está tapando a su compañera (cup=3) → no puede salir
        assertFalse(fl.canPop(tower, 3));
    }

    @Test
    public void accordingCC4Should12FearfulLeavesIfNotTappingCompanion()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        FearfulLid fl = new FearfulLid("green", 3);
        // Está sobre taza 2, compañera es 3 → puede salir
        assertTrue(fl.canPop(tower, 2));
    }

    // ═══════════════════════════════════════════════════════════════
    //  Interacción CrazyLid
    // ═══════════════════════════════════════════════════════════════

    @Test
    public void accordingCC4Should13CrazyGoesToBaseNotTarget()
    {
        tower.pushCup("normal", 5);
        tower.pushCup("normal", 2);
        tower.pushLid("crazy", 2, "magenta");
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        // La base (taza 5) debe tener la tapa, no la taza 2
        boolean baseHasLid = false;
        for (int n : lided) if (n == 5) baseHasLid = true;
        assertTrue(baseHasLid);
    }

    @Test
    public void accordingCC4Should14CrazyOverwritesBaseLid()
    {
        tower.pushCup("normal", 4);
        tower.pushCup("normal", 2);
        tower.pushLid("normal", 4, "red"); // tapa previa en base
        tower.pushLid("crazy", 2, "magenta"); // crazy la sobreescribe
        assertTrue(tower.ok());
        // La base debe tener la tapa crazy (magenta)
        String[][] items = tower.stackingItems();
        assertEquals("magenta", items[0][1]);
    }

    // ═══════════════════════════════════════════════════════════════
    //  Interacción TimedLid
    // ═══════════════════════════════════════════════════════════════

    @Test
    public void accordingCC4Should15TimedLidPresentAfterFewOps()
    {
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        tower.pushLid("timed", 3, "orange"); // maxOps=3
        assertTrue(tower.ok());
        // Solo 1 operación → tapa sigue presente
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
        tower.pushLid("timed", 3, "orange"); // maxOps=3
        // 3 operaciones para que expire
        tower.pushLid("normal", 2, "blue");  // op 1
        tower.popLid(2);                      // op 2
        tower.pushLid("normal", 2, "red");   // op 3 → expira
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

    // ═══════════════════════════════════════════════════════════════
    //  Integración general: mezcla de tipos
    // ═══════════════════════════════════════════════════════════════

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
        tower.pushLid("crazy", 1, "magenta"); // va a base (taza 5)
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
