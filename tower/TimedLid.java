package tower;

/**
 * Clase TimedLid - Tapa de tipo temporizada (TIPO NUEVO - Ciclo 4).
 * Comportamiento especial:
 * - Se coloca normalmente sobre su taza.
 * - Lleva un contador de operaciones realizadas en la torre desde su colocación.
 * - Cuando el contador llega a maxOperations, la tapa se autoelimine de la torre.
 * Se distingue visualmente con color rojo intermitente (representado como "orange").
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public class TimedLid extends Lid
{
    private int maxOperations;
    private int operationCount;

    /**
     * Crea una tapa temporizada.
     * @param color         color de la tapa
     * @param maxOperations número de operaciones tras las cuales desaparece
     */
    public TimedLid(String color, int maxOperations)
    {
        super(color);
        this.maxOperations  = maxOperations;
        this.operationCount = 0;
        this.color = "orange"; 
    }

    @Override
    public String getType() { return "timed"; }

    
    @Override
    public boolean canPush(Tower tower, int cupNumber) { return true; }

    
    @Override
    public boolean canPop(Tower tower, int cupNumber) { return true; }

    /**
     * Incrementa el contador de operaciones.
     * Cuando llega a maxOperations, indica a Tower que debe eliminarse.
     * Tower debe verificar isExpired() después de cada operación.
     * @param tower     la torre
     * @param cupNumber número de la taza compañera
     */
    @Override
    public void onOperation(Tower tower, int cupNumber)
    {
        operationCount++;
        if (isExpired()) {
            tower.removeExpiredLid(cupNumber);
        }
    }

    /**
     * Indica si la tapa ya agotó su tiempo de vida.
     * @return true si operationCount >= maxOperations
     */
    public boolean isExpired()
    {
        return operationCount >= maxOperations;
    }

    /**
     * Retorna el número de operaciones restantes antes de desaparecer.
     * @return operaciones restantes
     */
    public int getRemainingOperations()
    {
        return Math.max(0, maxOperations - operationCount);
    }

    /**
     * Retorna el total de operaciones configuradas.
     * @return maxOperations
     */
    public int getMaxOperations() { return maxOperations; }

    @Override
    public String toString()
    {
        return "timedLid(color=" + color +
               ", ops=" + operationCount + "/" + maxOperations + ")";
    }
}
