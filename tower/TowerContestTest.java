package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * TowerContestTest - Pruebas para TowerContest.
 * solve() retorna alturas en cm (ej: "7 3 5 1"), no números de taza.
 * Autores: GL
 */
public class TowerContestTest
{
    private TowerContest tc;

    @Before
    public void setUp() { tc = new TowerContest(); }

    @Test
    public void accordingGLShould01SolveMinHeightN4()
    {
        String r = tc.solve(4, 7);
        assertNotEquals("impossible", r);
        assertEquals(7, computeHeight(r));
        assertTrue(tc.ok());
    }

    @Test
    public void accordingGLShould02SolveMidHeightN4()
    {
        String r = tc.solve(4, 9);
        assertNotEquals("impossible", r);
        assertEquals(9, computeHeight(r));
        assertTrue(tc.ok());
    }

    @Test
    public void accordingGLShould03SolveMaxHeightN4()
    {
        String r = tc.solve(4, 11);
        assertNotEquals("impossible", r);
        assertEquals(11, computeHeight(r));
        assertTrue(tc.ok());
    }

    @Test
    public void accordingGLShould04SolveN1()
    {
        assertEquals("1", tc.solve(1, 1));
        assertTrue(tc.ok());
    }

    @Test
    public void accordingGLShould05SolveMinN3()
    {
        String r = tc.solve(3, 5);
        assertNotEquals("impossible", r);
        assertEquals(5, computeHeight(r));
        assertTrue(tc.ok());
    }

    @Test
    public void accordingGLShould06SolveMaxN3()
    {
        String r = tc.solve(3, 7);
        assertNotEquals("impossible", r);
        assertEquals(7, computeHeight(r));
        assertTrue(tc.ok());
    }

    // resultado contiene exactamente n alturas impares 1,3,...,2n-1 sin repetición
    @Test
    public void accordingGLShould08SolveUsesAllHeights()
    {
        int n = 4;
        String r = tc.solve(n, 9);
        assertNotEquals("impossible", r);
        String[] parts = r.split(" ");
        assertEquals(n, parts.length);
        boolean[] seen = new boolean[2 * n + 1];
        for (String p : parts) {
            int val = Integer.parseInt(p);
            assertTrue("valor invalido: " + val, val >= 1 && val <= 2 * n - 1 && val % 2 == 1);
            assertFalse("valor repetido: " + val, seen[val]);
            seen[val] = true;
        }
    }

    // solve acepta long para h
    @Test
    public void accordingGLShould12SolveLongH()
    {
        assertEquals("1", tc.solve(1, 1L));
        assertTrue(tc.ok());
    }

    //  solve: casos imposibles 

    @Test
    public void accordingGLShouldNot13SolveBelowMin()
    {
        assertEquals("impossible", tc.solve(4, 5));
        assertFalse(tc.ok());
    }

    @Test
    public void accordingGLShouldNot14SolveAboveMax()
    {
        assertEquals("impossible", tc.solve(4, 13));
        assertFalse(tc.ok());
    }

    @Test
    public void accordingGLShouldNot15SolveEvenHeight()
    {
        assertEquals("impossible", tc.solve(4, 8));
        assertEquals("impossible", tc.solve(4, 10));
        assertFalse(tc.ok());
    }

    @Test
    public void accordingGLShouldNot16SolveZeroHeight()
    {
        assertEquals("impossible", tc.solve(4, 0));
        assertFalse(tc.ok());
    }

    @Test
    public void accordingGLShouldNot17SolveNegativeHeight()
    {
        assertEquals("impossible", tc.solve(3, -1));
        assertFalse(tc.ok());
    }

    // simulate 

    @Test
    public void accordingGLShould18SimulateValidCase()
    {
        tc.simulate(4, 9);
        assertTrue(tc.ok());
    }

    @Test
    public void accordingGLShouldNot19SimulateImpossible()
    {
        tc.simulate(4, 6);
        assertFalse(tc.ok());
    }

    // auxiliar 

    /**
     * Calcula la altura total de una disposición expresada como alturas en cm.
     * Ej: "7 3 5 1" → 7 + max(0,3-7) + max(0,5-3) + max(0,1-5) = 7+0+2+0 = 9
     */
    private int computeHeight(String solution)
    {
        if (solution == null || solution.equals("impossible")) return -1;
        String[] parts = solution.split(" ");
        int[] h = new int[parts.length];
        for (int i = 0; i < parts.length; i++) h[i] = Integer.parseInt(parts[i]);
        int total = h[0];
        for (int i = 1; i < h.length; i++) total += Math.max(0, h[i] - h[i - 1]);
        return total;
    }
}
