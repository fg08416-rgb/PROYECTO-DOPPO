package Tower;

import javax.swing.JOptionPane;
import Shapes.Canvas;

/**
 * Clase Tower - Simulador visual de torre de tazas apiladas.
 *
 * CICLO 4: Refactorización y extensión con tipos de taza y tapa.
 *   - Tazas: NormalCup, OpenerCup, HierarchicalCup
 *   - Tapas: NormalLid, FearfulLid, CrazyLid, TimedLid (nuevo)
 *   - pushCup(type, i) y pushLid(type, i, color) con tipo
 *
 * CICLO 2: Tower(cups), swap, cover, swapToReduce, exit
 * CICLO 1: pushCup, popCup, peekCup, removeCup, pushLid, popLid,
 *          removeLid, orderTower, reverseTower, height, lidedCups,
 *          stackingItems, makeVisible, ok
 *
 * Identificación de objetos: {"cup","4"} o {"lid","4"}
 * Proyecto: stackingItems - Ciclo 4
 */
public class Tower
{
    private static final int GROUND_Y = 280;
    private static final int CENTER_X = 120;

    private int     towerWidth;
    private int     maxHeight;
    private Cup[]   cups;
    private Lid[]   lids;
    private int     size;
    private boolean makeVisible;
    private boolean ok;

    // ─── Constructores ───────────────────────────────────────────

    /**
     * Crea la torre con capacidad y altura máxima definidas.
     * @param width     ancho de la torre
     * @param maxHeight altura máxima permitida
     */
    public Tower(int width, int maxHeight)
    {
        this.towerWidth  = width;
        this.maxHeight   = maxHeight;
        this.cups        = new Cup[100];
        this.lids        = new Lid[100];
        this.size        = 0;
        this.makeVisible = true;
        this.ok          = false;
    }

    /**
     * Crea la torre automáticamente con n tazas normales de mayor (base) a menor (tope).
     * Ej: Tower(4) crea tazas 4,3,2,1 con alturas 7,5,3,1 cm.
     * @param cups número de tazas a crear
     */
    public Tower(int cups)
    {
        this.towerWidth  = cups * 8;
        this.maxHeight   = cups * cups * 2;
        this.cups        = new Cup[100];
        this.lids        = new Lid[100];
        this.size        = 0;
        this.makeVisible = true;
        this.ok          = false;
        for (int i = cups; i >= 1; i--) {
            this.cups[size] = new NormalCup(i);
            this.lids[size] = null;
            size++;
        }
        redraw();
        ok = true;
    }

    // ═══════════════════════════════════════════════════════════════
    //  CICLO 4 — MÉTODOS CON TIPO
    // ═══════════════════════════════════════════════════════════════

    /**
     * Agrega una taza de un tipo específico al tope de la torre.
     * Tipos: "normal", "opener", "hierarchical"
     *
     * @param type tipo de taza
     * @param i    número de la taza
     */
    public void pushCup(String type, int i)
    {
        if (!canFit(i)) {
            ok = false;
            if (makeVisible)
                JOptionPane.showMessageDialog(null,
                    "La taza " + i + " no cabe. Altura max: " + maxHeight + " cm.");
            return;
        }

        Cup cup = createCup(type, i);
        if (cup == null) { ok = false; return; }

        // Comportamiento especial antes de insertar
        cup.onPush(this);

        if (type.equals("hierarchical")) {
            insertHierarchical((HierarchicalCup) cup);
        } else {
            cups[size] = cup;
            lids[size] = null;
            size++;
        }

        if (makeVisible) redraw();
        ok = true;
    }

    /**
     * Agrega una tapa de un tipo específico sobre la taza indicada.
     * Tipos: "normal", "fearful", "crazy", "timed"
     *
     * @param type      tipo de tapa
     * @param cupNumber número de la taza a tapar
     * @param color     color de la tapa
     */
    public void pushLid(String type, int cupNumber, String color)
    {
        int idx = findCup(cupNumber);
        if (idx == -1) { ok = false; return; }

        Lid lid = createLid(type, color, cupNumber);
        if (lid == null) { ok = false; return; }

        if (!lid.canPush(this, cupNumber)) {
            ok = false;
            if (makeVisible)
                JOptionPane.showMessageDialog(null,
                    "La tapa no puede entrar: condición no cumplida.");
            return;
        }

        // CrazyLid: se ubica en la base (posición 0)
        if (lid instanceof CrazyLid) {
            if (lids[0] != null) lids[0].undraw();
            lids[0] = lid;
        } else {
            if (lids[idx] != null) lids[idx].undraw();
            lids[idx] = lid;
        }

        // Notificar operación a todas las TimedLids
        notifyOperation(cupNumber);

        if (makeVisible) redraw();
        ok = true;
    }

    // ─── Métodos auxiliares para los tipos especiales ────────────

    /**
     * Elimina la tapa del tope de la torre si existe.
     * Usado por OpenerCup al entrar.
     */
    public void removeTopLidIfPresent()
    {
        if (size > 0 && lids[size - 1] != null) {
            lids[size - 1].undraw();
            lids[size - 1] = null;
        }
    }

    /**
     * Determina si una HierarchicalCup llega al fondo al ser insertada.
     * @param cup la taza hierarchical a insertar
     * @return true si logra llegar al fondo
     */
    public boolean hierarchicalReachedBottom(HierarchicalCup cup)
    {
        // Contar cuántas tazas son más pequeñas (número menor)
        int smallerCount = 0;
        for (int i = 0; i < size; i++) {
            if (cups[i].getNumber() < cup.getNumber()) smallerCount++;
        }
        // Llega al fondo si todas las tazas actuales son menores
        return smallerCount == size;
    }

    /**
     * Elimina una tapa expirada (TimedLid) de la taza indicada.
     * Llamado por TimedLid cuando su contador llega al límite.
     * @param cupNumber número de la taza cuya tapa expiró
     */
    public void removeExpiredLid(int cupNumber)
    {
        int idx = findCup(cupNumber);
        if (idx != -1 && lids[idx] != null) {
            lids[idx].undraw();
            lids[idx] = null;
            if (makeVisible) redraw();
        }
    }

    /**
     * Verifica si una taza con el número dado está en la torre.
     * Usado por FearfulLid para verificar su taza compañera.
     * @param cupNumber número de la taza a buscar
     * @return true si está en la torre
     */
    public boolean containsCup(int cupNumber)
    {
        return findCup(cupNumber) != -1;
    }

    
    //  CICLO 2 — MÉTODOS
    

    /**
     * Intercambia la posición de dos objetos en la torre.
     * @param o1 objeto 1: {"cup","4"} o {"lid","4"}
     * @param o2 objeto 2: {"cup","3"} o {"lid","3"}
     */
    public void swap(String[] o1, String[] o2)
    {
        if (o1 == null || o2 == null || o1.length < 2 || o2.length < 2) {
            ok = false; return;
        }
        String type1 = o1[0]; int num1 = Integer.parseInt(o1[1]);
        String type2 = o2[0]; int num2 = Integer.parseInt(o2[1]);

        int idx1 = findCup(num1);
        int idx2 = findCup(num2);
        if (idx1 == -1 || idx2 == -1) { ok = false; return; }

        if (type1.equals("cup") && type2.equals("cup")) {
            Cup tc = cups[idx1]; cups[idx1] = cups[idx2]; cups[idx2] = tc;
            Lid tl = lids[idx1]; lids[idx1] = lids[idx2]; lids[idx2] = tl;
        } else if (type1.equals("lid") && type2.equals("lid")) {
            Lid tl = lids[idx1]; lids[idx1] = lids[idx2]; lids[idx2] = tl;
        } else {
            int idxCup = type1.equals("cup") ? idx1 : idx2;
            int idxLid = type1.equals("lid") ? idx1 : idx2;
            Lid tl = lids[idxCup]; lids[idxCup] = lids[idxLid]; lids[idxLid] = tl;
        }

        if (makeVisible) redraw();
        ok = true;
    }

    /**
     * Cubre todas las tazas sin tapa con tapas normales del mismo color.
     */
    public void cover()
    {
        for (int i = 0; i < size; i++) {
            if (lids[i] == null) {
                lids[i] = new NormalLid(cups[i].getColor());
            }
        }
        if (makeVisible) redraw();
        ok = true;
    }

    /**
     * Retorna los intercambios que reducirían la altura de la torre.
     * @return matriz con [tipo1, num1, tipo2, num2, reduccion]
     */
    public String[][] swapToReduce()
    {
        if (size < 2) { ok = false; return new String[0][5]; }

        int currentHeight = height();
        int count = 0;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++)
                if (currentHeight - simulateSwapHeight(i, j) > 0) count++;

        String[][] result = new String[count][5];
        int r = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int reduction = currentHeight - simulateSwapHeight(i, j);
                if (reduction > 0) {
                    result[r][0] = "cup";
                    result[r][1] = String.valueOf(cups[i].getNumber());
                    result[r][2] = "cup";
                    result[r][3] = String.valueOf(cups[j].getNumber());
                    result[r][4] = String.valueOf(reduction);
                    r++;
                }
            }
        }
        ok = true;
        return result;
    }

    
    //  CICLO 1 — MÉTODOS ORIGINALES
    

    /** Agrega una taza normal al tope de la torre. */
    public void pushCup(int cup)
    {
        pushCup("normal", cup);
    }

    /** Quita y retorna el número de la taza del tope. */
    public int popCup()
    {
        if (size == 0) { ok = false; return -1; }

        Cup topCup = cups[size - 1];
        if (!topCup.onPop(this)) {
            ok = false;
            if (makeVisible)
                JOptionPane.showMessageDialog(null,
                    "La taza " + topCup.getNumber() + " no puede quitarse.");
            return -1;
        }

        size--;
        cups[size].undraw();
        if (lids[size] != null) lids[size].undraw();
        int removed = cups[size].getNumber();
        cups[size] = null;
        lids[size] = null;
        if (makeVisible) redraw();
        ok = true;
        return removed;
    }

    /** Retorna el número de la taza del tope sin quitarla. */
    public int peekCup()
    {
        if (size == 0) { ok = false; return -1; }
        ok = true;
        return cups[size - 1].getNumber();
    }

    /** Quita la taza con el número dado de cualquier posición. */
    public void removeCup(int cup)
    {
        int idx = findCup(cup);
        if (idx == -1) { ok = false; return; }

        if (!cups[idx].onPop(this)) {
            ok = false;
            if (makeVisible)
                JOptionPane.showMessageDialog(null,
                    "La taza " + cup + " no puede quitarse.");
            return;
        }

        cups[idx].undraw();
        if (lids[idx] != null) lids[idx].undraw();
        for (int i = idx; i < size - 1; i++) {
            cups[i] = cups[i + 1];
            lids[i] = lids[i + 1];
        }
        size--;
        cups[size] = null;
        lids[size] = null;
        if (makeVisible) redraw();
        ok = true;
    }

    /** Agrega una tapa normal sobre la taza indicada. */
    public void pushLid(int cup, String color)
    {
        pushLid("normal", cup, color);
    }

    /** Quita y retorna el color de la tapa de la taza indicada. */
    public String popLid(int cup)
    {
        int idx = findCup(cup);
        if (idx == -1 || lids[idx] == null) { ok = false; return ""; }

        if (!lids[idx].canPop(this, cup)) {
            ok = false;
            if (makeVisible)
                JOptionPane.showMessageDialog(null,
                    "La tapa no puede salir: condición no cumplida.");
            return "";
        }

        String color = lids[idx].getColor();
        lids[idx].undraw();
        lids[idx] = null;
        notifyOperation(cup);
        if (makeVisible) redraw();
        ok = true;
        return color;
    }

    /** Quita la tapa de la taza indicada sin retornarla. */
    public void removeLid(int cup)
    {
        int idx = findCup(cup);
        if (idx == -1 || lids[idx] == null) { ok = false; return; }
        lids[idx].undraw();
        lids[idx] = null;
        if (makeVisible) redraw();
        ok = true;
    }

    /** Ordena las tazas de menor a mayor (base a tope). */
    public void orderTower()
    {
        eraseAll();
        for (int i = 0; i < size - 1; i++)
            for (int j = 0; j < size - 1 - i; j++)
                if (cups[j].getNumber() > cups[j + 1].getNumber()) {
                    Cup tc = cups[j]; cups[j] = cups[j + 1]; cups[j + 1] = tc;
                    Lid tl = lids[j]; lids[j] = lids[j + 1]; lids[j + 1] = tl;
                }
        if (makeVisible) redraw();
        ok = true;
    }

    /** Invierte el orden de las tazas en la torre. */
    public void reverseTower()
    {
        eraseAll();
        for (int i = 0; i < size / 2; i++) {
            Cup tc = cups[i]; cups[i] = cups[size - 1 - i]; cups[size - 1 - i] = tc;
            Lid tl = lids[i]; lids[i] = lids[size - 1 - i]; lids[size - 1 - i] = tl;
        }
        if (makeVisible) redraw();
        ok = true;
    }

    /** Retorna la altura actual de la torre en cm. */
    public int height()
    {
        if (size == 0) return 0;
        int total = cups[0].getHeight();
        for (int i = 1; i < size; i++)
            total += cups[i].getHeight() - 1;
        if (lids[size - 1] != null) total += 1;
        return total;
    }

    /** Retorna los números de las tazas que tienen tapa, ordenados de mayor a menor. */
    public int[] lidedCups()
    {
        int count = 0;
        for (int i = 0; i < size; i++) if (lids[i] != null) count++;
        int[] result = new int[count];
        int j = 0;
        for (int i = 0; i < size; i++) if (lids[i] != null) result[j++] = cups[i].getNumber();
        for (int i = 0; i < result.length - 1; i++)
            for (int k = 0; k < result.length - 1 - i; k++)
                if (result[k] < result[k + 1]) {
                    int t = result[k]; result[k] = result[k + 1]; result[k + 1] = t;
                }
        ok = true;
        return result;
    }

    /**
     * Retorna los elementos de la torre como matriz [número, color_tapa].
     * @return String[size][2] donde [i][0]=número taza, [i][1]=color tapa o ""
     */
    public String[][] stackingItems()
    {
        String[][] copy = new String[size][2];
        for (int i = 0; i < size; i++) {
            copy[i][0] = String.valueOf(cups[i].getNumber());
            copy[i][1] = (lids[i] != null) ? lids[i].getColor() : "";
        }
        ok = true;
        return copy;
    }

    public void makeVisible(boolean visible)
    {
        if (!visible) {
            JOptionPane.showMessageDialog(null,
                "El simulador no se puede ocultar en esta version.");
            ok = false;
            return;
        }
        this.makeVisible = visible;
        redraw();
        ok = true;
    }

    public void setVisibleForTest(boolean visible)
    {
        this.makeVisible = visible;
        ok = true;
    }

    public void exit()
    {
        eraseAll();
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(false);
        ok = true;
    }

    public void makeInvisible()
    {
        eraseAll();
        this.makeVisible = false;
        ok = true;
    }

    public boolean ok() { return ok; }

    
    //  MÉTODOS PRIVADOS
    

    /** Inserta una HierarchicalCup en la posición correcta (debajo de tazas menores). */
    private void insertHierarchical(HierarchicalCup cup)
    {
        // Encontrar la posición: debajo de todas las tazas con número menor
        int insertPos = 0;
        for (int i = 0; i < size; i++) {
            if (cups[i].getNumber() < cup.getNumber()) insertPos = i + 1;
        }

        // Desplazar hacia arriba
        for (int i = size; i > insertPos; i--) {
            cups[i] = cups[i - 1];
            lids[i] = lids[i - 1];
        }
        cups[insertPos] = cup;
        lids[insertPos] = null;
        size++;

        // Verificar si llegó al fondo
        cup.setReachedBottom(insertPos == 0);
    }

    /** Notifica a todas las TimedLids que ocurrió una operación. */
    private void notifyOperation(int triggerCupNumber)
    {
        for (int i = 0; i < size; i++) {
            if (lids[i] instanceof TimedLid) {
                lids[i].onOperation(this, cups[i].getNumber());
            }
        }
    }

    /** Fabrica una taza según el tipo. */
    private Cup createCup(String type, int number)
    {
        if (type == null) return new NormalCup(number);
        switch (type.toLowerCase()) {
            case "opener":       return new OpenerCup(number);
            case "hierarchical": return new HierarchicalCup(number);
            default:             return new NormalCup(number);
        }
    }

    /** Fabrica una tapa según el tipo. */
    private Lid createLid(String type, String color, int cupNumber)
    {
        if (type == null) return new NormalLid(color);
        switch (type.toLowerCase()) {
            case "fearful": return new FearfulLid(color, cupNumber);
            case "crazy":   return new CrazyLid(color);
            case "timed":   return new TimedLid(color, 3); // 3 operaciones por defecto
            default:        return new NormalLid(color);
        }
    }

    private void redraw()
    {
        eraseAll();
        int currentBottomY = GROUND_Y;
        for (int i = 0; i < size; i++) {
            cups[i].draw(CENTER_X, currentBottomY);
            if (lids[i] != null)
                lids[i].draw(cups[i], CENTER_X, currentBottomY);
            currentBottomY -= (cups[i].getHeight() - 1) * Cup.SCALE;
        }
    }

    private void eraseAll()
    {
        for (int i = 0; i < size; i++) {
            if (cups[i] != null) cups[i].undraw();
            if (lids[i] != null) lids[i].undraw();
        }
    }

    private int simulateSwapHeight(int i, int j)
    {
        int[] heights    = new int[size];
        boolean[] hasLid = new boolean[size];
        for (int k = 0; k < size; k++) {
            heights[k] = cups[k].getHeight();
            hasLid[k]  = (lids[k] != null);
        }
        int tmpH = heights[i]; heights[i] = heights[j]; heights[j] = tmpH;
        boolean tmpL = hasLid[i]; hasLid[i] = hasLid[j]; hasLid[j] = tmpL;

        int total = heights[0];
        for (int k = 1; k < size; k++) total += heights[k] - 1;
        if (hasLid[size - 1]) total += 1;
        return total;
    }

    private boolean canFit(int cup)
    {
        int h = 2 * cup - 1;
        int projected = (size == 0) ? h : height() + h - 1;
        return projected <= maxHeight;
    }

    private int findCup(int cup)
    {
        for (int i = 0; i < size; i++)
            if (cups[i].getNumber() == cup) return i;
        return -1;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Tower ===\n")
          .append("Altura max: ").append(maxHeight).append(" cm | ")
          .append("Altura actual: ").append(height()).append(" cm\n")
          .append("Tazas (base -> tope):\n");
        for (int i = 0; i < size; i++) {
            sb.append("  ").append(i + 1).append(". ").append(cups[i]);
            if (lids[i] != null) sb.append(" | ").append(lids[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}
