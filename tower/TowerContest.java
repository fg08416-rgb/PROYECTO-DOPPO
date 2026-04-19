package tower;

import java.util.*;

/**
 * Clase TowerContest - Resuelve el problema de la torre de tazas.
 * Dado n tazas y una altura objetivo h, encuentra una permutación
 * de las tazas 1..n que produzca exactamente esa altura.
 *
 * Proyecto: stackingItems
 */
public class TowerContest
{
    private boolean ok;

    public TowerContest()
    {
        this.ok = false;
    }

    public boolean ok() { return ok; }

    //  solve
    /**
     * Encuentra una permutación de las tazas 1..n cuya altura apilada
     * sea exactamente h.
     *
     * Altura de una disposición: h[0] + sum(max(0, h[i]-h[i-1]) para i>0)
     * donde h[i] = altura en cm de la taza en posición i.
     * Retorna las alturas en cm separadas por espacios. Ej: "7 3 5 1"
     *
     * @param n número de tazas disponibles (tazas 1..n, alturas 1,3,...,2n-1)
     * @param h altura objetivo en cm
     * @return alturas en cm en el orden encontrado, o "impossible"
     */
    public String solve(int n, long h)
    {
        if (n <= 0 || h <= 0) { ok = false; return "impossible"; }

        
        if (h % 2 == 0) { ok = false; return "impossible"; }

                long[] heights = new long[n];
        for (int i = 0; i < n; i++) {
            heights[i] = 2L * (i + 1) - 1;
        }

       
        long minH = heights[0];
        for (int i = 1; i < n; i++) {
            minH += Math.max(0, heights[i] - heights[i - 1]);
        }

        
        long maxH = 0;
        int left = 0, right = n - 1;
        long prev = -1;
        boolean takeBig = true;
        while (left <= right) {
            long next = takeBig ? heights[right--] : heights[left++];
            if (prev == -1) {
                maxH = next;
            } else if (next > prev) {
                maxH += next - prev;
            }
            prev = next;
            takeBig = !takeBig;
        }

        if (h < minH || h > maxH) { ok = false; return "impossible"; }

        
        List<Long> cupOrder = buildOrder(n, heights, h);

        if (cupOrder.isEmpty()) { ok = false; return "impossible"; }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cupOrder.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(cupOrder.get(i));
        }
        ok = true;
        return sb.toString();
    }

    /**
     * Construye la secuencia de alturas en cm que produce exactamente target.
     *
     * Estrategia:
     * 1. Colocar primero la taza más grande (2n-1). Altura = 2n-1.
     * 2. Calcular needed = target - (2n-1): cuánto más hay que subir.
     * 3. Si needed == 0: agregar el resto en orden decreciente (no suben).
     * 4. Si needed > 0: buscar p (pequeña) y x = p + needed en las restantes.
     *    Secuencia: grande, p, x, resto decreciente. p no sube (p < grande),
     *    x sube exactamente (x - p = needed), resto decreciente no sube.
     */
    private List<Long> buildOrder(int n, long[] heights, long target)
    {
        long big = heights[n - 1];  
        long needed = target - big;

        List<Long> remaining = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) remaining.add(heights[i]);

        List<Long> order = new ArrayList<>();
        order.add(big);

        if (needed == 0) {
            
            for (int i = remaining.size() - 1; i >= 0; i--) {
                order.add(remaining.get(i));
            }
            return order;
        }

        
        Set<Long> remSet = new HashSet<>(remaining);
        for (long p : remaining) {
            long x = p + needed;
            if (x != p && remSet.contains(x)) {
                List<Long> rest = new ArrayList<>();
                for (long v : remaining) {
                    if (v != p && v != x) rest.add(v);
                }
                Collections.sort(rest, Collections.reverseOrder());

                List<Long> candidate = new ArrayList<>();
                candidate.add(big);
                candidate.add(p);
                candidate.add(x);
                candidate.addAll(rest);

                if (computeHeight(candidate) == target) {
                    return candidate;
                }
            }
        }

        return Collections.emptyList();
    }

    /** Calcula la altura acumulada de una secuencia de alturas en cm. */
    private long computeHeight(List<Long> seq)
    {
        long total = seq.get(0);
        for (int i = 1; i < seq.size(); i++) {
            total += Math.max(0, seq.get(i) - seq.get(i - 1));
        }
        return total;
    }

    //  simulate 
    /**
     * Resuelve y muestra visualmente la torre resultante.
     * Si es imposible, solo actualiza ok.
     *
     * @param n número de tazas
     * @param h altura objetivo
     */
    public void simulate(int n, int h)
    {
        String result = solve(n, h);
        if (result.equals("impossible")) { ok = false; return; }

        Tower tower = new Tower(n);
        tower.setVisibleForTest(false);

        String[] parts = result.split(" ");
        int[] order = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            order[i] = Integer.parseInt(parts[i]);
        }

        ok = true;
    }
}
