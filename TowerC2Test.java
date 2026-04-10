package Tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
 
/**
 * Pruebas de unidad del Ciclo 2 de Tower.
 * Modo invisible. Autores: GL
 * Proyecto: stackingItems
 */
public class TowerC2Test
{
    private Tower t;
 
    @Before
    public void setUp()
    {
        t = new Tower(4);           
        t.setVisibleForTest(false); 
    }
 
 
    // Tower(4) crea exactamente 4 tazas
    @Test
    public void accordingGLShould01ConstructorMakesFourCups()
    {
        t = new Tower(4);
        t.setVisibleForTest(false);
        assertEquals(4, t.stackingItems().length);
        assertTrue(t.ok());
    }
 
    // Tower(4) coloca la taza mas grande en la base
    @Test
    public void accordingGLShould02ConstructorPutsLargestAtBase()
    {
        t = new Tower(4);
        t.setVisibleForTest(false);
        String[][] items = t.stackingItems();
        assertEquals("4", items[0][0]);
        assertEquals("1", items[3][0]);
        assertTrue(t.ok());
    }
 
    // Tower(1) no debe tener mas de 1 taza
    @Test
    public void accordingGLShouldNot04ConstructorOneHasMoreThanOneCup()
    {
        t = new Tower(1);
        t.setVisibleForTest(false);
        assertEquals(1, t.stackingItems().length);
        assertEquals(1, t.height());
    }
 
    // swap cup4 con cup2 intercambia sus posiciones
    @Test
    public void accordingGLShould05SwapCupsChangesPositions()
    {
        t.swap(new String[]{"cup","4"}, new String[]{"cup","2"});
        String[][] items = t.stackingItems();
        assertEquals("2", items[0][0]);
        assertEquals("4", items[2][0]);
        assertTrue(t.ok());
    }
 
    // la tapa viaja con su taza al hacer swap
    @Test
    public void accordingGLShould06SwapCupsKeepsLidWithCup()
    {
        t.pushLid(4, "red");
        t.swap(new String[]{"cup","4"}, new String[]{"cup","1"});
        String[][] items = t.stackingItems();
        String lid4 = "";
        for (String[] item : items) if (item[0].equals("4")) lid4 = item[1];
        assertEquals("red", lid4);
        assertTrue(t.ok());
    }
 
    // swap con taza inexistente no debe marcar ok=true
    @Test
    public void accordingGLShouldNot07SwapInvalidCupDoesNotSetOk()
    {
        t.swap(new String[]{"cup","4"}, new String[]{"cup","9"});
        assertFalse(t.ok());
    }
 
 
    // swap lid4 con lid3 intercambia los colores
    @Test
    public void accordingGLShould08SwapLidsExchangesColors()
    {
        t.pushLid(4, "red");
        t.pushLid(3, "green");
        t.swap(new String[]{"lid","4"}, new String[]{"lid","3"});
        String[][] items = t.stackingItems();
        String lid4 = ""; String lid3 = "";
        for (String[] item : items) {
            if (item[0].equals("4")) lid4 = item[1];
            if (item[0].equals("3")) lid3 = item[1];
        }
        assertEquals("green", lid4);
        assertEquals("red",   lid3);
        assertTrue(t.ok());
    }
 
    // la tapa no debe quedar en su posicion original despues del swap
    @Test
    public void accordingGLShouldNot09LidStaysInOriginalPositionAfterSwap()
    {
        t.pushLid(3, "green");
        t.swap(new String[]{"lid","4"}, new String[]{"lid","3"});
        String[][] items = t.stackingItems();
        String lid3 = "";
        for (String[] item : items) if (item[0].equals("3")) lid3 = item[1];
        assertNotEquals("green", lid3);
    }

 
    // cover() tapa todas las tazas
    @Test
    public void accordingGLShould10CoverTapsAllCups()
    {
        t.cover();
        assertEquals(t.stackingItems().length, t.lidedCups().length);
        assertTrue(t.ok());
    }
 
    // cover() usa el color de la taza como color de tapa
    @Test
    public void accordingGLShould11CoverUsesCupColor()
    {
        t.cover();
        String[][] items = t.stackingItems();
        String lid4 = "";
        for (String[] item : items) if (item[0].equals("4")) lid4 = item[1];
        assertEquals("blue", lid4);
        assertTrue(t.ok());
    }
 
    // cover() no debe reemplazar tapas ya existentes
    @Test
    public void accordingGLShouldNot12CoverDoesNotReplaceExistingLids()
    {
        t.pushLid(4, "red");
        t.cover();
        String[][] items = t.stackingItems();
        String lid4 = "";
        for (String[] item : items) if (item[0].equals("4")) lid4 = item[1];
        assertEquals("red", lid4);
        assertTrue(t.ok());
    }
 
 
    // sin tapa en el tope no debe haber swaps que reduzcan
    @Test
    public void accordingGLShouldNot13SwapToReduceEmptyWithoutTopLid()
    {
        String[][] result = t.swapToReduce();
        assertEquals(0, result.length);
        assertTrue(t.ok());
    }
 
    // con tapa en el tope encuentra al menos 1 swap que reduce
    @Test
    public void accordingGLShould14SwapToReduceFindsSwapWhenTopHasLid()
    {
        t.pushLid(1, "red");   // taza 1 esta en el tope
        String[][] result = t.swapToReduce();
        assertTrue(result.length > 0);
        assertTrue(t.ok());
    }
 
    // la reduccion reportada es exactamente 1 cm
    @Test
    public void accordingGLShould15SwapToReduceReturnsOneCmReduction()
    {
        t.pushLid(1, "red");
        String[][] result = t.swapToReduce();
        assertTrue(result.length > 0);
        assertEquals("1", result[0][4]);
        assertTrue(t.ok());
    }
 
    // si todas tienen tapa no hay reduccion posible
    @Test
    public void accordingGLShouldNot16NoReductionWhenAllCupsHaveLids()
    {
        t.cover();
        String[][] result = t.swapToReduce();
        assertEquals(0, result.length);
        assertTrue(t.ok());
    }
 
 
    // exit() marca ok=true
    @Test
    public void accordingGLShould17ExitSetsOkTrue()
    {
        t.exit();
        assertTrue(t.ok());
    }
}
